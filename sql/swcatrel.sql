-- Drop table

-- DROP TABLE episode.swcatrel;

CREATE TABLE episode.swcatrel (
	id int4 NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	swcat_id1 varchar(30) NULL,
	swcat_id2 varchar(30) NULL,
	org_id varchar(20) NULL,
	CONSTRAINT swcatrel_idx1 PRIMARY KEY (id)
);

-- Permissions

ALTER TABLE episode.swcatrel OWNER TO episode;
GRANT ALL ON TABLE episode.swcatrel TO episode;
