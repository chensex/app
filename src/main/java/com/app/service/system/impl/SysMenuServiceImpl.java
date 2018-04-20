package com.app.service.system.impl;

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

	public void saveAndEditMenu(SysMenu sysMenu) {
		if(sysMenu.getMenuId()==null){
			sysMenuMapper.insertSelective(sysMenu);
		}else{
			sysMenuMapper.updateByPrimaryKeySelective(sysMenu);
		}
	}

	@Override
	public List<String> selectMenuNameList(Map<String, Object> map) {
		return sysMenuMapper.queryMenuNameList(map);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ZtreeVO> queryAllMenuList(Map<String, Object> map) {
		return sysMenuMapper.queryAllMenuList(map);
	}
}
