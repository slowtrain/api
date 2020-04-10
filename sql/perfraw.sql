-- Drop table

-- DROP TABLE episode.perfraw;

CREATE TABLE episode.perfraw (
	id int8 NOT NULL,
	ci_id varchar(30) NULL,
	sys_class varchar(16) NULL,
	devicename varchar(200) NULL,
	deviceip varchar(100) NULL,
	perf_type varchar(16) NULL,
	perf_detail varchar(32) NULL,
	rsc_key varchar(255) NULL,
	rsc_key_name varchar(255) NULL,
	rsc_datatype varchar(16) NULL,
	rscval_double float4 NULL,
	rscval_integer int4 NULL,
	rscval_string varchar(255) NULL,
	rscval_boolean bpchar(1) NULL,
	datasrckeyint int8 NOT NULL,
	datasrckeychar varchar(32) NULL,
	datasrc varchar(16) NULL,
	origin_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	CONSTRAINT perfraw_idx1 UNIQUE (id),
	CONSTRAINT perfraw_idx2 UNIQUE (datasrckeyint, origin_dt)
);
CREATE INDEX perfraw_origin_dt_index ON episode.perfraw USING btree (origin_dt DESC);

-- Permissions

ALTER TABLE episode.perfraw OWNER TO episode;
GRANT ALL ON TABLE episode.perfraw TO episode;
