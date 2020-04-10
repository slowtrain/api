-- Drop table

-- DROP TABLE episode.application;

CREATE TABLE episode.application (
	id int4 NOT NULL,
	app_id varchar(20) NOT NULL,
	description varchar(50) NULL DEFAULT NULL::character varying,
	url varchar(50) NULL DEFAULT NULL::character varying,
	status varchar(20) NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NULL DEFAULT NULL::character varying,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NULL DEFAULT NULL::character varying,
	CONSTRAINT application_idx1 PRIMARY KEY (id),
	CONSTRAINT application_idx2 UNIQUE (app_id)
);

-- Permissions

ALTER TABLE episode.application OWNER TO episode;
GRANT ALL ON TABLE episode.application TO episode;

INSERT INTO episode.application (id,app_id,description,url,status,create_dt,create_id,update_dt,update_id) VALUES 
(71,'SERVICEAPP','어플리케이션 손실비용','/infra/servicemgmnt/serviceapp','ACTIVE','2019-11-21 04:25:42.752','Z03283','2019-11-21 04:26:45.950','Z03283')
,(38,'EVENTDETAIL','EVENTDETAIL','/common/eventdetail','ACTIVE','2019-09-09 06:22:15.080','Z03283','2019-09-09 06:22:15.080','Z03283')
,(39,'ASIDE','ASIDE','/aside','ACTIVE','2019-09-09 06:35:40.275','Z03283','2019-09-09 06:35:40.275','Z03283')
,(37,'DISCI','DISCI','/infra/resource/disci','ACTIVE','2019-09-04 05:49:14.510','Z03284','2019-11-22 05:56:23.499','Z03255')
,(28,'MONITORDASHBOARD','MONITORDASHBOARD','/monitor/dashboard','ACTIVE','2019-07-31 06:33:27.439','esadmin','2019-11-13 22:54:56.372','Z03283')
,(41,'SWCAT','SWCAT','/infra/management/swcat','ACTIVE','2019-09-11 07:18:00.134','Z03284','2019-09-11 07:18:37.602','Z03284')
,(42,'CIEVENTLIST','CIEVENTLIST','/common/cieventlist','ACTIVE','2019-09-16 04:48:07.295','Z03283','2019-09-16 04:48:07.295','Z03283')
,(43,'RPASYSTEM','RPASYSTEM','/common/rpasystem','ACTIVE','2019-09-25 04:56:55.592','Z03283','2019-09-25 04:56:55.592','Z03283')
,(44,'RPALOCATION','RPALOCATION','/common/eventdetail','ACTIVE','2019-09-25 09:39:22.574','Z03283','2019-09-25 09:39:22.574','Z03283')
,(6,'USERGROUP','UESRGROUP','/system/usergroup','ACTIVE','2019-05-20 07:19:33.000','esadmin','2019-11-15 02:09:14.836','Z03283')
;
INSERT INTO episode.application (id,app_id,description,url,status,create_dt,create_id,update_dt,update_id) VALUES 
(46,'CONVERSION','CONVERSION','/infra/management/conversion','ACTIVE','2019-09-30 00:38:06.318','Z03284','2019-09-30 00:38:06.318','Z03284')
,(47,'CIHIS','CIHIS','/infra/resource/cihis','ACTIVE','2019-10-06 23:47:04.087','Z03284','2019-10-06 23:47:04.087','Z03284')
,(45,'E2E','E2E','/monitor/e2e','ACTIVE','2019-09-25 09:58:07.757','Z03283','2019-09-25 09:58:07.757','Z03283')
,(32,'CISW','CISW','/infra/resource/cisw','ACTIVE','2019-08-12 05:28:44.099','esadmin','2019-11-22 06:02:07.056','Z03284')
,(49,'BIZSYSTEM','BIZSYSTEM','/common/bizsystem','ACTIVE','2019-10-11 05:12:47.072','Z03283','2019-10-11 05:12:47.072','Z03283')
,(4,'MENU','MENU','/system/menu','ACTIVE','2019-04-17 08:35:59.000','esadmin','2019-07-26 05:35:33.898','esadmin')
,(50,'SCHEDULER','background task scheduler','/system/scheduler','ACTIVE','2019-10-15 02:32:57.667','Z03255','2019-10-15 02:33:24.237','Z03255')
,(9,'ALARM','ALARM MANAGEMENT','/monitor/alarm','ACTIVE','2019-05-14 05:16:48.000','esadmin','2019-08-02 00:06:49.248','esadmin')
,(13,'CONFIRM','CONFIRM','/confirm','ACTIVE','2019-06-20 07:47:51.000','esadmin','2019-08-02 00:07:02.773','esadmin')
,(3,'ABOUT','ABOUT','/about','ACTIVE','2019-04-17 08:33:26.000','esadmin','2019-07-26 05:45:57.643','esadmin')
;
INSERT INTO episode.application (id,app_id,description,url,status,create_dt,create_id,update_dt,update_id) VALUES 
(51,'ATTACHMENT','ATTACHMENT','/modal/attachment','ACTIVE','2019-10-21 22:36:44.558','Z03283','2019-10-21 22:36:44.558','Z03283')
,(24,'FILEDOWNLOAD','FILEDOWNLOAD','/modal/filedown','ACTIVE','2019-07-24 02:53:01.017','esadmin','2019-11-26 03:55:48.291','Z03283')
,(21,'CLASSIFICATION','CLASSIFICATION MNAGEMENT','/system/classification','ACTIVE','2019-07-15 01:25:52.406','esadmin','2019-11-18 04:32:58.377','Z03284')
,(52,'COMPANY','업체관리','/infra/management/company','ACTIVE','2019-10-29 07:30:45.881','Z03284','2019-10-29 07:30:45.881','Z03284')
,(53,'GATE','GATE','/gate','ACTIVE','2019-10-30 00:08:50.679','Z03283','2019-10-30 00:08:50.679','Z03283')
,(2,'USER','USER MANAGEMENT','/system/user','ACTIVE','2019-04-17 16:27:34.000','esadmin','2019-11-18 04:54:29.685','Z03283')
,(10,'DEVICE','DEVICE MANAGEMENT','/system/device','ACTIVE','2019-05-14 05:17:30.000','esadmin','2019-07-26 06:44:08.579','esadmin')
,(12,'MESSAGE','MESSAGE BOX','/message','ACTIVE','2019-06-20 07:46:58.000','esadmin','2019-08-02 00:07:19.343','esadmin')
,(23,'FILEUPLOAD','FILEUPLOAD','/modal/fileupload','ACTIVE','2019-07-23 07:31:24.828','esadmin','2019-11-26 03:58:24.709','Z03283')
,(27,'ASSETDASHBOARD','ASSETDASHBOARD','/infra/dashboard','ACTIVE','2019-07-31 06:20:05.576','esadmin','2019-08-02 04:35:26.011','esadmin')
;
INSERT INTO episode.application (id,app_id,description,url,status,create_dt,create_id,update_dt,update_id) VALUES 
(31,'LICENSEUSE','LICENSEUSE','/infra/licensemgmnt/licenseuse','ACTIVE','2019-08-02 05:43:44.663','esadmin','2019-08-02 06:02:39.727','esadmin')
,(29,'LICENSE','LICENSE','/infra/licensemgmnt/license','ACTIVE','2019-08-02 05:29:48.956','esadmin','2019-08-02 06:02:56.764','esadmin')
,(11,'ROLE','ROLE','/system/role','ACTIVE','2019-06-17 07:55:54.000','esadmin','2019-11-18 06:07:58.758','Z03283')
,(8,'DOMAIN','DOMAIN CODE MANAGEMENT','/system/code','ACTIVE','2019-04-17 16:27:34.000','esadmin','2019-11-26 07:10:20.629','Z03284')
,(34,'CIREGISTRATION','CIREGISTRATION','/monitor/userci/ciregistration','ACTIVE','2019-08-20 00:02:22.575','Z03283','2019-08-20 00:02:22.575','Z03283')
,(35,'CIMONITORDETAIL','CIMONITORDETAIL','/monitor/cimonitordetail','ACTIVE','2019-08-20 07:52:04.822','Z03283','2019-08-20 07:52:04.822','Z03283')
,(36,'EVENT','EVENT','/monitor/event','ACTIVE','2019-09-03 03:42:55.631','Z03283','2019-09-03 03:42:55.631','Z03283')
,(1,'HOME',' HOME','/','ACTIVE','2019-04-17 16:27:34.000','esadmin','2019-11-19 05:20:36.633','Z03283')
,(5,'APPLICATION','APPLICATION MANAGEMENT','/system/application','ACTIVE','2019-04-17 16:27:34.000','esadmin','2019-11-06 00:52:13.541','Z03284')
,(48,'SERVICE','SERVICE','/infra/servicemgmnt/service','ACTIVE','2019-10-10 07:08:21.999','Z03283','2019-11-13 04:06:31.058','Z03284')
;
INSERT INTO episode.application (id,app_id,description,url,status,create_dt,create_id,update_dt,update_id) VALUES 
(33,'CI','CI','/infra/resource/ci','ACTIVE','2019-08-13 10:44:50.604','esadmin','2019-11-13 05:24:46.524','Z03284')
,(26,'LOCATION','LOCATION','/system/location','ACTIVE','2019-07-24 04:52:15.782','esadmin','2019-11-20 05:18:14.478','Z03283')
,(25,'NAVIGATOR','NAVIGATOR','/system/menu','ACTIVE','2019-07-24 03:19:51.955','esadmin','2019-11-21 04:02:09.606','Z03283')
,(7,'ORGANIZATION','ORGANIZATION MANAGEMENT','/system/organization','ACTIVE','2019-04-17 08:36:02.000','esadmin','2019-11-26 07:13:19.581','Z03284')
;