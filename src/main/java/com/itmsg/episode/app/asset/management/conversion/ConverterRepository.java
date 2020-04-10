/**
* 변환 Converter 엔터티 Jparepository 클래스
* @author   최영훈
* @since    2019.10.21
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.10.21   최영훈   최초 생성
*/
package com.itmsg.episode.app.asset.management.conversion;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConverterRepository extends JpaRepository<Converter, Long> {}