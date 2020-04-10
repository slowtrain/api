/**
* 어플리케이션 비지니스 서비스 클래스
* @author   최영훈
* @since    2019.11.06
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.06   최영훈   최초 생성
*/
package com.itmsg.episode.app.system.application;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ApplicationService {
    @Resource
    private ApplicationMapper applicationMapper;
}