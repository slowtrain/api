-- Drop table

-- DROP TABLE episode.company;

CREATE TABLE episode.company (
	id int4 NOT NULL,
	org_id varchar(20) NOT NULL,
	company_id varchar(30) NOT NULL,
	companyname varchar(100) NOT NULL,
	companydescription varchar(255) NULL,
	companytype varchar(20) NULL,
	companynum varchar(100) NULL,
	companytel varchar(100) NULL,
	ownername varchar(100) NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	CONSTRAINT company_idx1 PRIMARY KEY (id),
	CONSTRAINT company_idx2 UNIQUE (org_id, company_id)
);

-- Permissions

ALTER TABLE episode.company OWNER TO episode;
GRANT ALL ON TABLE episode.company TO episode;
