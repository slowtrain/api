/**
* 사이드바 컨트롤러 클래스
* @author   이정호
* @since    2019.08.13
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.08.13   이정호   최초 생성
*/
package com.itmsg.episode.app.sidebar;

import com.itmsg.episode.app.asset.resource.ci.CIRepository;
import com.itmsg.episode.app.event.EventMapper;
import com.itmsg.episode.app.system.user.User;
import com.itmsg.episode.app.system.user.UserPassword;
import com.itmsg.episode.app.system.user.UserPasswordRepository;
import com.itmsg.episode.app.system.user.UserRepository;
import com.itmsg.episode.app.system.user.UserService;
import com.itmsg.episode.system.ESConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value= SidebarController.URL)
public class SidebarController {
    public static final String APPLICATION="SIDEBAR";
    public static final String URL="/api/sidebar";

    @Resource
    private UserCiRepository userCiRepository;

    @Resource
    private SidebarMapper sidebarMapper;

    @Resource
    private CIRepository ciRepository;

    @Resource
    private SidebarService sidebarService;

    @Resource
    UserPasswordRepository userPasswordRepository;

    @Resource
    private PasswordEncoder passwordEncoderBean;

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserService userService;

    @Resource
    private EventMapper eventMapper;

    /**
     * name : getUserCiList
     * description : UserCI 리스트
     * @param : String searchParam
     * @return : ResponseEntity
     */ 
    @GetMapping(value="/userci")
    @ResponseBody
    public ResponseEntity<?> getUserCiList(@RequestParam("searchParam") String searchParam, HttpServletRequest request) {

        UserDetails detail = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        return ResponseEntity.ok().body(sidebarMapper.getUserCiList(searchParam, detail.getUsername()));
    }

    /**
     * name : postCi
     * description : UserCI 등록
     * @param : Map param
     * @return : ResponseEntity
     */
    @PostMapping(value="/userci")
    @ResponseBody
    public ResponseEntity<?> postCi(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        
        UserDetails detail = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);

        String userName = detail.getUsername();

        sidebarService.addCi(data, userName);

        return ResponseEntity.ok().build();
    }

    /**
     * name : deleteUserCi
     * description : UserCI 등록
     * @param : long id
     * @return : ResponseEntity
     */
    @DeleteMapping(value = "/userci/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteUserCi(@PathVariable("id") Long id) {
        Optional<UserCi> app = userCiRepository.findById(id);
        if (!app.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        userCiRepository.delete(app.get());
        return ResponseEntity.ok().build();
    }

    /**
     * name : getUserpw
     * description : user password list
     * @param : HttpServletRequest request
     * @return : ResponseEntity
     */
    @GetMapping(value="/userpw")
    @ResponseBody
    public ResponseEntity<?> getUserpw(HttpServletRequest request){
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        UserPassword userpw = userPasswordRepository.findTop1ByUsernameOrderByCreateDtDesc(userDetails.getUsername());
        return ResponseEntity.ok().body(userpw);
    }

    /**
     * name : putPasswordChk
     * description : update password popup
     * @param : Long id, Map param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PutMapping(value="/userpw/chk/{id}")                            
    @ResponseBody
    public ResponseEntity<?> putPasswordChk(@PathVariable("id") Long id, @RequestBody Map<String, String> param, HttpServletRequest request) throws Exception{

        Optional<User> app=userRepository.findById(id);
        if(!app.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if(!passwordEncoderBean.matches(param.get("pw"), app.get().getPassword())){
            return ResponseEntity.badRequest().body("0");
        } else {
            return ResponseEntity.ok().build();
        }
       
    }    

    /**
     * name : putPassword
     * description : popup update password
     * @param : Long id, User data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @Transactional(value="transactionManager")
    @PutMapping(value="/userpw/{id}")                          
    @ResponseBody
    public ResponseEntity<?> putPassword(@PathVariable("id") Long id, @RequestBody User param, HttpServletRequest request) throws Exception{
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        Optional<User> app=userRepository.findById(id);
        if(!app.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return userService.userPassword(app.get(), param.getPassword(), userDetails.getUsername());
    }

    /**
     * name : getEventList
     * description : 나의 관심장비 이벤트 조회
     * @param : Map param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @GetMapping(value="/userEventList")
    @ResponseBody
    public ResponseEntity<?> getEventList(@RequestParam Map<String, Object> param, HttpServletRequest request) {

        UserDetails detail = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);

        param.put("userId", detail.getUsername());

        return ResponseEntity.ok().body(eventMapper.getEventList(param));
    }

}