package com.siwan.studyApp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.siwan.studyApp.service.UserService;

@Controller
public class LoginController extends BaseController{

	@Autowired
	private UserService userService;

	@Value("${path.baseDir}")
	private String baseDir;
	
	@RequestMapping("/")
	public String locateLoginPage(HttpServletRequest request) {
		logger.info(baseDir);
		return "login";
	}

	@RequestMapping("/login.do")
	@ResponseBody
	public Map login(HttpServletRequest request) {
		Map map = userService.selectUser(getParams(request));
		if(map != null) {
			HttpSession httpSession = request.getSession(true);
			httpSession.setAttribute("USER", map);
		}
		return map;
	}

	@RequestMapping("/duplCheck.do")
	@ResponseBody
	public Map duplCheck(HttpServletRequest request) {
		return userService.isDuplicate(getParams(request));
	}

	@RequestMapping("/join.do")
	@ResponseBody
	public Map join(HttpServletRequest request) {
		Map map = userService.insertUser(getParams(request));
		logger.info("TEST ::::::>" + map.toString());
		return map;
	}
}
