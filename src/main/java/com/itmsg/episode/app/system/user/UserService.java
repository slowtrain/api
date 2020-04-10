/**
* 유저 서비스 클래스
* @author   이정호
* @since    2019.11.06
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.06   이정호   최초 생성
*/  
package com.itmsg.episode.app.system.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

@Service
public class UserService {

    @Value("${system.pattern.pw}")
    private String pw;

    @Value("${system.pattern.same}")
    private String same;

    @Resource
    private UserMapper userMapper;
    @Resource
    private TextEncryptor textEncryptor;
    @Resource
    private UserRepository userRepository;

    @Resource
    private UserPasswordRepository userPasswordRepository;

    @Resource
    private PasswordEncoder passwordEncoderBean;

    public ResponseEntity<?> userPassword(User user, String password, String userName){

        List<UserPassword> passwords = userPasswordRepository.findTop3ByUsernameOrderByCreateDtDesc(user.getUsername());


        if(!password.matches(pw) || password.matches(same)) {
            return ResponseEntity.badRequest().body("0");
        }
   
        if(passwords.stream().anyMatch(r -> passwordEncoderBean.matches(password, r.getPassword()))) {
            return ResponseEntity.badRequest().body("1");
        }

        user.setPassword(passwordEncoderBean.encode(password));
        if (user.getChangepassword()) {
          user.setChangepassword(false);
        }
        UserPassword userPassword = new UserPassword();
        userPassword.setUsername(user.getUsername());
        userPassword.setCreateId(userName);
        userPassword.setPassword(user.getPassword());

        userPasswordRepository.save(userPassword);

        user.setUpdateId(userName);
        userRepository.save(user);
    
        return ResponseEntity.ok().body(userRepository.save(user));
    
    }
}
