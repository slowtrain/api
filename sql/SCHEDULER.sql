-- Drop table

-- DROP TABLE episode.scheduler;

CREATE TABLE episode.scheduler (
	id int4 NOT NULL,
	scheduler_id varchar(30) NOT NULL,
	description varchar(50) NOT NULL,
	"expression" varchar(30) NOT NULL,
	"parameter" varchar(100) NULL,
	status varchar(10) NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NULL DEFAULT NULL::character varying,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NULL DEFAULT NULL::character varying,
	historyquantity int4 NOT NULL DEFAULT 100,
	CONSTRAINT scheduler_idx1 PRIMARY KEY (id),
	CONSTRAINT scheduler_idx2 UNIQUE (scheduler_id)
);

-- Permissions

ALTER TABLE episode.scheduler OWNER TO episode;
GRANT ALL ON TABLE episode.scheduler TO episode;
