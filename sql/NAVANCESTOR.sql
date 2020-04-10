-- Drop table

-- DROP TABLE episode.navancestor;

CREATE TABLE episode.navancestor (
	id int4 NOT NULL,
	org_id varchar(20) NOT NULL,
	ancestor varchar(30) NOT NULL,
	navigator_id varchar(30) NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NULL DEFAULT NULL::character varying,
	CONSTRAINT navancestor_idx1 PRIMARY KEY (id),
	CONSTRAINT navancestor_idx2 UNIQUE (org_id, ancestor, navigator_id)
);
CREATE INDEX navancestor_idx3 ON episode.navancestor USING btree (org_id, navigator_id);

-- Permissions

ALTER TABLE episode.navancestor OWNER TO episode;
GRANT ALL ON TABLE episode.navancestor TO episode;

INSERT INTO episode.navancestor (id,org_id,ancestor,navigator_id,create_dt,create_id) VALUES 
(333,'KAI','SYSTEM','SYSTEM','2019-07-31 05:47:43.615','esadmin')
,(335,'KAI','APPLICATION','APPLICATION','2019-07-31 05:47:54.630','esadmin')
,(423,'KAI','LICENSEMGMNT','LICENSEMGMNT','2019-08-02 05:40:19.719','esadmin')
,(337,'KAI','CLASSIFICATION','CLASSIFICATION','2019-07-31 05:47:54.630','esadmin')
,(339,'KAI','DOMAIN','DOMAIN','2019-07-31 05:47:54.646','esadmin')
,(341,'KAI','NAVIGATOR','NAVIGATOR','2019-07-31 05:48:03.074','esadmin')
,(715,'KAI','CISW','CISW','2019-08-19 01:08:58.045','Z03284')
,(343,'KAI','USERGROUP','USERGROUP','2019-07-31 05:48:13.893','esadmin')
,(891,'KAI','COMPANY','COMPANY','2019-10-29 07:38:19.414','Z03284')
,(345,'KAI','USER','USER','2019-07-31 05:48:13.893','esadmin')
;
INSERT INTO episode.navancestor (id,org_id,ancestor,navigator_id,create_dt,create_id) VALUES 
(892,'KAI','INFRA','COMPANY','2019-10-29 22:58:27.194','Z03284')
,(347,'KAI','ROLE','ROLE','2019-07-31 05:48:18.693','esadmin')
,(429,'KAI','LICENSEUSE','LICENSEUSE','2019-08-02 05:45:25.730','esadmin')
,(893,'KAI','INFRAMGMNT','COMPANY','2019-10-29 22:58:27.279','Z03284')
,(787,'KAI','INFRA','DISCI','2019-09-23 04:37:51.524','Z03284')
,(788,'KAI','RESOURCE','DISCI','2019-09-23 04:37:51.524','Z03284')
,(352,'KAI','LOCATION','LOCATION','2019-07-31 05:53:37.080','esadmin')
,(789,'KAI','INFRA','CISW','2019-09-23 04:37:57.457','Z03284')
,(790,'KAI','RESOURCE','CISW','2019-09-23 04:37:57.457','Z03284')
,(791,'KAI','INFRA','LICENSEMGMNT','2019-09-23 04:38:25.421','Z03284')
;
INSERT INTO episode.navancestor (id,org_id,ancestor,navigator_id,create_dt,create_id) VALUES 
(356,'KAI','MONITORDASHBOARD','MONITORDASHBOARD','2019-07-31 06:33:50.703','esadmin')
,(894,'KAI','INFRA','CONVERSION','2019-10-29 22:59:50.170','Z03284')
,(314,'KAI','MONITORING','MONITORING','2019-07-31 05:29:08.000','esadmin')
,(895,'KAI','INFRAMGMNT','CONVERSION','2019-10-29 22:59:50.170','Z03284')
,(798,'KAI','INFRA','LICENSE','2019-09-23 04:39:27.554','Z03284')
,(799,'KAI','LICENSEMGMNT','LICENSE','2019-09-23 04:39:27.554','Z03284')
,(800,'KAI','INFRA','LICENSEUSE','2019-09-23 04:39:58.169','Z03284')
,(801,'KAI','LICENSEMGMNT','LICENSEUSE','2019-09-23 04:39:58.169','Z03284')
,(802,'KAI','SYSTEM','USER','2019-09-23 04:41:28.452','Z03284')
,(803,'KAI','SYSTEM','USERGROUP','2019-09-23 04:41:59.571','Z03284')
;
INSERT INTO episode.navancestor (id,org_id,ancestor,navigator_id,create_dt,create_id) VALUES 
(583,'KAI','LICENSE','LICENSE','2019-08-02 06:44:51.108','esadmin')
,(804,'KAI','SYSTEM','ROLE','2019-09-23 04:42:31.010','Z03284')
,(805,'KAI','SYSTEM','LOCATION','2019-09-23 04:43:29.610','Z03284')
,(371,'KAI','ORGANIZATION','ORGANIZATION','2019-07-31 06:44:53.851','esadmin')
,(902,'KAI','INFRA','SERVICEAPP','2019-11-21 03:58:00.392','Z03283')
,(903,'KAI','SERVICEMGMNT','SERVICEAPP','2019-11-21 03:58:00.401','Z03283')
,(904,'KAI','SERVICEAPP','SERVICEAPP','2019-11-21 03:58:00.403','Z03283')
,(806,'KAI','SYSTEM','DOMAIN','2019-09-23 04:44:14.649','Z03284')
,(591,'KAI','RESOURCE','RESOURCE','2019-08-12 03:16:33.194','esadmin')
,(807,'KAI','SYSTEM','CLASSIFICATION','2019-09-23 04:44:43.479','Z03284')
;
INSERT INTO episode.navancestor (id,org_id,ancestor,navigator_id,create_dt,create_id) VALUES 
(808,'KAI','SYSTEM','APPLICATION','2019-09-23 04:45:09.844','Z03284')
,(809,'KAI','SYSTEM','NAVIGATOR','2019-09-23 04:45:46.238','Z03284')
,(810,'KAI','SYSTEM','ORGANIZATION','2019-09-23 04:47:43.242','Z03284')
,(812,'KAI','INFRAMGMNT','INFRAMGMNT','2019-09-23 07:18:45.698','Z03284')
,(743,'KAI','EVENT','EVENT','2019-09-03 03:35:28.744','Z03283')
,(816,'KAI','SWCAT','SWCAT','2019-09-23 07:19:14.353','Z03284')
,(817,'KAI','INFRA','INFRAMGMNT','2019-09-23 07:19:21.722','Z03284')
,(819,'KAI','INFRA','SWCAT','2019-09-23 07:21:44.018','Z03284')
,(820,'KAI','INFRAMGMNT','SWCAT','2019-09-23 07:21:44.018','Z03284')
,(750,'KAI','DISCI','DISCI','2019-09-04 05:50:33.906','Z03284')
;
INSERT INTO episode.navancestor (id,org_id,ancestor,navigator_id,create_dt,create_id) VALUES 
(610,'KAI','CI','CI','2019-08-13 01:48:39.670','esadmin')
,(405,'KAI','INFRA','INFRA','2019-08-02 04:20:45.265','esadmin')
,(407,'KAI','ASSETDASHBOARD','ASSETDASHBOARD','2019-08-02 04:20:57.720','esadmin')
,(823,'KAI','CONVERSION','CONVERSION','2019-09-30 00:42:25.645','Z03284')
,(828,'KAI','CIHIS','CIHIS','2019-10-06 23:47:53.194','Z03284')
,(829,'KAI','INFRA','CIHIS','2019-10-06 23:48:07.292','Z03284')
,(830,'KAI','RESOURCE','CIHIS','2019-10-06 23:48:07.292','Z03284')
,(833,'KAI','MONITORING','E2E','2019-10-07 06:15:11.227','Z03283')
,(762,'KAI','MONITORING','MONITORDASHBOARD','2019-09-23 04:30:24.318','Z03284')
,(763,'KAI','MONITORING','EVENT','2019-09-23 04:31:20.289','Z03284')
;
INSERT INTO episode.navancestor (id,org_id,ancestor,navigator_id,create_dt,create_id) VALUES 
(764,'KAI','INFRA','ASSETDASHBOARD','2019-09-23 04:32:44.557','Z03284')
,(834,'KAI','E2E','E2E','2019-10-07 06:15:11.228','Z03283')
,(835,'KAI','INFRA','SERVICEMGMNT','2019-10-09 05:47:55.868','Z03283')
,(836,'KAI','SERVICEMGMNT','SERVICEMGMNT','2019-10-09 05:47:55.878','Z03283')
,(776,'KAI','INFRA','RESOURCE','2019-09-23 04:36:04.081','Z03284')
,(777,'KAI','INFRA','CI','2019-09-23 04:36:16.971','Z03284')
,(778,'KAI','RESOURCE','CI','2019-09-23 04:36:16.971','Z03284')
,(859,'KAI','INFRA','SERVICE','2019-10-21 01:59:19.560','Z03284')
,(860,'KAI','SERVICEMGMNT','SERVICE','2019-10-21 01:59:19.560','Z03284')
,(861,'KAI','SERVICE','SERVICE','2019-10-21 01:59:19.560','Z03284')
;
INSERT INTO episode.navancestor (id,org_id,ancestor,navigator_id,create_dt,create_id) VALUES 
(864,'KAI','SYSTEM','SCHEDULER','2019-10-27 23:23:05.060','Z03255')
,(865,'KAI','SCHEDULER','SCHEDULER','2019-10-27 23:23:05.061','Z03255')
;