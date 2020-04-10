-- Drop table

-- DROP TABLE episode.ci;

CREATE TABLE episode.ci (
	id int4 NOT NULL,
	org_id varchar(20) NULL,
	ci_id varchar(30) NULL,
	classstructure_id varchar(30) NOT NULL,
	status varchar(20) NOT NULL,
	description varchar(100) NULL,
	extaln1 varchar(200) NULL,
	extaln2 varchar(200) NULL,
	extaln3 varchar(200) NULL,
	extaln4 varchar(200) NULL,
	extaln5 varchar(200) NULL,
	extaln6 varchar(200) NULL,
	extaln7 varchar(200) NULL,
	extaln8 varchar(200) NULL,
	extaln9 varchar(200) NULL,
	extaln10 varchar(200) NULL,
	extaln11 varchar(200) NULL,
	extaln12 varchar(200) NULL,
	extaln13 varchar(200) NULL,
	extaln14 varchar(200) NULL,
	extaln15 varchar(200) NULL,
	extaln16 varchar(200) NULL,
	extaln17 varchar(200) NULL,
	extaln18 varchar(200) NULL,
	extaln19 varchar(200) NULL,
	extaln20 varchar(200) NULL,
	extnum1 numeric(15,4) NULL,
	extnum2 numeric(15,4) NULL,
	extnum3 numeric(15,4) NULL,
	extnum4 numeric(15,4) NULL,
	extnum5 numeric(15,4) NULL,
	extdate1 timestamp NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	extdate2 timestamp NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	extdate3 timestamp NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	extdate4 timestamp NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	extdate5 timestamp NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	extbool1 bool NULL DEFAULT false,
	extbool2 bool NULL DEFAULT false,
	extbool3 bool NULL DEFAULT false,
	extbool4 bool NULL DEFAULT false,
	extbool5 bool NULL DEFAULT false,
	location_id varchar(30) NULL,
	manager_id varchar(20) NULL,
	"name" varchar(50) NULL,
	"type" varchar(15) NULL,
	userdefine bool NOT NULL DEFAULT false,
	isdelete bool NOT NULL DEFAULT false,
	ciname varchar(100) NULL,
	cisw_id varchar(30) NULL,
	CONSTRAINT ci_idx1 PRIMARY KEY (id),
	CONSTRAINT ci_idx2 UNIQUE (org_id, ci_id)
);
CREATE INDEX ci_idx3 ON episode.ci USING btree (ci_id, classstructure_id);

-- Permissions

ALTER TABLE episode.ci OWNER TO episode;
GRANT ALL ON TABLE episode.ci TO episode;
