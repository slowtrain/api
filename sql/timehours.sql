-- Drop table

-- DROP TABLE episode.timehours;

CREATE TABLE episode.timehours (
	id int4 NOT NULL,
	hours time NOT NULL,
	CONSTRAINT timehours_idx1 PRIMARY KEY (id),
	CONSTRAINT timehours_idx2 UNIQUE (hours)
);

-- Permissions

ALTER TABLE episode.timehours OWNER TO episode;
GRANT ALL ON TABLE episode.timehours TO episode;
