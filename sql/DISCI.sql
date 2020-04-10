-- Drop table

-- DROP TABLE episode.disci;

CREATE TABLE episode.disci (
	id int4 NOT NULL,
	disci_id varchar(150) NOT NULL,
	ci_id varchar(30) NULL,
	isdelete bool NOT NULL DEFAULT false,
	datasrc varchar(20) NULL,
	disigtype varchar(20) NULL,
	datasrckey1 varchar(100) NULL,
	datasrckey2 varchar(100) NULL,
	datasrcpkey1 varchar(100) NULL,
	datasrcpkey2 varchar(100) NULL,
	datasrcver varchar(20) NULL,
	discitype varchar(100) NULL,
	disciclass varchar(200) NULL,
	disciname varchar(100) NULL,
	discidescription varchar(100) NULL,
	discihostname varchar(100) NULL,
	disciipaddress varchar(200) NULL,
	disciserialnumber varchar(100) NULL,
	disciappport varchar(200) NULL,
	discovery_dt timestamp NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	isupdate bool NOT NULL DEFAULT true,
	isvirtual bool NOT NULL DEFAULT false,
	discivirtype varchar(20) NULL,
	discilocation varchar(100) NULL,
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
	extnum1 numeric(20,4) NULL,
	extnum2 numeric(20,4) NULL,
	extnum3 numeric(20,4) NULL,
	extnum4 numeric(20,4) NULL,
	extnum5 numeric(20,4) NULL,
	extdate1 timestamp NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	extdate2 timestamp NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	extdate3 timestamp NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	extdate4 timestamp NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	extdate5 timestamp NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	extbool1 bool NOT NULL DEFAULT false,
	extbool2 bool NOT NULL DEFAULT false,
	extbool3 bool NOT NULL DEFAULT false,
	extbool4 bool NOT NULL DEFAULT false,
	extbool5 bool NOT NULL DEFAULT false,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	CONSTRAINT disci_idx1 PRIMARY KEY (id),
	CONSTRAINT disci_idx2 UNIQUE (disci_id)
);
CREATE INDEX disci_idx3 ON episode.disci USING btree (datasrckey1);
CREATE INDEX disci_idx4 ON episode.disci USING btree (datasrckey2);
CREATE INDEX disci_idx5 ON episode.disci USING btree (ci_id);
CREATE INDEX disci_idx6 ON episode.disci USING btree (datasrc, disigtype, disciclass);

-- Permissions

ALTER TABLE episode.disci OWNER TO episode;
GRANT ALL ON TABLE episode.disci TO episode;
