/**
* 이벤트 엔터티 JapaRepository 클래스
* @author   이정호
* @since    2019.08.23
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.23   이정호   최초 생성
*/
package com.itmsg.episode.app.event;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
    
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByCiId(String ciId);
}