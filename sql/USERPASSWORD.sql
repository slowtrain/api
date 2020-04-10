-- Drop table

-- DROP TABLE episode.userpassword;

CREATE TABLE episode.userpassword (
	id int4 NOT NULL,
	username varchar(20) NOT NULL,
	"password" varchar(100) NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NULL DEFAULT NULL::character varying,
	CONSTRAINT userpassword_idx1 PRIMARY KEY (id)
);
CREATE INDEX userpassword_idx2 ON episode.userpassword USING btree (username);

-- Permissions

ALTER TABLE episode.userpassword OWNER TO episode;
GRANT ALL ON TABLE episode.userpassword TO episode;
