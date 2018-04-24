package com.app.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.app.model.system.SysUser;
import com.base.annotation.HandleMethodLog;
import com.base.basecontroller.BaseController;
import com.base.util.Cipher;
import com.base.util.CommonAjax;
import com.base.util.CommonConstant;
import com.base.util.CommonUtil;
import com.base.util.JackSonUtil;
/**
 * 类说明：系统管理
 * @author CHENWEI
 * 2016年8月25日
 */
@Controller
@RequestMapping(value="/system")
public class SystemController extends BaseController{
	
	/**
	 * 方法说明：登录页面
	 * @author CHENWEI
	 * @return
	 * 2016年8月28日
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public ModelAndView login(){
		return new ModelAndView("login");
	}
	
	/**
	 * 方法说明：登录验证
	 * @author CHENWEI
	 * @return
	 * 2016年8月28日
	 * @throws Exception 
	 */
	@HandleMethodLog("用户登录")
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public String login(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		CommonAjax<SysUser> ajax = new CommonAjax<SysUser>();
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(request.getParameter("loginName"), Cipher.encryption(request.getParameter("password")));
		try {
			subject.login(token);//会跳到我们自定义的realm中
			ajax.setState(CommonUtil.SUCCESS);
			ajax.setContent("登录成功");
		} catch (UnknownAccountException e) {
			ajax.setState(CommonUtil.NOTPASSERROR);
			ajax.setContent("用户名密码错误");
		} catch (IncorrectCredentialsException e) {  
			ajax.setState(CommonUtil.NOTPASSERROR);
			ajax.setContent("用户名密码错误");
        }catch (LockedAccountException e) {  
			ajax.setState(CommonUtil.NOTPASSERROR);
			ajax.setContent("登录次数超限");
        }catch (AuthenticationException e){
        	ajax.setState(CommonUtil.NOTPASSERROR);
			ajax.setContent("其他错误："+e.getMessage());
        }
		
		
		
		/*SysUser user = sysUserService.selectSysUser(new RequestToMap().getRequestToMap(request));
		if(user!=null){
			if(user.getNum() >= 3){
				ajax.setState(CommonUtil.NOTPASSERROR);
				ajax.setContent("登录次数超限");
			}else{
				request.getSession().setAttribute(CommonConstant.SESSION_USER, user);
				user.setLastLoginTime(CommonUtil.getNowDate());
				sysUserService.updateUserByEntity(user);
				ajax.setState(CommonUtil.SUCCESS);
				ajax.setObject(user);
				ajax.setContent("登录成功");
			}
		}else{
			user = sysUserService.selectUserByLoginName(request.getParameter("loginName"));
			if(user!=null){
				user.setNum(user.getNum()+1);
				sysUserService.updateUserByEntity(user);
				if(user.getNum() >= 3){
					ajax.setState(CommonUtil.NOTPASSERROR);
					ajax.setContent("登录次数超限");
				}else{
					ajax.setState(CommonUtil.NOTPASSERROR);
					ajax.setContent("登录失败");
				}
			}else{
				ajax.setState(CommonUtil.NOTPASSERROR);
				ajax.setContent("登录失败");
			}
		}*/
		return JackSonUtil.ObjectToJson(ajax);
	}
	
	/**
	 * 方法说明：进入主页
	 * @author CHENWEI
	 * @return
	 * 2016年8月28日
	 */
	@RequestMapping(value="/main",method=RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("main");
	}
	
	/**
	 * 方法说明：没有权限
	 * @author CHENWEI
	 * @return
	 * 2016年8月28日
	 */
	@RequestMapping(value="/noAuthority",method=RequestMethod.GET)
	public ModelAndView noAuthority(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("error/noAuthority");
	}
	
	/**
	 * 方法说明：退出登录
	 * @author CHENWEI
	 * @return
	 * 2016年9月04日
	 */
	@HandleMethodLog("退出登录")
	@RequestMapping(value="/userLoginOut",method=RequestMethod.GET)
	public ModelAndView userLoginOut(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView("login");
		request.getSession().removeAttribute(CommonConstant.SESSION_USER);
		if(request.getSession().getAttribute(CommonConstant.SESSION_USER)==null){
			return mv;
		}else{
			return null;
		}
	}
}
