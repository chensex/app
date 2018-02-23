package com.app.intercepters;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.app.model.system.SysLog;
import com.app.model.system.SysUser;
import com.app.service.system.SysLogService;
import com.app.service.system.SysUserService;
import com.base.annotation.HandleMethodLog;
import com.base.util.CommonConstant;
import com.base.util.CommonUtil;
@Component
@Aspect
public class LogRecord {
private static final Logger logger = Logger.getLogger(LogRecord.class);
	
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private SysUserService sysUserService;
	
	//controller层切点
	@Pointcut("@annotation(com.base.annotation.HandleMethodLog)")
	public void controllerAspect(){
	}
	
	// 前置通知 用于拦截Controller层记录用户的操作
	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session=request.getSession();
		String userName = "";
		SysUser user = null;
		if("login".equals(joinPoint.getSignature().getName())){
			userName =  request.getParameter("loginName");
			SysUser sysUser = new SysUser();
			sysUser.setLoginName(userName);
			user = sysUserService.getSysUserByEntity(sysUser);
		}else{
			user = (SysUser) session.getAttribute(CommonConstant.SESSION_USER);
		}
		SysLog log = new SysLog();
		log.setUserId(user.getUserId());
		log.setCreateTime(CommonUtil.getNowDate());
		log.setLogType(1);
		log.setUserIp(request.getRemoteAddr());
		log.setEventName(getHandleName(joinPoint));
		sysLogService.saveSysLog(log);
	}

	
	// 前置通知 用于拦截Controller层记录用户的操作
	@AfterThrowing(value="controllerAspect()",throwing="e")
	public void doThrowing(JoinPoint joinPoint, Throwable e){
		logger.info("异常开始："+joinPoint.getSignature().getName());
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session=request.getSession();
		String userName = "";
		SysUser user = null;
		if("login".equals(joinPoint.getSignature().getName())){
			userName =  request.getParameter("loginName");
			SysUser sysUser = new SysUser();
			sysUser.setLoginName(userName);
			user = sysUserService.getSysUserByEntity(sysUser);
		}else{
			user = (SysUser) session.getAttribute(CommonConstant.SESSION_USER);
		}
		SysLog log = new SysLog();
		log.setUserId(user.getUserId());
		log.setCreateTime(CommonUtil.getNowDate());
		log.setLogType(0);
		log.setUserIp(request.getRemoteAddr());
		log.setEventName(getHandleName(joinPoint));
		log.setDescription(e.getMessage().substring(0, 2000));
		sysLogService.saveSysLog(log);
		logger.info("保存异常日志结束");
	}
	
	//获取操作名称
	@SuppressWarnings("rawtypes")
	public static String getHandleName(JoinPoint joinPoint){
		String targetName = joinPoint.getTarget().getClass().getName();//请求类名
		String methodName = joinPoint.getSignature().getName();//请求方法名
		String value = "";
		try {
			Class clazz = Class.forName(targetName);
			Method[] method = clazz.getDeclaredMethods();
			for (Method m : method) {
				if(methodName.equals(m.getName())){
					value = m.getAnnotation(HandleMethodLog. class).value();
					break;
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return value;
	}
}
