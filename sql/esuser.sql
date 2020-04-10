-- Drop table

-- DROP TABLE episode.esuser;

CREATE TABLE episode.esuser (
	id int4 NOT NULL,
	username varchar(20) NOT NULL,
	"password" varchar(100) NOT NULL,
	first_name varchar(100) NULL DEFAULT NULL::character varying,
	last_name varchar(50) NULL DEFAULT NULL::character varying,
	organization varchar(100) NULL DEFAULT NULL::character varying,
	email varchar(100) NULL DEFAULT NULL::character varying,
	tel varchar(100) NULL DEFAULT NULL::character varying,
	mobile varchar(100) NULL DEFAULT NULL::character varying,
	address varchar(200) NULL DEFAULT NULL::character varying,
	status varchar(20) NOT NULL DEFAULT 'ACTIVE'::character varying,
	lang varchar(20) NOT NULL DEFAULT 'ko-kr'::character varying,
	org_id varchar(20) NOT NULL,
	login_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	password_reset_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	create_id varchar(20) NULL DEFAULT NULL::character varying,
	update_dt timestamp NOT NULL DEFAULT timezone('utc'::text, CURRENT_TIMESTAMP),
	update_id varchar(20) NULL DEFAULT NULL::character varying,
	defaultgroup varchar(20) NULL,
	changepassword bool NOT NULL DEFAULT false,
	CONSTRAINT user_idx1 PRIMARY KEY (id),
	CONSTRAINT user_idx2 UNIQUE (username)
);

-- Permissions

ALTER TABLE episode.esuser OWNER TO episode;
GRANT ALL ON TABLE episode.esuser TO episode;

INSERT INTO esuser (id, username, "password", first_name, last_name, organization, email, tel, mobile, address, status, lang, org_id, login_dt, password_reset_dt, create_dt, create_id, update_dt, update_id, defaultgroup, changepassword) VALUES(2, 'esadmin', '$2a$10$oJjA5ngKYUNF52E3AYSwy.hREGXSmjnb.OlpY8v7O88RX/hxXJNBK', '52cbf8c0fee09d4332495bbbcd416642', NULL, NULL, 'd1efdb03360c9f9ba123c294bec7d2c55492f311e47e72e23e4c8d2eac46a49d', NULL, '3809cca084c625dda2131f361cc78b6f', NULL, 'ACTIVE', 'ko-kr', 'KAI', '2019-07-05 21:10:57.382', '2019-11-01 08:12:57.445', '2019-07-05 21:10:57.382', 'esadmin', '2019-10-07 05:53:47.997', 'Z03283', NULL, false);

INSERT INTO esuser (id,username,"password",first_name,last_name,organization,email,tel,mobile,address,status,lang,org_id,login_dt,password_reset_dt,create_dt,create_id,update_dt,update_id,defaultgroup,changepassword) VALUES 
(2,'esadmin','$2a$10$oJjA5ngKYUNF52E3AYSwy.hREGXSmjnb.OlpY8v7O88RX/hxXJNBK','52cbf8c0fee09d4332495bbbcd416642',NULL,NULL,'d1efdb03360c9f9ba123c294bec7d2c55492f311e47e72e23e4c8d2eac46a49d',NULL,'3809cca084c625dda2131f361cc78b6f',NULL,'ACTIVE','ko-kr','KAI','2019-07-05 21:10:57.382','2019-11-01 08:12:57.445','2019-07-05 21:10:57.382','esadmin','2019-10-07 05:53:47.997','Z03283',NULL,false)
;