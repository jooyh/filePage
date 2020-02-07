package com.siwan.studyApp.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class IntercepterUtil extends HandlerInterceptorAdapter{
	protected static final Logger logger = LoggerFactory.getLogger(IntercepterUtil.class);


	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
//        if (logger.isDebugEnabled()) {
            logger.info("===================       START       ===================");
            logger.info(" Request URI \t:  " + request.getRequestURI());
//        }
        HttpSession httpSession = request.getSession(true);
        Map session = (Map) httpSession.getAttribute("USER");

        if("/".equals(request.getRequestURI())) {
        	httpSession.removeAttribute("USER");
        }else if("/login.do".equals(request.getRequestURI())){
        	request.setAttribute("session", session);
        }else {
        	if(!request.getRequestURI().contains("login")
        			&& !request.getRequestURI().contains("join")
        			&& !request.getRequestURI().contains("resources")
        			&& session == null) {
        		response.sendRedirect("/");
        	}
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
//        if (logger.isDebugEnabled()) {
            logger.info("===================        END        ===================\n");
//        }
    }

}
