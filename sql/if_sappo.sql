-- Drop table

-- DROP TABLE episode.if_sappo;

CREATE TABLE episode.if_sappo (
	id int4 NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL DEFAULT 'sappo'::character varying,
	sourcesystem varchar(20) NOT NULL DEFAULT 'SAPPO'::character varying,
	targetsystem varchar(20) NOT NULL DEFAULT 'ICAM'::character varying,
	"result" varchar(10) NULL DEFAULT 'FAIL'::character varying,
	remarks varchar(256) NULL,
	ic_send_object_id varchar(96) NULL,
	ic_send_party varchar(180) NULL,
	ic_send_compnm varchar(180) NULL,
	ic_send_ifnm varchar(360) NULL,
	ic_send_ifns varchar(765) NULL,
	ic_send_channel varchar(180) NULL,
	ic_send_adaptertype varchar(360) NULL,
	ic_send_direction varchar(30) NULL,
	ic_send_msgprot varchar(30) NULL,
	ic_send_trnsprot varchar(30) NULL,
	ic_recv_object_id varchar(96) NULL,
	ic_recv_party varchar(180) NULL,
	ic_recv_compnm varchar(180) NULL,
	ic_recv_ifnm varchar(360) NULL,
	ic_recv_ifns varchar(765) NULL,
	ic_recv_channel varchar(180) NULL,
	ic_recv_adaptertype varchar(360) NULL,
	ic_recv_direction varchar(30) NULL,
	ic_recv_msgprot varchar(30) NULL,
	ic_recv_trnsprot varchar(30) NULL,
	CONSTRAINT if_sappo_idx1 PRIMARY KEY (id)
);

-- Permissions

ALTER TABLE episode.if_sappo OWNER TO episode;
GRANT ALL ON TABLE episode.if_sappo TO episode;
