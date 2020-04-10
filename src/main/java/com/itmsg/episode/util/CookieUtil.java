package com.itmsg.episode.util;

// import lombok.experimental.UtilityClass;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

// @UtilityClass
public class CookieUtil {

	public static Cookie createCookie(String name, String value){
		return createCookie(name, value, null,null, null, null,null);
	}

	public static Cookie createCookie(String name, String value,String domain){
		return createCookie(name, value, domain,null, null, null, null);
	}

	public static Cookie createCookie(String name, String value,String domain,String path){
		return createCookie(name, value, domain,path, null, null, null);
	}

	public static Cookie createCookie(String name, String value,String domain,String path,Integer maxAge){
		return createCookie(name, value, domain,path, maxAge, null, null);
	}

	public static Cookie createCookie(String name, String value,String domain,String path,Integer maxAge,Boolean httpOnly) {
		return createCookie(name, value, domain,path, maxAge, httpOnly, null);
	}

	public static Cookie createCookie(String name, String value, String domain, String path, Integer maxAge,Boolean httpOnly,Boolean secure){
		Cookie cookie = new Cookie(name, value);
		if(domain!=null){
			cookie.setDomain(domain);
		}
		if(path!=null){
			cookie.setPath(path);
		}
		if(maxAge!=null){
			cookie.setMaxAge(maxAge);
		}
		if(httpOnly!=null){
			cookie.setHttpOnly(httpOnly);
		}	
		if(secure!=null){
			cookie.setSecure(secure);
		}	
		
		return cookie;
	}

	public static Cookie getCookie(HttpServletRequest request, String name) {

		Map<String, Cookie> cookieMap = getCookieMap(request);
		return cookieMap.get(name);
	}

	public static String getValue(HttpServletRequest request, String name) throws IOException {

		Map<String, Cookie> cookieMap = getCookieMap(request);
		Cookie cookie = cookieMap.get(name);
		if (cookie == null) return null;
		return URLDecoder.decode(cookie.getValue(), "UTF8");
	}

	public static boolean exists(HttpServletRequest request, String name) {

		Map<String, Cookie> cookieMap = getCookieMap(request);
		return cookieMap.get(name) != null;
	}

	private static Map<String, Cookie> getCookieMap(HttpServletRequest request) {

		Map<String, Cookie> cookieMap = new HashMap<>();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}
}