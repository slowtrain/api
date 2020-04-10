/**
* 유저 그룹 컨트롤러 클래스
* @author   이정호
* @since    2019.11.06
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.06   이정호   최초 생성
*/
package com.itmsg.episode.app.system.usergroup;

import com.itmsg.episode.app.system.role.RoleGroupRepository;
import com.itmsg.episode.app.system.user.User;
import com.itmsg.episode.system.DataFilterEnabled;
import com.itmsg.episode.system.ESApplicationOption;
import com.itmsg.episode.system.ESConstants;
import com.itmsg.episode.system.ESDataFilter;
import com.itmsg.episode.system.SystemCommonService;
import com.itmsg.episode.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping(value= UserGroupController.URL)
public class UserGroupController {
    public static final String APPLICATION="USERGROUP";
    public static final String URL="/api/system/usergroup";

    @ESApplicationOption(application = UserGroupController.APPLICATION, description = "UserGroup read option")
    public static final String READ = "USERGROUP_READ";

    @ESApplicationOption(application = UserGroupController.APPLICATION, description = "UserGroup save option")
    public static final String SAVE = "USERGROUP_SAVE";

    @ESApplicationOption(application = UserGroupController.APPLICATION, description = "UserGroup delete option")
    public static final String DELETE = "USERGROUP_DELETE";


    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private GroupUserRepository groupUserRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Resource
    private UserGroupService usergroupService;

    @Resource
    private UserGroupMapper usergroupMapper;

    @Resource
    private RoleGroupRepository roleGroupRepository;

    @Resource
    private TextEncryptor textEncryptor;

    @Resource
    private SystemCommonService systemCommonService;

    /**
     * name : getUserGroupList
     * description : userGroup list
     * @param : Map param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.USERGROUP_LIST)
    @PreAuthorize("hasAuthority('"+UserGroupController.READ+"')")
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getUserGroupList(@RequestParam Map<String,Object> param, HttpServletRequest request) {
        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));
          
       
        if(param.get("orderBy") != null){
            param.put("orderBy", StringUtils.changeColName(User.class, (String) param.get("orderBy")));
        }
        
        Map<String, Object> rtnMap = new HashMap<String, Object>();

        List<UserGroup> userGroupList = usergroupMapper.userGroupList(param);  

        for(UserGroup userGroup : userGroupList){
            String createNm = userGroup.getCreateNm();
            String updateNm = userGroup.getUpdateNm();
            if(!StringUtils.isNull(createNm)){
                userGroup.setCreateNm(textEncryptor.decrypt(createNm));
            }
            if(!StringUtils.isNull(updateNm)){
                userGroup.setUpdateNm(textEncryptor.decrypt(updateNm));
            }
        }

        rtnMap.put("total", usergroupMapper.listTot(param));
        rtnMap.put("data", userGroupList);
        
        return ResponseEntity.ok().body(rtnMap);
    }

    /**
     * name : getUserGroupAnecstor
     * description : userGroup get anecstor
     * @param : Map param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.USERGROUP_LIST)
    @PreAuthorize("hasAuthority('"+UserGroupController.READ+"')")
    @GetMapping(value="/usergroupancstor")
    @ResponseBody
    public ResponseEntity<?> getUserGroupAnecstor(@RequestParam Map<String,Object> param, HttpServletRequest request) {
        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));
        
        
        return ResponseEntity.ok().body(usergroupMapper.userGroupAncestorList(param));
    }
    

    /**
     * name : getUserGroupChild
     * description : userGroup sub data
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+UserGroupController.READ+"')")
    @GetMapping(value="/{id}/child")
    @ResponseBody
    public ResponseEntity<?> getUserGroupChild(@PathVariable("id") Long id) {
        
        Optional<UserGroup> app = userGroupRepository.findById(id);
        if(!app.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String usergroupId = app.get().getUsergroupId();
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("USERGROUPID", usergroupId);

        return ResponseEntity.ok().body(usergroupMapper.childDataCount(map));
    }

    /**
     * name : getUserGroup
     * description : userGroup one row
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+UserGroupController.READ+"')")
    @GetMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> getUserGroup(@PathVariable("id") Long id) {
        Optional<UserGroup> app = userGroupRepository.findById(id);
        if(!app.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String createNm = systemCommonService.getusername(app.get().getCreateId());
        String updateNm = systemCommonService.getusername(app.get().getUpdateId());
        if(!StringUtils.isNull(createNm)){
            app.get().setCreateNm(textEncryptor.decrypt(createNm));
        }
        if(!StringUtils.isNull(updateNm)){
            app.get().setUpdateNm(textEncryptor.decrypt(updateNm));
        }

        return ResponseEntity.ok().body(app.get());
    }

    /**
     * name : getUserGroupByName
     * description : check duplicate userGroupId
     * @param : Map param
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+UserGroupController.READ+"')")
    @GetMapping(value="/userGroupIdChk")
    @ResponseBody
    public ResponseEntity<?> getUserGroupByName(@RequestParam Map<String,Object> param) {

        String usergroupId = (String)param.get("usergroupId"); 
        
        return ResponseEntity.ok().body(userGroupRepository.findByUsergroupId(usergroupId));
    }

    /**
     * name : insertUserGroup
     * description : insert new userGroup
     * @param : UserGroup data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+UserGroupController.SAVE+"')")
    @Transactional(value="transactionManager")
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> insertUserGroup(@RequestBody UserGroup data, HttpServletRequest request){
        UserDetails userDetails = (UserDetails) request.getAttribute(ESConstants.USER_DETAILS_KEY);

        data.setCreateId(userDetails.getUsername());
        data.setUpdateId(userDetails.getUsername());

        return ResponseEntity.ok().body(userGroupRepository.save(data));
    }

    /**
     * name : updateUserGroup
     * description : update userGroup
     * @param : UserGroup data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+UserGroupController.SAVE+"')")
    @Transactional(value="transactionManager")
    @PutMapping
    @ResponseBody
    public ResponseEntity<?> updateUserGroup(@RequestBody UserGroup data, HttpServletRequest request){
        UserDetails userDetails = (UserDetails) request.getAttribute(ESConstants.USER_DETAILS_KEY);

        data.setCreateId(userDetails.getUsername());
        data.setUpdateId(userDetails.getUsername());

        return ResponseEntity.ok().body(userGroupRepository.save(data));
    }

    /**
     * name : deleteUserGroup
     * description :  delete userGroup
     * @param : Long id, String usergroupId
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+UserGroupController.DELETE+"')")
    @Transactional(value="transactionManager")
    @DeleteMapping(value="/{id}/{usergroupId}")
    @ResponseBody
    public ResponseEntity<?> deleteUserGroup(@PathVariable("id") Long id, @PathVariable("usergroupId") String usergroupId) {
        Optional<UserGroup> app = userGroupRepository.findById(id);
        if(!app.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        UserGroup oldUsergroupId = userGroupRepository.findById(id).get();
        roleGroupRepository.deleteByUsergroupId(oldUsergroupId.getUsergroupId());
        userGroupRepository.delete(app.get());

        return ResponseEntity.ok().build();
    }

    /**
     * name : getGroupUserList
     * description : groupUser List
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+UserGroupController.READ+"')")
    @GetMapping(value="/{id}/user")
    @ResponseBody
    public ResponseEntity<?> getGroupUserList(@PathVariable("id") Long id) {

        Optional<UserGroup> app = userGroupRepository.findById(id);
        if(!app.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String usergroupId = app.get().getUsergroupId();

        List<GroupUser> groupUsers = groupUserRepository.getByUsergroupId(usergroupId);
        List<User> userList = new ArrayList<>();
        
        for(GroupUser groupUser : groupUsers){

            User user = groupUser.getUser();
            if(user.getFirstname() != null && !user.getFirstname().equals("")) user.setFirstname(textEncryptor.decrypt(user.getFirstname())); 
            if(user.getMobile() != null && !user.getMobile().equals("")) user.setMobile(textEncryptor.decrypt(user.getMobile()));
            if(user.getEmail() != null && !user.getEmail().equals("")) user.setEmail(textEncryptor.decrypt(user.getEmail()));
            user.setGroupUserId(groupUser.getId());
            userList.add(user);
          }

        return ResponseEntity.ok().body(userList);
    }

    /**
     * name : getuserOrgList
     * description : organization list
     * @param : Map param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+UserGroupController.READ+"')")
    @GetMapping(value="/user/orglist")
    @ResponseBody
    public ResponseEntity<?> getuserOrgList(@RequestParam Map<String,Object> param, HttpServletRequest request){
        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));

        List<User> users = usergroupMapper.users(param);

        for(User user : users){

            if(user.getFirstname() != null && !user.getFirstname().equals("")) user.setFirstname(textEncryptor.decrypt(user.getFirstname())); 
            if(user.getMobile() != null && !user.getMobile().equals("")) user.setMobile(textEncryptor.decrypt(user.getMobile()));
            if(user.getEmail() != null && !user.getEmail().equals("")) user.setEmail(textEncryptor.decrypt(user.getEmail()));
          }

        return ResponseEntity.ok().body(users);
    }

    /**
     * name : postGroupUser
     * description : insert new groupUser
     * @param : Map data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+UserGroupController.SAVE+"')")
    @PostMapping(value="/user")
    @Transactional(value="transactionManager")
    @ResponseBody
    public ResponseEntity<?> postGroupUser(@RequestBody Map<String, Object> data, HttpServletRequest request){
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        String usergroupId = data.get("usergroupId").toString();

        List<Map<String, Object>> rows = (List<Map<String, Object>>) data.get("rows");
        for ( Map row : rows ) {
            Optional<GroupUser> app= Optional.ofNullable(groupUserRepository.findByUsergroupIdAndUsername(usergroupId, row.get("username").toString()));
            if(!app.isPresent()){
                GroupUser gui = new GroupUser();
                
                gui.setUsergroupId(usergroupId);
                gui.setUsername(row.get("username").toString());
                gui.setOrgId(row.get("orgId").toString());
                gui.setCreateId(userDetails.getUsername());
                gui.setUpdateId(userDetails.getUsername());

                groupUserRepository.save(gui);
            }
        }

        return ResponseEntity.ok().build();
    }

    /**
     * name : getUsers
     * description : add user popup, get user list not include in this group
     * @param : List params, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+UserGroupController.SAVE+"')")
    @GetMapping(value="/user/getUsers")
    @Transactional(value="transactionManager")
    @ResponseBody
    public ResponseEntity<?> getUsers(@RequestBody List<Map<String,Object>> params){

        StringBuffer users = new StringBuffer();
        for (int i=0; i<params.size(); i++) {
            users.append("'" + params.get(i).get("username") + "'");

            if (i != (params.size() - 1)) {
                users.append(",");
            }
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("GROUPUSERS", users.toString());

        return ResponseEntity.ok().body(usergroupMapper.users(map));
    }

    /**
     * name : deleteUser
     * description : delete userGroup
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+UserGroupController.DELETE+"')")
    @DeleteMapping(value="/user/{id}")
    @Transactional(value="transactionManager")
    @ResponseBody
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        Optional<GroupUser> app = groupUserRepository.findById(id);
        if(!app.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        groupUserRepository.delete(app.get());
        return ResponseEntity.ok().build();
    }
}
