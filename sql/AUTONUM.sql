-- Drop table

-- DROP TABLE episode.autonum;

CREATE TABLE episode.autonum (
	id int4 NOT NULL,
	org_id varchar(20) NULL,
	tablename varchar(50) NULL,
	colname varchar(50) NULL,
	prefix varchar(10) NULL,
	curnum int4 NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	CONSTRAINT autonum_idx1 PRIMARY KEY (id),
	CONSTRAINT autonum_idx2 UNIQUE (org_id, tablename, colname)
);

-- Permissions

ALTER TABLE episode.autonum OWNER TO episode;
GRANT ALL ON TABLE episode.autonum TO episode;

INSERT INTO episode.autonum (id,org_id,tablename,colname,prefix,curnum,create_dt,create_id,update_dt,update_id) VALUES 
(6,'KAI','attachment','attachment_id','AT',343,'2019-08-29 09:01:08.000','Z03284','2019-08-29 09:01:08.000','Z03284')
,(7,'KAI','company','company_id','CP',116,'2019-08-29 09:01:08.000','Z03284','2019-08-29 09:01:08.000','Z03284')
,(5,'KAI','scheduler','scheduler_id','SC',4,'2019-08-29 09:01:08.000','Z03284','2019-08-29 09:01:08.000','Z03284')
,(4,'KAI','swcat','swcat_id','SC',5436,'2019-08-29 09:01:08.000','Z03284','2019-08-29 09:01:08.000','Z03284')
,(1,'KAI','ci','ci_id','IT',556967,'2019-08-29 09:01:08.490','Z03284','2019-08-29 09:01:08.490','Z03284')
,(3,'KAI','swlicense','swlicense_id','LI',230,'2019-09-09 13:01:19.187','Z03284','2019-09-09 13:01:19.187','Z03284')
,(2,'KAI','disci','disci_id','DI',63993,'2019-08-29 09:01:08.000','Z03284','2019-08-29 09:01:08.000','Z03284')
,(15,'KAI','cisw','cisw_id','SW',7221,'2019-08-29 09:01:08.000','Z03284','2019-08-29 09:01:08.000','Z03284')
;