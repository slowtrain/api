-- Drop table

-- DROP TABLE episode.attachment;

CREATE TABLE episode.attachment (
	id int4 NOT NULL,
	attachment_id varchar(30) NOT NULL,
	description varchar(50) NULL DEFAULT NULL::character varying,
	ownertable varchar(30) NOT NULL,
	ownerid int4 NOT NULL,
	filename varchar(100) NOT NULL,
	storedname varchar(100) NOT NULL,
	urlname varchar(150) NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NULL DEFAULT NULL::character varying,
	CONSTRAINT attachment_idx1 PRIMARY KEY (id),
	CONSTRAINT attachment_idx2 UNIQUE (attachment_id)
);
CREATE INDEX attachment_idx3 ON episode.attachment USING btree (ownertable, ownerid);

-- Permissions

ALTER TABLE episode.attachment OWNER TO episode;
GRANT ALL ON TABLE episode.attachment TO episode;
