-- Drop table

-- DROP TABLE episode.groupuser;

CREATE TABLE episode.groupuser (
	id int4 NOT NULL,
	usergroup_id varchar(20) NOT NULL,
	username varchar(20) NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NULL DEFAULT NULL::character varying,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NULL DEFAULT NULL::character varying,
	org_id varchar(20) NOT NULL,
	CONSTRAINT groupuser_idx1 PRIMARY KEY (id),
	CONSTRAINT groupuser_idx2 UNIQUE (usergroup_id, username)
);
CREATE INDEX groupuser_idx3 ON episode.groupuser USING btree (username);

-- Permissions

ALTER TABLE episode.groupuser OWNER TO episode;
GRANT ALL ON TABLE episode.groupuser TO episode;

INSERT INTO episode.groupuser (id,usergroup_id,username,create_dt,create_id,update_dt,update_id,org_id) VALUES 
(32,'KAIADMIN','Z03284','2019-10-07 05:54:28.144','Z03283','2019-10-07 05:54:28.144','Z03283','KAI')
,(33,'KAIADMIN','Z03255','2019-10-07 05:54:28.149','Z03283','2019-10-07 05:54:28.149','Z03283','KAI')
,(34,'KAIADMIN','Z03283','2019-10-07 05:54:28.153','Z03283','2019-10-07 05:54:28.153','Z03283','KAI')
,(35,'KAIADMIN','eaiuser01','2019-10-07 05:54:28.157','Z03283','2019-10-07 05:54:28.157','Z03283','KAI')
,(37,'KAIADMIN','103938','2019-10-07 05:54:28.166','Z03283','2019-10-07 05:54:28.166','Z03283','KAI')
,(38,'KAIADMIN','Z03000','2019-10-07 05:54:28.170','Z03283','2019-10-07 05:54:28.170','Z03283','KAI')
,(39,'KAIADMIN','107358','2019-10-07 05:54:28.174','Z03283','2019-10-07 05:54:28.174','Z03283','KAI')
,(40,'KAIADMIN','Z03232','2019-10-07 05:54:28.179','Z03283','2019-10-07 05:54:28.179','Z03283','KAI')
,(41,'KAIADMIN','intadmin','2019-10-07 05:54:28.183','Z03283','2019-10-07 05:54:28.183','Z03283','KAI')
,(49,'KAIADMIN','105018','2019-10-14 12:18:03.561','Z03283','2019-10-14 12:18:03.561','Z03283','KAI')
;
INSERT INTO episode.groupuser (id,usergroup_id,username,create_dt,create_id,update_dt,update_id,org_id) VALUES 
(50,'KAIADMIN','Z03259','2019-10-15 00:02:06.360','Z03283','2019-10-15 00:02:06.360','Z03283','KAI')
,(51,'KAIADMIN','Z03256','2019-10-17 05:48:22.931','Z03232','2019-10-17 05:48:22.931','Z03232','KAI')
,(52,'CIADMIN','Z03255','2019-11-04 01:29:38.436','Z03255','2019-11-04 01:29:38.436','Z03255','KAI')
,(53,'CIADMIN','Z03259','2019-11-04 04:25:12.330','Z03255','2019-11-04 04:25:12.330','Z03255','KAI')
,(54,'CIADMIN','Z03231','2019-11-05 02:27:51.029','Z03255','2019-11-05 02:27:51.029','Z03255','KAI')
,(55,'CIADMIN','Z03232','2019-11-05 02:27:51.037','Z03255','2019-11-05 02:27:51.037','Z03255','KAI')
,(56,'CIADMIN','Z03283','2019-11-05 02:27:51.037','Z03255','2019-11-05 02:27:51.037','Z03255','KAI')
,(57,'CIADMIN','Z03284','2019-11-05 05:07:46.867','Z03255','2019-11-05 05:07:46.867','Z03255','KAI')
,(58,'KAIEKN2222','Z03232','2019-11-05 07:44:09.709','intadmin','2019-11-05 07:44:09.709','intadmin','KAI')
,(59,'KAIEKN2222','Z9999999','2019-11-05 08:08:41.558','intadmin','2019-11-05 08:08:41.558','intadmin','KAI')
;