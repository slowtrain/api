-- Drop table

-- DROP TABLE episode.if_itsm_wtclsans;

CREATE TABLE episode.if_itsm_wtclsans (
	id int4 NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	ancestor varchar(32) NULL,
	ci_type_id varchar(32) NULL,
	CONSTRAINT if_itsm_wtclsans_idx1 PRIMARY KEY (id)
);

-- Permissions

ALTER TABLE episode.if_itsm_wtclsans OWNER TO episode;
GRANT ALL ON TABLE episode.if_itsm_wtclsans TO episode;
