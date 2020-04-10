-- Drop table

-- DROP TABLE episode.if_nutanix;

CREATE TABLE episode.if_nutanix (
	id int4 NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	scan_dt varchar(32) NULL,
	"type" varchar(10) NULL,
	uuid varchar(40) NULL,
	parent_id varchar(40) NULL,
	"name" varchar(100) NULL,
	ipaddress varchar(16) NULL,
	clu_iscsi_ip varchar(16) NULL,
	clu_version varchar(16) NULL,
	clu_num_host int4 NULL,
	host_serial varchar(32) NULL,
	host_hyper_ip varchar(16) NULL,
	host_ipmi_ip varchar(16) NULL,
	host_cpu_model varchar(100) NULL,
	cpu_core int4 NULL,
	host_cpu_threads int4 NULL,
	host_cpu_sockets int4 NULL,
	memory_gb int4 NULL,
	host_hyper_name varchar(100) NULL,
	host_num_vm int4 NULL,
	host_boot_time varchar(100) NULL,
	vm_power varchar(8) NULL,
	CONSTRAINT if_nutanix_idx1 PRIMARY KEY (id)
);

-- Permissions

ALTER TABLE episode.if_nutanix OWNER TO episode;
GRANT ALL ON TABLE episode.if_nutanix TO episode;
