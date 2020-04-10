-- Drop table

-- DROP TABLE episode.timetable;

CREATE TABLE episode.timetable (
	id int4 NOT NULL,
	hours varchar(10) NOT NULL,
	slot time NULL,
	CONSTRAINT timetable_idx1 PRIMARY KEY (id),
	CONSTRAINT timetable_idx3 UNIQUE (slot)
);
CREATE INDEX timetable_idx2 ON episode.timetable USING btree (hours);

-- Permissions

ALTER TABLE episode.timetable OWNER TO episode;
GRANT ALL ON TABLE episode.timetable TO episode;
