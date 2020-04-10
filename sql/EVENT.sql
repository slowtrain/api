-- Drop table

-- DROP TABLE episode."event";

CREATE TABLE episode."event" (
	id int4 NOT NULL,
	ci_id varchar(30) NULL,
	evtresource varchar(16) NULL,
	status varchar(8) NULL,
	severity varchar(16) NULL,
	"level" int4 NULL,
	prelevel int4 NULL,
	devicename varchar(200) NULL,
	deviceip varchar(100) NULL,
	appname varchar(200) NULL,
	keyname varchar(200) NULL,
	message varchar(255) NULL,
	evtcondition varchar(255) NULL,
	suppcount int4 NULL,
	ackby varchar(20) NULL,
	ack_dt timestamp NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	closeby varchar(20) NULL,
	close_dt timestamp NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	datasrckeyint int8 NULL,
	datasrckeychar varchar(32) NULL,
	datasrc varchar(16) NULL,
	origin_dt timestamp NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	supp_dt timestamp NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	CONSTRAINT event_idx1 PRIMARY KEY (id)
);

-- Permissions

ALTER TABLE episode."event" OWNER TO episode;
GRANT ALL ON TABLE episode."event" TO episode;
