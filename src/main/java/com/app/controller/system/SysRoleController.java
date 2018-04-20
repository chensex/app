package com.app.controller.system;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.app.model.system.SysRole;
import com.app.service.system.SysRoleService;
import com.base.basecontroller.BaseController;
import com.base.model.ZtreeVO;
import com.base.util.CommonAjax;
import com.base.util.CommonUtil;
import com.base.util.JackSonUtil;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value="/system")
public class SysRoleController extends BaseController{
	
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(SysRoleController.class);
	
	@Autowired
	private SysRoleService roleService;
	
	@RequestMapping(value="/sysRoleList",method=RequestMethod.GET)
	public ModelAndView sysRoleList(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView("system/sysRoleList");
		return mv;
	}
	
	@RequestMapping(value="/sysRoleList",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getSysRoleList(HttpServletRequest req){
		int page = 1;
		int pageSize = 10;
		if(req.getParameter("page") != null){
			page = Integer.valueOf(req.getParameter("page"));
		}
		if(req.getParameter("rows") != null){
			pageSize = Integer.valueOf(req.getParameter("rows"));
		}
		debug("Controller分页日志:page=" + page + "pageSize=" + pageSize);
		return pageToJson(new PageInfo<SysRole>(roleService.selectSysRoleList(
			getRequestParameterAsMap(req), page, pageSize)));
	}
	
	@RequestMapping(value="/saveRole",method = RequestMethod.POST)
	@ResponseBody
	public String saveRole(SysRole sysRole){
		CommonAjax<SysRole> ajax = new CommonAjax<SysRole>();
		sysRole.setCreateTime(new Date());
		roleService.saveAndEditRole(sysRole);
		ajax.setState(CommonUtil.SUCCESS);
		ajax.setContent("保存成功");
		return JackSonUtil.ObjectToJson(ajax);
	}
	
	@RequestMapping(value="/updateRole",method = RequestMethod.POST)
	@ResponseBody
	public String updateRole(SysRole sysRole){
		CommonAjax<SysRole> ajax = new CommonAjax<SysRole>();
		roleService.saveAndEditRole(sysRole);
		ajax.setState(CommonUtil.SUCCESS);
		ajax.setContent("修改成功");
		return JackSonUtil.ObjectToJson(ajax);
	}
	
	@RequestMapping(value="/grantRole",method = RequestMethod.POST)
	@ResponseBody
	public String grantRole(HttpServletRequest request){
		CommonAjax<SysRole> ajax = new CommonAjax<SysRole>();
		roleService.grantRole(Long.valueOf(request.getParameter("roleId")), request.getParameter("menuIds"));
		ajax.setState(CommonUtil.SUCCESS);
		ajax.setContent("授权成功");
		return JackSonUtil.ObjectToJson(ajax);
	}
	
	@RequestMapping(value = "/getSysRoleById")
	@ResponseBody
	public SysRole getSysRoleById(Long roleId){
		return roleService.selectRoleByRoleId(roleId);
	}
	
	@RequestMapping(value="/grantMenuList",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String grantMenuList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleId", request.getParameter("roleId"));
		map.put("state", "1");
		List<ZtreeVO> menList = roleService.getGrantMenuList(map);
		return JackSonUtil.ObjectToJson(menList);
	}
}
