CREATE TABLE es_code_grp (
       grp_cd varchar(20) NOT NULL COMMENT '그룹 코드',
       grp_nm varchar(50) NOT NULL COMMENT '그룹 이름',
       grp_desc varchar(200) NULL COMMENT '그룹 설명',
       ext1_desc varchar(50) NULL COMMENT '확장1 설명',
       ext2_desc varchar(50) NULL COMMENT '확장2 설명',
       ext3_desc varchar(50) NULL COMMENT '확장3 설명',
       use_yn char(1) DEFAULT 'Y' NOT NULL COMMENT '사용 여부',
       create_dt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL COMMENT '생성 일시',
       create_id varchar(20) NULL COMMENT '생성자 ID',
       update_dt TIMESTAMP NULL COMMENT '수정 일시',
       update_id varchar(20) NULL COMMENT '수정자 ID',
       CONSTRAINT es_code_grp_pk PRIMARY KEY (grp_cd)
)
  ENGINE=InnoDB
  DEFAULT CHARSET=utf8
  COLLATE=utf8_general_ci
  COMMENT='코드 그룹';

CREATE TABLE es_code (
       grp_cd varchar(20) NOT NULL COMMENT '그룹 코드',
       code_cd varchar(20) NOT NULL COMMENT '코드',
       code_nm varchar(50) NOT NULL COMMENT '코드 이름',
       code_desc varchar(200) NULL COMMENT '코드 설명',
       order_no int DEFAULT 1 NULL COMMENT '정렬 번호',
       ext1_value varchar(100) NULL COMMENT '확장1 값',
       ext2_value varchar(100) NULL COMMENT '확장2 값',
       ext3_value varchar(100) NULL COMMENT '확장3 값',
       use_yn char(1) DEFAULT 'Y' NOT NULL COMMENT '사용 여부',
       sub_grp_cd varchar(20) DEFAULT NULL COMMENT '하위 그룹 코드',
       create_dt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '생성 일시',
       create_id varchar(20) NULL COMMENT '생성자 ID',
       update_dt TIMESTAMP NULL COMMENT '변경 일시',
       update_id varchar(20) NULL COMMENT '변경자 ID',
       CONSTRAINT es_code_pk PRIMARY KEY (grp_cd,code_cd),
       CONSTRAINT es_code_es_code_grp_fk FOREIGN KEY (grp_cd) REFERENCES es_code_grp(grp_cd)
)
  ENGINE=InnoDB
  DEFAULT CHARSET=utf8
  COLLATE=utf8_general_ci
  COMMENT='코드';

CREATE TABLE es_dept (
       dept_cd varchar(20) NOT NULL COMMENT '부서 코드',
       up_dept_cd varchar(20) NOT NULL COMMENT '상위 부서 코드',
       dept_nm varchar(50) NOT NULL COMMENT '부서 이름',
       dept_desc varchar(200) NULL COMMENT '부서 설명',
       order_no INT DEFAULT 1 NOT NULL COMMENT '정렬 번호',
       use_yn varchar(100) DEFAULT 'Y' NOT NULL COMMENT '사용 여부',
       create_dt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL COMMENT '생성 일시',
       create_id varchar(20) NULL COMMENT '생성자 ID',
       update_dt TIMESTAMP NULL COMMENT '변경 일시',
       update_id varchar(20) NULL COMMENT '변경자 ID',
       CONSTRAINT es_dept_pk PRIMARY KEY (dept_cd)
)
  ENGINE=InnoDB
  DEFAULT CHARSET=utf8
  COLLATE=utf8_general_ci
  COMMENT='부서';

CREATE TABLE es_menu (
       menu_seq bigint NOT NULL AUTO_INCREMENT COMMENT '메뉴 일련번호',
       up_menu_seq bigint NOT NULL COMMENT '상위 메뉴 일련번호',
       menu_nm varchar(50) NOT NULL COMMENT '메뉴 이름',
       menu_url varchar(100) NULL COMMENT '메뉴 URL',
       url_pattern varchar(100) NULL COMMENT 'URL 패턴',
       menu_id varchar(20) NULL COMMENT '메뉴 ID',
       menu_icon varchar(20) NULL COMMENT '메뉴 아이콘',
       order_no INT DEFAULT 1 NULL COMMENT '정렬 번호',
       use_yn char(1) DEFAULT 'Y' NULL COMMENT '사용 여부',
       create_dt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL COMMENT '생성 일시',
       create_id varchar(20) NULL COMMENT '생성자 ID',
       update_dt TIMESTAMP NULL COMMENT '변경 일시',
       update_id varchar(20) NULL COMMENT '변경자 ID',
       CONSTRAINT es_menu_pk PRIMARY KEY (menu_seq)
)
  ENGINE=InnoDB
  DEFAULT CHARSET=utf8
  COLLATE=utf8_general_ci
  COMMENT='메뉴';

CREATE TABLE es_mgr (
        mgr_id varchar(20) NOT NULL COMMENT '관리자 ID',
        dept_cd varchar(20) NOT NULL COMMENT '부서 코드',
        mgr_nm varchar(50) NOT NULL COMMENT '관라자 이름',
        mgr_pwd varchar(100) NOT NULL COMMENT '관리자 비밀번호',
        grade_cd varchar(20) DEFAULT NULL COMMENT '직책 코드',
        mobile varchar(20) DEFAULT NULL COMMENT '휴대폰 번호',
        email varchar(50) DEFAULT NULL COMMENT '이메일주소',
        tel_no varchar(20) DEFAULT NULL COMMENT '전화번호',
        fax_no varchar(20) DEFAULT NULL COMMENT '팩스번호',
        create_dt TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
        create_id varchar(20) DEFAULT NULL COMMENT '생성자 ID',
        update_dt TIMESTAMP DEFAULT NULL COMMENT '변경 일시',
        update_id varchar(20) DEFAULT NULL COMMENT '변경자 ID',
        login_dt TIMESTAMP DEFAULT NULL COMMENT '로그인 일시',
        login_cnt int(11) DEFAULT '0' COMMENT '로그인 횟수',
        PRIMARY KEY (mgr_id),
        KEY es_mgr_es_dept_fk (dept_cd),
        CONSTRAINT es_mgr_es_dept_fk FOREIGN KEY (dept_cd) REFERENCES es_dept (dept_cd)
)
  ENGINE=InnoDB
  DEFAULT CHARSET=utf8
  COLLATE=utf8_general_ci
  COMMENT='관리자';

CREATE TABLE es_errer_log (
        err_seq BIGINT NOT NULL AUTO_INCREMENT COMMENT '에러 일련번호',
        title varchar(250) NOT NULL COMMENT '타이틀',
        contents LONGTEXT NULL COMMENT '에러 내용',
        url varchar(100) NULL COMMENT 'URL',
        fix_yn char(1) DEFAULT 'N' NOT NULL COMMENT '해결 여부',
        fix_dt TIMESTAMP NULL COMMENT '해결 일시',
        create_dt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '생성 일시',
        CONSTRAINT es_errer_log_pk PRIMARY KEY (err_seq)
)
  ENGINE=InnoDB
  DEFAULT CHARSET=utf8
  COLLATE=utf8_general_ci
  COMMENT='에러 로그';

CREATE TABLE oauth_client_details (
        client_id varchar(256) COLLATE utf8_unicode_ci NOT NULL COMMENT '클라이언트 ID',
        resource_ids varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '자원 ID들',
        client_secret varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '클라이언트 비밀코드',
        scope varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '범위',
        authorized_grant_types varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '권한 타입',
        web_server_redirect_uri varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '웹서버 이동 경로',
        authorities varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '권한들',
        access_token_validity int(11) DEFAULT NULL COMMENT '접속 토큰 검증',
        refresh_token_validity int(11) DEFAULT NULL COMMENT '재접속 토큰 검증',
        additional_information varchar(4096) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '추가 정보',
        autoapprove varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '자동승인',
        CONSTRAINT oauth_client_details_pk PRIMARY KEY (client_id)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8
  COLLATE=utf8_general_ci
  COMMENT='사용자 권한 상세';

CREATE TABLE es_user_info (
        user_id varchar(20) NOT NULL COMMENT '사용자 ID',
        user_pwd varchar(100) NOT NULL COMMENT '사용자 패스워드',
        user_nm varchar(50) DEFAULT NULL COMMENT '사용자 이름',
        user_role varchar(100) DEFAULT NULL COMMENT '사용자 롤',
        company varchar(100) DEFAULT NULL COMMENT '소속회사',
        user_group varchar(200) DEFAULT NULL COMMENT '사용자 그룹',
        email varchar(100) DEFAULT NULL COMMENT '이메일',
        tel_no varchar(20) DEFAULT NULL COMMENT '전화번호',
        mobile_no varchar(20) DEFAULT NULL COMMENT '휴대폰 번호',
        addr varchar(200) DEFAULT NULL COMMENT '주소',
        use_yn char(1) DEFAULT 'Y' COMMENT '사용 여부',
        login_dt timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '로그인 일시',
        create_dt timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
        create_id varchar(20) DEFAULT NULL COMMENT '생성자 ID',
        update_dt timestamp NULL COMMENT '변경 일시',
        update_id varchar(20) DEFAULT NULL COMMENT '변경자 ID',
        CONSTRAINT es_user_info_pk PRIMARY KEY (user_id)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8
  COLLATE=utf8_general_ci
  COMMENT='사용자';