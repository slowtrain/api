-- Drop table

-- DROP TABLE episode.classspec;

CREATE TABLE episode.classspec (
	id int4 NOT NULL,
	org_id varchar(20) NOT NULL,
	classstructure_id varchar(30) NOT NULL,
	classspec_id varchar(30) NOT NULL,
	description varchar(50) NOT NULL,
	"type" varchar(10) NULL,
	measureunit varchar(10) NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	CONSTRAINT classspec_idx1 PRIMARY KEY (id),
	CONSTRAINT classspec_idx2 UNIQUE (org_id, classstructure_id, classspec_id)
);
CREATE INDEX classspec_idx3 ON episode.classspec USING btree (org_id, classstructure_id);

-- Permissions

ALTER TABLE episode.classspec OWNER TO episode;
GRANT ALL ON TABLE episode.classspec TO episode;

INSERT INTO episode.classspec (id,org_id,classstructure_id,classspec_id,description,"type",measureunit,create_dt,create_id,update_dt,update_id) VALUES 
(66,'KAI','SW_OS_LINUX','Java Process','자바','ALNVALUE','EA','2019-10-18 01:51:02.133','Z03232','2019-10-18 01:51:02.133','Z03232')
,(67,'KAI','SW_VIRTULA_SERVER','VirtualID','가상화 ID','ALNVALUE','STR','2019-10-18 02:52:17.182','Z03232','2019-10-18 02:52:17.182','Z03232')
,(68,'KAI','SW_VIRTULA_SERVER','Physical Core','물리코어','NUMVALUE','EA','2019-10-18 02:52:42.122','Z03232','2019-10-18 02:52:42.122','Z03232')
,(69,'KAI','SW_VIRTULA_SERVER','Cluster ID','클러스터 정보','ALNVALUE','STR','2019-10-18 02:53:24.216','Z03232','2019-10-18 02:53:24.216','Z03232')
,(70,'KAI','SVR_LINUX','test','test','ALNVALUE','STR','2019-10-20 22:35:30.388','Z03284','2019-10-20 22:35:30.388','Z03284')
,(71,'KAI','BUSINESSSYSTEM','JOB','JOB','ALNVALUE','STR','2019-10-22 00:00:19.518','Z03284','2019-10-22 00:00:19.518','Z03284')
,(72,'KAI','SVR_VIRTUAL_VIR','Test1','Test1','ALNVALUE','GHZ','2019-11-01 08:05:49.029','105018','2019-11-01 08:05:49.029','105018')
,(79,'KAI','TEST','test','test','ALNVALUE','HZ','2019-11-19 01:44:14.584','Z03284','2019-11-19 01:44:22.495','Z03284')
,(48,'KAI','LINUXSYSTEM','SWAPTOTBYTES','스왑 총 용량','ALNVALUE','BYTE','2019-08-19 06:54:14.255','Z03284','2019-08-19 06:54:14.255','Z03284')
,(49,'KAI','LINUXSYSTEM','CPUCOREPERPHYS','물리 프로세서 당 Core 수','ALNVALUE','EA','2019-08-19 06:54:14.270','Z03284','2019-08-19 06:54:14.270','Z03284')
;
INSERT INTO episode.classspec (id,org_id,classstructure_id,classspec_id,description,"type",measureunit,create_dt,create_id,update_dt,update_id) VALUES 
(50,'KAI','LINUXSYSTEM','CPUCORETOT','총 코어수 (하이퍼스레딩을 고려하지 않는 물리 프로세서의 총 코어 합)','ALNVALUE','EA','2019-08-19 06:54:14.270','Z03284','2019-08-19 06:54:14.270','Z03284')
,(53,'KAI','LINUXSYSTEM','VIRMANAGERID','가상화 매니저 ID','ALNVALUE','STR','2019-08-19 06:57:57.442','Z03284','2019-08-19 06:59:26.438','Z03284')
,(52,'KAI','LINUXSYSTEM','VIRDESCRIPTION','가상화 머신 상세 정보','ALNVALUE','STR','2019-08-19 06:54:14.270','Z03284','2019-08-19 06:59:26.438','Z03284')
,(51,'KAI','LINUXSYSTEM','OSDESCRIPTION','운영체제 설명','ALNVALUE','STR','2019-08-19 06:54:14.270','Z03284','2019-08-19 06:59:45.887','Z03284')
,(54,'KAI','LINUXSYSTEM','VIRUUID','가상화 UUID','ALNVALUE','STR','2019-08-19 06:57:57.457','Z03284','2019-08-20 07:55:23.532','Z03284')
,(56,'KAI','LINUXSYSTEM','VIRDATACENTER','가상화 머신 DataCenter','NUMVALUE','STR','2019-08-19 06:57:57.457','Z03284','2019-08-20 07:59:10.674','Z03284')
,(83,'KAI','SVR_WINDOWS','test','test','ALNVALUE','EA','2019-11-26 05:00:23.988','Z03284','2019-11-26 05:00:23.988','Z03284')
,(57,'KAI','SOFTWARE','APPPROCESS','APPPROCESS','ALNVALUE','EA','2019-08-22 02:10:40.649','Z03284','2019-08-22 02:10:40.649','Z03284')
,(58,'KAI','SOFTWARE','APPHOMEDIR','APPHOMEDIR','ALNVALUE','EA','2019-08-22 02:10:40.649','Z03284','2019-08-22 02:10:40.649','Z03284')
,(62,'KAI','WINDOWSSYSTEM','CPUCORETOT','총 코어수 (하이퍼스레딩을 고려하지 않는 물리 프로세서의 총 코어 합)','ALNVALUE','EA','2019-10-07 02:49:02.336','Z03284','2019-10-07 02:49:02.336','Z03284')
;
INSERT INTO episode.classspec (id,org_id,classstructure_id,classspec_id,description,"type",measureunit,create_dt,create_id,update_dt,update_id) VALUES 
(63,'KAI','WINDOWSSYSTEM','CPUCOREPERPHYS','물리 프로세서 당 Core 수','ALNVALUE','EA','2019-10-07 02:49:02.336','Z03284','2019-10-07 02:49:02.336','Z03284')
,(61,'KAI','WINDOWSSYSTEM','SWAPTOTBYTES','스왑 총 용량','ALNVALUE','BYTE','2019-10-07 02:48:38.162','Z03284','2019-10-07 03:08:36.756','Z03284')
;