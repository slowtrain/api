-- Drop table

-- DROP TABLE episode.scheduleraction;

CREATE TABLE episode.scheduleraction (
	id int4 NOT NULL,
	scheduler_id varchar(30) NOT NULL,
	"action" varchar(10) NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NULL DEFAULT NULL::character varying,
	CONSTRAINT scheduleraction_idx1 PRIMARY KEY (id)
);

-- Permissions

ALTER TABLE episode.scheduleraction OWNER TO episode;
GRANT ALL ON TABLE episode.scheduleraction TO episode;
