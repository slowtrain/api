-- Drop table

-- DROP TABLE episode.cihis;

CREATE TABLE episode.cihis (
	id int4 NOT NULL,
	org_id varchar(20) NOT NULL,
	ci_id varchar(30) NOT NULL,
	chgtype varchar(30) NULL,
	chgitem varchar(30) NULL,
	fromvalue varchar(200) NULL,
	tovalue varchar(200) NULL,
	chgdetail text NULL,
	chgdescription text NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	CONSTRAINT cihis_idx1 PRIMARY KEY (id)
);
CREATE INDEX cihis_idx2 ON episode.cihis USING btree (org_id, ci_id);

-- Permissions

ALTER TABLE episode.cihis OWNER TO episode;
GRANT ALL ON TABLE episode.cihis TO episode;
