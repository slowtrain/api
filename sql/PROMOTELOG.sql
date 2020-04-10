-- Drop table

-- DROP TABLE episode.promotelog;

CREATE TABLE episode.promotelog (
	id int4 NOT NULL,
	disci_id varchar(50) NOT NULL,
	datasrc varchar(50) NOT NULL,
	description varchar(300) NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	CONSTRAINT promotelog_idx1 PRIMARY KEY (id)
);

-- Permissions

ALTER TABLE episode.promotelog OWNER TO episode;
GRANT ALL ON TABLE episode.promotelog TO episode;
