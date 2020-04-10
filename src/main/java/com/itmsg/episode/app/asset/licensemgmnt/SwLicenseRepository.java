/**
* 라이선스 엔터티 Jparepository 클래스
* @author   최영훈
* @since    2019.09.16
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.09.16   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.licensemgmnt;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SwLicenseRepository extends JpaRepository<SwLicense, Long> {}