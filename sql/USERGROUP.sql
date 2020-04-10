-- Drop table

-- DROP TABLE episode.usergroup;

CREATE TABLE episode.usergroup (
	id int4 NOT NULL,
	org_id varchar(20) NOT NULL,
	parent varchar(20) NULL,
	usergroup_id varchar(20) NOT NULL,
	description varchar(50) NULL DEFAULT NULL::character varying,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NULL DEFAULT NULL::character varying,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NULL DEFAULT NULL::character varying,
	groupname_ko varchar(50) NULL,
	groupname_en varchar(50) NULL,
	status varchar(10) NOT NULL DEFAULT 'ACTIVE'::character varying,
	CONSTRAINT usergroup_idx1 PRIMARY KEY (id),
	CONSTRAINT usergroup_idx2 UNIQUE (org_id, usergroup_id)
);
CREATE INDEX usergroup_idx3 ON episode.usergroup USING btree (org_id, parent);

-- Permissions

ALTER TABLE episode.usergroup OWNER TO episode;
GRANT ALL ON TABLE episode.usergroup TO episode;
