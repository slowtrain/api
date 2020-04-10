-- Drop table

-- DROP TABLE episode."label";

CREATE TABLE episode."label" (
	"coalesce" varchar NULL
);

-- Permissions

ALTER TABLE episode."label" OWNER TO episode;
GRANT ALL ON TABLE episode."label" TO episode;
