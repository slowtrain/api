-- Drop table

-- DROP TABLE episode.if_itsm_wtcls;

CREATE TABLE episode.if_itsm_wtcls (
	id int4 NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	ci_type_id varchar(32) NULL,
	ci_type_name varchar(64) NULL,
	parent_ci_type_id varchar(32) NULL,
	depth_fullname varchar(4000) NULL,
	depth_fullid varchar(4000) NULL,
	rootclassid varchar(32) NULL,
	usestatus varchar(20) NULL,
	CONSTRAINT if_itsm_wtcls_idx1 PRIMARY KEY (id)
);

-- Permissions

ALTER TABLE episode.if_itsm_wtcls OWNER TO episode;
GRANT ALL ON TABLE episode.if_itsm_wtcls TO episode;
