package com.siwan.studyApp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
public class BaseController {

	protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	protected Map getParams(HttpServletRequest request) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		String jsonStr = (String) request.getParameter("jsonStr");
		ObjectMapper mapper = new ObjectMapper(); // create once, reuse

		if(jsonStr != null) {
			try {
				paramMap = mapper.readValue(jsonStr, new TypeReference<Map<String, Object>>(){});

		        for( String key : paramMap.keySet() ){
		        	Object obj = paramMap.get(key);
		        	if(obj != null) {
			        	String clsName = paramMap.get(key).getClass().getName();
			        	if(clsName.contains("List")) {
			        		List<Map> cnvtList = mapper.readValue(paramMap.get(key).toString(), new TypeReference<List<Map>>(){});
			        		paramMap.put(key,cnvtList);
			        	}
	//		            System.out.println( String.format("Å° : %s, °ª : %s", key, paramMap.get(key)) );
		        	}
		        }

			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Map sessionMap = (Map) request.getSession().getAttribute("USER");
		if(sessionMap != null) paramMap.putAll(sessionMap);

		return paramMap;
	}

}
