package com.app.controller.system;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.app.model.system.SysMenu;
import com.app.service.system.SysMenuService;
import com.base.basecontroller.BaseController;
import com.base.util.CommonAjax;
import com.base.util.CommonUtil;
import com.base.util.JackSonSerializeUtil;
import com.github.pagehelper.PageInfo;

/**
 * 类说明：菜单类
 * @author CHENWEI
 * 2016年9月1日
 */
@Controller
@RequestMapping(value="/app/system")
public class SysMenuController extends BaseController{
	@Autowired
	private SysMenuService menuService;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@RequestMapping(value="/sysMenuList",method=RequestMethod.GET)
	public ModelAndView sysMenuList(HttpServletRequest request,HttpServletResponse response){
		
		ModelAndView mv = new ModelAndView("system/sysMenuList");
		mv.addObject("menuList", menuService.selectOptionMenuList());
		return mv;
	}
	
	@RequestMapping(value="/sysMenuList",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getSysMenuList(HttpServletRequest req){
		int page = 1;
		int pageSize = 10;
		if(req.getParameter("page") != null){
			page = Integer.valueOf(req.getParameter("page"));
		}
		if(req.getParameter("rows") != null){
			pageSize = Integer.valueOf(req.getParameter("rows"));
		}
		debug("Controller分页日志:page=" + page + "pageSize=" + pageSize);
		
		//自定义时间存储
		if(redisTemplate.opsForValue().get("test") == null) {
			//从数据库读取 并放入redis
			redisTemplate.opsForValue().set("test", "test",1,TimeUnit.HOURS);//存储一个小时
		}
		
		return pageToJson(new PageInfo<SysMenu>(menuService.selectSysMenuList(
			getRequestParameterAsMap(req), page, pageSize)));
	}
	
	@RequestMapping(value="/saveMenu",method = RequestMethod.POST)
	@ResponseBody
	public String saveMenu(SysMenu sysMenu){
		CommonAjax<SysMenu> ajax = new CommonAjax<SysMenu>();
		sysMenu.setCreateTime(new Date());
		sysMenu.setTarget("_self");
		sysMenu.setSort(menuService.selectMaxSort()+1);
		if(sysMenu.getParentId()!=0){
			SysMenu menu = menuService.selectMenuByMenuId(sysMenu.getParentId());
			sysMenu.setLevel(menu.getLevel()+1);
		}else{
			sysMenu.setLevel(1);
		}
		menuService.saveAndEditMenu(sysMenu);
		ajax.setState(CommonUtil.SUCCESS);
		ajax.setContent("保存成功");
		return JackSonSerializeUtil.ObjectToJson(ajax);
	}
	
	@RequestMapping(value="/updateMenu",method = RequestMethod.POST)
	@ResponseBody
	public String updateMenu(SysMenu sysMenu){
		CommonAjax<SysMenu> ajax = new CommonAjax<SysMenu>();
		if(sysMenu.getParentId()!=0){
			SysMenu paMenu = menuService.selectMenuByMenuId(sysMenu.getParentId());
			sysMenu.setLevel(paMenu.getLevel()+1);
		}else{
			sysMenu.setLevel(1);
		}
		menuService.saveAndEditMenu(sysMenu);
		ajax.setState(CommonUtil.SUCCESS);
		ajax.setContent("保存成功");
		return JackSonSerializeUtil.ObjectToJson(ajax);
	}
	
	@RequestMapping(value = "/getSysMenuById")
	@ResponseBody
	public SysMenu getSysMenuById(Long menuId){
		return menuService.selectMenuByMenuId(menuId);
	}
	
}
