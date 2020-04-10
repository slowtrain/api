-- Drop table

-- DROP TABLE episode.usergroupancestor;

CREATE TABLE episode.usergroupancestor (
	id int4 NOT NULL,
	org_id varchar(20) NOT NULL,
	ancestor varchar(20) NOT NULL,
	usergroup_id varchar(20) NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NULL DEFAULT NULL::character varying,
	CONSTRAINT usergroupancestor_idx1 PRIMARY KEY (id),
	CONSTRAINT usergroupancestor_idx2 UNIQUE (org_id, ancestor, usergroup_id)
);
CREATE INDEX usergroupancestor_idx3 ON episode.usergroupancestor USING btree (org_id, usergroup_id);

-- Permissions

ALTER TABLE episode.usergroupancestor OWNER TO episode;
GRANT ALL ON TABLE episode.usergroupancestor TO episode;
