-- Drop table

-- DROP TABLE episode.appmodule;

CREATE TABLE episode.appmodule (
	id int4 NOT NULL,
	app_id varchar(20) NOT NULL,
	module_id varchar(20) NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NULL DEFAULT NULL::character varying,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NULL DEFAULT NULL::character varying,
	CONSTRAINT appmodule_idx1 PRIMARY KEY (id)
);
CREATE INDEX appmodule_idx2 ON episode.appmodule USING btree (app_id);

-- Permissions

ALTER TABLE episode.appmodule OWNER TO episode;
GRANT ALL ON TABLE episode.appmodule TO episode;
