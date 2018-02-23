package com.app.intercepters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class LoginIntercepter implements HandlerInterceptor{
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(LoginIntercepter.class);

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean result = true;
		/*SysUser user = (SysUser) request.getAttribute("session_user");
		if(user==null){
			request.getRequestDispatcher("/view/login.jsp").forward(request, response);
			result = false;
		}else{
			result = true;
		}*/
		return result;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
