package com.itmsg.episode.app.system.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.itmsg.episode.app.system.esserver.ESServerRepository;
import com.itmsg.episode.config.WebSecurityConfig;
import com.itmsg.episode.system.DataFilterEnabled;
import com.itmsg.episode.system.ESApplicationOption;
import com.itmsg.episode.system.ESConstants;
import com.itmsg.episode.system.ESDataFilter;
import com.itmsg.episode.system.SystemCommonService;
import com.itmsg.episode.util.StringUtils;

@RestController
@RequestMapping(value= SchedulerController.URL)
public class SchedulerController {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String TABLE_NAME="SCHEDULER";

    public static final String URL="/api/system/scheduler";

    @ESApplicationOption(application = SchedulerController.TABLE_NAME, description = "Scheduler read option")
    public static final String READ = "SCHEDULER_READ";

    @ESApplicationOption(application = SchedulerController.TABLE_NAME, description = "Scheduler add option")
    public static final String ADD = "SCHEDULER_ADD";

    @ESApplicationOption(application = SchedulerController.TABLE_NAME, description = "Scheduler save option")
    public static final String SAVE = "SCHEDULER_SAVE";

    @ESApplicationOption(application = SchedulerController.TABLE_NAME, description = "Scheduler delete option")
    public static final String DELETE = "SCHEDULER_DELETE";

    @ESApplicationOption(application = SchedulerController.TABLE_NAME, description = "Scheduler start option")
    public static final String START = "SCHEDULER_START";

    @ESApplicationOption(application = SchedulerController.TABLE_NAME, description = "Scheduler stop option")
    public static final String STOP = "SCHEDULER_STOP";

    @ESApplicationOption(application = SchedulerController.TABLE_NAME, description = "Scheduler execute option")
    public static final String EXECUTE = "SCHEDULER_EXECUTE";
    
    @Resource
    TextEncryptor textEncryptor;

    @Resource
    SchedulerRepository schedulerRepository;

    @Resource
    SchedulerHistoryRepository schedulerHistoryRepository;

    @Resource
    SchedulerActionRepository schedulerActionRepository;

    @Resource
    SchedulerService schedulerService;

    @Resource
    private SystemCommonService systemCommonService;

    @DataFilterEnabled(filter = ESDataFilter.SCHEDULER_LIST)
    @PreAuthorize("hasAuthority('"+SchedulerController.READ+"')")
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> list(@RequestParam Map<String,Object> param){
        List<Scheduler> schedulers = null;
        if(param.get("schedulerId")!= null){
            schedulers = schedulerRepository.findBySchedulerId((String)param.get("schedulerId"));
        }else {
            schedulers = schedulerRepository.findAll(new Sort(Sort.Direction.DESC,"schedulerId"));
        }
        for(Scheduler scheduler:schedulers){
            scheduler.setIsAlive(schedulerService.isAlive(scheduler));
        }
        return ResponseEntity.ok().body(schedulers);
    }
    
    
    @PreAuthorize("hasAuthority('"+SchedulerController.READ+"')")
    @GetMapping(value="/{id}")
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") Long id){
        Optional<Scheduler> find=schedulerRepository.findById(id);
        if(!find.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Scheduler scheduler = find.get();

        String createNm = systemCommonService.getusername(scheduler.getCreateId());
        if(!StringUtils.isNull(createNm)){
            scheduler.setCreateNm(textEncryptor.decrypt(createNm));
        }
        String updateNm = systemCommonService.getusername(scheduler.getUpdateId());
        if(!StringUtils.isNull(updateNm)){
            scheduler.setUpdateNm(textEncryptor.decrypt(updateNm));
        }      

        scheduler.setIsAlive(schedulerService.isAlive(scheduler));
        return ResponseEntity.ok().body(scheduler);
    }

    @PreAuthorize("hasAuthority('"+SchedulerController.READ+"')")
    @GetMapping(value="/{id}/history")
    @ResponseBody
    public ResponseEntity<?> getHistory(@PathVariable("id") Long id){
        Optional<Scheduler> find=schedulerRepository.findById(id);
        if(!find.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().body(schedulerHistoryRepository.findBySchedulerId(find.get().getSchedulerId(),new Sort(Sort.Direction.DESC,"id")));
    }

    
    @PreAuthorize("hasAuthority('"+SchedulerController.READ+"')")
    @GetMapping(value="/{id}/actionhistory")
    @ResponseBody
    public ResponseEntity<?> getAction(@PathVariable("id") Long id){
        Optional<Scheduler> find=schedulerRepository.findById(id);
        if(!find.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().body(schedulerActionRepository.findBySchedulerId(find.get().getSchedulerId(),new Sort(Sort.Direction.DESC,"id")));
    }

    @PreAuthorize("hasAuthority('"+SchedulerController.EXECUTE+"')")
    @PostMapping(value="/{id}/execute")
    @ResponseBody
    public ResponseEntity<?> execute(@PathVariable("id") Long id, HttpServletRequest request){
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        Optional<Scheduler> find=schedulerRepository.findById(id);
        if(!find.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        schedulerService.execute(find.get(),userDetails.getUsername());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('"+SchedulerController.READ+"')")
    @GetMapping(value="/available")
    @ResponseBody
    public ResponseEntity<?> schedulerList(@RequestParam Map<String,String> param){
        return ResponseEntity.ok().body(schedulerService.appSchedulers());
    }

    @PreAuthorize("hasAuthority('"+SchedulerController.START+"')")
    @PostMapping(value="/{id}/start")
    @ResponseBody
    public ResponseEntity<?> start(@PathVariable("id") Long id, HttpServletRequest request){
        schedulerService.action(id,"START","startScheduler", request);
        return  ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('"+SchedulerController.STOP+"')")
    @PostMapping(value="/{id}/stop")
    @ResponseBody
    public ResponseEntity<?> stop(@PathVariable("id") Long id, HttpServletRequest request){
        schedulerService.action(id,"STOP","stopScheduler", request);
        return  ResponseEntity.ok().build();
    }

    @PostMapping(value="/{id}/startScheduler")
    @ResponseBody
    public ResponseEntity<?> startScheduler(@PathVariable("id") Long id, @RequestHeader(name = "username") String username, @RequestHeader(name = "password") String password){
        schedulerService.startScheduler(id, username, password);
        return  ResponseEntity.ok().build();
    }

    @PostMapping(value="/{id}/stopScheduler")
    @ResponseBody
    public ResponseEntity<?> stopScheduler(@PathVariable("id") Long id, @RequestHeader(name = "username") String username, @RequestHeader(name = "password") String password){
        schedulerService.stopScheduler(id, username, password);
        return  ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('"+SchedulerController.SAVE+"')")
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> post(@RequestBody Scheduler data, HttpServletRequest request){
        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);
        data.setCreateId(userDetails.getUsername());
        data.setUpdateId(userDetails.getUsername());
        Scheduler scheduler = schedulerRepository.save(data);
        scheduler.setIsAlive(schedulerService.isAlive(scheduler));
        return ResponseEntity.ok().body(scheduler);
    }
  
    @PreAuthorize("hasAuthority('"+SchedulerController.SAVE+"')")
    @PutMapping(value="/{id}")                          
    @ResponseBody
    public ResponseEntity<?> put(@PathVariable("id") Long id,@RequestBody Scheduler data, HttpServletRequest request) throws Exception{
        Optional<Scheduler> find=schedulerRepository.findById(id);

        if(!find.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        UserDetails userDetails = (UserDetails)request.getAttribute(ESConstants.USER_DETAILS_KEY);

        Scheduler scheduler = find.get();
        scheduler.setDescription(data.getDescription());
        scheduler.setExpression(data.getExpression());
        scheduler.setParameter(data.getParameter());
        scheduler.setUpdateId(userDetails.getUsername());
        scheduler = schedulerRepository.save(data);
        scheduler.setIsAlive(schedulerService.isAlive(scheduler));
        return ResponseEntity.ok().body(scheduler);
    }

}