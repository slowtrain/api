package com.itmsg.episode.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.Resource;
import javax.xml.ws.BindingProvider;

import com.itmsg.episode.common.files.Attachment;
import com.itmsg.episode.common.files.AttachmentMapper;
import com.itmsg.episode.common.files.AttachmentRepository;
import com.itmsg.episode.system.ESConstants;
import com.koreaaero.st.DTST00150SEND;
import com.koreaaero.st.DTST00150SENDResponse;
import com.koreaaero.st.ObjectFactory;
import com.koreaaero.st.ST00150SO;
import com.koreaaero.st.ST00150SOService;
import com.koreaaero.st.DTST00150SEND.IFAMLHS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class Notice {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String SKYPE_SEND_TYPE_TXT = "txt";
	public static final String SKYPE_SEND_TYPE_HTML = "html";

	@Value("${sap.integration.username}")
	String sapUsername;

	@Value("${sap.integration.password}")
	String sapPassword;

	@Value("${skype.integration.url}")
	String skypeUrl;

	@Value("${system.domain}")
	String systemDomain;

	public void notifyByMail(String mailFrom, List<String> mailTos, String title, String content) {
		ST00150SOService sT00150SOService = new ST00150SOService();
		ST00150SO mailService = sT00150SOService.getHTTPPort();

		Map<String, Object> requestContext = ((BindingProvider) mailService).getRequestContext();
		requestContext.put(BindingProvider.USERNAME_PROPERTY, sapUsername); // "ICAMIFUSER"
		requestContext.put(BindingProvider.PASSWORD_PROPERTY, sapPassword); // "Pidev!17"

		ObjectFactory factory = new ObjectFactory();

		IFAMLHS ifamlhs = null;
		DTST00150SEND dTST00150SEND = null;
		DTST00150SENDResponse res = null;
		DTST00150SENDResponse.ITRETURN itReturn = null;
		for (String mailTo : mailTos) {
			ifamlhs = factory.createDTST00150SENDIFAMLHS();
			ifamlhs.setMAILFROMADDR(mailFrom);
			ifamlhs.setMAILTOADDR(mailTo);
			ifamlhs.setMAILTIT(title);
			ifamlhs.setMAILCONT(content);
			ifamlhs.setMAILTYPE("A");

			dTST00150SEND = factory.createDTST00150SEND();
			dTST00150SEND.setIFAMLHS(ifamlhs);
			res = mailService.st00150SO(dTST00150SEND);
			itReturn = res.getITRETURN();
			logger.debug("notifyByMail from {} to {} : {} {} {} {}",mailFrom, mailTo, itReturn.getZIFSTT(),itReturn.getZIFDT(),itReturn.getZIFTM(),itReturn.getZIFRSTMSG());
		}
	}

	public void notifyBySkype(String sendor, List<String> receivers, String title, String content, String contentType) {		
		
		HttpURLConnection con = null;
		try{
			URL url = new URL(skypeUrl);
			
			StringBuilder build = new StringBuilder();
			for (String receiver:receivers){
				build.append(receiver).append(";U;");
			}
			StringBuilder data = new StringBuilder();
			data.append("Sender=" + URLEncoder.encode(sendor, "UTF-8"));
			data.append("&ReceiverType=" + URLEncoder.encode(";U;", "UTF-8"));
			data.append("&Receiver=" + URLEncoder.encode(build.toString(),"UTF-8"));
			data.append("&Title=" + URLEncoder.encode(title, "UTF-8"));
			data.append("&Link=" + URLEncoder.encode("http://"+systemDomain, "UTF-8"));
			data.append("&ContentType=" + URLEncoder.encode(contentType, "UTF-8"));
			data.append("&Content=" + URLEncoder.encode(content, "UTF-8"));

			con = (HttpURLConnection)url.openConnection();
			con.setRequestProperty(HttpHeaders.ACCEPT_CHARSET,"UTF-8" );
			con.setRequestProperty(HttpHeaders.CONTENT_TYPE,"application/x-www-form-urlencoded");
			con.setConnectTimeout(10000);
			con.setReadTimeout(10000);
			con.setRequestMethod("POST");
			con.setDoOutput(true);

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), "UTF-8"));
			writer.write(data.toString());
			writer.flush();
			writer.close();
			logger.debug("notifyBySkype receiver {} response code {} {}", build.toString(), con.getResponseCode(), con.getResponseMessage());
			
        } catch (Exception e) {
            e.printStackTrace();
		}finally{
			if(con!=null){
				con.disconnect();
			}
		}
	}

}