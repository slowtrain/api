-- Drop table

-- DROP TABLE episode.groupmap;

CREATE TABLE episode.groupmap (
	id int4 NOT NULL,
	oldgroup varchar(20) NOT NULL,
	newgroup varchar(20) NOT NULL,
	CONSTRAINT groupmap_idx1 PRIMARY KEY (id),
	CONSTRAINT groupmap_idx2 UNIQUE (oldgroup),
	CONSTRAINT groupmap_idx3 UNIQUE (newgroup)
);

-- Permissions

ALTER TABLE episode.groupmap OWNER TO episode;
GRANT ALL ON TABLE episode.groupmap TO episode;
