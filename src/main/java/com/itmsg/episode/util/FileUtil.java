package com.itmsg.episode.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.itmsg.episode.common.files.Attachment;
import com.itmsg.episode.common.files.AttachmentMapper;
import com.itmsg.episode.common.files.AttachmentRepository;
import com.itmsg.episode.system.ESConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Component
public class FileUtil {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${system.attachment.root}")
	String webRoot;

	@Value("${system.attachment.allow-extension}")
	String allowExtentions;

	@Resource
	AttachmentMapper attachmentMapper;

	@Resource
	AttachmentRepository attachmentRepository;


    public ResponseEntity<?> upload(MultipartHttpServletRequest multipartHttpServletRequest) {
		UserDetails detail = (UserDetails)multipartHttpServletRequest.getAttribute(ESConstants.USER_DETAILS_KEY);
		String description = multipartHttpServletRequest.getParameter("description");
		String ownerTable = multipartHttpServletRequest.getParameter("ownerTable");
		String ownerId = multipartHttpServletRequest.getParameter("ownerId");
		String path = webRoot;
		List<Attachment> result = new ArrayList<Attachment>();
		List<MultipartFile> files =  multipartHttpServletRequest.getFiles("files");
		for(MultipartFile file: files){
			String fileName = file.getOriginalFilename();
			int index = fileName.lastIndexOf(".");
			String extention = fileName.substring(index + 1).toLowerCase();
			if(!allowExtentions.contains(extention)){
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		}
		
		for(MultipartFile file: files){
			String fileName = file.getOriginalFilename();
			logger.debug("!!!!! original fileName : {}",fileName);
			if(fileName.contains("\\")){
				fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
			}
			logger.debug("!!!!! convert fileName : {}",fileName);
			int index = fileName.lastIndexOf(".");
			String extention = fileName.substring(index + 1);
			
			String storedName = fileName.replaceAll("."+extention,"")+ Long.toString(System.nanoTime()) + "." + extention;
			logger.debug("!!!!! store fileName : {}",storedName);
			String attachmentId = attachmentMapper.getAutonum();
			Attachment attachment = new Attachment();
			attachment.setFilename(fileName);
			attachment.setAttachmentId(attachmentId);
			attachment.setStoredname(storedName);
			attachment.setUrlname(path);
			attachment.setDescription(description);
			attachment.setOwnertable(ownerTable);
			attachment.setOwnerid(Long.parseLong(ownerId));
			attachment.setCreateId(detail.getUsername());
			try{
				file.transferTo(new File(path + storedName));
			}catch(Exception e){
				e.printStackTrace();
			}
			
			result.add(attachmentRepository.save(attachment));
		}
		
		return ResponseEntity.ok().body(result);
	}

	public ResponseEntity<?> fileDownload(Attachment attachment){
		File file = new File(attachment.getUrlname(),attachment.getStoredname());
		if(!file.exists()){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		InputStreamResource resource = null;
		String fileName = null;
		try {
			fileName = URLEncoder.encode(attachment.getFilename(), "UTF-8");
			resource = new InputStreamResource(new FileInputStream(file));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=" + fileName);
		headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
		headers.add(HttpHeaders.PRAGMA, "no-cache");
		headers.add(HttpHeaders.EXPIRES, "0");
		headers.add("filename",  fileName);
		return ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}

	 public ResponseEntity<?> deleteFile(Attachment attachment){
		attachmentRepository.delete(attachment);
		File file = new File(attachment.getUrlname(), attachment.getStoredname());
		if(file.exists()){
			file.delete();
		}
		return ResponseEntity.ok().build();
	 }

}