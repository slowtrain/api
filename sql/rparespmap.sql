-- Drop table

-- DROP TABLE episode.rparespmap;

CREATE TABLE episode.rparespmap (
	id int4 NOT NULL,
	ci_id varchar(30) NOT NULL,
	exetools varchar(30) NOT NULL,
	linebizstep varchar(30) NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	CONSTRAINT rparespmap_idx1 PRIMARY KEY (id),
	CONSTRAINT rparespmap_idx2 UNIQUE (ci_id, exetools, linebizstep)
);

-- Permissions

ALTER TABLE episode.rparespmap OWNER TO episode;
GRANT ALL ON TABLE episode.rparespmap TO episode;
