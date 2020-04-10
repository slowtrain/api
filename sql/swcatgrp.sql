-- Drop table

-- DROP TABLE episode.swcatgrp;

CREATE TABLE episode.swcatgrp (
	id int4 NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	org_id varchar(20) NULL,
	swcatgrp_id varchar(30) NULL,
	swcat_id varchar(30) NULL,
	CONSTRAINT swcatgrp_idx1 PRIMARY KEY (id)
);

-- Permissions

ALTER TABLE episode.swcatgrp OWNER TO episode;
GRANT ALL ON TABLE episode.swcatgrp TO episode;
