-- Drop table

-- DROP TABLE episode.swlicrel;

CREATE TABLE episode.swlicrel (
	id int4 NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	swlicense_id1 varchar(30) NULL,
	swlicense_id2 varchar(30) NULL,
	org_id varchar(20) NULL,
	CONSTRAINT swlicrel_idx1 PRIMARY KEY (id)
);

-- Permissions

ALTER TABLE episode.swlicrel OWNER TO episode;
GRANT ALL ON TABLE episode.swlicrel TO episode;
