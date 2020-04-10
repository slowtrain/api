-- Drop table

-- DROP TABLE episode."conversion";

CREATE TABLE episode."conversion" (
	id int4 NOT NULL,
	tablename varchar(50) NOT NULL,
	columnname varchar(50) NOT NULL,
	classstructure_id varchar(30) NOT NULL,
	converter_id varchar(30) NOT NULL,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NOT NULL,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NOT NULL,
	conversionprocess varchar(100) NULL,
	CONSTRAINT conversion_idx1 PRIMARY KEY (id),
	CONSTRAINT conversion_idx2 UNIQUE (tablename, columnname, classstructure_id)
);

-- Permissions

ALTER TABLE episode."conversion" OWNER TO episode;
GRANT ALL ON TABLE episode."conversion" TO episode;

INSERT INTO episode."conversion" (id,tablename,columnname,classstructure_id,converter_id,create_dt,create_id,update_dt,update_id,conversionprocess) VALUES 
(75,'ci','extaln3','SVR_SINGLE','COMPANYNAME','2019-11-07 16:57:39.703','esadmin','2019-11-07 16:57:39.703','esadmin',NULL)
,(76,'ci','extaln3','SVR_UNIX','COMPANYNAME','2019-11-07 16:57:39.703','esadmin','2019-11-07 16:57:39.703','esadmin',NULL)
,(77,'ci','extaln3','SVR_VIRTUAL','COMPANYNAME','2019-11-07 16:57:39.703','esadmin','2019-11-07 16:57:39.703','esadmin',NULL)
,(78,'ci','extaln3','SVR_VIRTUAL_VIR','COMPANYNAME','2019-11-07 16:57:39.703','esadmin','2019-11-07 16:57:39.703','esadmin',NULL)
,(79,'ci','extaln3','SVR_WINDOWS','COMPANYNAME','2019-11-07 16:57:39.703','esadmin','2019-11-07 16:57:39.703','esadmin',NULL)
,(80,'ci','extaln7','NETWORK','OSNAME','2019-11-07 16:57:39.703','esadmin','2019-11-07 16:57:39.703','esadmin',NULL)
,(81,'ci','extaln7','NW_L2SW','OSNAME','2019-11-07 16:57:39.703','esadmin','2019-11-07 16:57:39.703','esadmin',NULL)
,(82,'ci','extaln7','NW_L4SW','OSNAME','2019-11-07 16:57:39.703','esadmin','2019-11-07 16:57:39.703','esadmin',NULL)
,(83,'ci','extaln7','NW_ROUTER','OSNAME','2019-11-07 16:57:39.703','esadmin','2019-11-07 16:57:39.703','esadmin',NULL)
,(84,'ci','extaln7','SVR_LINUX','OSNAME','2019-11-07 16:57:39.703','esadmin','2019-11-07 16:57:39.703','esadmin',NULL)
;
INSERT INTO episode."conversion" (id,tablename,columnname,classstructure_id,converter_id,create_dt,create_id,update_dt,update_id,conversionprocess) VALUES 
(85,'ci','extaln7','SVR_SINGLE','OSNAME','2019-11-07 16:57:39.703','esadmin','2019-11-07 16:57:39.703','esadmin',NULL)
,(86,'ci','extaln7','SVR_UNIX','OSNAME','2019-11-07 16:57:39.703','esadmin','2019-11-07 16:57:39.703','esadmin',NULL)
,(87,'ci','extaln7','SVR_VIRTUAL','OSNAME','2019-11-07 16:57:39.703','esadmin','2019-11-07 16:57:39.703','esadmin',NULL)
,(88,'ci','extaln7','SVR_VIRTUAL_VIR','OSNAME','2019-11-07 16:57:39.703','esadmin','2019-11-07 16:57:39.703','esadmin',NULL)
,(89,'ci','extaln7','SVR_WINDOWS','OSNAME','2019-11-07 16:57:39.703','esadmin','2019-11-07 16:57:39.703','esadmin',NULL)
,(74,'ci','extaln3','SVR_LINUX','COMPANYNAME','2019-11-07 16:57:39.703','esadmin','2019-11-21 00:43:26.902','Z03284','')
,(73,'ci','extaln3','NW_ROUTER','COMPANYNAME','2019-11-07 16:57:39.703','esadmin','2019-11-21 00:43:32.665','Z03284','')
,(72,'ci','extaln3','NW_L4SW','COMPANYNAME','2019-11-07 16:57:39.703','esadmin','2019-11-21 00:43:38.276','Z03284','')
,(71,'ci','extaln3','NW_L2SW','COMPANYNAME','2019-11-07 16:57:39.703','esadmin','2019-11-21 04:44:23.418','Z03284','')
,(70,'ci','extaln3','NETWORK','COMPANYNAME','2019-11-07 16:57:39.703','esadmin','2019-11-21 04:44:29.488','Z03284','')
;
INSERT INTO episode."conversion" (id,tablename,columnname,classstructure_id,converter_id,create_dt,create_id,update_dt,update_id,conversionprocess) VALUES 
(138,'ci','extaln19','TEST','TEST','2019-11-14 05:31:23.031','Z03284','2019-11-19 05:11:42.681','Z03284','')
,(141,'ci','extaln7','E2E','OSNAME','2019-11-20 00:28:12.080','105018','2019-11-20 00:28:12.080','105018',NULL)
;