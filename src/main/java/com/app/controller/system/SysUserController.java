package com.app.controller.system;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.app.model.system.SysMenu;
import com.app.model.system.SysUser;
import com.app.service.system.SysRoleService;
import com.app.service.system.SysUserService;
import com.base.basecontroller.BaseController;
import com.base.util.Cipher;
import com.base.util.CommonAjax;
import com.base.util.CommonUtil;
import com.base.util.JackSonUtil;
import com.base.util.MQSendUtil;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value="/system")
public class SysUserController extends BaseController{
	
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(SysUserController.class);
	
	@Autowired
	private SysUserService userService;
	
	@Autowired
	private SysRoleService roleService;
	
	/*@Resource(name="testDestination")
	private Destination testDestination;*/
	
	//@Autowired
	//private JmsTemplate jmsTemplate;
	
	@RequestMapping(value="/sysUserList",method=RequestMethod.GET)
	public ModelAndView sysUserList(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView("system/sysUserList");
		return mv;
	}
	
	@RequestMapping(value="/sysUserList",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getSysUserList(HttpServletRequest req){
		int page = 1;
		int pageSize = 10;
		if(req.getParameter("page") != null){
			page = Integer.valueOf(req.getParameter("page"));
		}
		if(req.getParameter("rows") != null){
			pageSize = Integer.valueOf(req.getParameter("rows"));
		}
		//Map<String, Object> mqMap = new HashMap<String,Object>();
		//mqMap.put("test", "testDestination");
		//MQSendUtil.sendMessage(jmsTemplate, testDestination, mqMap);
		debug("Controller分页日志:page=" + page + "pageSize=" + pageSize);
		return pageToJson(new PageInfo<SysMenu>(userService.selectSysUserList(
			getRequestParameterAsMap(req), page, pageSize)));
	}
	
	@RequestMapping(value="/saveUser",method = RequestMethod.POST)
	@ResponseBody
	public String saveUser(SysUser sysUser,HttpServletRequest request){
		CommonAjax<SysUser> ajax = new CommonAjax<SysUser>();
		sysUser.setCreateTime(new Date());
		sysUser.setPassword(Cipher.encryption(sysUser.getPassword()));
		userService.saveAndEditUser(sysUser,request.getParameter("roleIds"));
		ajax.setState(CommonUtil.SUCCESS);
		ajax.setContent("保存成功");
		return JackSonUtil.ObjectToJson(ajax);
	}
	
	@RequestMapping(value="/updateUser",method = RequestMethod.POST)
	@ResponseBody
	public String updateUser(SysUser sysUser,HttpServletRequest request){
		CommonAjax<SysUser> ajax = new CommonAjax<SysUser>();
		sysUser.setPassword(Cipher.encryption(sysUser.getPassword()));
		userService.saveAndEditUser(sysUser,request.getParameter("roleIds"));
		ajax.setState(CommonUtil.SUCCESS);
		ajax.setContent("修改成功");
		return JackSonUtil.ObjectToJson(ajax);
	}
	
	@RequestMapping(value="/clearUser",method = RequestMethod.POST)
	@ResponseBody
	public String clearUser(HttpServletRequest request){
		CommonAjax<SysUser> ajax = new CommonAjax<SysUser>();
		SysUser sysUser = userService.selectUserByUserId(Long.valueOf(request.getParameter("userId")));
		sysUser.setNum(0);
		userService.saveAndEditUser(sysUser,null);
		ajax.setState(CommonUtil.SUCCESS);
		ajax.setContent("清除成功");
		return JackSonUtil.ObjectToJson(ajax);
	}
	
	@RequestMapping(value = "/getAddSysUserById")
	@ResponseBody
	public Object getAddSysUserById(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", 1); 
		return roleService.selectSysRoleList(map);
	}
	
	@RequestMapping(value = "/getSysUserById")
	@ResponseBody
	public SysUser getSysUserById(Long userId){
		SysUser sysUser =  userService.selectUserByUserId(userId);
		
		sysUser.setPassword(Cipher.decrypt(sysUser.getPassword()));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", 1);
		
		sysUser.setRoles(roleService.selectSysRoleList(map));
		sysUser.setUseRoles(roleService.selectRoleByUserId(userId));
		return sysUser;
	}
}
