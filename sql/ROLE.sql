-- Drop table

-- DROP TABLE episode."role";

CREATE TABLE episode."role" (
	id int4 NOT NULL,
	role_id varchar(20) NOT NULL,
	description varchar(50) NULL DEFAULT NULL::character varying,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NULL DEFAULT NULL::character varying,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NULL DEFAULT NULL::character varying,
	CONSTRAINT role_idx1 PRIMARY KEY (id),
	CONSTRAINT role_idx2 UNIQUE (role_id)
);

-- Permissions

ALTER TABLE episode."role" OWNER TO episode;
GRANT ALL ON TABLE episode."role" TO episode;

INSERT INTO "role" (id,role_id,description,create_dt,create_id,update_dt,update_id) VALUES 
(1,'ESADMIN','ESADMIN','2019-06-04 08:18:31.000','esadmin','2019-09-02 03:48:32.508','Z03284')
;