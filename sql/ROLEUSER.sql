-- Drop table

-- DROP TABLE episode.roleuser;

CREATE TABLE episode.roleuser (
	id int4 NOT NULL,
	role_id varchar(20) NOT NULL,
	username varchar(20) NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NULL DEFAULT NULL::character varying,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NULL DEFAULT NULL::character varying,
	CONSTRAINT roleuser_idx1 PRIMARY KEY (id),
	CONSTRAINT roleuser_role_id_username_key UNIQUE (role_id, username)
);

-- Permissions

ALTER TABLE episode.roleuser OWNER TO episode;
GRANT ALL ON TABLE episode.roleuser TO episode;

INSERT INTO roleuser (id,role_id,username,create_dt,create_id,update_dt,update_id) VALUES 
(2,'ESADMIN','esadmin','2019-07-05 06:11:24.642','esadmin','2019-07-05 06:11:24.642','esadmin');