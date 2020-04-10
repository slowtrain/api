-- Drop table

-- DROP TABLE episode.orgvariable;

CREATE TABLE episode.orgvariable (
	id int4 NOT NULL,
	org_id varchar(20) NOT NULL,
	"key" varchar(20) NOT NULL,
	value varchar(20) NOT NULL,
	description varchar(50) NULL DEFAULT NULL::character varying,
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NULL DEFAULT NULL::character varying,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NULL DEFAULT NULL::character varying,
	CONSTRAINT orgvariable_idx1 PRIMARY KEY (id),
	CONSTRAINT orgvariable_idx2 UNIQUE (org_id, key)
);

-- Permissions

ALTER TABLE episode.orgvariable OWNER TO episode;
GRANT ALL ON TABLE episode.orgvariable TO episode;

INSERT INTO episode.orgvariable (id,org_id,"key",value,description,create_dt,create_id,update_dt,update_id) VALUES 
(46,'','1','1','1','2019-11-20 06:17:11.663','Z03284','2019-11-20 06:17:11.663','Z03284')
,(49,'KAI','AVG_WAGE_RATE','290000','평균 임율(원)','2019-11-25 06:17:41.427','Z03283','2019-11-25 06:17:41.427','Z03283')
,(15,'KAI','NOTI_CI_NEW','CIADMIN','신규 CI 생성 통보 그룹','2019-11-04 01:22:12.476','Z03255','2019-11-18 02:00:47.000','Z03255')
,(16,'KAI','NOTI_CI_INACTIVE','CIADMIN','CI Inactive 된경우 통보 그룹','2019-11-04 01:25:14.562','Z03255','2019-11-18 02:00:47.000','Z03255')
,(17,'KAI','NOTI_ITSMCI_DELETE','CIADMIN','ITSM CI 삭제시 통보 그룹','2019-11-04 01:29:06.181','Z03255','2019-11-18 02:00:47.000','Z03255')
,(18,'KAI','NOTI_EMS_DELETE','CIADMIN','EMS AGENT 가 삭제된 경우 통보 그룹','2019-11-04 01:29:06.182','Z03255','2019-11-18 02:00:47.000','Z03255')
,(19,'KAI','NOTI_DISCI_NOTSCAN','CIADMIN','CI 스캔 2주이상 미 스캔시','2019-11-04 02:24:12.156','Z03255','2019-11-18 02:00:47.000','Z03255')
,(31,'KAI','DSRPA','1800','대시보드 RPA(초)','2019-11-08 07:30:31.367','Z03283','2019-11-18 02:00:47.000','Z03255')
,(32,'KAI','DSEVENT','60','대시보드 이벤트(초)','2019-11-08 07:30:31.367','Z03283','2019-11-18 02:00:47.000','Z03255')
,(33,'KAI','PERF','60','모니터 성능(초)','2019-11-08 07:30:31.367','Z03283','2019-11-18 02:00:47.000','Z03255')
;
INSERT INTO episode.orgvariable (id,org_id,"key",value,description,create_dt,create_id,update_dt,update_id) VALUES 
(34,'KAI','PERFDELAY','15','성능 딜레이 (분)','2019-11-08 07:30:31.367','Z03283','2019-11-18 02:00:47.000','Z03255')
,(35,'KAI','EVENT','60','이벤트(초)','2019-11-08 07:30:31.367','Z03283','2019-11-18 02:00:47.000','Z03255')
,(36,'KAI','EVENTDELAY','24','이벤트 처리지연(시간)','2019-11-18 02:00:47.794','Z03283','2019-11-18 02:00:47.000','Z03255')
,(2,'KAI','E2E-CRITICAL','10','E2E 대시보드 응답속도 CRITICAL 임계치, 초단위','2019-10-14 14:10:19.912','RPAUSR','2019-11-18 02:00:47.000','Z03255')
,(3,'KAI','E2E-WARNING','5','E2E 대시보드 응답속도 WARNING 임계치, 초단위','2019-10-14 14:10:19.912','RPAUSR','2019-11-18 02:00:47.000','Z03255')
;