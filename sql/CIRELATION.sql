-- Drop table

-- DROP TABLE episode.cirelation;

CREATE TABLE episode.cirelation (
    id int4 NOT NULL,
    org_id varchar(20) NOT NULL,
    sourceci varchar(30) NOT NULL,
    targetci varchar(30) NOT NULL,
    relationname varchar(20) NOT NULL,
    status varchar(10) NOT NULL DEFAULT 'ACTIVE'::character varying,
    create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
    create_id varchar(20) NOT NULL,
    update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
    update_id varchar(20) NOT NULL,
    end_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
    userdefine bool NOT NULL,
    CONSTRAINT cirelation_idx1 PRIMARY KEY (id),
    CONSTRAINT cirelation_idx2 UNIQUE (org_id, sourceci, targetci, relationname)
);
CREATE INDEX cirelation_idx3 ON episode.cirelation USING btree (org_id, sourceci);
CREATE INDEX cirelation_idx4 ON episode.cirelation USING btree (org_id, targetci);

-- Permissions

ALTER TABLE episode.cirelation OWNER TO episode;
GRANT ALL ON TABLE episode.cirelation TO episode;
