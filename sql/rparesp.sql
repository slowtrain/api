-- Drop table

-- DROP TABLE episode.rparesp;

CREATE TABLE episode.rparesp (
	id int4 NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	ci_id varchar(30) NULL,
	robot_dt timestamp NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	linebiz_id varchar(30) NULL,
	robotresult varchar(30) NULL,
	linebizresptime int4 NULL,
	CONSTRAINT rparesp_idx1 PRIMARY KEY (id),
	CONSTRAINT rparesp_idx2 UNIQUE (ci_id, robot_dt, linebiz_id)
);

-- Permissions

ALTER TABLE episode.rparesp OWNER TO episode;
GRANT ALL ON TABLE episode.rparesp TO episode;
