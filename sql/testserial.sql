-- Drop table

-- DROP TABLE episode.testserial;

CREATE TABLE episode.testserial (
	id serial NOT NULL,
	description varchar(100) NOT NULL,
	lang varchar(10) NOT NULL,
	CONSTRAINT test1 PRIMARY KEY (id)
);

-- Permissions

ALTER TABLE episode.testserial OWNER TO episode;
GRANT ALL ON TABLE episode.testserial TO episode;
