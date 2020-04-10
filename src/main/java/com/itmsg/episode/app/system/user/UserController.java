/**
* 유저 컨트롤러 클래스
* @author   이정호
* @since    2019.11.06
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.06   이정호   최초 생성
*/
package com.itmsg.episode.app.system.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.itmsg.episode.app.system.application.AuthorityRepository;
import com.itmsg.episode.app.system.role.RoleUserRepository;
import com.itmsg.episode.app.system.usergroup.GroupUser;
import com.itmsg.episode.app.system.usergroup.GroupUserRepository;
import com.itmsg.episode.system.DataFilterEnabled;
import com.itmsg.episode.system.ESApplicationOption;
import com.itmsg.episode.system.ESConstants;
import com.itmsg.episode.system.ESDataFilter;
import com.itmsg.episode.system.SystemCommonService;
import com.itmsg.episode.util.StringUtils;

@RestController
@RequestMapping(value = UserController.URL)
public class UserController {

    private static final String USER = "USER";

    public static final String URL = "/api/system/user";

    @ESApplicationOption(application = UserController.USER, description = "User read option")
    public static final String READ = "USER_READ";

    @ESApplicationOption(application = UserController.USER, description = "User save option")
    public static final String SAVE = "USER_SAVE";

    @ESApplicationOption(application = UserController.USER, description = "User delete option")
    public static final String DELETE = "USER_DELETE";

    @ESApplicationOption(application = UserController.USER, description = "User authority read option")
    public static final String AUTHREAD = "USER_AUTH_READ";

    @ESApplicationOption(application = UserController.USER, description = "User filter read option")
    public static final String FILTERREAD = "USER_FILTER_READ";

    @ESApplicationOption(application = UserController.USER, description = "User filter save option")
    public static final String FILTERSAVE = "USER_FILTER_SAVE";

    @ESApplicationOption(application = UserController.USER, description = "User filter delete option")
    public static final String FILTERDELETE = "USER_FILTER_DELETE";

    @Resource
    private PasswordEncoder passwordEncoderBean;

    @Resource
    private TextEncryptor textEncryptor;

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserPasswordRepository userPasswordRepository;

    @Resource
    private UserFilterRepository userFilterRepository;

    @Resource
    private AuthorityRepository authorityRepository;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;

    @Resource
    private RoleUserRepository roleUserRepository;

    @Resource
    private GroupUserRepository groupUserRepository;

    @Resource
    private SystemCommonService systemCommonService;

    /**
     * name : list description : role list
     * 
     * @param : Map param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.USER_LIST)
    @PreAuthorize("hasAuthority('" + UserController.READ + "')")
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> list(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));

        if (param.get("orderBy") != null) {
            param.put("orderBy", StringUtils.changeColName(User.class, (String) param.get("orderBy")));
        }

        if (param.get("searchName") != null && param.get("searchName") != "") {
            param.put("searchName", textEncryptor.encrypt((param.get("searchName").toString())));
        }

        Map<String, Object> rtnMap = new HashMap<String, Object>();

        List<User> userList = userMapper.list(param);

        userList.stream().forEach(row -> {

            if (row.getFirstname() != null && !row.getFirstname().equals(""))
                row.setFirstname(textEncryptor.decrypt(row.getFirstname()));
            if (row.getMobile() != null && !row.getMobile().equals(""))
                row.setMobile(textEncryptor.decrypt(row.getMobile()));
            if (row.getEmail() != null && !row.getEmail().equals(""))
                row.setEmail(textEncryptor.decrypt(row.getEmail()));

            String createNm = row.getCreateNm();
            String updateNm = row.getUpdateNm();

            if (!StringUtils.isNull(createNm)) {
                row.setCreateNm(textEncryptor.decrypt(createNm));
            }
            if (!StringUtils.isNull(updateNm)) {
                row.setUpdateNm(textEncryptor.decrypt(updateNm));
            }
        });

        rtnMap.put("total", userMapper.listTot(param));
        rtnMap.put("data", userList);
        return ResponseEntity.ok().body(rtnMap);
    }
    // public ResponseEntity<?> list(@RequestParam Map<String,Object> param,
    // HttpServletRequest request){
    // param.put(ESConstants.USER_FILTER_KEY,
    // request.getAttribute(ESConstants.USER_FILTER_KEY));

    // List<User> userList = userMapper.list(param);

    // userList.stream().forEach(row -> {

    // if(row.getFirstname() != null && !row.getFirstname().equals(""))
    // row.setFirstname(textEncryptor.decrypt(row.getFirstname()));
    // if(row.getMobile() != null && !row.getMobile().equals(""))
    // row.setMobile(textEncryptor.decrypt(row.getMobile()));
    // if(row.getEmail() != null && !row.getEmail().equals(""))
    // row.setEmail(textEncryptor.decrypt(row.getEmail()));

    // String createNm = row.getCreateNm();
    // String updateNm = row.getUpdateNm();

    // if(!StringUtils.isNull(createNm)){
    // row.setCreateNm(textEncryptor.decrypt(createNm));
    // }
    // if(!StringUtils.isNull(updateNm)){
    // row.setUpdateNm(textEncryptor.decrypt(updateNm));
    // }
    // });

    // return ResponseEntity.ok().body(userList);
    // }

    /**
     * name : get description : user one row
     * 
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + UserController.READ + "')")
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<User> app = userRepository.findById(id);

        if (!app.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        User user = app.get();
        if (user.getFirstname() != null && !user.getFirstname().equals(""))
            user.setFirstname(textEncryptor.decrypt(user.getFirstname()));
        if (user.getMobile() != null && !user.getMobile().equals(""))
            user.setMobile(textEncryptor.decrypt(user.getMobile()));
        if (user.getEmail() != null && !user.getEmail().equals(""))
            user.setEmail(textEncryptor.decrypt(user.getEmail()));

        String createNm = systemCommonService.getusername(user.getCreateId());
        String updateNm = systemCommonService.getusername(user.getUpdateId());
        if (!StringUtils.isNull(createNm)) {
            user.setCreateNm(textEncryptor.decrypt(createNm));
        }
        if (!StringUtils.isNull(updateNm)) {
            user.setUpdateNm(textEncryptor.decrypt(updateNm));
        }

        return ResponseEntity.ok().body(user);
    }

    /**
     * name : post description : insert a new user to esuser table
     * 
     * @param : User data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + UserController.SAVE + "')")
    @Transactional(value = "transactionManager")
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> post(@RequestBody User data, HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) request.getAttribute(ESConstants.USER_DETAILS_KEY);
        data.setPassword(passwordEncoderBean.encode("kaiuser01!"));
        data.setCreateId(userDetails.getUsername());
        data.setUpdateId(userDetails.getUsername());

        if (data.getFirstname() != null && !data.getFirstname().equals(""))
            data.setFirstname(textEncryptor.encrypt(data.getFirstname()));
        if (data.getEmail() != null && !data.getEmail().equals(""))
            data.setEmail(textEncryptor.encrypt(data.getEmail()));
        if (data.getMobile() != null && !data.getMobile().equals(""))
            data.setMobile(textEncryptor.encrypt(data.getMobile()));
        return ResponseEntity.ok().body(userRepository.save(data));
    }

    /**
     * name : put description : update user
     * 
     * @param : Long id, User data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + UserController.SAVE + "')")
    @Transactional(value = "transactionManager")
    @PutMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<?> put(@PathVariable("id") Long id, @RequestBody User data, HttpServletRequest request){
        Optional<User> app = userRepository.findById(id);
        if (!app.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        UserDetails userDetails = (UserDetails) request.getAttribute(ESConstants.USER_DETAILS_KEY);

        User user = app.get();
        if (data.getFirstname() != null && !data.getFirstname().equals(""))
            user.setFirstname(textEncryptor.encrypt(data.getFirstname()));
        user.setLastname(data.getLastname());
        user.setOrgId(data.getOrgId());
        if (data.getEmail() != null && !data.getEmail().equals(""))
            user.setEmail(textEncryptor.encrypt(data.getEmail()));
        if (data.getMobile() != null && !data.getMobile().equals("")) {
            user.setMobile(textEncryptor.encrypt(data.getMobile()));
        } else {
            user.setMobile(null);
        }
        // user.setTel(data.getTel());
        user.setAddress(data.getAddress());
        user.setStatus(data.getStatus());
        user.setLang(data.getLang());
        user.setUpdateId(userDetails.getUsername());

        return ResponseEntity.ok().body(userRepository.save(user));
    }

    // page: user / update password popup, update password
    @PreAuthorize("hasAuthority('" + UserController.SAVE + "')")
    @Transactional(value = "transactionManager")
    @PutMapping(value = "/pw/{id}")
    @ResponseBody
    public ResponseEntity<?> putPassword(@PathVariable("id") Long id, @RequestBody User data,
            HttpServletRequest request) throws Exception {
        UserDetails userDetails = (UserDetails) request.getAttribute(ESConstants.USER_DETAILS_KEY);
        Optional<User> app = userRepository.findById(id);
        if (!app.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        User user = app.get();
        user.setPassword(passwordEncoderBean.encode(data.getPassword()));
        user.setUpdateId(userDetails.getUsername());
        return ResponseEntity.ok().body(userRepository.save(user));
    }

    /**
     * name : delete description : delete user
     * 
     * @param : Long id,
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + UserController.DELETE + "')")
    @Transactional(value = "transactionManager")
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Optional<User> app = userRepository.findById(id);
        if (!app.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        User oldUser = userRepository.findById(id).get();
        roleUserRepository.deleteByUsername(oldUser.getUsername());
        groupUserRepository.deleteByUsername(oldUser.getUsername());
        userRepository.delete(app.get());
        return ResponseEntity.ok().build();
    }

    /**
     * name : roleList description : get user's role list
     * 
     * @param : Long id,
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + UserController.AUTHREAD + "')")
    @GetMapping(value = "/{id}/role")
    @ResponseBody
    public ResponseEntity<?> roleList(@PathVariable("id") Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().body(userMapper.getUserRole(user.get()));
    }

    /**
     * name : authList description : get user's authority list
     * 
     * @param : Long id,
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + UserController.AUTHREAD + "')")
    @GetMapping(value = "/{id}/userauth")
    @ResponseBody
    public ResponseEntity<?> authList(@PathVariable("id") Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().body(userMapper.getUserAuthorities(user.get()));
    }

    /**
     * name : filterList description : get user's filter list
     * 
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + UserController.FILTERREAD + "')")
    @GetMapping(value = "/{id}/userfilter")
    @ResponseBody
    public ResponseEntity<?> filterList(@PathVariable("id") Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Map<String, String> param = new HashMap<String, String>();
        param.put("username", user.get().getUsername());
        return ResponseEntity.ok().body(userMapper.getUserFilter(param));
        // return
        // ResponseEntity.ok().body(userFilterRepository.findByUsername(user.get().getUsername()));
    }

    /**
     * name : postFilter description : insert userfilter
     * 
     * @param : UserFilter param
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + UserController.FILTERSAVE + "')")
    @PostMapping(value = "/userfilter")
    @ResponseBody
    public ResponseEntity<?> postFilter(@RequestBody UserFilter param) {
        UserFilter data = param;
        if (param.getId() != null) {
            Optional<UserFilter> option = userFilterRepository.findById(param.getId());
            if (option.isPresent()) {
                data = option.get();
                data.setWhereClause(param.getWhereClause());
            }
        }
        return ResponseEntity.ok().body(userFilterRepository.save(data));
    }

    /**
     * name : deleteFilter description : delete filter
     * 
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + UserController.FILTERDELETE + "')")
    @DeleteMapping(value = "/userfilter/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteFilter(@PathVariable("id") Long id) {
        userFilterRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}