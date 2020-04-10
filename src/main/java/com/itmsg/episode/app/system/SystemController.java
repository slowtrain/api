package com.itmsg.episode.app.system;

import java.util.Map;

import com.itmsg.episode.app.system.application.AppLabelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SystemController {
       
    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AppLabelRepository appLabelRepository;

    @Autowired
    private SystemMapper systemMapper;

    
    @GetMapping(value= "/api/system/label")
    @ResponseBody
    public ResponseEntity<?> appLabelList(@RequestParam Map<String,String> param){
        return ResponseEntity.ok().body(appLabelRepository.findByAppIdAndLang(param.get("appId"),param.get("lang")));   
    }
    

}