-- Drop table

-- DROP TABLE episode.discimaptoci;

CREATE TABLE episode.discimaptoci (
	id int4 NOT NULL,
	datasrc varchar(20) NULL,
	discitype varchar(100) NULL,
	disciclass varchar(200) NULL,
	isvirtual bool NULL DEFAULT false,
	classstructure_id varchar(30) NULL,
	status varchar(10) NOT NULL DEFAULT 'ACTIVE'::character varying,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	CONSTRAINT discimaptoci_idx1 PRIMARY KEY (id),
	CONSTRAINT discimaptoci_idx2 UNIQUE (datasrc, discitype, disciclass, isvirtual)
);

-- Permissions

ALTER TABLE episode.discimaptoci OWNER TO episode;
GRANT ALL ON TABLE episode.discimaptoci TO episode;

INSERT INTO episode.discimaptoci (id,datasrc,discitype,disciclass,isvirtual,classstructure_id,status,create_dt,create_id,update_dt,update_id) VALUES 
(123,'TADDM','CPU','CPU',false,'CPU','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(124,'TADDM','FileSystem','WindowsFileSystem',false,'FILESYSTEM','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(125,'TADDM','FileSystem','UnixFileSystem',false,'FILESYSTEM','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(126,'TADDM','FileSystem','NFSFileSystem',false,'FILESYSTEM','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(127,'TADDM','FileSystem','SMBFileSystem',false,'FILESYSTEM','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(128,'TADDM','FileSystem','FileSystem',false,'FILESYSTEM','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(129,'TADDM','IPAddress','IPv6',false,'IPADDRESS','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(130,'TADDM','IPAddress','IPv4',false,'IPADDRESS','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(131,'TADDM','Port','Port',false,'PORT','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(150,'TADDM','NetInterface','L2INTERFACE',false,'NETINTERFACE','ACTIVE','2019-09-25 15:24:17.321','intadmin','2019-09-25 15:24:17.321','intadmin')
;
INSERT INTO episode.discimaptoci (id,datasrc,discitype,disciclass,isvirtual,classstructure_id,status,create_dt,create_id,update_dt,update_id) VALUES 
(99,'TADDM','ComputerSystem','WindowsComputerSystem',false,'SVR_WINDOWS','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(154,'WATCHALL','ComputerSystem','WindowsComputerSystem',false,'SVR_WINDOWS','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(157,'BFI','ComputerSystem','WindowsComputerSystem',false,'SVR_WINDOWS','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(103,'TADDM','ComputerSystem','AixUnitaryComputerSystem',true,'SVR_UNIX','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(104,'TADDM','ComputerSystem','AixUnitaryComputerSystem',false,'SVR_UNIX','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(100,'TADDM','ComputerSystem','WindowsComputerSystem',true,'SVR_VIRTUAL_VIR','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(102,'TADDM','ComputerSystem','LinuxUnitaryComputerSystem',true,'SVR_VIRTUAL_VIR','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(107,'TADDM','ComputerSystem','VmwareUnitaryComputerSystem',true,'SVR_VIRTUAL_VIR','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(111,'TADDM','Bridge','ComputerSystem',false,'NW_L2SW','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(112,'TADDM','Bridge','UnitaryComputerSystem',false,'NW_L2SW','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
;
INSERT INTO episode.discimaptoci (id,datasrc,discitype,disciclass,isvirtual,classstructure_id,status,create_dt,create_id,update_dt,update_id) VALUES 
(113,'TADDM','LoadBalancer','UnitaryComputerSystem',false,'NW_L4SW','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(114,'TADDM','LoadBalancer','ComputerSystem',false,'NW_L4SW','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(115,'TADDM','LoadBalancer','Bridge',false,'NW_L4SW','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(116,'TADDM','Router','UnitaryComputerSystem',false,'NW_ROUTER','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(117,'TADDM','Router','ComputerSystem',false,'NW_ROUTER','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(118,'TADDM','WebServer','ApacheServer',false,'SW_MIDDLESW_WEB','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(119,'TADDM','WebServer','HTTPServer',false,'SW_MIDDLESW_WEB','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(120,'TADDM','WebServer','IBMHTTPServer',false,'SW_MIDDLESW_WEB','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(121,'TADDM','WebServer','IIsWebService',false,'SW_MIDDLESW_WEB','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(122,'TADDM','WebServer','OracleAppHTTPServer',false,'SW_MIDDLESW_WEB','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
;
INSERT INTO episode.discimaptoci (id,datasrc,discitype,disciclass,isvirtual,classstructure_id,status,create_dt,create_id,update_dt,update_id) VALUES 
(132,'TADDM','DatabaseServer','HanaDB',false,'SW_DBSW','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(134,'TADDM','DatabaseServer','MaxDB',false,'SW_DBSW','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(137,'TADDM','DatabaseServer','PostgreSQL',false,'SW_DBSW','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(139,'TADDM','DatabaseServer','SybaseServer',false,'SW_DBSW','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(140,'TADDM','DatabaseServer','TiberoDB',false,'SW_DBSW','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(133,'TADDM','DatabaseServer','Db2Instance',false,'SW_DBSW_DB2','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(135,'TADDM','DatabaseServer','MySql',false,'SW_DBSW_MYSQL','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(136,'TADDM','DatabaseServer','OracleInstance',false,'SW_DBSW_ORACLE','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(138,'TADDM','DatabaseServer','SqlServer',false,'SW_DBSW_MSSQL','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(142,'TADDM','ApplicationServer','KVM',false,'SW_VIRTULA_SERVER','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
;
INSERT INTO episode.discimaptoci (id,datasrc,discitype,disciclass,isvirtual,classstructure_id,status,create_dt,create_id,update_dt,update_id) VALUES 
(143,'TADDM','ApplicationServer','MicrosoftHyperV',false,'SW_VIRTULA_SERVER','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(144,'TADDM','ApplicationServer','VirtualCenter',false,'SW_VIRTULA_SERVER','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(145,'TADDM','ApplicationServer','IBMWebSphereLibertyProfile',false,'SW_VIRTULA_SERVER','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(146,'TADDM','WebApplicationServer','Tomcat',false,'SW_MIDDLESW_WAS','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(147,'TADDM','WebApplicationServer','WebLogicAdminServer',false,'SW_MIDDLESW_WAS','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(148,'TADDM','WebApplicationServer','WebLogicNodeManager',false,'SW_MIDDLESW_WAS','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(149,'TADDM','WebApplicationServer','WebLogicServer',false,'SW_MIDDLESW_WAS','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(108,'TADDM','ComputerSystem','ComputerSystem',false,'SVR_SINGLE','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(109,'TADDM','IpDevice','ComputerSystem',false,'SVR_SINGLE','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(110,'TADDM','IpDevice','UnitaryComputerSystem',false,'SVR_SINGLE','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
;
INSERT INTO episode.discimaptoci (id,datasrc,discitype,disciclass,isvirtual,classstructure_id,status,create_dt,create_id,update_dt,update_id) VALUES 
(141,'TADDM','ApplicationServer','DominoServer',false,'COMPANY_SW_GW','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(219,'TADDM','WebApplicationServer','IBMWebSphereLibertyProfile',false,'SW_MIDDLESW_WAS','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(220,'TADDM','WebApplicationServer','JBossServer',false,'SW_MIDDLESW_WAS','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(158,'BFI','ComputerSystem','HpUxUnitaryComputerSystem',false,'SVR_UNIX','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(101,'TADDM','ComputerSystem','LinuxUnitaryComputerSystem',false,'SVR_LINUX','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(155,'WATCHALL','ComputerSystem','LinuxUnitaryComputerSystem',false,'SVR_LINUX','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(156,'BFI','ComputerSystem','LinuxUnitaryComputerSystem',false,'SVR_LINUX','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(311,'NUTANIX','ComputerSystem','LinuxUnitaryComputerSystem',false,'SVR_LINUX','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(105,'TADDM','ComputerSystem','HpUxUnitaryComputerSystem',true,'SVR_UNIX','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(106,'TADDM','ComputerSystem','HpUxUnitaryComputerSystem',false,'SVR_UNIX','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
;
INSERT INTO episode.discimaptoci (id,datasrc,discitype,disciclass,isvirtual,classstructure_id,status,create_dt,create_id,update_dt,update_id) VALUES 
(151,'WATCHALL','ComputerSystem','AixUnitaryComputerSystem',false,'SVR_UNIX','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(152,'WATCHALL','ComputerSystem','HpUxUnitaryComputerSystem',false,'SVR_UNIX','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(153,'WATCHALL','ComputerSystem','SunSPARCUnitaryComputerSystem',false,'SVR_UNIX','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(159,'BFI','ComputerSystem','AixUnitaryComputerSystem',false,'SVR_UNIX','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(160,'BFI','ComputerSystem','SunSPARCUnitaryComputerSystem',false,'SVR_UNIX','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(307,'BFI','ComputerSystem','AixUnitaryComputerSystem',true,'SVR_UNIX','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(161,'BFI','ComputerSystem','WindowsComputerSystem',true,'SVR_VIRTUAL_VIR','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(162,'BFI','ComputerSystem','LinuxUnitaryComputerSystem',true,'SVR_VIRTUAL_VIR','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(163,'BFI','ComputerSystem','VmwareUnitaryComputerSystem',true,'SVR_VIRTUAL_VIR','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(164,'WATCHALL','ComputerSystem','WindowsComputerSystem',true,'SVR_VIRTUAL_VIR','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
;
INSERT INTO episode.discimaptoci (id,datasrc,discitype,disciclass,isvirtual,classstructure_id,status,create_dt,create_id,update_dt,update_id) VALUES 
(165,'WATCHALL','ComputerSystem','LinuxUnitaryComputerSystem',true,'SVR_VIRTUAL_VIR','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(166,'WATCHALL','ComputerSystem','VmwareUnitaryComputerSystem',true,'SVR_VIRTUAL_VIR','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(300,'WATCHALL','DatabaseServer','SYBASE',false,'SW_DBSW','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(301,'WATCHALL','DatabaseServer','TIBERO',false,'SW_DBSW','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(297,'WATCHALL','DatabaseServer','DB2',false,'SW_DBSW_DB2','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(295,'WATCHALL','DatabaseServer','MYSQL',false,'SW_DBSW_MYSQL','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(296,'WATCHALL','DatabaseServer','ORACLE',false,'SW_DBSW_ORACLE','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(294,'WATCHALL','DatabaseServer','MSSQL',false,'SW_DBSW_MSSQL','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(267,'WATCHALL','WebApplicationServer','TOMCAT',false,'SW_MIDDLESW_WAS','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(268,'WATCHALL','WebApplicationServer','Undefined',false,'SW_MIDDLESW_WAS','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
;
INSERT INTO episode.discimaptoci (id,datasrc,discitype,disciclass,isvirtual,classstructure_id,status,create_dt,create_id,update_dt,update_id) VALUES 
(269,'WATCHALL','WebApplicationServer','WEBLOGIC',false,'SW_MIDDLESW_WAS','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(176,'WATCHALL','NetDevice','Authentication Server',false,'NETWORK','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(177,'WATCHALL','NetDevice','ETC',false,'NETWORK','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(178,'WATCHALL','NetDevice','FireWall',false,'NETWORK','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(179,'WATCHALL','NetDevice','IPS',false,'NETWORK','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(180,'WATCHALL','NetDevice','Layer2 Switch',false,'NETWORK','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(181,'WATCHALL','NetDevice','Router',false,'NETWORK','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(182,'WATCHALL','NetDevice','SAN',false,'NETWORK','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(183,'WATCHALL','NetDevice','VPN',false,'NETWORK','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(184,'WATCHALL','NetDevice','Wireless AP',false,'NETWORK','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
;
INSERT INTO episode.discimaptoci (id,datasrc,discitype,disciclass,isvirtual,classstructure_id,status,create_dt,create_id,update_dt,update_id) VALUES 
(199,'TADDM','Bridge','Bridge',false,'NETWORK','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(200,'TADDM','Bridge','BrocadeSwitch',false,'NETWORK','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(226,'TADDM','ComputerSystem','VmwareUnitaryComputerSystem',false,'SVR_VIRTUAL','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(245,'TADDM','ComputerSystem','ComputerSystem',true,'SVR_VIRTUAL_VIR','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(312,'NUTANIX','ClusterApplicationServer','NutanixCluster',false,'COMPANY_SW','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-10-11 01:44:52.492','Z03284')
,(201,'TADDM','Bridge','CiscoSwitch',false,'NETWORK','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(202,'TADDM','Bridge','HpeFlexNetwork',false,'NETWORK','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(316,'BFI','ComputerSystem','HpUxUnitaryComputerSystem',true,'SVR_UNIX','ACTIVE','2019-09-25 15:05:34.136','intadmin','2019-09-25 15:05:34.136','intadmin')
,(317,'SAPPO','SAPPO','ReceiveComponent',false,'SAPPORECVCOMP','ACTIVE','2019-11-08 13:48:38.838','intadmin','2019-11-08 13:48:38.838','intadmin')
,(318,'SAPPO','SAPPO','SendComponent',false,'SAPPOSENDCOMP','ACTIVE','2019-11-08 13:48:38.838','intadmin','2019-11-08 13:48:38.838','intadmin')
;
INSERT INTO episode.discimaptoci (id,datasrc,discitype,disciclass,isvirtual,classstructure_id,status,create_dt,create_id,update_dt,update_id) VALUES 
(319,'CYH','CYH','TEST',false,'TEST','ACTIVE','2019-11-14 05:08:25.828','Z03284','2019-11-14 05:08:25.828','Z03284')
;