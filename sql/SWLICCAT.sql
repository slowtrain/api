-- Drop table

-- DROP TABLE episode.swliccat;

CREATE TABLE episode.swliccat (
	id int4 NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	swlicense_id varchar(30) NULL,
	swcat_id varchar(30) NULL,
	org_id varchar(20) NULL,
	CONSTRAINT swliccat_idx1 PRIMARY KEY (id)
);

-- Permissions

ALTER TABLE episode.swliccat OWNER TO episode;
GRANT ALL ON TABLE episode.swliccat TO episode;
