package com.app.mapper.system;


import java.util.List;
import java.util.Map;

import com.app.model.system.SysMenu;
import com.base.util.ZtreeVO;

import tk.mybatis.mapper.common.Mapper;
/**
 * 类说明：菜单
 * @author CHENWEI
 * 2016年9月5日
 */
public interface SysMenuMapper extends Mapper<SysMenu> {
	public List<ZtreeVO> queryMenuListByMap(Map<String, Object> map);
	public List<ZtreeVO> queryAllMenuList();
	public List<SysMenu> queryOptionMenuList();
	public List<SysMenu> querySysMenuList(Map<String, Object> map);
	/**
	 * 获取菜单最大排序号
	 * @return
	 */
	public Integer queryMaxSort();
	
	/**
	 * 根据菜单ID查询菜单
	 * @param menuId
	 * @return
	 */
	public SysMenu queryMenuByMenuId(Long menuId);
	
	/**
	 * 根据Map用户角色授权过的菜单
	 * @param map
	 * @return
	 */
	public List<SysMenu> queryGrantMenuList(Map<String, Object> map);
	

}