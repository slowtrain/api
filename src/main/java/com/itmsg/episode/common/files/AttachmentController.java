
package com.itmsg.episode.common.files;

import java.util.Optional;

import javax.annotation.Resource;

import com.itmsg.episode.util.FileUtil;
import org.springframework.data.domain.Sort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping(value = AttachmentController.URL)
public class AttachmentController {


  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  public static final String URL = "/api/common/attachment";

  @Value("${system.attachment.allow-extension}")
	String allowExtentions;

  @Resource
  FileUtil fileUtil;

  @Resource
  AttachmentRepository attachmentRepository;

  @Resource
  AttachmentMapper attachmentMapper;

  @GetMapping
  @ResponseBody
  public ResponseEntity<?> getAttachmentList(@RequestParam("ownertable") String ownertable, @RequestParam("ownerid") Long id) {
    return ResponseEntity.ok().body(attachmentRepository.findByOwnertableAndOwnerid(ownertable, id, new Sort(Sort.Direction.DESC,"id")));
  }

  @GetMapping(value = "/extentions")
  @ResponseBody
  public ResponseEntity<?> getExtentions() {
    return ResponseEntity.ok().body(allowExtentions);
  }

  @PostMapping(value = "/upload")
  @ResponseBody
  public ResponseEntity<?> fileUpload(MultipartHttpServletRequest multipartHttpServletRequest) {
    return fileUtil.upload(multipartHttpServletRequest);
  }

  @GetMapping(value = "/{id}/download")
  @ResponseBody
  public ResponseEntity<?> fileDownload(@PathVariable("id") Long id){
    Optional<Attachment> find = attachmentRepository.findById(id);
    if(!find.isPresent()){
      return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    return fileUtil.fileDownload(find.get());
  }

  @DeleteMapping(value = "/{id}")
  @ResponseBody
  public ResponseEntity<?> deleteFile(@PathVariable("id") Long id) {
    Optional<Attachment> app = attachmentRepository.findById(id);
    if (!app.isPresent()){ 
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    return fileUtil.deleteFile(app.get());
  }
}