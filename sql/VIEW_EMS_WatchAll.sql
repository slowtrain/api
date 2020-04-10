-----------------------------------------------------
-- WatchAll EMS 정보를 가져와서 DISCI 에 입력한다.
-----------------------------------------------------
-- EMS 에서 등록하는 정보는 5가지 종류이다.
-- 1. obj_managetype_id = SMS. SERVER
-- 2. obj_managetype_id = NMS. Network 장비. NETDEVICE
-- 3. obj_managetype_id = NMS. Network 장비. NETDEVICE
-----------------------------------------------------
-- [참고 정보]
-- obj 테이블 -> 등록된 모든 장비들이 기록되어 있다.
--			-> obj_managetype_id : APM, DBMS, NMS, SMS 로 구분되어서 제품이 설치된다.
-----------------------------------------------------
-- 1. SERVER 정보 : SMS
--   장비명 : obj.obj_name
--   장비종류 : obj.objtype_id
-----------------------------------------------------
select o.obj_id									/* WatchAll 장비 Object ID */
	, o.obj_managetype_id						/* SMS (서버 관리) */
	, o.obj_name								/* 장비명 */
	, o.parent_obj_id							/* 상위 장비명. NULL */
	, o.obj_define_id							/* Object 에 대한 정의 ID. SERVER */
	, o.objtype_id								/* 장비 종류에 대한 내부 코드. WINSVR, LINUX, IBMAIX 등 */
	, ot.objtype_name							/* 장비 종류. Windows, Linux, IBM-AIX 등 */
	, oc.obj_company_name						/* 제조사명 */
	, op.objtype_id as obj_product_typeid		/* 모델에 대한 Type ID. 일반적으로 o.objtype_id 와 동일. */
	, o.obj_product_id							/* 모델. 일반적으로 생각하는 모델이 아니라 Windows Server, Linux, AIX 등  */
	, o.ipaddress_applytype						/* IP 주소 적용 유형. IPv4, IPv6 */
	, o.ipaddress_ipv4							/* IP v4 주소. IP 주소 */
	, o.ipaddress_ipv6							/* IP v6 주소 */
	, date_add(o.regist_datetime, interval -9 hour)	as regist_datetime					/* UTC 등록일 */
	/*, o.regist_datetime*/						/* LOCAL TIME 등록일 */
	, obj_management_apply_flag  				/* 이게 1이면 관리, 0 이면 관리 안함 */
	, o.serial_no								/* Serial Number. 대부분 NULL */
	, (select ci_id from obj_ci where obj_id = o.obj_id limit 1) as itsm_ci_id 		/*  CI 와 Multi 로 연결될 수 있어서 (이럴 경우는 없지만 ITSM 프로그램상 그럴수 있다.) 최초 한개만 할당한다. */
	, s.server_hostname hostname				/* 서버 hostname */
	, s.os_name os_name							/* 서버 OS 명 */
	, s.os_archtecture os_architecture			/* 서버 OS 아키텍처 */
	, s.os_servicepack_ver os_servicepack_ver	/* 서버 OS 서비스 팩 */
	, s.domain as os_domain						/* 서버 OS Domain 명 */
from obj o
join server s on s.obj_id = o.obj_id
left join objtype ot on ot.objtype_id = o.objtype_id
left join obj_company oc on oc.obj_company_id = o.obj_company_id
left join obj_product op on op.obj_company_id = oc.obj_company_id and op.obj_product_id = o.obj_product_id
where o.obj_managetype_id in ('SMS')
/*order by o.obj_name*/
-----------------------------------------------------
-- 2. Network 장비 정보 : NMS
--   장비명 : obj.obj_name
--   장비종류 : obj.objtype_id
-- obj_define_id 이 NETDEVICE, SERVER 이면 장비
-- obj_define_id 이 NETINTERFACE 이면 장비의 네트워크 인터페이스
-----------------------------------------------------
select o.obj_id									/* WatchAll 장비 Object ID */
	, o.obj_managetype_id						/* NMS (네트워크 관리) */
	, o.obj_name								/* 장비명 */
	, substring(netdev.netdevice_desc, 1, 50) as obj_desc	/* 장비설명 */
	, o.parent_obj_id							/* 상위 장비명. 대부분 NULL */
	, o.obj_define_id							/* Object 에 대한 정의 ID. 네트워크 장비는 SERVER, NETDEVICE */
	, o.objtype_id								/* 장비 종류에 대한 내부 코드 */
	, ot.objtype_name							/* 장비 종류 */
	, oc.obj_company_name						/* 제조사명 */
	, op.objtype_id as obj_product_typeid		/* 모델에 대한 Type ID. 일반적으로 o.objtype_id 와 동일하나 다를 수 있다. */
	, o.obj_product_id							/* 모델  */
	, o.ipaddress_applytype						/* IP 주소 적용 유형. IPv4, IPv6 */
	, o.ipaddress_ipv4							/* IP v4 주소. IP 주소 */
	, o.ipaddress_ipv6							/* IP v6 주소 */
	, date_add(o.regist_datetime, interval -9 hour)	as regist_datetime					/* UTC 등록일 */
	/*, o.regist_datetime*/						/* LOCAL TIME 등록일 */
	, obj_management_apply_flag  				/* 이게 1이면 관리, 0 이면 관리 안함 */
	, o.serial_no								/* Serial Number */
	, (select ci_id from obj_ci where obj_id = o.obj_id limit 1) as itsm_ci_id 		/*  CI 와 Multi 로 연결될 수 있어서 (이럴 경우는 없지만 ITSM 프로그램상 그럴수 있다.) 최초 한개만 할당한다. */
	, (select count(*) from netinterface where netdevice_id = netdev.netdevice_id) as interface_num		/* 장비에 달린 Interface Card 수 */
from obj o
join netdevice netdev on netdev.obj_id = o.obj_id
left join objtype ot on ot.objtype_id = o.objtype_id
left join obj_company oc on oc.obj_company_id = o.obj_company_id
left join obj_product op on op.obj_company_id = oc.obj_company_id and op.obj_product_id = o.obj_product_id
where o.obj_managetype_id in ('NMS')
and o.obj_define_id in ('SERVER', 'NETDEVICE')
/*order by o.obj_name*/

-----------------------------------------------------
-- 3. Network Interface 정보 : NMS
--   인터페이스명 : obj.obj_name
-- obj_define_id 이 NETDEVICE, SERVER 이면 장비
-- obj_define_id 이 NETINTERFACE 이면 장비의 네트워크 인터페이스
-----------------------------------------------------
select o.obj_id									/* WatchAll 장비 Object ID */
	, o.obj_managetype_id						/* NMS (네트워크 관리) */
	, o.obj_name								/* 인터페이스명. 예 : ### BonGwan-CAD ### netif.netinterface_alias 이름을 기본으로 등록하고, netif.netinterface_alias 값이 없으면 netif.netinterface_name 을 등록 */
	, netif.netinterface_devicename				/* 인터페이스명. 예 : GigabitEthernet3/1. 이걸 인터페이스 이름으로 하는게 좋을듯 */
	, netif.netinterface_name					/* 인터페이스명. 예 : ethernet3/1 */
	, netif.netinterface_alias					/* 인터페이스 Alias 명. 예 : ### BonGwan-CAD ### */
	, netif.netinterface_ifindex				/* 인터페이스 Index 수. 예 : 129 */
	/*, netif.netinterface_speed*/					/* 인터페이스 스피드. Hz 단위 */
	, netif.netinterface_speed	/ 1000000 as netinterface_speed				/* 인터페이스 스피드. MHz 단위 */
	, netif.netinterface_macaddress				/* 인터페이스 MAC Address */
	/*, netif.netinterface_status_admin */ 		/* 값에 대한 코드 테이블이 없어서 없어서 데이터 가져오기에서 SKIP 한다. */
	/*, netif.netinterface_status_oper */ 		/* 값에 대한 코드 테이블이 없어서 없어서 데이터 가져오기에서 SKIP 한다. */
	/*, netif.netinterface_type */ 				/* 값에 대한 코드 테이블이 없어서 없어서 데이터 가져오기에서 SKIP 한다. */
	, netif.netinterface_duplex					/* 인터페이스 Duplex 여부. FULL, HALF, null */
	, o.parent_obj_id							/* 상위 장비명. 상위 네트워크 장비 */
	, o.obj_define_id							/* Object 에 대한 정의 ID. 네트워크 인터페이스는 NETINTERFACE */
	, o.objtype_id								/* 장비 종류에 대한 내부 코드. NETINTERFACE */
	, ot.objtype_name							/* 장비 종류명. 네트워크 인터페이스 */
	, oc.obj_company_name						/* 제조사명. NULL */
	, op.objtype_id as obj_product_typeid		/* 모델에 대한 Type ID. NULL */
	, o.obj_product_id							/* 모델. NULL */
	, o.ipaddress_applytype						/* IP 주소 적용 유형. NULL */
	, o.ipaddress_ipv4							/* IP v4 주소. IP 주소. NULL */
	, o.ipaddress_ipv6							/* IP v6 주소. NULL */
	, date_add(o.regist_datetime, interval -9 hour)	as regist_datetime					/* UTC 등록일 */
	/*, o.regist_datetime*/						/* LOCAL TIME 등록일 */	
	, obj_management_apply_flag  				/* 이게 1이면 관리, 0 이면 관리 안함 */
from obj o
join netinterface netif on netif.obj_id = o.obj_id
left join objtype ot on ot.objtype_id = o.objtype_id
left join obj_company oc on oc.obj_company_id = o.obj_company_id
left join obj_product op on op.obj_company_id = oc.obj_company_id and op.obj_product_id = o.obj_product_id
where o.obj_managetype_id in ('NMS')
and o.obj_define_id in ('NETINTERFACE')
/*order by o.obj_name*/
-----------------------------------------------------
-- 4. WAS 정보 : APM
-- obj.obj_name : 인스턴스명
-- obj.product_id : WAS 버전
-- obj.ipaddress_ipv4 : IP
-----------------------------------------------------
select o.obj_id									/* WatchAll 장비 Object ID */
	, o.obj_managetype_id						/* APM (어플리케이션 통합관리) */
	/*, o.obj_name*/								/* WAS Instance 명 */
	, concat(ot.objtype_name,':',o.obj_name)	as obj_name							/* WAS Instance 명 */
	, o.parent_obj_id							/* 상위 장비명. NULL */
	, o.obj_define_id							/* Object 에 대한 정의 ID. INSTANCE */
	, o.objtype_id								/* WAS 종류에 대한 내부 코드. WAS */
	, ot.objtype_name							/* WAS 종류. WAS */
	, oc.obj_company_name						/* 제조사명 */
	, op.objtype_id as obj_product_typeid		/* WAS 제품명. WEBLOGIC, TOMCAT 등 */
	, o.obj_product_id							/* WAS 버전  */
	, o.ipaddress_applytype						/* IP 주소 적용 유형. IPv4, IPv6 */
	, o.ipaddress_ipv4							/* IP v4 주소. IP 주소 */
	, o.ipaddress_ipv6							/* IP v6 주소 */
	, date_add(o.regist_datetime, interval -9 hour)	as regist_datetime					/* UTC 등록일 */
	/*, o.regist_datetime*/						/* LOCAL TIME 등록일 */	
	, obj_management_apply_flag  				/* 이게 1이면 관리, 0 이면 관리 안함 */
	, (select ci_id from obj_ci where obj_id = o.obj_id limit 1) as itsm_ci_id 		/* CI 와 Multi 로 연결될 수 있어서 최초 한개만 할당한다. */
	, (select name from apm_service where id = (select service_id from apm_instance where obj_id = o.obj_id )) as apm_servicename	
from obj o
left join objtype ot on ot.objtype_id = o.objtype_id
left join obj_company oc on oc.obj_company_id = o.obj_company_id
left join obj_product op on op.obj_company_id = oc.obj_company_id and op.obj_product_id = o.obj_product_id
where o.obj_managetype_id in ('APM')
and o.obj_define_id in ('INSTANCE')
/*order by o.obj_name*/

-----------------------------------------------------
-- 5. DBMS 정보 : DBMS
-- obj.obj_name : DB 명
-- objtype.objtype_name : DB 종류
-- obj.obj_product_id : DB 버전
-- obj.ipaddress_ipv4 : IP 주소
-- dbms.dbms_service : SID
-- dbms.dbms_alias : 서비스명
-- dbms.dbms_port : 포트번호
-----------------------------------------------------
select o.obj_id				/* WatchAll 장비 Object ID */
	, o.obj_managetype_id	/* DBMS (데이터베이스 관리) */
	/*, o.obj_name*/			/* DB 명 */
	, concat(ot.objtype_name,':',o.obj_name)	as obj_name	/* DB 명 */
	, o.parent_obj_id		/* 상위 장비명. SERVER 의 HOST 정보이나, 정보가 부정확함. NULL 인 경우 있음. */
	, o.obj_define_id		/* Object 에 대한 정의 ID. DBMS */
	, o.objtype_id			/* DB 종류에 대한 내부 코드. ORACLE, MSSQL, TIBERO, SYBASE, MYSQL */
	, ot.objtype_name		/* DB 종류. Oracle, MS-SQL, Tibero, Sybase, MySQL */
	, oc.obj_company_name	/* 제조사명 */
	, op.objtype_id as obj_product_typeid	/* 버전에 대한 Type ID. 일반적으로 o.objtype_id 와 동일. */
	, o.obj_product_id		/* DB 버전  */
	, o.ipaddress_applytype	/* IP 주소 적용 유형. IPv4, IPv6 */
	, o.ipaddress_ipv4		/* IP v4 주소. IP 주소 */
	, o.ipaddress_ipv6		/* IP v6 주소 */
	, date_add(o.regist_datetime, interval -9 hour)	as regist_datetime					/* UTC 등록일 */
	/*, o.regist_datetime*/						/* LOCAL TIME 등록일 */	
	, obj_management_apply_flag  /* 이게 1이면 관리, 0 이면 관리 안함 */
	, (select ci_id from obj_ci where obj_id = o.obj_id limit 1) as itsm_ci_id /* CI 와 Multi 로 연결될 수 있어서 최초 한개만 할당한다. */
	, dbms.dbms_port		/* DB 사용 Port */
	, dbms.dbms_service as dbms_sid			/* DB SID */
	, dbms.dbms_alias as dbms_servicename	/* DB 서비스명 */
from obj o
join dbms dbms on dbms.obj_id = o.obj_id
left join objtype ot on ot.objtype_id = o.objtype_id
left join obj_company oc on oc.obj_company_id = o.obj_company_id
left join obj_product op on op.obj_company_id = oc.obj_company_id and op.obj_product_id = o.obj_product_id
where o.obj_managetype_id in ('DBMS')
/*order by o.obj_name*/


------------------------------------
-- 아래는 참고 정보이므로 무시한다. (2019.09.18)
------------------------------------

3. ITSM 연결은
- obj.obj_managetype_id : APM, DBMS, E2E, NMS, SMS 인 것들 중에서
- obj.o.obj_define_id not in ('NETINTERFACE') 인것들에 대해서 매핑한다. 08.20 일 현재 873 EA
- ITSM 이 여러개 연결될수 있다.

----------------------------------------------------------------------------------------------------------------
1. 이벤트가 갈때 ITSM 의 심각도 정보가 간다고 했는데 어떤 정보가 가는지 확인해보아야 한다.
2. ITSM 데이터 업데이트를 신경써야 한다.



5. DBMS 인 경우



