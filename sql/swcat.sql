-- Drop table

-- DROP TABLE episode.swcat;

CREATE TABLE episode.swcat (
	id int4 NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	swcat_id varchar(30) NULL,
	parent_id varchar(30) NULL,
	swcattype varchar(20) NULL,
	classstructure_id varchar(30) NULL,
	org_id varchar(20) NULL,
	swcatname varchar(200) NULL,
	swcatdescription varchar(255) NULL,
	swcatversion varchar(100) NULL,
	publishername varchar(100) NULL,
	isdomestic bool NULL DEFAULT false,
	isdelete bool NULL DEFAULT false,
	userdefine bool NULL DEFAULT false,
	ispromotetoci bool NULL DEFAULT false,
	datasrc varchar(20) NULL,
	datasrc_guid varchar(50) NULL,
	hierarchypath varchar(100) NULL,
	CONSTRAINT swcat_idx1 PRIMARY KEY (id),
	CONSTRAINT swcat_idx2 UNIQUE (swcat_id)
);

-- Permissions

ALTER TABLE episode.swcat OWNER TO episode;
GRANT ALL ON TABLE episode.swcat TO episode;
