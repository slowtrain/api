-- Drop table

-- DROP TABLE episode.rolegroup;

CREATE TABLE episode.rolegroup (
	id int4 NOT NULL,
	role_id varchar(20) NOT NULL,
	usergroup_id varchar(20) NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NULL DEFAULT NULL::character varying,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NULL DEFAULT NULL::character varying,
	CONSTRAINT rolegroup_idx1 PRIMARY KEY (id),
	CONSTRAINT rolegroup_idx2 UNIQUE (role_id, usergroup_id)
);

-- Permissions

ALTER TABLE episode.rolegroup OWNER TO episode;
GRANT ALL ON TABLE episode.rolegroup TO episode;
