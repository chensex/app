package com.app.service.system.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.app.mapper.system.SysMenuMapper;
import com.app.model.system.SysMenu;
import com.app.service.system.SysMenuService;
import com.base.model.ZtreeVO;
import com.base.service.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;

/**
 * 类说明：菜单
 * @author CHENWEI
 * 2016年8月28日
 */
@Service
public class SysMenuServiceImpl extends BaseServiceImpl implements SysMenuService{

	@Autowired
	private SysMenuMapper sysMenuMapper;
	
	public List<SysMenu> selectMenuList(Map<String, Object> map) {
		return sysMenuMapper.queryMenuList(map);
	}

	//使用配置时间存储
	@Cacheable("selectOptionMenuList")
	public List<SysMenu> selectOptionMenuList() {
		return sysMenuMapper.queryOptionMenuList();
	}

	public List<SysMenu> selectSysMenuList(Map<String, Object> map, int page, int pageSize) {
		PageHelper.startPage(page, pageSize);
		return sysMenuMapper.querySysMenuList(map);
	}

	public Integer selectMaxSort() {
		return sysMenuMapper.queryMaxSort();
	}

	public SysMenu selectMenuByMenuId(Long menuId) {
		return sysMenuMapper.queryMenuByMenuId(menuId);
	}

	public Map<String, Object> saveAndEditMenu(SysMenu sysMenu) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(sysMenu.getMenuUrl() != null && sysMenu.getMenuUrl() !=""){
			
			SysMenu menu = new SysMenu();
			menu.setMenuUrl(sysMenu.getMenuUrl());
			menu = sysMenuMapper.selectOne(menu);
			
			if(sysMenu.getMenuId()==null){
				if(menu == null){
					sysMenu.setCreateTime(new Date());
					sysMenu.setTarget("_self");
					sysMenuMapper.insertSelective(sysMenu);
					map.put("state", "1");
				}else{
					map.put("state", "0");
				}
			}else{
				SysMenu editMenu = sysMenuMapper.selectByPrimaryKey(sysMenu.getMenuId());
				if(editMenu.getMenuUrl().equals(sysMenu.getMenuUrl())){
					sysMenuMapper.updateByPrimaryKeySelective(sysMenu);
					map.put("state", "1");
				}else{
					if(menu == null){
						sysMenuMapper.updateByPrimaryKeySelective(sysMenu);
						map.put("state", "1");
					}else{
						map.put("state", "0");
					}
				}
				
			}
		}else{
			if(sysMenu.getMenuId()==null){
				sysMenu.setCreateTime(new Date());
				sysMenu.setTarget("_self");
				sysMenuMapper.insertSelective(sysMenu);
				map.put("state", "1");
			}else{
				sysMenuMapper.updateByPrimaryKeySelective(sysMenu);
				map.put("state", "1");
			}
		}
		return map;
	}

	public List<String> selectMenuNameList(Map<String, Object> map) {
		return sysMenuMapper.queryMenuNameList(map);
	}

	@SuppressWarnings("rawtypes")
	public List<ZtreeVO> queryAllMenuList(Map<String, Object> map) {
		return sysMenuMapper.queryAllMenuList(map);
	}
}
