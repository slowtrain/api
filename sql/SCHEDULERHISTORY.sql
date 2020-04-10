-- Drop table

-- DROP TABLE episode.schedulerhistory;

CREATE TABLE episode.schedulerhistory (
	id int4 NOT NULL,
	scheduler_id varchar(30) NOT NULL,
	ipaddress varchar(30) NOT NULL,
	start_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	end_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NULL DEFAULT NULL::character varying,
	CONSTRAINT schedulerhistory_idx1 PRIMARY KEY (id)
);

-- Permissions

ALTER TABLE episode.schedulerhistory OWNER TO episode;
GRANT ALL ON TABLE episode.schedulerhistory TO episode;
