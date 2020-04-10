/**
* 업체 엔터티 Jparepository 클래스
* @author   최영훈
* @since    2019.10.28
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.10.28   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.management.company;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findByOrgIdAndCompanytype(String orgId, String companytype);
}