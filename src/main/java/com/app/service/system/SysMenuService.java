package com.app.service.system;

import java.util.List;
import java.util.Map;

import com.app.model.system.SysMenu;
import com.base.model.ZtreeVO;


/**
 * 类说明：菜单
 * @author CHENWEI
 * 2016年8月28日
 */
public interface SysMenuService {
	
	/**
	 * 获取用户角色菜单
	 * @param map
	 * @return
	 */
	public List<SysMenu> selectMenuList(Map<String, Object> map);
	
	/**
	 * 查询所有菜单 state = 1 查询有效的；state = 0 查询全部的
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<ZtreeVO> queryAllMenuList(Map<String, Object> map);
	
	public List<String> selectMenuNameList(Map<String, Object> map);
	
	/**
	 * 获取有效的菜单
	 * @param map
	 * @return
	 */
	public List<SysMenu> selectOptionMenuList();
	
	/**
	 * 菜单列表
	 * @param map
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<SysMenu> selectSysMenuList(Map<String, Object> map,int page,int pageSize);
	/**
	 * 获取菜单最大排序号
	 * @return
	 */
	public Integer selectMaxSort();
	
	/**
	 * 根据菜单ID查询菜单
	 * @param menuId
	 * @return
	 */
	public SysMenu selectMenuByMenuId(Long menuId);
	
	/**
	 * 新增和修改菜单
	 * @param sysMenu
	 */
	public void saveAndEditMenu(SysMenu sysMenu);
}
