-- Drop table

-- DROP TABLE episode.discirelation;

CREATE TABLE episode.discirelation (
	id int8 NOT NULL,
	srcdisci_id varchar(150) NULL,
	tgtdisci_id varchar(150) NULL,
	relationname varchar(20) NULL,
	datasrc varchar(20) NULL,
	srcdatasrckey1 varchar(100) NULL,
	srcdatasrckey2 varchar(100) NULL,
	tgtdatasrckey1 varchar(100) NULL,
	tgtdatasrckey2 varchar(100) NULL,
	start_dt timestamp NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	CONSTRAINT discirelation_idx1 PRIMARY KEY (id),
	CONSTRAINT discirelation_idx2 UNIQUE (srcdisci_id, tgtdisci_id, relationname)
);
CREATE INDEX discirelation_idx3 ON episode.discirelation USING btree (srcdisci_id);
CREATE INDEX discirelation_idx4 ON episode.discirelation USING btree (tgtdisci_id);
CREATE INDEX discirelation_idx5 ON episode.discirelation USING btree (relationname);

-- Permissions

ALTER TABLE episode.discirelation OWNER TO episode;
GRANT ALL ON TABLE episode.discirelation TO episode;
