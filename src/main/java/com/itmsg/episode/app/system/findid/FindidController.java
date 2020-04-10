package com.itmsg.episode.app.system.findid;

import com.itmsg.episode.app.system.user.User;
import com.itmsg.episode.app.system.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;

import java.util.*;

@RestController
@RequestMapping(value= FindidController.URL)
public class FindidController {
    public static final String APPLICATION="SIGNUP";
    public static final String URL="/api/authentication/findid";

    @Resource
    private FindidService findidService;

    @Resource
    private FindidMapper findidMapper;

    @Resource
    private PasswordEncoder passwordEncoderBean;

    @Resource
    private UserRepository userRepository;

    // page: findId, find id by variables
    @GetMapping(value="/id")
    @ResponseBody
    public ResponseEntity<?> findById(@PathVariable("firstname") String firstname, @PathVariable("lastname") String lastname, @PathVariable("email") String email){
        Optional<User> app= Optional.ofNullable(userRepository.findByFirstnameAndLastnameAndEmail(firstname, lastname, email));
        if(!app.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().body(app.get());
    }

}