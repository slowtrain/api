-- Drop table

-- DROP TABLE episode.cisw;

CREATE TABLE episode.cisw (
	id int4 NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	ci_id varchar(30) NULL,
	org_id varchar(20) NOT NULL,
	swcat_id varchar(30) NOT NULL,
	ispresent bool NULL DEFAULT true,
	start_dt timestamp NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	end_dt timestamp NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	detailedversion varchar(50) NULL,
	discoverypath varchar(255) NULL,
	userdefine bool NULL DEFAULT false,
	islicnouse bool NULL DEFAULT false,
	licnusecause varchar(255) NULL,
	datasrc varchar(20) NULL,
	datasrckey1 varchar(100) NULL,
	datasrckey2 varchar(100) NULL,
	datasrcpkey1 varchar(100) NULL,
	datasrcpkey2 varchar(100) NULL,
	datasrcver varchar(20) NULL,
	isupdate bool NOT NULL DEFAULT true,
	cisw_id varchar(30) NOT NULL,
	CONSTRAINT cisw_idx1 PRIMARY KEY (id),
	CONSTRAINT cisw_idx2 UNIQUE (cisw_id)
);

-- Permissions

ALTER TABLE episode.cisw OWNER TO episode;
GRANT ALL ON TABLE episode.cisw TO episode;
