-- Drop table

-- DROP TABLE episode.esserver;

CREATE TABLE episode.esserver (
	id int4 NOT NULL,
	ipaddress varchar(20) NOT NULL,
	port varchar(10) NOT NULL,
	seq int4 NOT NULL,
	description varchar(50) NULL,
	status varchar(10) NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NULL DEFAULT NULL::character varying,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NULL DEFAULT NULL::character varying,
	CONSTRAINT esserver_idx1 PRIMARY KEY (id),
	CONSTRAINT esserver_idx2 UNIQUE (ipaddress, port),
	CONSTRAINT esserver_idx3 UNIQUE (seq)
);

-- Permissions

ALTER TABLE episode.esserver OWNER TO episode;
GRANT ALL ON TABLE episode.esserver TO episode;
