-- Drop table

-- DROP TABLE episode.if_usergroup;

CREATE TABLE episode.if_usergroup (
	id int4 NOT NULL,
	sourcesystem varchar(20) NOT NULL,
	targetsystem varchar(20) NOT NULL,
	actionflag varchar(10) NOT NULL,
	"result" varchar(10) NULL DEFAULT 'FAIL'::character varying,
	remarks varchar(256) NULL,
	col1 varchar(200) NULL,
	col2 varchar(200) NULL,
	col3 varchar(200) NULL,
	col4 varchar(200) NULL,
	col5 varchar(200) NULL,
	col6 varchar(200) NULL,
	col7 varchar(200) NULL,
	col8 varchar(200) NULL,
	col9 varchar(200) NULL,
	col10 varchar(200) NULL,
	col11 varchar(200) NULL,
	col12 varchar(200) NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NULL DEFAULT 'intadmin'::character varying,
	CONSTRAINT if_usergroup_idx1 PRIMARY KEY (id)
);

-- Permissions

ALTER TABLE episode.if_usergroup OWNER TO episode;
GRANT ALL ON TABLE episode.if_usergroup TO episode;
