package com.app.intercepters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.app.model.system.SysUser;
import com.base.util.CommonConstant;

public class AppIntercepter implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean result = false;

		String url = request.getRequestURL().toString();
		SysUser user = (SysUser) request.getSession().getAttribute(CommonConstant.SESSION_USER);
		if(user==null){
			if(url.contains("/login.jsp") || url.contains("/login")){
				result = true;
			}else{
				request.getRequestDispatcher("/view/login.jsp").forward(request, response);
				result = false;
			}
		}else{
			result = true;
		}
		return result;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
