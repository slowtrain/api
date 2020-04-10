package com.itmsg.episode.app.system.signup;

import com.itmsg.episode.app.system.organization.OrganizationMapper;
import com.itmsg.episode.app.system.user.User;
import com.itmsg.episode.app.system.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value= SignupController.URL)
public class SignupController {
    public static final String APPLICATION="SIGNUP";
    public static final String URL="/api/authentication/signup";

    @Autowired
    private SignupRepository signupRepository;

    @Resource
    private SignupService signupService;

    @Resource
    private SignupMapper signupMapper;

    @Resource
    private PasswordEncoder passwordEncoderBean;

    @Resource
    private UserRepository userRepository;

    @Resource
    private OrganizationMapper organizationMapper;

    // page: signUp, find id by variables
    @GetMapping(value="/{username}")
    @ResponseBody
    public ResponseEntity<?> findByUsername(@PathVariable("username") String username){
        Optional<User> app= Optional.ofNullable(userRepository.findByUsername(username));
        if(!app.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().body(app.get());
    }

    // page: signUp, insert new user
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> post(@RequestBody Signup data) {
        data.setPassword(passwordEncoderBean.encode(data.getPassword()));
        data.setPassword_reset_flag(1);
        return ResponseEntity.ok().body(signupRepository.save(data));
    }

    // page: signUp, get organization list
    @GetMapping(value="/org")
    @ResponseBody
    public ResponseEntity<?> getOrgList(@RequestParam Map<String,Object> param){
        return ResponseEntity.ok().body(organizationMapper.list(param));
    }
}