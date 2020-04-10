-- Drop table

-- DROP TABLE episode.if_itsm_wtcispec;

CREATE TABLE episode.if_itsm_wtcispec (
	id int8 NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	ci_id varchar(64) NULL,
	ci_attr_id varchar(64) NULL,
	ci_attr_name varchar(64) NULL,
	ci_attr_value_last varchar(255) NULL,
	ci_attr_value varchar(255) NULL,
	ci_basicinfo_subname varchar(255) NULL,
	ci_basicinfo_id varchar(32) NULL,
	CONSTRAINT if_itsm_wtcispec_idx1 PRIMARY KEY (id)
);
CREATE INDEX if_itsm_wtcispec_idx2 ON episode.if_itsm_wtcispec USING btree (ci_id);
CREATE INDEX if_itsm_wtcispec_idx3 ON episode.if_itsm_wtcispec USING btree (ci_attr_id);

-- Permissions

ALTER TABLE episode.if_itsm_wtcispec OWNER TO episode;
GRANT ALL ON TABLE episode.if_itsm_wtcispec TO episode;
