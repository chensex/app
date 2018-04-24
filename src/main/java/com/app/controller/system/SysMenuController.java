package com.app.controller.system;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.app.model.system.SysUser;
import com.app.service.system.SysMenuService;
import com.base.basecontroller.BaseController;
import com.base.model.ZtreeVO;
import com.base.util.BuildTree;
import com.base.util.CommonAjax;
import com.base.util.CommonConstant;
import com.base.util.CommonUtil;
import com.base.util.JackSonUtil;
import com.github.pagehelper.PageInfo;

/**
 * 类说明：菜单类
 * @author CHENWEI
 * 2016年9月1日
 */
@Controller
@RequestMapping(value="/system")
public class SysMenuController extends BaseController{
	@Autowired
	private SysMenuService sysMenuService;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@RequestMapping(value="/sysMenuInit",method=RequestMethod.GET)
	public ModelAndView sysMenuInit(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("system/sysMenuInit");
	}
	
	@RequestMapping(value="/sysMenuInit",method=RequestMethod.POST)
	@ResponseBody
	public Object querySysMenuInit(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String,Object>();
		List<ZtreeVO> list = sysMenuService.queryAllMenuList(map);
		ZtreeVO vo = new ZtreeVO();
		vo.setId(0);
		vo.setName("菜单管理");
		vo.setOpen(true);
		list.add(vo);
		return list;
	}
	
	@RequestMapping(value="/addMenu",method=RequestMethod.GET)
	public ModelAndView addMenu(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView("system/addMenu");
		Long menuId = Long.valueOf(request.getParameter("menuId"));
		SysMenu sysMenu = null;
		if(menuId==0){
			sysMenu = new SysMenu();
			sysMenu.setMenuName("菜单管理");
			sysMenu.setIsOpen("true");
			sysMenu.setState(1);
			sysMenu.setLevel(0);
			sysMenu.setSort(0);
			sysMenu.setParentId(0l);
			sysMenu.setMenuId(0l);
		}else{
			sysMenu = sysMenuService.selectMenuByMenuId(menuId);
			if(sysMenu.getLevel()==1){
				sysMenu.setParentMenuName("菜单管理");
			}
		}
		mv.addObject("sysMenu", sysMenu);
		return mv;
	}
	
	@RequestMapping(value="/sysMenuList",method=RequestMethod.GET)
	public ModelAndView sysMenuList(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView("system/sysMenuList");
		mv.addObject("menuList", sysMenuService.selectOptionMenuList());
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
		
		return pageToJson(new PageInfo<SysMenu>(sysMenuService.selectSysMenuList(
			getRequestParameterAsMap(req), page, pageSize)));
	}
	
	@RequestMapping(value="/saveMenu",method = RequestMethod.POST)
	@ResponseBody
	public String saveMenu(SysMenu sysMenu){
		CommonAjax<SysMenu> ajax = new CommonAjax<SysMenu>();
		Map<String, Object> map = sysMenuService.saveAndEditMenu(sysMenu);
		if(map.get("state").equals("1")){
			ajax.setState(CommonUtil.SUCCESS);
			ajax.setContent("保存成功");
		}else {
			ajax.setState(CommonUtil.NOTPASSERROR);
			ajax.setContent("菜单重复");
		}
		return JackSonUtil.ObjectToJson(ajax);
	}
	
	@RequestMapping(value = "/getSysMenuById")
	@ResponseBody
	public SysMenu getSysMenuById(Long menuId){
		return sysMenuService.selectMenuByMenuId(menuId);
	}
	
	/**
	 * 方法说明：获取菜单列表
	 * @author CHENWEI
	 * @return
	 * 2016年8月28日
	 */
	@RequestMapping(value="/getMenuList",method=RequestMethod.POST)
	@ResponseBody
	public Object getMenuList(HttpServletRequest request,HttpServletResponse response){
		SysUser user = (SysUser) request.getSession().getAttribute(CommonConstant.SESSION_USER);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", user.getUserId());
		List<SysMenu> menList = sysMenuService.selectMenuList(map);
		CommonAjax<Object> ajax = new CommonAjax<Object>();
		List<ZtreeVO<SysMenu>> trees = new ArrayList<ZtreeVO<SysMenu>>();
		for (SysMenu sysMenu : menList) {
			ZtreeVO<SysMenu> tree = new ZtreeVO<SysMenu>();
			tree.setId(Integer.parseInt(String.valueOf(sysMenu.getMenuId())));
			tree.setpId(Integer.parseInt(String.valueOf(sysMenu.getParentId())));
			tree.setName(sysMenu.getMenuName());
			tree.setOpenUrl(sysMenu.getMenuUrl());
			trees.add(tree);
		}
		List<ZtreeVO<SysMenu>> children = BuildTree.build(trees);
		ajax.setState(CommonUtil.SUCCESS);
		ajax.setObject(children);
		return ajax;
	}
	/**
	 * 
	 * 方法说明：根据主键查询菜单
	 * @author CHENWEI
	 * @return
	 * 2018年4月21日
	 */
	@RequestMapping(value="/querySysMenuById",method=RequestMethod.POST)
	@ResponseBody
	public SysMenu querySysMenuById(Long menuId){
		return sysMenuService.selectMenuByMenuId(menuId);
	}
}
