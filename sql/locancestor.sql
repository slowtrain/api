-- Drop table

-- DROP TABLE episode.locancestor;

CREATE TABLE episode.locancestor (
	id int4 NOT NULL,
	org_id varchar(20) NOT NULL,
	ancestor varchar(30) NOT NULL,
	location_id varchar(30) NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NULL DEFAULT NULL::character varying,
	CONSTRAINT locancestor_idx1 PRIMARY KEY (id),
	CONSTRAINT locancestor_idx2 UNIQUE (org_id, ancestor, location_id)
);
CREATE INDEX locancestor_idx3 ON episode.locancestor USING btree (org_id, location_id);

-- Permissions

ALTER TABLE episode.locancestor OWNER TO episode;
GRANT ALL ON TABLE episode.locancestor TO episode;

INSERT INTO episode.locancestor (id,org_id,ancestor,location_id,create_dt,create_id) VALUES 
(114,'KAI','HQ','HQ','2019-08-26 01:30:08.259','Z03283')
,(115,'KAI','HQ','HQ-BK','2019-08-26 01:30:37.755','Z03283')
,(116,'KAI','BK','HQ-BK','2019-08-26 01:30:37.756','Z03283')
,(117,'KAI','HQ','HQ-BK-BD','2019-08-26 01:31:12.213','Z03283')
,(118,'KAI','BK','HQ-BK-BD','2019-08-26 01:31:12.214','Z03283')
,(119,'KAI','BD','HQ-BK-BD','2019-08-26 01:31:12.215','Z03283')
,(120,'KAI','HQ','HQ-HG','2019-08-26 01:32:02.687','Z03283')
,(121,'KAI','HG','HQ-HG','2019-08-26 01:32:02.688','Z03283')
,(122,'KAI','HQ','HQ-HG-HD','2019-08-26 01:32:34.481','Z03283')
,(123,'KAI','HG','HQ-HG-HD','2019-08-26 01:32:34.482','Z03283')
;
INSERT INTO episode.locancestor (id,org_id,ancestor,location_id,create_dt,create_id) VALUES 
(124,'KAI','HD','HQ-HG-HD','2019-08-26 01:32:34.483','Z03283')
,(125,'KAI','HQ','HQ-JR','2019-08-26 01:33:01.939','Z03283')
,(126,'KAI','JR','HQ-JR','2019-08-26 01:33:01.940','Z03283')
,(127,'KAI','HQ','HQ-BP','2019-08-26 01:33:28.344','Z03283')
,(128,'KAI','BP','HQ-BP','2019-08-26 01:33:28.345','Z03283')
,(129,'KAI','HQ','HQ-BG','2019-08-26 01:33:52.222','Z03283')
,(130,'KAI','BG','HQ-BG','2019-08-26 01:33:52.224','Z03283')
,(131,'KAI','HQ','HQ-DC','2019-08-26 01:34:15.646','Z03283')
,(132,'KAI','DC','HQ-DC','2019-08-26 01:34:15.647','Z03283')
,(133,'KAI','HQ','HQ-SC','2019-08-26 01:34:38.334','Z03283')
;
INSERT INTO episode.locancestor (id,org_id,ancestor,location_id,create_dt,create_id) VALUES 
(134,'KAI','SC','HQ-SC','2019-08-26 01:34:38.336','Z03283')
,(135,'KAI','HQ','HQ-HC','2019-08-26 01:35:25.054','Z03283')
,(136,'KAI','HC','HQ-HC','2019-08-26 01:35:25.055','Z03283')
,(139,'KAI','HQ','HQ-GJ','2019-08-26 01:36:11.643','Z03283')
,(140,'KAI','GJ','HQ-GJ','2019-08-26 01:36:11.644','Z03283')
,(147,'KAI','HQ','HQ-DJ','2019-08-26 01:42:49.476','Z03283')
,(148,'KAI','DJ','HQ-DJ','2019-08-26 01:42:49.478','Z03283')
,(149,'KAI','HQ','HQ-GC','2019-08-26 01:43:15.053','Z03283')
,(150,'KAI','GC','HQ-GC','2019-08-26 01:43:15.055','Z03283')
,(151,'KAI','HQ','HQ-GN','2019-08-26 01:43:35.300','Z03283')
;
INSERT INTO episode.locancestor (id,org_id,ancestor,location_id,create_dt,create_id) VALUES 
(152,'KAI','GN','HQ-GN','2019-08-26 01:43:35.300','Z03283')
,(153,'KAI','HQ','HQ-JA','2019-08-26 01:44:07.508','Z03283')
,(154,'KAI','JA','HQ-JA','2019-08-26 01:44:07.509','Z03283')
,(155,'KAI','HQ','HQ-YY','2019-08-26 01:44:38.785','Z03283')
,(156,'KAI','YY','HQ-YY','2019-08-26 01:44:38.786','Z03283')
,(157,'KAI','HQ','HQ-BH','2019-08-26 01:45:00.910','Z03283')
,(158,'KAI','BH','HQ-BH','2019-08-26 01:45:00.911','Z03283')
,(159,'KAI','HQ','HQ-PH','2019-08-26 01:45:31.268','Z03283')
,(160,'KAI','PH','HQ-PH','2019-08-26 01:45:31.269','Z03283')
,(161,'KAI','HQ','HQ-HT','2019-08-26 01:45:52.810','Z03283')
;
INSERT INTO episode.locancestor (id,org_id,ancestor,location_id,create_dt,create_id) VALUES 
(162,'KAI','HT','HQ-HT','2019-08-26 01:45:52.811','Z03283')
,(163,'KAI','KA','KA','2019-08-26 01:46:18.088','Z03283')
,(164,'KAI','KA','KA-KS','2019-08-26 01:46:50.336','Z03283')
,(165,'KAI','KS','KA-KS','2019-08-26 01:46:50.337','Z03283')
,(166,'KAI','KA','KA-KJ','2019-08-26 01:47:21.725','Z03283')
,(167,'KAI','KJ','KA-KJ','2019-08-26 01:47:21.727','Z03283')
,(168,'KAI','JP','JP','2019-08-26 01:47:46.421','Z03283')
,(169,'KAI','JP','JP-JS','2019-08-26 01:48:09.921','Z03283')
,(170,'KAI','JS','JP-JS','2019-08-26 01:48:09.922','Z03283')
,(171,'KAI','JP','JP-JJ','2019-08-26 01:48:33.970','Z03283')
;
INSERT INTO episode.locancestor (id,org_id,ancestor,location_id,create_dt,create_id) VALUES 
(172,'KAI','JJ','JP-JJ','2019-08-26 01:48:33.971','Z03283')
,(173,'KAI','JP','JP-JB','2019-08-26 01:48:59.043','Z03283')
,(174,'KAI','JB','JP-JB','2019-08-26 01:48:59.044','Z03283')
,(175,'KAI','SO','SO','2019-08-26 01:49:24.444','Z03283')
,(176,'KAI','DD','DD','2019-08-26 01:49:42.208','Z03283')
,(177,'KAI','SP','SP','2019-08-26 01:50:01.971','Z03283')
,(178,'KAI','AO','AO','2019-08-26 01:50:17.325','Z03283')
,(179,'KAI','KR','KR','2019-08-26 01:50:34.011','Z03283')
,(180,'KAI','TS','TS','2019-08-26 01:50:53.041','Z03283')
,(215,'KAI','HQ','HQ-A3','2019-10-11 02:53:50.250','Z03283')
;
INSERT INTO episode.locancestor (id,org_id,ancestor,location_id,create_dt,create_id) VALUES 
(216,'KAI','A3','HQ-A3','2019-10-11 02:53:50.258','Z03283')
,(217,'KAI','HQ','HQ-HE','2019-10-11 02:54:27.219','Z03283')
,(218,'KAI','HE','HQ-HE','2019-10-11 02:54:27.220','Z03283')
;