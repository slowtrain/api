-- Drop table

-- DROP TABLE episode.discispec;

CREATE TABLE episode.discispec (
	id int8 NOT NULL,
	disci_id varchar(150) NOT NULL,
	disci_isdelte bool NULL DEFAULT false,
	disclassspec_id varchar(50) NOT NULL,
	discovery_dt timestamp NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	isupdate bool NOT NULL DEFAULT true,
	spectype varchar(30) NULL,
	alnvalue varchar(100) NULL,
	numvalue numeric(20,4) NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	CONSTRAINT discispec_idx1 PRIMARY KEY (id),
	CONSTRAINT discispec_idx2 UNIQUE (disci_id, disclassspec_id)
);

-- Permissions

ALTER TABLE episode.discispec OWNER TO episode;
GRANT ALL ON TABLE episode.discispec TO episode;
