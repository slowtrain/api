-- Drop table

-- DROP TABLE episode.cispec;

CREATE TABLE episode.cispec (
	id int4 NOT NULL,
	org_id varchar(20) NOT NULL,
	ci_id varchar(30) NOT NULL,
	classstructure_id varchar(30) NOT NULL,
	classspec_id varchar(30) NOT NULL,
	"type" varchar(10) NULL,
	alnvalue varchar(100) NULL,
	numvalue numeric(15,4) NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	CONSTRAINT cispec_idx1 PRIMARY KEY (id),
	CONSTRAINT cispec_idx2 UNIQUE (org_id, ci_id, classstructure_id, classspec_id)
);
CREATE INDEX cispec_idx3 ON episode.cispec USING btree (org_id, ci_id);

-- Permissions

ALTER TABLE episode.cispec OWNER TO episode;
GRANT ALL ON TABLE episode.cispec TO episode;
