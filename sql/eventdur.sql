-- Drop table

-- DROP TABLE episode.eventdur;

CREATE TABLE episode.eventdur (
	id int8 NOT NULL,
	ci_id varchar(30) NULL,
	datasrckeyint int8 NULL,
	datasrckeychar varchar(32) NULL,
	datasrc varchar(16) NULL,
	origin_start_dt timestamp NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	origin_end_dt timestamp NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	origin_start_seq int8 NULL,
	origin_end_seq int8 NULL,
	duration_time int4 NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	CONSTRAINT eventdur_idx1 PRIMARY KEY (id)
);

-- Permissions

ALTER TABLE episode.eventdur OWNER TO episode;
GRANT ALL ON TABLE episode.eventdur TO episode;
