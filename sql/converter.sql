-- Drop table

-- DROP TABLE episode.converter;

CREATE TABLE episode.converter (
	id int4 NOT NULL,
	converter_id varchar(30) NOT NULL,
	originalvalue varchar(200) NOT NULL,
	convertedvalue varchar(200) NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	CONSTRAINT converter_idx1 PRIMARY KEY (id),
	CONSTRAINT converter_idx2 UNIQUE (converter_id, originalvalue)
);
CREATE INDEX converter_idx3 ON episode.converter USING btree (converter_id);
CREATE INDEX converter_idx4 ON episode.converter USING btree (converter_id, convertedvalue);

-- Permissions

ALTER TABLE episode.converter OWNER TO episode;
GRANT ALL ON TABLE episode.converter TO episode;

INSERT INTO episode.converter (id,converter_id,originalvalue,convertedvalue,create_dt,create_id,update_dt,update_id) VALUES 
(1,'COMPANYNAME','-','-','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(2,'COMPANYNAME','ASUSTeK COMPUTER INC.','ASUSTeK COMPUTER INC.','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(3,'COMPANYNAME','American Megatrends Inc.','American Megatrends Inc.','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(4,'COMPANYNAME','Aruba Networks Inc','Aruba Networks Inc','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(5,'COMPANYNAME','Brocade Communication Systems Inc.','Brocade Communication Systems Inc.','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(10,'COMPANYNAME','Dell Inc.','Dell Inc.','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(11,'COMPANYNAME','FUJITSU','FUJITSU','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(12,'COMPANYNAME','H3C','H3C','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(16,'COMPANYNAME','Hewlett-Packard','Hewlett-Packard','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(19,'COMPANYNAME','Intel Corporation','Intel Corporation','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
;
INSERT INTO episode.converter (id,converter_id,originalvalue,convertedvalue,create_dt,create_id,update_dt,update_id) VALUES 
(20,'COMPANYNAME','LENOVO','LENOVO','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(22,'COMPANYNAME','Linux Communications AS','Linux Communications AS','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(24,'COMPANYNAME','Microsoft Corporation','Microsoft Corporation','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(25,'COMPANYNAME','NetScreen Technologies, Inc.','NetScreen Technologies, Inc.','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(26,'COMPANYNAME','Nortel','Nortel','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(28,'COMPANYNAME','Nutanix','Nutanix','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(29,'COMPANYNAME','Red Hat','Red Hat','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(30,'COMPANYNAME','Sun Microsystems','Sun Microsystems','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(31,'COMPANYNAME','Supermicro','Supermicro','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(32,'COMPANYNAME','SynOptics','SynOptics','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
;
INSERT INTO episode.converter (id,converter_id,originalvalue,convertedvalue,create_dt,create_id,update_dt,update_id) VALUES 
(33,'COMPANYNAME','VMware, Inc.','VMware, Inc.','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(37,'OSNAME','AIX','AIX','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(38,'OSNAME','AIX 6.1','AIX 6.1','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(57,'OSNAME','IOS','IOS','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(58,'OSNAME','Linux','Linux','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(64,'OSNAME','Microsoft Windows 2000 Server','Microsoft Windows 2000 Server','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(18,'COMPANYNAME','IBM Corp.','IBM Corporation','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(7,'COMPANYNAME','Brocade Communication Systems, Inc. (previous was  Foundry Networks, Inc. )','Brocade Communication Systems Inc.','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(8,'COMPANYNAME','Brocade Communications Systems, Inc.','Brocade Communication Systems Inc.','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(13,'COMPANYNAME','HP','Hewlett-Packard','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
;
INSERT INTO episode.converter (id,converter_id,originalvalue,convertedvalue,create_dt,create_id,update_dt,update_id) VALUES 
(14,'COMPANYNAME','HPE','Hewlett-Packard','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(15,'COMPANYNAME','Hewlett Packard Enterprise','Hewlett-Packard','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(21,'COMPANYNAME','Lenovo','LENOVO','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(23,'COMPANYNAME','Microsoft','Microsoft Corporation','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(27,'COMPANYNAME','Nortel Networks (Alteon)','Nortel','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(35,'COMPANYNAME','hp','Hewlett-Packard','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(39,'OSNAME','CentOS 4/5 or later (64-bit)','CentOS 4/5 or later','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(40,'OSNAME','CentOS 7 (64-bit)','CentOS Linux 7','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(41,'OSNAME','CentOS Linux 7 (Core)','CentOS Linux 7','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(42,'OSNAME','CentOS Linux release 7.0.1406 (Core)','CentOS Linux 7','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
;
INSERT INTO episode.converter (id,converter_id,originalvalue,convertedvalue,create_dt,create_id,update_dt,update_id) VALUES 
(43,'OSNAME','CentOS Linux release 7.2.1511 (Core)','CentOS Linux 7','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(44,'OSNAME','CentOS Linux release 7.3.1611 (Core)','CentOS Linux 7','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(45,'OSNAME','CentOS Linux release 7.4.1708 (Core)','CentOS Linux 7','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(46,'OSNAME','CentOS Linux release 7.5.1804 (Core)','CentOS Linux 7','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(47,'OSNAME','CentOS Linux release 7.6.1810 (Core)','CentOS Linux 7','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(48,'OSNAME','CentOS release 5.8 (Final)','CentOS Linux 5','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(49,'OSNAME','CentOS release 6.10 (Final)','CentOS Linux 6','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(50,'OSNAME','CentOS release 6.3 (Final)','CentOS Linux 6','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(51,'OSNAME','CentOS release 6.4 (Final)','CentOS Linux 6','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(52,'OSNAME','CentOS release 6.5 (Final)','CentOS Linux 6','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
;
INSERT INTO episode.converter (id,converter_id,originalvalue,convertedvalue,create_dt,create_id,update_dt,update_id) VALUES 
(53,'OSNAME','CentOS release 6.6 (Final)','CentOS Linux 6','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(54,'OSNAME','CentOS release 6.7 (Final)','CentOS Linux 6','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(55,'OSNAME','CentOS release 6.8 (Final)','CentOS Linux 6','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(56,'OSNAME','CentOS release 6.9 (Final)','CentOS Linux 6','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(60,'OSNAME','Microsoft® Windows Server® 2008 Enterprise  한국어(대한민국) version','Microsoft Windows Server 2008 2008 Enterprise','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(61,'OSNAME','Microsoft® Windows Server® 2008 Standard','Microsoft Windows Server 2008 2008 Standard','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(62,'OSNAME','Microsoft® Windows Server® 2008 Standard  한국어(대한민국) version','Microsoft Windows Server 2008 2008 Standard','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(63,'OSNAME','Microsoft Windows 10 (64-bit)','Microsoft Windows 10','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(65,'OSNAME','Microsoft Windows 7 (32-bit)','Microsoft Windows 7','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(66,'OSNAME','Microsoft Windows 7 (64-bit)','Microsoft Windows 7','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
;
INSERT INTO episode.converter (id,converter_id,originalvalue,convertedvalue,create_dt,create_id,update_dt,update_id) VALUES 
(59,'OSNAME','Microsoft® Windows Server® 2008 Enterprise','Microsoft Windows Server 2008 Enterprise','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(36,'COMPANYNAME','net-snmp','net-snmp','2019-11-07 17:00:49.592','esadmin','2019-11-19 04:51:26.861','Z03255')
,(34,'COMPANYNAME','ciscoSystems','Cisco','2019-11-07 17:00:49.592','esadmin','2019-11-21 00:44:53.438','Z03284')
,(9,'COMPANYNAME','Cisco','Cisco','2019-11-07 17:00:49.592','esadmin','2019-11-21 00:44:53.438','Z03284')
,(67,'OSNAME','Microsoft Windows 7 Enterprise K','Microsoft Windows 7 Enterprise K','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(70,'OSNAME','Microsoft Windows 7 Professional K','Microsoft Windows 7 Professional K','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(71,'OSNAME','Microsoft Windows Server 2003','Microsoft Windows Server 2003','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(79,'OSNAME','Microsoft Windows Server 2008 Enterprise','Microsoft Windows Server 2008 Enterprise','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(81,'OSNAME','Microsoft Windows Server 2008 R2 Enterprise','Microsoft Windows Server 2008 R2 Enterprise','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(86,'OSNAME','Microsoft Windows Server 2008 R2 Standard','Microsoft Windows Server 2008 R2 Standard','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
;
INSERT INTO episode.converter (id,converter_id,originalvalue,convertedvalue,create_dt,create_id,update_dt,update_id) VALUES 
(94,'OSNAME','Microsoft Windows Server 2012 R2 Foundation','Microsoft Windows Server 2012 R2 Foundation','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(96,'OSNAME','Microsoft Windows Server 2012 R2 Standard','Microsoft Windows Server 2012 R2 Standard','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(100,'OSNAME','Microsoft Windows Server 2012 Standard','Microsoft Windows Server 2012 Standard','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(103,'OSNAME','Microsoft Windows Server 2016 Standard','Microsoft Windows Server 2016 Standard','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(105,'OSNAME','Microsoft Windows Storage Server 2012 R2 Standard','Microsoft Windows Storage Server 2012 R2 Standard','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(69,'OSNAME','Microsoft Windows 7 Enterprise K x64 Edition','Microsoft Windows 7 Enterprise K','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(72,'OSNAME','Microsoft Windows Server 2003 (32-bit)','Microsoft Windows Server 2003','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(73,'OSNAME','Microsoft Windows Server 2003 Standard (32-bit)','Microsoft Windows Server 2003 Standard','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(74,'OSNAME','Microsoft Windows Server 2003 Standard (64-bit)','Microsoft Windows Server 2003 Standard','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(75,'OSNAME','Microsoft Windows Server 2003 Standard Edition','Microsoft Windows Server 2003 Standard','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
;
INSERT INTO episode.converter (id,converter_id,originalvalue,convertedvalue,create_dt,create_id,update_dt,update_id) VALUES 
(76,'OSNAME','Microsoft Windows Server 2003, Enterprise Edition (32-bit)','Microsoft Windows Server 2003 Standard Enterprise','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(77,'OSNAME','Microsoft Windows Server 2003, Standard Edition (32-bit)','Microsoft Windows Server 2003 Standard Enterprise','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(78,'OSNAME','Microsoft Windows Server 2008 (64-bit)','Microsoft Windows Server 2008','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(80,'OSNAME','Microsoft Windows Server 2008 R2 (64-bit)','Microsoft Windows Server 2008 R2','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(82,'OSNAME','Microsoft Windows Server 2008 R2 Enterprise  ???(????) version','Microsoft Windows Server 2008 R2 Enterprise','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(83,'OSNAME','Microsoft Windows Server 2008 R2 Enterprise  English (United States) version','Microsoft Windows Server 2008 R2 Enterprise','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(84,'OSNAME','Microsoft Windows Server 2008 R2 Enterprise  한국어(대한민국) version','Microsoft Windows Server 2008 R2 Enterprise','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(85,'OSNAME','Microsoft Windows Server 2008 R2 Enterprise x64 Edition','Microsoft Windows Server 2008 R2 Enterprise','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(87,'OSNAME','Microsoft Windows Server 2008 R2 Standard  ???(????) version','Microsoft Windows Server 2008 R2 Standard','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(88,'OSNAME','Microsoft Windows Server 2008 R2 Standard  English (United States) version','Microsoft Windows Server 2008 R2 Standard','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
;
INSERT INTO episode.converter (id,converter_id,originalvalue,convertedvalue,create_dt,create_id,update_dt,update_id) VALUES 
(89,'OSNAME','Microsoft Windows Server 2008 R2 Standard  영어(미국) version','Microsoft Windows Server 2008 R2 Standard','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(90,'OSNAME','Microsoft Windows Server 2008 R2 Standard  한국어(대한민국) version','Microsoft Windows Server 2008 R2 Standard','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(91,'OSNAME','Microsoft Windows Server 2008 R2 Standard x64 Edition','Microsoft Windows Server 2008 R2 Standard','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(92,'OSNAME','Microsoft Windows Server 2008 Standard x64 Edition','Microsoft Windows Server 2008 Standard','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(93,'OSNAME','Microsoft Windows Server 2012 (64-bit)','Microsoft Windows Server 2012','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(95,'OSNAME','Microsoft Windows Server 2012 R2 Foundation 한국어(대한민국) version','Microsoft Windows Server 2012 R2 Foundation','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(97,'OSNAME','Microsoft Windows Server 2012 R2 Standard ???(????) version','Microsoft Windows Server 2012 R2 Standard','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(98,'OSNAME','Microsoft Windows Server 2012 R2 Standard Korean (Korea) version','Microsoft Windows Server 2012 R2 Standard','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(99,'OSNAME','Microsoft Windows Server 2012 R2 Standard 한국어(대한민국) version','Microsoft Windows Server 2012 R2 Standard','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(101,'OSNAME','Microsoft Windows Server 2012 Standard 한국어(대한민국) version','Microsoft Windows Server 2012 Standard','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
;
INSERT INTO episode.converter (id,converter_id,originalvalue,convertedvalue,create_dt,create_id,update_dt,update_id) VALUES 
(102,'OSNAME','Microsoft Windows Server 2016 (64-bit)','Microsoft Windows Server 2016','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(104,'OSNAME','Microsoft Windows Server 2016 Standard 한국어(대한민국) version','Microsoft Windows Server 2016 Standard','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(106,'OSNAME','Microsoft Windows XP Professional (32-bit)','Microsoft Windows XP Professional','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(107,'OSNAME','Microsoft(R) Windows(R) Server 2003 Standard x64 Edition','Microsoft Windows Server 2003 Standard','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(108,'OSNAME','Microsoft(R) Windows(R) Server 2003, Enterprise Edition','Microsoft Windows Server 2003 Enterprise Edition','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(109,'OSNAME','Microsoft(R) Windows(R) Server 2003, Standard Edition','Microsoft Windows Server 2003 Standard','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(110,'OSNAME','Oracle Linux 7 (64-bit)','Oracle Linux 7','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(111,'OSNAME','Oracle Linux Server 7.3','Oracle Linux 7','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(130,'OSNAME','Solaris','Solaris','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(17,'COMPANYNAME','IBM','IBM Corporation','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
;
INSERT INTO episode.converter (id,converter_id,originalvalue,convertedvalue,create_dt,create_id,update_dt,update_id) VALUES 
(6,'COMPANYNAME','Brocade Communication Systems, Inc. (Foundry)','Brocade Communication Systems Inc.','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(68,'OSNAME','Microsoft Windows 7 Enterprise K  한국어(대한민국) version','Microsoft Windows 7 Enterprise K','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(112,'OSNAME','Other (64-bit)','Other','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(113,'OSNAME','Other 3.x or later Linux (64-bit)','Other 3.x or later Linux','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(114,'OSNAME','Red Hat Enterprise Linux 6 (32-bit)','Red Hat Enterprise Linux 6','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(115,'OSNAME','Red Hat Enterprise Linux 6 (64-bit)','Red Hat Enterprise Linux 6','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(116,'OSNAME','Red Hat Enterprise Linux 7 (64-bit)','Red Hat Enterprise Linux 7','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(117,'OSNAME','Red Hat Enterprise Linux Server 7.2 (Maipo)','Red Hat Enterprise Linux 7 (Maipo)','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(118,'OSNAME','Red Hat Enterprise Linux Server 7.4 (Maipo)','Red Hat Enterprise Linux 7 (Maipo)','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(119,'OSNAME','Red Hat Enterprise Linux Server 7.6 (Maipo)','Red Hat Enterprise Linux 7 (Maipo)','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
;
INSERT INTO episode.converter (id,converter_id,originalvalue,convertedvalue,create_dt,create_id,update_dt,update_id) VALUES 
(120,'OSNAME','Red Hat Enterprise Linux Server release 5.9 (Tikanga)','Red Hat Enterprise Linux 5 (Tikanga)','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(121,'OSNAME','Red Hat Enterprise Linux Server release 6.4 (Santiago)','Red Hat Enterprise Linux 6 (Santiago)','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(122,'OSNAME','Red Hat Enterprise Linux Server release 6.7 (Santiago)','Red Hat Enterprise Linux 6 (Santiago)','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(123,'OSNAME','Red Hat Enterprise Linux Server release 6.8 (Santiago)','Red Hat Enterprise Linux 6 (Santiago)','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(124,'OSNAME','Red Hat Enterprise Linux Server release 6.9 (Santiago)','Red Hat Enterprise Linux 6 (Santiago)','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(125,'OSNAME','Red Hat Enterprise Linux Server release 7.2 (Maipo)','Red Hat Enterprise Linux 7 (Maipo)','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(126,'OSNAME','Red Hat Enterprise Linux Server release 7.3 (Maipo)','Red Hat Enterprise Linux 7 (Maipo)','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(127,'OSNAME','Red Hat Enterprise Linux Server release 7.4 (Maipo)','Red Hat Enterprise Linux 7 (Maipo)','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(128,'OSNAME','Red Hat Enterprise Linux Server release 7.6 (Maipo)','Red Hat Enterprise Linux 7 (Maipo)','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(129,'OSNAME','SUSE Linux Enterprise 12 (64-bit)','SUSE Linux Enterprise 12','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
;
INSERT INTO episode.converter (id,converter_id,originalvalue,convertedvalue,create_dt,create_id,update_dt,update_id) VALUES 
(131,'OSNAME','Suse Linux Enterprise 11 (64-bit)','Suse Linux Enterprise 11','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(133,'OSNAME','Win2008R2 6.1.7600','Microsoft Windows Server 2008 R2','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(134,'OSNAME','Win2008R2 6.1.7601','Microsoft Windows Server 2008 R2','2019-11-07 17:00:49.592','esadmin','2019-11-07 17:00:49.592','esadmin')
,(431,'OSNAME','Vmnix','Vmnix','2019-11-21 10:41:48.406','esadmin','2019-11-21 10:41:48.406','esadmin')
,(435,'COMPANYNAME','','','2019-11-23 06:13:57.844','esadmin','2019-11-23 06:13:57.844','esadmin')
,(436,'COMPANYNAME','strawberryperl.com project','strawberryperl.com project','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(437,'COMPANYNAME','Sophos Plc','Sophos Plc','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(438,'COMPANYNAME','ActiveState Tool Corp.','ActiveState Tool Corp.','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(439,'COMPANYNAME','RARLab','RARLab','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(440,'COMPANYNAME','Mozilla.org','Mozilla.org','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
;
INSERT INTO episode.converter (id,converter_id,originalvalue,convertedvalue,create_dt,create_id,update_dt,update_id) VALUES 
(441,'COMPANYNAME','CollabNet Inc','CollabNet Inc','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(442,'COMPANYNAME','Acronis Inc.','Acronis Inc.','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(443,'COMPANYNAME','Jetform Corporation','Jetform Corporation','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(412,'WASNAM','tomcat 7.1','tomcat 8.2','2019-11-12 06:29:03.385','Z03232','2019-11-20 00:21:33.204','105018')
,(444,'COMPANYNAME','AhnLab, Inc.','AhnLab, Inc.','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(445,'COMPANYNAME','IBM Corp. and HCL Technologies Limited','IBM Corp. and HCL Technologies Limited','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(446,'COMPANYNAME','PortableApps.com','PortableApps.com','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(447,'COMPANYNAME','Apple Computer Corporation','Apple Computer Corporation','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(448,'COMPANYNAME','Apache Software Foundation','Apache Software Foundation','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(449,'COMPANYNAME','EMC Corporation','EMC Corporation','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
;
INSERT INTO episode.converter (id,converter_id,originalvalue,convertedvalue,create_dt,create_id,update_dt,update_id) VALUES 
(450,'COMPANYNAME','Broadcom','Broadcom','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(451,'COMPANYNAME','Info-ZIP','Info-ZIP','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(452,'COMPANYNAME','반디소프트','반디소프트','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(453,'COMPANYNAME','SafeNet','SafeNet','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(454,'COMPANYNAME','InstallShield Software Corporation','InstallShield Software Corporation','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(455,'COMPANYNAME','Design Science Inc.','Design Science Inc.','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(456,'COMPANYNAME','Brian Kato','Brian Kato','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(457,'COMPANYNAME','Riverbed Technology Inc.','Riverbed Technology Inc.','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(458,'COMPANYNAME','Hancom Inc.','Hancom Inc.','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(459,'COMPANYNAME','ATI Technologies Inc','ATI Technologies Inc','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
;
INSERT INTO episode.converter (id,converter_id,originalvalue,convertedvalue,create_dt,create_id,update_dt,update_id) VALUES 
(460,'COMPANYNAME','Notepad++ Team','Notepad++ Team','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(461,'COMPANYNAME','Intel','Intel','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(462,'COMPANYNAME','Fyodor','Fyodor','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(463,'COMPANYNAME','Hummingbird Enterprise','Hummingbird Enterprise','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(464,'COMPANYNAME','SafeNet Inc.','SafeNet Inc.','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(465,'COMPANYNAME','Siemens','Siemens','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(466,'COMPANYNAME','Sublime Text','Sublime Text','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(467,'COMPANYNAME','Flexera Software Inc.','Flexera Software Inc.','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(468,'COMPANYNAME','Greg Roelofs','Greg Roelofs','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(469,'COMPANYNAME','WinSCP.net','WinSCP.net','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
;
INSERT INTO episode.converter (id,converter_id,originalvalue,convertedvalue,create_dt,create_id,update_dt,update_id) VALUES 
(470,'COMPANYNAME','Roxio Inc','Roxio Inc','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(471,'COMPANYNAME','The Eraser Project','The Eraser Project','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(472,'COMPANYNAME','Adobe','Adobe','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(473,'COMPANYNAME','aaa','aaa','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(474,'COMPANYNAME','PostgreSQL Global Development Group','PostgreSQL Global Development Group','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(475,'COMPANYNAME','Softland SRL','Softland SRL','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(476,'COMPANYNAME','Thibaut Lauziere','Thibaut Lauziere','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(477,'COMPANYNAME','JAM Software','JAM Software','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(478,'COMPANYNAME','NUnit.org','NUnit.org','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(479,'COMPANYNAME','Pkware Inc','Pkware Inc','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
;
INSERT INTO episode.converter (id,converter_id,originalvalue,convertedvalue,create_dt,create_id,update_dt,update_id) VALUES 
(480,'COMPANYNAME','MySQL AB','MySQL AB','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(481,'COMPANYNAME','Lenovo Group Ltd.','Lenovo Group Ltd.','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(482,'COMPANYNAME','Igor Pavlov','Igor Pavlov','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(483,'COMPANYNAME','Aastra Technologies Ltd.','Aastra Technologies Ltd.','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(484,'COMPANYNAME','Sun','Sun','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(485,'COMPANYNAME','NextPage Inc.','NextPage Inc.','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(486,'COMPANYNAME','Bram Moolenaar','Bram Moolenaar','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(487,'COMPANYNAME','OHS','OHS','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(488,'COMPANYNAME','Acronis','Acronis','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(489,'COMPANYNAME','BeyondTrust','BeyondTrust','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
;
INSERT INTO episode.converter (id,converter_id,originalvalue,convertedvalue,create_dt,create_id,update_dt,update_id) VALUES 
(490,'COMPANYNAME','WatchTek','WatchTek','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(491,'COMPANYNAME','The Git Development Community','The Git Development Community','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(492,'COMPANYNAME','SAP','SAP','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(493,'COMPANYNAME','TeamViewer GmbH','TeamViewer GmbH','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(494,'COMPANYNAME','Hummingbird Communications Ltd','Hummingbird Communications Ltd','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(495,'COMPANYNAME','SOLARWINDS','SOLARWINDS','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(496,'COMPANYNAME','FastStone Soft','FastStone Soft','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(497,'COMPANYNAME','ES-Computing','ES-Computing','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(498,'COMPANYNAME','Symantec','Symantec','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(499,'COMPANYNAME','NetSarang Computer Inc.','NetSarang Computer Inc.','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
;
INSERT INTO episode.converter (id,converter_id,originalvalue,convertedvalue,create_dt,create_id,update_dt,update_id) VALUES 
(500,'COMPANYNAME','Budy Setiawan Kusumah','Budy Setiawan Kusumah','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(501,'COMPANYNAME','The OpenLDAP Foundation','The OpenLDAP Foundation','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(502,'COMPANYNAME','Martin Prikryl','Martin Prikryl','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(503,'COMPANYNAME','Atanas Neshkov','Atanas Neshkov','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(504,'COMPANYNAME','ARTIFEX','ARTIFEX','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(505,'COMPANYNAME','wireshark','wireshark','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(506,'COMPANYNAME','Siebel System Inc','Siebel System Inc','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(507,'COMPANYNAME','DASSAULT SYSTEMES','DASSAULT SYSTEMES','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(508,'COMPANYNAME','CentOS','CentOS','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(509,'COMPANYNAME','Research In Motion Limited','Research In Motion Limited','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
;
INSERT INTO episode.converter (id,converter_id,originalvalue,convertedvalue,create_dt,create_id,update_dt,update_id) VALUES 
(510,'COMPANYNAME','Rockwell','Rockwell','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(511,'COMPANYNAME','Hancom','Hancom','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(512,'COMPANYNAME','Oracle','Oracle','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(513,'COMPANYNAME','RED HAT','RED HAT','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(514,'COMPANYNAME','Advanced Micro Devices Inc.','Advanced Micro Devices Inc.','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(515,'COMPANYNAME','AhnLab Inc.','AhnLab Inc.','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(516,'COMPANYNAME','Christian Ghisler','Christian Ghisler','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(517,'COMPANYNAME','RealVNC Ltd','RealVNC Ltd','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(518,'COMPANYNAME','PrestoSoft','PrestoSoft','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(519,'COMPANYNAME','MadCap Software Inc.','MadCap Software Inc.','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
;
INSERT INTO episode.converter (id,converter_id,originalvalue,convertedvalue,create_dt,create_id,update_dt,update_id) VALUES 
(520,'COMPANYNAME','Macrovision Corp.','Macrovision Corp.','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(521,'COMPANYNAME','QLogic','QLogic','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(522,'COMPANYNAME','OpenSSL Project','OpenSSL Project','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(523,'COMPANYNAME','IDM Computer Solutions, Inc.','IDM Computer Solutions, Inc.','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(524,'COMPANYNAME','DK Multimedia','DK Multimedia','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(525,'COMPANYNAME','WebEx Communications Inc.','WebEx Communications Inc.','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(526,'COMPANYNAME','UGS','UGS','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(527,'COMPANYNAME','CACE Technologies','CACE Technologies','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(528,'COMPANYNAME','ACTIVESTATE','ACTIVESTATE','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(529,'COMPANYNAME','ESTSoft Corp.','ESTSoft Corp.','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
;
INSERT INTO episode.converter (id,converter_id,originalvalue,convertedvalue,create_dt,create_id,update_dt,update_id) VALUES 
(530,'COMPANYNAME','FileZilla.org','FileZilla.org','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(531,'COMPANYNAME','Ksign CO','Ksign CO','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(532,'COMPANYNAME','VisualSVN Ltd.','VisualSVN Ltd.','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(533,'COMPANYNAME','ActiveState Software','ActiveState Software','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(534,'COMPANYNAME','ActiveState Software Inc. (see SOPHOS)','ActiveState Software Inc. (see SOPHOS)','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(535,'COMPANYNAME','SPSS Inc','SPSS Inc','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(536,'COMPANYNAME','Kodak','Kodak','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(537,'COMPANYNAME','Microfocus','Microfocus','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(538,'COMPANYNAME','KSign','KSign','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(539,'COMPANYNAME','Heidisql.com','Heidisql.com','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
;
INSERT INTO episode.converter (id,converter_id,originalvalue,convertedvalue,create_dt,create_id,update_dt,update_id) VALUES 
(540,'COMPANYNAME','ESTsoft Corp.','ESTsoft Corp.','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(541,'COMPANYNAME','Borland','Borland','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(542,'COMPANYNAME','C. Ghisler Co.','C. Ghisler Co.','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(543,'COMPANYNAME','Kofax Image Products','Kofax Image Products','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(544,'COMPANYNAME','Sysinternals LLC','Sysinternals LLC','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(545,'COMPANYNAME','Conexant Systems Inc.','Conexant Systems Inc.','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(546,'COMPANYNAME','DataDirect Technologies','DataDirect Technologies','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(547,'COMPANYNAME','Allround Automations','Allround Automations','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(548,'COMPANYNAME','ZDNet','ZDNet','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(549,'COMPANYNAME','Datalogic S.p.A','Datalogic S.p.A','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
;
INSERT INTO episode.converter (id,converter_id,originalvalue,convertedvalue,create_dt,create_id,update_dt,update_id) VALUES 
(550,'COMPANYNAME','Wireshark.org','Wireshark.org','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(551,'COMPANYNAME','KYOCERA MITA CORPORATION','KYOCERA MITA CORPORATION','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(552,'COMPANYNAME','VanDyke Software Inc.','VanDyke Software Inc.','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(553,'COMPANYNAME','Google Inc.','Google Inc.','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(554,'COMPANYNAME','ImageMagick Studio','ImageMagick Studio','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(555,'COMPANYNAME','Intel Network Systems Inc.','Intel Network Systems Inc.','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(556,'COMPANYNAME','JasperSoft Corp.','JasperSoft Corp.','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(557,'COMPANYNAME','VMware','VMware','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(558,'COMPANYNAME','Deerfield com','Deerfield com','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(559,'COMPANYNAME','MariaDB Foundation','MariaDB Foundation','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
;
INSERT INTO episode.converter (id,converter_id,originalvalue,convertedvalue,create_dt,create_id,update_dt,update_id) VALUES 
(560,'COMPANYNAME','Simon Tatham','Simon Tatham','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(561,'COMPANYNAME','Dell Computer Corporation','Dell Computer Corporation','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(562,'COMPANYNAME','Realtek','Realtek','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(563,'COMPANYNAME','MathWorks','MathWorks','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(564,'COMPANYNAME','Raize Software Inc.','Raize Software Inc.','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(565,'COMPANYNAME','Foxit Software Company','Foxit Software Company','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(566,'COMPANYNAME','SEIKO','SEIKO','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(567,'COMPANYNAME','JOSHUA F. MADISON','JOSHUA F. MADISON','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(568,'COMPANYNAME','WinMerge.org','WinMerge.org','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(569,'COMPANYNAME','Netscape','Netscape','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
;
INSERT INTO episode.converter (id,converter_id,originalvalue,convertedvalue,create_dt,create_id,update_dt,update_id) VALUES 
(570,'COMPANYNAME','Bandisoft','Bandisoft','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(571,'COMPANYNAME','IBE Software','IBE Software','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(572,'COMPANYNAME','Sung Dong Kim','Sung Dong Kim','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(573,'COMPANYNAME','PreEmptive Solutions','PreEmptive Solutions','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(574,'COMPANYNAME','BMC','BMC','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(575,'COMPANYNAME','cyhsoft','cyhsoft','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(576,'COMPANYNAME','Parametric Technology Corporation','Parametric Technology Corporation','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(577,'COMPANYNAME','Shirouzu Hiroaki','Shirouzu Hiroaki','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(578,'COMPANYNAME','UNICOM SYSTEMS','UNICOM SYSTEMS','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(579,'COMPANYNAME','America Online Inc.','America Online Inc.','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
;
INSERT INTO episode.converter (id,converter_id,originalvalue,convertedvalue,create_dt,create_id,update_dt,update_id) VALUES 
(580,'COMPANYNAME','BZIP2 Organization','BZIP2 Organization','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(581,'COMPANYNAME','Scooter Software Inc','Scooter Software Inc','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(582,'COMPANYNAME','Zebra Technologies Corporation','Zebra Technologies Corporation','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(583,'COMPANYNAME','don.h@free.fr','don.h@free.fr','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(584,'COMPANYNAME','Conover Company','Conover Company','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(585,'COMPANYNAME','Free Software Foundation, Inc.','Free Software Foundation, Inc.','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(586,'COMPANYNAME','H. Shirouzu','H. Shirouzu','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(587,'COMPANYNAME','TortoiseCVS','TortoiseCVS','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(588,'COMPANYNAME','Python Software Foundation','Python Software Foundation','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(589,'COMPANYNAME','Irfan Skiljan','Irfan Skiljan','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
;
INSERT INTO episode.converter (id,converter_id,originalvalue,convertedvalue,create_dt,create_id,update_dt,update_id) VALUES 
(590,'COMPANYNAME','pgAdmin Development Team','pgAdmin Development Team','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(591,'COMPANYNAME','IDM Computer Solution','IDM Computer Solution','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(592,'COMPANYNAME','Acro Software Inc.','Acro Software Inc.','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(593,'COMPANYNAME','OpenSSH','OpenSSH','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
,(594,'COMPANYNAME','The PHP Group','The PHP Group','2019-11-26 10:28:55.132','esadmin','2019-11-26 10:28:55.132','esadmin')
;