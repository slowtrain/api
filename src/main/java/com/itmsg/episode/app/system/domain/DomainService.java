/**
* 코드체계 서비스 클래스
* @author   최영훈
* @since    2019.11.19
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.19   최영훈   최초 생성
*/
package com.itmsg.episode.app.system.domain;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DomainService {
    @Resource
    private DomainMapper domainMapper;
    
}
