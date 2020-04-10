-- Drop table

-- DROP TABLE episode."location";

CREATE TABLE episode."location" (
	id int4 NOT NULL,
	org_id varchar(20) NOT NULL,
	parent varchar(30) NULL,
	location_id varchar(30) NOT NULL,
	status varchar(10) NOT NULL,
	"type" varchar(10) NOT NULL,
	description varchar(50) NOT NULL,
	namekor varchar(50) NOT NULL,
	nameeng varchar(50) NOT NULL,
	hierarchypath varchar(100) NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	CONSTRAINT location_idx1 PRIMARY KEY (id),
	CONSTRAINT location_idx2 UNIQUE (org_id, location_id),
	CONSTRAINT location_idx3 UNIQUE (org_id, parent, location_id)
);
CREATE INDEX location_idx4 ON episode.location USING btree (org_id, parent);

-- Permissions

ALTER TABLE episode."location" OWNER TO episode;
GRANT ALL ON TABLE episode."location" TO episode;

INSERT INTO episode."location" (id,org_id,parent,location_id,status,"type",description,namekor,nameeng,hierarchypath,create_dt,create_id,update_dt,update_id) VALUES 
(49,'KAI','','HQ','ACTIVE','OPERATING','본사','본사','HeadQuater','/HQ','2019-08-26 01:30:08.261','Z03283','2019-08-26 01:30:08.261','Z03283')
,(50,'KAI','HQ','HQ-BK','ACTIVE','OPERATING','본관','본관','BonKwan','/HQ/BK','2019-08-26 01:30:37.757','Z03283','2019-08-26 01:30:37.757','Z03283')
,(51,'KAI','HQ-BK','HQ-BK-BD','ACTIVE','OPERATING','본관동 전산기계실','본관동 전산기계실','Bonkwan Datacenter','/HQ/BK/BD','2019-08-26 01:31:12.216','Z03283','2019-08-26 01:31:12.216','Z03283')
,(52,'KAI','HQ','HQ-HG','ACTIVE','OPERATING','항공기동','항공기동','HangGongki Dong','/HQ/HG','2019-08-26 01:32:02.689','Z03283','2019-08-26 01:32:02.689','Z03283')
,(53,'KAI','HQ-HG','HQ-HG-HD','ACTIVE','OPERATING','항공기동 전산기계실','항공기동 전산기계실','HangGongki Dong Datacenter','/HQ/HG/HD','2019-08-26 01:32:34.483','Z03283','2019-08-26 01:32:34.483','Z03283')
,(54,'KAI','HQ','HQ-JR','ACTIVE','OPERATING','조립동','조립동','JoRip Dong','/HQ/JR','2019-08-26 01:33:01.941','Z03283','2019-08-26 01:33:01.941','Z03283')
,(55,'KAI','HQ','HQ-BP','ACTIVE','OPERATING','부품동','부품동','BooPoom Dong','/HQ/BP','2019-08-26 01:33:28.346','Z03283','2019-08-26 01:33:28.346','Z03283')
,(56,'KAI','HQ','HQ-BG','ACTIVE','OPERATING','복지동','복지동','BokGi Dong','/HQ/BG','2019-08-26 01:33:52.226','Z03283','2019-08-26 01:33:52.226','Z03283')
,(57,'KAI','HQ','HQ-DC','ACTIVE','OPERATING','개발센터','개발센터','Development Center','/HQ/DC','2019-08-26 01:34:15.648','Z03283','2019-08-26 01:34:15.648','Z03283')
,(58,'KAI','HQ','HQ-SC','ACTIVE','OPERATING','우주개발센터','우주개발센터','Space Development Center','/HQ/SC','2019-08-26 01:34:38.337','Z03283','2019-08-26 01:34:38.337','Z03283')
;
INSERT INTO episode."location" (id,org_id,parent,location_id,status,"type",description,namekor,nameeng,hierarchypath,create_dt,create_id,update_dt,update_id) VALUES 
(59,'KAI','HQ','HQ-HC','ACTIVE','OPERATING','인재개발센터','인재개발센터','Humen resource Development Center','/HQ/HC','2019-08-26 01:35:25.056','Z03283','2019-08-26 01:35:25.056','Z03283')
,(61,'KAI','HQ','HQ-GJ','ACTIVE','OPERATING','구조시험동','구조시험동','GoojouSihum Dong','/HQ/GJ','2019-08-26 01:36:11.645','Z03283','2019-08-26 01:36:11.645','Z03283')
,(65,'KAI','HQ','HQ-DJ','ACTIVE','OPERATING','도장동','도장동','Dojang Dong','/HQ/DJ','2019-08-26 01:42:49.478','Z03283','2019-08-26 01:42:49.478','Z03283')
,(66,'KAI','HQ','HQ-GC','ACTIVE','OPERATING','기총사격장','기총사격장','GiChong Sageckjang','/HQ/GC','2019-08-26 01:43:15.056','Z03283','2019-08-26 01:43:15.056','Z03283')
,(67,'KAI','HQ','HQ-GN','ACTIVE','OPERATING','격납고','격납고','GeockNapgo','/HQ/GN','2019-08-26 01:43:35.301','Z03283','2019-08-26 01:43:35.301','Z03283')
,(68,'KAI','HQ','HQ-JA','ACTIVE','OPERATING','자재동','자재동','Jajae Dong','/HQ/JA','2019-08-26 01:44:07.510','Z03283','2019-08-26 01:44:07.510','Z03283')
,(69,'KAI','HQ','HQ-YY','ACTIVE','OPERATING','용역동','용역동','YoungYeok Dong','/HQ/YY','2019-08-26 01:44:38.786','Z03283','2019-08-26 01:44:38.786','Z03283')
,(70,'KAI','HQ','HQ-BH','ACTIVE','OPERATING','복합재동','복합재동','BockHapjae Dong','/HQ/BH','2019-08-26 01:45:00.911','Z03283','2019-08-26 01:45:00.911','Z03283')
,(71,'KAI','HQ','HQ-PH','ACTIVE','OPERATING','페인트헹거','페인트헹거','PaintHangar','/HQ/PH','2019-08-26 01:45:31.270','Z03283','2019-08-26 01:45:31.270','Z03283')
,(72,'KAI','HQ','HQ-HT','ACTIVE','OPERATING','헬타워','헬타워','HellTower','/HQ/HT','2019-08-26 01:45:52.812','Z03283','2019-08-26 01:45:52.812','Z03283')
;
INSERT INTO episode."location" (id,org_id,parent,location_id,status,"type",description,namekor,nameeng,hierarchypath,create_dt,create_id,update_dt,update_id) VALUES 
(73,'KAI','','KA','ACTIVE','OPERATING','KAMS','KAEMS','KAEMS','/KA','2019-08-26 01:46:18.089','Z03283','2019-08-26 01:46:18.089','Z03283')
,(74,'KAI','KA','KA-KS','ACTIVE','OPERATING','KAEMS사무동','KAEMS사무동','KAEMS Samu Dong','/KA/KS','2019-08-26 01:46:50.338','Z03283','2019-08-26 01:46:50.338','Z03283')
,(75,'KAI','KA','KA-KJ','ACTIVE','OPERATING','KAEMS조립동','KAEMS조립동','KAEMS Jorip Dong','/KA/KJ','2019-08-26 01:47:21.727','Z03283','2019-08-26 01:47:21.727','Z03283')
,(76,'KAI','','JP','ACTIVE','OPERATING','종포사업장','종포사업장','Jongpho Plant','/JP','2019-08-26 01:47:46.423','Z03283','2019-08-26 01:47:46.423','Z03283')
,(77,'KAI','JP','JP-JS','ACTIVE','OPERATING','종포사무동','종포사무동','Jongpho Samu Dong','/JP/JS','2019-08-26 01:48:09.923','Z03283','2019-08-26 01:48:09.923','Z03283')
,(78,'KAI','JP','JP-JJ','ACTIVE','OPERATING','종포자재가공동','종포자재가공동','Jongpho Jajae Gagong Dong','/JP/JJ','2019-08-26 01:48:33.972','Z03283','2019-08-26 01:48:33.972','Z03283')
,(79,'KAI','JP','JP-JB','ACTIVE','OPERATING','종포발사체동','종포발사체동','Jongpho Balsache Dong','/JP/JB','2019-08-26 01:48:59.045','Z03283','2019-08-26 01:48:59.045','Z03283')
,(80,'KAI','','SO','ACTIVE','OPERATING','서울사무소','서울사무소','Seoul Office','/SO','2019-08-26 01:49:24.445','Z03283','2019-08-26 01:49:24.445','Z03283')
,(81,'KAI','','DD','ACTIVE','OPERATING','대전연구센터','대전연구센터','Daejeon Development Center','/DD','2019-08-26 01:49:42.209','Z03283','2019-08-26 01:49:42.209','Z03283')
,(82,'KAI','','SP','ACTIVE','OPERATING','산청사업장','산청사업장','Sancheong Plan','/SP','2019-08-26 01:50:01.972','Z03283','2019-08-26 01:50:01.972','Z03283')
;
INSERT INTO episode."location" (id,org_id,parent,location_id,status,"type",description,namekor,nameeng,hierarchypath,create_dt,create_id,update_dt,update_id) VALUES 
(84,'KAI','','KR','ACTIVE','OPERATING','항우연','항우연','KARI','/KR','2019-08-26 01:50:34.012','Z03283','2019-08-26 01:50:34.012','Z03283')
,(85,'KAI','','TS','ACTIVE','OPERATING','기지사무소','기지사무소','Technical Support ','/TS','2019-08-26 01:50:53.042','Z03283','2019-08-26 01:50:53.042','Z03283')
,(83,'KAI','','AO','ACTIVE','OPERATING','미주사무소','미주사무소','America Office','/AO','2019-08-26 01:50:17.326','Z03283','2019-08-29 02:47:33.460','Z03283')
,(101,'KAI','HQ','HQ-A3','ACTIVE','OPERATING','A350동','A350동','A350 Dong','/HQ/A3','2019-10-11 02:53:50.259','Z03283','2019-10-11 02:53:50.259','Z03283')
,(102,'KAI','HQ','HQ-HE','ACTIVE','OPERATING','헬기동','헬기동','HelGi Dong','/HQ/HE','2019-10-11 02:54:27.221','Z03283','2019-10-11 02:54:27.221','Z03283')
;