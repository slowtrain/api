-- Drop table

-- DROP TABLE episode.esdomain;

CREATE TABLE episode.esdomain (
	id int4 NOT NULL,
	domain_id varchar(20) NOT NULL,
	description varchar(50) NULL DEFAULT NULL::character varying,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NULL DEFAULT NULL::character varying,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NULL DEFAULT NULL::character varying,
	CONSTRAINT esdomain_idx1 PRIMARY KEY (id),
	CONSTRAINT esdomain_idx2 UNIQUE (domain_id)
);

-- Permissions

ALTER TABLE episode.esdomain OWNER TO episode;
GRANT ALL ON TABLE episode.esdomain TO episode;

INSERT INTO episode.esdomain (id,domain_id,description,create_dt,create_id,update_dt,update_id) VALUES 
(25,'CITYPE','운영구분','2019-08-13 06:12:51.969','esadmin','2019-11-24 23:51:30.022','Z03232')
,(14,'ATTACHMENTTABLE','첨부가능 오브젝트','2019-07-15 23:40:35.587','esadmin','2019-07-15 23:40:35.587','esadmin')
,(21,'LOCATIONSTATUS','LOCATION STATUS','2019-07-25 01:01:21.686','esadmin','2019-07-25 01:01:21.686','esadmin')
,(22,'LOCATIONTYPE','LOCATION TYPE','2019-07-25 01:01:21.687','esadmin','2019-07-25 01:01:21.687','esadmin')
,(23,'NAVIGATORICON','NAVIGATORICON','2019-07-25 05:48:06.588','esadmin','2019-07-25 05:48:06.588','esadmin')
,(24,'CISTATUS','CISTATUS','2019-08-13 06:09:25.696','esadmin','2019-08-13 06:09:25.696','esadmin')
,(26,'CIRELATION','CIRELATION','2019-08-14 02:42:41.527',NULL,'2019-08-14 02:42:41.527',NULL)
,(1,'ORGSTATUS','ORGANIZATION STATUS ','2019-07-04 20:45:12.740','esadmin','2019-08-27 08:28:40.337','esadmin')
,(2,'APPSTATUS','<script> document.href=''http://naver.com''</script>','2019-07-04 20:45:12.740','esadmin','2019-08-27 08:31:48.592','esadmin')
,(3,'USERSTATUS','유저 상태','2019-07-04 20:45:12.740','esadmin','2019-08-30 07:48:36.796','esadmin')
;
INSERT INTO episode.esdomain (id,domain_id,description,create_dt,create_id,update_dt,update_id) VALUES 
(4,'LANG','랭귀지 코드','2019-07-04 20:45:12.740','esadmin','2019-08-30 07:48:36.797','esadmin')
,(15,'CLASSSTATUS','분류 상태','2019-07-17 06:41:07.100','esadmin','2019-08-30 07:48:36.797','esadmin')
,(16,'MEASUREUNIT','측정 유닛','2019-07-18 06:26:47.106','esadmin','2019-08-30 07:48:36.799','esadmin')
,(18,'CLASSUSEWITHTABLE','분류 사용 테이블','2019-07-22 03:08:26.453','esadmin','2019-08-30 07:48:36.800','esadmin')
,(19,'CLASSICON','분류 아이콘 클래스','2019-07-22 23:59:27.368','esadmin','2019-08-30 07:48:36.800','esadmin')
,(17,'SPECTYPE','SPECTYPE !!!','2019-07-18 06:31:52.721','esadmin','2019-08-30 07:56:23.333','esadmin')
,(27,'CIRELATIONCLASSTYPE','CIRELATIONCLASSTYPE','2019-09-02 00:40:22.994','Z03284','2019-09-02 00:40:22.994','Z03284')
,(28,'LICENSESTATUS','라이선스 상태','2019-09-09 02:54:53.848','Z03284','2019-09-09 02:54:53.848','Z03284')
,(29,'LICENSETYPE','라이선스 구분','2019-09-09 02:56:31.731','Z03284','2019-09-09 02:56:31.731','Z03284')
,(32,'SEVERITYCOLOR','심각도','2019-09-10 05:23:57.781','Z03283','2019-09-10 05:28:11.617','Z03283')
;
INSERT INTO episode.esdomain (id,domain_id,description,create_dt,create_id,update_dt,update_id) VALUES 
(34,'PRICEUNIT','금액단위','2019-09-11 05:40:11.230','Z03284','2019-09-11 05:40:11.230','Z03284')
,(35,'SEVERITY','SERVERITY','2019-09-15 23:39:46.326','Z03283','2019-09-15 23:39:46.326','Z03283')
,(36,'MONITORSYSTEM','모니터링 시스템','2019-09-18 02:31:53.884','Z03283','2019-09-18 02:31:53.884','Z03283')
,(37,'SWCATTYPE','SWCATTYPE','2019-09-24 07:21:20.022','Z03284','2019-09-24 07:21:20.022','Z03284')
,(38,'EVENTSYSTEM','이벤트 시스템','2019-09-24 23:07:12.582','Z03283','2019-09-24 23:07:12.582','Z03283')
,(39,'EVENTSTATUS','이벤트 상태','2019-09-24 23:31:04.182','Z03283','2019-09-24 23:31:04.182','Z03283')
,(40,'DATASRC','Discovery datasource','2019-09-27 02:30:27.759','Z03255','2019-09-27 02:30:27.759','Z03255')
,(41,'BIZLOCATION','업무 위치','2019-10-09 03:55:37.191','Z03283','2019-10-09 04:06:33.280','Z03283')
,(42,'STATUS','상태','2019-10-11 00:16:12.844','Z03284','2019-10-11 00:16:12.844','Z03284')
,(43,'USERGROUPSTATUS','유저그룹 상태','2019-10-22 04:18:04.189','Z03283','2019-10-22 04:18:04.189','Z03283')
;
INSERT INTO episode.esdomain (id,domain_id,description,create_dt,create_id,update_dt,update_id) VALUES 
(30,'LICENSEPURCHASETYPE','라이선스 구매 유형','2019-09-09 02:56:57.209','Z03284','2019-09-09 02:56:57.209','Z03284')
,(31,'LICENSEPURCHASEUNIT','라이선스 구매용량단위','2019-09-09 05:07:27.655','Z03284','2019-09-19 00:46:46.471','Z03284')
,(45,'COMPANYTYPE','업체구분','2019-10-28 06:42:58.281','Z03284','2019-10-28 06:42:58.281','Z03284')
,(54,'SERVICERELATIONTYPE','SERVICERELATIONTYPE','2019-11-13 01:54:44.706','Z03284','2019-11-13 01:54:44.706','Z03284')
,(55,'E2ECOLOR','E2E 그래프 색상','2019-11-13 23:56:20.268','Z03283','2019-11-13 23:56:20.268','Z03283')
;