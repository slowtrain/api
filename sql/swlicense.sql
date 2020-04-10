-- Drop table

-- DROP TABLE episode.swlicense;

CREATE TABLE episode.swlicense (
	id int4 NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	org_id varchar(20) NULL,
	swlicense_id varchar(30) NULL,
	swlicstatus varchar(20) NULL,
	purchasetype varchar(20) NULL,
	swlictype varchar(20) NULL,
	company_id varchar(30) NULL,
	asset_id varchar(30) NULL,
	po_id varchar(30) NULL,
	purchase_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	start_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	end_dt timestamp NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	purchaseprice numeric(20,4) NULL,
	priceunit varchar(10) NULL,
	purchaseunit varchar(10) NULL,
	purchasenum int4 NULL,
	inyear int4 NULL,
	remainprice numeric(10,4) NULL,
	swlicdescription varchar(255) NULL,
	swlicname varchar(100) NULL,
	manager_id varchar(20) NULL,
	CONSTRAINT swlicense_idx1 PRIMARY KEY (id)
);
CREATE INDEX swlicense_org_id_idx ON episode.swlicense USING btree (org_id, swlicense_id);

-- Permissions

ALTER TABLE episode.swlicense OWNER TO episode;
GRANT ALL ON TABLE episode.swlicense TO episode;
