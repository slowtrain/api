/**
* 위치 엔터티 JapaRepository 클래스
* @author   이정호
* @since    2019.08.27
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.27   이정호   최초 생성
*/
package com.itmsg.episode.app.asset.location;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findById(long id);
}
