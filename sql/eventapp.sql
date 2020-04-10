-- Drop table

-- DROP TABLE episode.eventapp;

CREATE TABLE episode.eventapp (
	id int4 NOT NULL,
	ci_id varchar(30) NULL,
	coll_dt timestamp NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	total_time int4 NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	CONSTRAINT eventapp_idx1 PRIMARY KEY (id),
	CONSTRAINT eventapp_idx2 UNIQUE (ci_id, coll_dt)
);

-- Permissions

ALTER TABLE episode.eventapp OWNER TO episode;
GRANT ALL ON TABLE episode.eventapp TO episode;
