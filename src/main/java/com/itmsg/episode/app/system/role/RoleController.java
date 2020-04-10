/**
* 롤 컨트롤러 클래스
* @author   이정호
* @since    2019.11.06
* 수정일       수정자   수정내용
* ==========   =====   ================
* 2019.11.06   이정호   최초 생성
*/
package com.itmsg.episode.app.system.role;

import com.itmsg.episode.app.system.user.User;
import com.itmsg.episode.system.DataFilterEnabled;
import com.itmsg.episode.system.ESApplicationOption;
import com.itmsg.episode.system.ESConstants;
import com.itmsg.episode.system.ESDataFilter;
import com.itmsg.episode.system.SystemCommonController;
import com.itmsg.episode.system.SystemCommonService;
import com.itmsg.episode.util.StringUtils;

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
@RequestMapping(value = RoleController.URL)
public class RoleController {
    public static final String APPLICATION = "ROLE";
    public static final String URL = "/api/system/role";

    @ESApplicationOption(application = RoleController.APPLICATION, description = "Role read option")
    public static final String READ = "ROLE_READ";

    @ESApplicationOption(application = RoleController.APPLICATION, description = "Role save option")
    public static final String SAVE = "ROLE_SAVE";

    @ESApplicationOption(application = RoleController.APPLICATION, description = "Role delete option")
    public static final String DELETE = "ROLE_DELETE";

    @ESApplicationOption(application = RoleController.APPLICATION, description = "Role authority read option")
    public static final String AUTH_READ = "ROLE_AUTH_READ";

    @ESApplicationOption(application = RoleController.APPLICATION, description = "Role authority save option")
    public static final String AUTH_SAVE = "ROLE_AUTH_SAVE";

    @ESApplicationOption(application = RoleController.APPLICATION, description = "Role authority delete option")
    public static final String AUTH_DELETE = "ROLE_AUTH_DELETE";

    @ESApplicationOption(application = RoleController.APPLICATION, description = "Role filter read option")
    public static final String FILTER_READ = "ROLE_FILTER_READ";

    @ESApplicationOption(application = RoleController.APPLICATION, description = "Role filter save option")
    public static final String FILTER_SAVE = "ROLE_FILTER_SAVE";

    @ESApplicationOption(application = RoleController.APPLICATION, description = "Role filter delete option")
    public static final String FILTER_DELETE = "ROLE_FILTER_DELETE";

    @Resource
    private RoleUserRepository roleUserRepository;

    @Resource
    private RoleAuthRepository roleAuthRepository;

    @Resource
    private RoleFilterRepository roleFilterRepository;

    @Resource
    private RoleGroupRepository roleGroupRepository;

    @Resource
    private RoleRepository roleRepository;

    @Resource
    private RoleService roleService;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private SystemCommonService systemCommonService;

    @Resource
    private SystemCommonController systemCommonController;
    
    @Resource
    private TextEncryptor textEncryptor;

    /**
     * name : list
     * description : role list
     * @param : Map param, HttpServletRequest request
     * @return : ResponseEntity
     */
    @DataFilterEnabled(filter = ESDataFilter.ROLE_LIST)
    @PreAuthorize("hasAuthority('" + RoleController.READ + "')")
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getRoleList(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        param.put(ESConstants.USER_FILTER_KEY, request.getAttribute(ESConstants.USER_FILTER_KEY));

        List<Role> roleList = roleMapper.roleList(param);  
        for(Role userGroup : roleList){
            String createNm = userGroup.getCreateNm();
            String updateNm = userGroup.getUpdateNm();
            if(!StringUtils.isNull(createNm)){
                userGroup.setCreateNm(textEncryptor.decrypt(createNm));
            }
            if(!StringUtils.isNull(updateNm)){
                userGroup.setUpdateNm(textEncryptor.decrypt(updateNm));
            }
        }
        return ResponseEntity.ok().body(roleList);
    }

    /**
     * name : getRoleChild
     * description : role, check sub data (user, authority, filter) by role id
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + RoleController.READ + "')")
    @GetMapping(value = "/child/{roldId}")
    @ResponseBody
    public ResponseEntity<?> getRoleChild(@PathVariable("roldId") String roldId) {
        return ResponseEntity.ok().body(roleMapper.childDataCount(roldId));
    }

    /**
     * name : getRole
     * description : role one row
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + RoleController.READ + "')")
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<?> getRole(@PathVariable("id") Long id) {
        Optional<Role> app = roleRepository.findById(id);
        if (!app.isPresent()) {
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
     * name : getRoleByName
     * description : role, check for duplicate role id
     * @param : String roleId
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + RoleController.READ + "')")
    @GetMapping(value = "/cd/{roldId}")
    @ResponseBody
    public ResponseEntity<?> getRoleByName(@PathVariable("roldId") String roldId) {
        return ResponseEntity.ok().body(roleRepository.findByRoleId(roldId));
    }

    /**
     * name : insertRole
     * description : role create
     * @param : Role data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + RoleController.SAVE + "')")
    @Transactional(value = "transactionManager")
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> insertRole(@RequestBody Role data, HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) request.getAttribute(ESConstants.USER_DETAILS_KEY);

        data.setCreateId(userDetails.getUsername());
        data.setUpdateId(userDetails.getUsername());

        Role role = roleRepository.save(data);

        if(data.getRoleFilter().size() != 0){
          for(RoleFilter roleFilter : data.getRoleFilter()){
            if(roleFilter.getStatus().equals("save")){
              roleFilter.setRoleId(role.getRoleId());
              roleFilter.setDatafilterId(roleFilter.getDatafilterId());
              roleFilter.setWhereClause(roleFilter.getWhereClause());
              roleFilter.setCreateId(userDetails.getUsername());
              roleFilter.setUpdateId(userDetails.getUsername());
              roleFilterRepository.save(roleFilter);
            }
          }
        }
        return ResponseEntity.ok().body(roleRepository.save(role));

    }

    /**
     * name : updateRole
     * description : role update
     * @param : Long id, Role data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + RoleController.SAVE + "')")
    @Transactional(value = "transactionManager")
    @PutMapping(value="/{id}")   
    @ResponseBody
    public ResponseEntity<?> updateRole(@PathVariable("id") Long id, @RequestBody Role data, HttpServletRequest request) {
        Optional<Role> app = roleRepository.findById(id);
        if(!app.isPresent()){            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        UserDetails userDetails = (UserDetails) request.getAttribute(ESConstants.USER_DETAILS_KEY);

        Role target=app.get();

        target.setDescription(data.getDescription());
        target.setUpdateId(userDetails.getUsername());
        
        Role role = roleRepository.save(target);
        
        if(data.getRoleFilter().size() != 0){
          for(RoleFilter roleFilter : data.getRoleFilter()){
              if(roleFilter.getStatus().equals("save")){
                if(roleFilter.getId() == null){
                  roleFilter.setRoleId(roleFilter.getRoleId());
                  roleFilter.setDatafilterId(roleFilter.getDatafilterId());
                  roleFilter.setWhereClause(roleFilter.getWhereClause());
                  roleFilter.setCreateId(userDetails.getUsername());
                  roleFilter.setUpdateId(userDetails.getUsername());
                } else {
                  roleFilter.setWhereClause(roleFilter.getWhereClause());
                  roleFilter.setUpdateId(userDetails.getUsername());
                }
                roleFilterRepository.save(roleFilter);
            }
          }
        }

        return ResponseEntity.ok().body(roleRepository.save(role));
    }

    /**
     * name : deleteRole
     * description : role delete
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + RoleController.DELETE + "')")
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteRole(@PathVariable("id") Long id) {
        Optional<Role> app = roleRepository.findById(id);
        if (!app.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        roleRepository.delete(app.get());
        return ResponseEntity.ok().build();
    }

    /**
     * name : getRoleAuthList
     * description : get authority list
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + RoleController.AUTH_READ + "')")
    @GetMapping(value = "/{id}/auth")
    @ResponseBody
    public ResponseEntity<?> getRoleAuthList(@PathVariable("id") Long id) {
        Optional<Role> role = roleRepository.findById(id);

        Map<String, Object> map = new HashMap<>();
        map.put("ROLEID", role.get().getRoleId());
        return ResponseEntity.ok().body(roleMapper.roleAuthList(map));
    }

    /**
     * name : getAuthList
     * description : add auth popup, get authority list (not include in this role)
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + RoleController.AUTH_READ + "')")
    @GetMapping(value = "/{id}/optionsearch")
    @ResponseBody
    public ResponseEntity<?> getAuthList(@PathVariable("id") Long id) {

        Optional<Role> role = roleRepository.findById(id);

        Map<String, Object> param = new HashMap<>();

        param.put("ROLEID", role.get().getRoleId());
        List<Map<String, String>> appList = systemCommonService.appOptionList(null);
        List<RoleAuth> roleAuths = roleMapper.roleAuthList(param);
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();

        for (Map<String, String> app : appList) {
            String option = app.get("option");
            if (roleAuths.stream().filter(roleAuth -> {
                return option.equals(roleAuth.getAuthorityId());
            }).count() == 0) {
                result.add(app);
            }
        }

        return ResponseEntity.ok().body(result);

    }

    /**
     * name : setRoleAuth
     * description : save authority to roleauth table
     * @param : Map data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + RoleController.AUTH_SAVE + "')")
    @PostMapping(value = "/auth")
    @ResponseBody
    public ResponseEntity<?> setRoleAuth(@RequestBody Map<String, Object> data, HttpServletRequest request) {

        UserDetails userDetails = (UserDetails) request.getAttribute(ESConstants.USER_DETAILS_KEY);

        roleService.setRoleAuth(userDetails, data);
  
        return ResponseEntity.ok().build();
    }

    /**
     * name : deleteAuth
     * description : to delete authority, get params (checked or all authority list)
     * @param : Map data
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + RoleController.AUTH_DELETE + "')")
    @PostMapping(value = "/auth/delete")
    @Transactional(value = "transactionManager")
    @ResponseBody
    public ResponseEntity<?> deleteAuth(@RequestBody Map<String, Object> data) {
        String roleId = data.get("roleId").toString();
        List<Map<String, Object>> list = (List<Map<String, Object>>) data.get("data");

        for (Map<String, Object> auth : list) {
            deleteAuth(Long.parseLong(auth.get("id").toString()), roleId);
        }

        return ResponseEntity.ok().build();
    }

     /**
     * name : authDeleteAll
     * description : to delete authority, get params (checked or all authority list)
     * @param : Map data
     * @return : ResponseEntity
     */   
    @PreAuthorize("hasAuthority('" + RoleController.AUTH_DELETE + "')")
    @DeleteMapping(value = "/{id}/authdeleteall")
    @Transactional(value = "transactionManager")
    @ResponseBody
    public ResponseEntity<?> authDeleteAll(@PathVariable("id") Long id) {
        
        Optional<Role> app = roleRepository.findById(id);
        String roleId =  app.get().getRoleId();
        roleMapper.authDeleteAll(roleId);
        return ResponseEntity.ok().build();

    }

     /**
     * name : deleteAuth
     * description : delete authority from roleauth
     * @param : Long id, String roleId
     * @return : ResponseEntity
     */   
    @PreAuthorize("hasAuthority('" + RoleController.AUTH_DELETE + "')")
    @DeleteMapping(value = "/auth/{id}/{roleId}")
    @Transactional(value = "transactionManager")
    @ResponseBody
    public ResponseEntity<?> deleteAuth(@PathVariable("id") Long id, @PathVariable("roleId") String roleId) {
        Optional<RoleAuth> app = roleAuthRepository.findById(id);
        if (!app.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        roleAuthRepository.delete(app.get());

        return ResponseEntity.ok().build();
    }

    /**
     * name : getRoleFilterList
     * description : get filter list
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + RoleController.FILTER_READ + "')")
    @GetMapping(value = "/{id}/filter")
    @ResponseBody
    public ResponseEntity<?> getRoleFilterList(@PathVariable("id") Long id) {

        Optional<Role> role = roleRepository.findById(id);

        Map<String, Object> map = new HashMap<>();
        map.put("ROLEID", role.get().getRoleId());

    
        return ResponseEntity.ok().body(roleMapper.roleFilterList(map));
    }

     /**
     * name : filterDeleteAll
     * description : delete filter, get params (checked or all filter list)
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + RoleController.FILTER_DELETE + "')")
    @DeleteMapping(value = "{id}/filterdeleteall")
    @Transactional(value = "transactionManager")
    @ResponseBody
    public ResponseEntity<?> filterDeleteAll(@PathVariable("id") Long id) {

        Optional<Role> app = roleRepository.findById(id);
        String roleId =  app.get().getRoleId();
        roleMapper.filterDeleteAll(roleId);
        return ResponseEntity.ok().build();
    }

     /**
     * name : filterDelete
     * description : delete filter, get params (checked or all filter list)
     * @param : Map data
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + RoleController.FILTER_DELETE + "')")
    @PostMapping(value = "/filterdelete")
    @Transactional(value = "transactionManager")
    @ResponseBody
    public ResponseEntity<?> filterDelete(@RequestBody Map<String, Object> data) {
        

        List<Map<String, Object>> list = (List<Map<String, Object>>) data.get("data");

        for (Map<String, Object> filter : list) {
            Optional<RoleFilter> rolefilter = roleFilterRepository.findById(Long.parseLong(filter.get("id").toString()));
            roleFilterRepository.delete(rolefilter.get());
        }

        return ResponseEntity.ok().build();
    }

     /**
     * name : deleteFilter
     * description : delete filter from rolefilter
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + RoleController.FILTER_DELETE + "')")
    @DeleteMapping(value = "/filter/{id}")
    @Transactional(value = "transactionManager")
    @ResponseBody
    public ResponseEntity<?> deleteFilter(@PathVariable("id") Long id) {
        Optional<RoleFilter> app = roleFilterRepository.findById(id);
        if (!app.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        roleFilterRepository.delete(app.get());
        return ResponseEntity.ok().build();
    }

    /**
     * name : getRoleUserList
     * description : get user list
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + RoleController.READ + "')")
    @GetMapping(value = "{id}/user")
    @ResponseBody
    public ResponseEntity<?> getRoleUserList(@PathVariable("id") Long id) {

        Optional<Role> role = roleRepository.findById(id);

        String roleId = role.get().getRoleId();

        List<RoleUser> groupUsers = roleUserRepository.getByRoleId(roleId);
        List<User> userList = new ArrayList<>();

        for(RoleUser roleUser : groupUsers){

          User user = roleUser.getUser();
          if(user.getFirstname() != null && !user.getFirstname().equals("")) user.setFirstname(textEncryptor.decrypt(user.getFirstname())); 
          if(user.getMobile() != null && !user.getMobile().equals("")) user.setMobile(textEncryptor.decrypt(user.getMobile()));
          if(user.getEmail() != null && !user.getEmail().equals("")) user.setEmail(textEncryptor.decrypt(user.getEmail()));
          user.setRoleUserId(roleUser.getId());
          userList.add(user);
        }


        return ResponseEntity.ok().body(userList);
    }

    /**
     * name : postRoleUser
     * description : insert new user to role
     * @param : Map data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + RoleController.SAVE + "')")
    @PostMapping(value = "/user")
    @ResponseBody
    public ResponseEntity<?> postRoleUser(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) request.getAttribute(ESConstants.USER_DETAILS_KEY);

        roleService.addRoleUser(userDetails, data);

        return ResponseEntity.ok().build();
    }

    /**
     * name : getUsers
     * description : add user popup, get user list (not include in this role)x
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + RoleController.READ + "')")
    @GetMapping(value = "/{id}/usersearch")
    @ResponseBody
    public ResponseEntity<?> getUsers(@PathVariable("id") Long id) {

        Optional<Role> role = roleRepository.findById(id);
        String roleId = role.get().getRoleId();
        List<RoleUser> groupUsers = roleUserRepository.getByRoleId(roleId);

        List<User> users = roleService.users(groupUsers);

        for(User user : users){

            if(user.getFirstname() != null && !user.getFirstname().equals("")) user.setFirstname(textEncryptor.decrypt(user.getFirstname())); 
            if(user.getMobile() != null && !user.getMobile().equals("")) user.setMobile(textEncryptor.decrypt(user.getMobile()));
            if(user.getEmail() != null && !user.getEmail().equals("")) user.setEmail(textEncryptor.decrypt(user.getEmail()));
          }

        return ResponseEntity.ok().body(users);
    }

    /**
     * name : deleteUser
     * description : delete user from roleuser
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + RoleController.DELETE + "')")
    @DeleteMapping(value = "/user/{id}")
    @Transactional(value = "transactionManager")
    @ResponseBody
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {

        Optional<RoleUser> app = roleUserRepository.findById(id);
        if (!app.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        roleUserRepository.delete(app.get());
        return ResponseEntity.ok().build();
    }

    /**
     * name : getGroups
     * description : get user group list in this role
     * @param : List params
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + RoleController.READ + "')")
    @PostMapping(value = "/rolegroup/cd/groups")
    @ResponseBody
    public ResponseEntity<?> getGroups(@RequestBody List<Map<String, Object>> params) {
        StringBuffer groups = new StringBuffer();
        for (int i = 0; i < params.size(); i++) {
            groups.append("'" + params.get(i).get("usergroupId") + "'");

            if (i != (params.size() - 1)) {
                groups.append(",");
            }
        }

        Map map = new HashMap();
        map.put("GROUPS", groups.toString());
        return ResponseEntity.ok().body(roleMapper.getGroups(map));
    }

    /**
     * name : getUserGroupList
     * description : get user group info by role id
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + RoleController.READ + "')")
    @GetMapping(value = "/{id}/rolegroup")
    @ResponseBody
    public ResponseEntity<?> getUserGroupList(@PathVariable("id") Long id) {
    // public ResponseEntity<?> getUserGroupList(@PathVariable("roleId") String roleId) {
        Optional<Role> role = roleRepository.findById(id);
        if(!role.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().body(roleGroupRepository.findByRoleId(role.get().getRoleId()));
    }

    /**
     * name : setUserGroup
     * description : insert a user group to rolegroup table
     * @param : Map data, HttpServletRequest request
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + RoleController.SAVE + "')")
    @PostMapping(value = "/rolegroup")
    @ResponseBody
    public ResponseEntity<?> setUserGroup(@RequestBody Map<String,Object> data, HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) request.getAttribute(ESConstants.USER_DETAILS_KEY);

        roleService.setUserGroup(userDetails,data);

        return ResponseEntity.ok().build();
    }

    /**
     * name : setUserGroup
     * description : delete usergroup from role
     * @param : Map items
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('"+ RoleController.READ+"')")
    @PostMapping(value="/rolegroup/delete")
    @Transactional(value="transactionManager")
    @ResponseBody
    public ResponseEntity<?> deleteUserGroup(@RequestBody Map<String, Object> items) {
        String roleId = items.get("roleId").toString();

        Optional<RoleGroup> app = Optional.ofNullable(roleGroupRepository.findByRoleIdAndUsergroupId(roleId, items.get("usergroupId").toString()));
        if(!app.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        roleGroupRepository.delete(app.get());

        return ResponseEntity.ok().build();
    }

    /**
     * name : getFilterSearchList
     * description : filterSearchList
     * @param : Long id
     * @return : ResponseEntity
     */
    @PreAuthorize("hasAuthority('" + RoleController.AUTH_READ + "')")
    @GetMapping(value = "/{id}/filtersearch")
    @ResponseBody
    public ResponseEntity<?> getFilterSearchList(@PathVariable("id") Long id) {

        ESDataFilter[] dataFilterList = ESDataFilter.values();

        Optional<Role> role = roleRepository.findById(id);
        Map<String, Object> param = new HashMap<>();
        param.put("ROLEID", role.get().getRoleId());
 
        List<RoleFilter> roleFilters = roleMapper.roleFilterList(param);
        List<ESDataFilter> result = new ArrayList<ESDataFilter>();

        for (ESDataFilter app : dataFilterList) {
            String option = app.name();
            if (roleFilters.stream().filter(rolefilter -> {
                return option.equals(rolefilter.getDatafilterId());
            }).count() == 0) {
                result.add(app);
            }
        }

        return ResponseEntity.ok().body(result);

    }

}
