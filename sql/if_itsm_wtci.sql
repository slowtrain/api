-- Drop table

-- DROP TABLE episode.if_itsm_wtci;

CREATE TABLE episode.if_itsm_wtci (
	id int4 NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	ci_id varchar(64) NULL,
	ci_type_id varchar(32) NULL,
	kai_ciid varchar(64) NULL,
	ci_name varchar(255) NULL,
	usestatus varchar(20) NULL,
	CONSTRAINT if_itsm_wtci_idx1 PRIMARY KEY (id)
);
CREATE INDEX if_itsm_wtci_idx2 ON episode.if_itsm_wtci USING btree (ci_id);

-- Permissions

ALTER TABLE episode.if_itsm_wtci OWNER TO episode;
GRANT ALL ON TABLE episode.if_itsm_wtci TO episode;
