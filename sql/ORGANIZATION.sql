-- Drop table

-- DROP TABLE episode.organization;

CREATE TABLE episode.organization (
	id int4 NOT NULL,
	org_id varchar(20) NOT NULL,
	description varchar(50) NULL DEFAULT NULL::character varying,
	status varchar(20) NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NULL DEFAULT NULL::character varying,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NULL DEFAULT NULL::character varying,
	CONSTRAINT esorg_idx1 PRIMARY KEY (id),
	CONSTRAINT esorg_idx2 UNIQUE (org_id)
);

-- Permissions

ALTER TABLE episode.organization OWNER TO episode;
GRANT ALL ON TABLE episode.organization TO episode;

INSERT INTO episode.organization (id,org_id,description,status,create_dt,create_id,update_dt,update_id) VALUES 
(1,'ITMSG',' DAEYOUNG GROUP','ACTIVE','2019-07-04 20:43:18.819','esadmin','2019-10-28 04:25:28.994','Z03284')
,(12,'KAI','한국 항공 우주 산업','ACTIVE','2019-07-04 20:43:18.819','esadmin','2019-11-18 02:00:47.797','Z03283')
;