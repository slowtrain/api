-- Drop table

-- DROP TABLE episode.software;

CREATE TABLE episode.software (
	id int4 NOT NULL,
	org_id varchar(20) NOT NULL,
	software_id varchar(30) NOT NULL,
	classstructure_id varchar(30) NOT NULL,
	ci_id varchar(30) NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	CONSTRAINT software_idx1 PRIMARY KEY (id),
	CONSTRAINT software_idx2 UNIQUE (org_id, software_id)
);

-- Permissions

ALTER TABLE episode.software OWNER TO episode;
GRANT ALL ON TABLE episode.software TO episode;
