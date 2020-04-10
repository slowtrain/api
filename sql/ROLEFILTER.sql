-- Drop table

-- DROP TABLE episode.rolefilter;

CREATE TABLE episode.rolefilter (
	id int4 NOT NULL,
	role_id varchar(20) NOT NULL,
	datafilter_id varchar(20) NOT NULL,
	whereclause varchar(100) NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NULL DEFAULT NULL::character varying,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NULL DEFAULT NULL::character varying,
	CONSTRAINT rolefilter_idx1 PRIMARY KEY (id),
	CONSTRAINT rolefilter_idx2 UNIQUE (role_id, datafilter_id)
);

-- Permissions

ALTER TABLE episode.rolefilter OWNER TO episode;
GRANT ALL ON TABLE episode.rolefilter TO episode;
