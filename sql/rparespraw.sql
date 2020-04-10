-- Drop table

-- DROP TABLE episode.rparespraw;

CREATE TABLE episode.rparespraw (
	id int4 NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	ci_id varchar(30) NULL,
	robot_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	robotname varchar(50) NULL,
	robotip varchar(30) NULL,
	location_id varchar(30) NULL,
	robotfloor varchar(30) NULL,
	robotresult varchar(30) NULL,
	linebiz_id varchar(30) NULL,
	linebiz varchar(30) NULL,
	exetools varchar(30) NULL,
	linebizstep varchar(30) NULL,
	linebizresult varchar(30) NULL,
	linebizstart_dt timestamp NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	linebizend_dt timestamp NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	linebizrunsec int4 NULL,
	CONSTRAINT rparespraw_idx1 PRIMARY KEY (id),
	CONSTRAINT rparespraw_idx2 UNIQUE (ci_id, robot_dt, linebiz_id, exetools, linebizstep)
);

-- Permissions

ALTER TABLE episode.rparespraw OWNER TO episode;
GRANT ALL ON TABLE episode.rparespraw TO episode;
