-- Drop table

-- DROP TABLE episode.userci;

CREATE TABLE episode.userci (
	id int4 NOT NULL,
	username varchar(20) NOT NULL,
	org_id varchar(20) NOT NULL,
	ci_id varchar(30) NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NULL DEFAULT NULL::character varying,
	CONSTRAINT userci_idx1 PRIMARY KEY (id),
	CONSTRAINT userci_idx2 UNIQUE (username, org_id, ci_id)
);
CREATE INDEX userci_idx3 ON episode.userci USING btree (username);

-- Permissions

ALTER TABLE episode.userci OWNER TO episode;
GRANT ALL ON TABLE episode.userci TO episode;
