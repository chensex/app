package com.app.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.app.model.system.SysUser;
import com.app.service.system.SysMenuService;
import com.app.service.system.SysUserService;
import com.base.annotation.HandleMethodLog;
import com.base.basecontroller.BaseController;
import com.base.util.CommonAjax;
import com.base.util.CommonConstant;
import com.base.util.CommonUtil;
import com.base.util.JackSonSerializeUtil;
import com.base.util.RequestToMap;
import com.base.util.ZtreeVO;
/**
 * 类说明：系统管理
 * @author CHENWEI
 * 2016年8月25日
 */
@Controller
@RequestMapping(value="/app/system")
public class SystemController extends BaseController{
	
	
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysMenuService sysMenuService;
	
	
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
		SysUser user = sysUserService.selectSysUser(new RequestToMap().getRequestToMap(request));
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
		}
		return JackSonSerializeUtil.ObjectToJson(ajax);
	}
	
	/**
	 * 方法说明：进入主页
	 * @author CHENWEI
	 * @return
	 * 2016年8月28日
	 */
	@RequestMapping(value="/main",method=RequestMethod.POST)
	public ModelAndView main(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView("main");
		return mv;
	}
	
	/**
	 * 方法说明：获取菜单列表
	 * @author CHENWEI
	 * @return
	 * 2016年8月28日
	 */
	@RequestMapping(value="/getMenuList",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getMenuList(HttpServletRequest request,HttpServletResponse response){
		SysUser user = (SysUser) request.getSession().getAttribute(CommonConstant.SESSION_USER);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", user.getUserId());
		List<ZtreeVO> menList = sysMenuService.selectMenuListByMap(map);
		return JackSonSerializeUtil.ObjectToJson(menList);
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
