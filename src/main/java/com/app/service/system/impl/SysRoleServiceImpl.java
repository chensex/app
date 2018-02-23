package com.app.service.system.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.mapper.system.SysMenuMapper;
import com.app.mapper.system.SysRoleMapper;
import com.app.mapper.system.SysRoleMenuMapper;
import com.app.model.system.SysMenu;
import com.app.model.system.SysRole;
import com.app.model.system.SysRoleMenu;
import com.app.service.system.SysRoleService;
import com.base.service.impl.BaseServiceImpl;
import com.base.util.ZtreeVO;
import com.github.pagehelper.PageHelper;

/**
 * 类说明：角色管理
 * @author CHENWEI
 * 2016年8月25日
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl implements SysRoleService{
	@Autowired
	private SysRoleMapper mapper;
	
	@Autowired
	private SysMenuMapper menuMapper;
	
	@Autowired
	private SysRoleMenuMapper roleMenuMapper;


	public List<SysRole> selectSysRoleList(Map<String, Object> map, int page, int pageSize) {
		PageHelper.startPage(page, pageSize);
		return mapper.querySysRoleList(map);
	}

	public void saveAndEditRole(SysRole sysRole) {
		if(sysRole.getRoleId()==null){
			mapper.insertSelective(sysRole);
		}else{
			mapper.updateByPrimaryKeySelective(sysRole);
		}
	}

	public SysRole selectRoleByRoleId(Long roleId) {
		return mapper.queryRoleByRoleId(roleId);
	}

	public List<SysRole> selectSysRoleList(Map<String, Object> map) {
		return mapper.querySysRoleList(map);
	}

	public List<SysRole> selectRoleByUserId(Long userId) {
		return mapper.queryRoleByUserId(userId);
	}

	public List<ZtreeVO> getGrantMenuList(Map<String, Object> map) {
		List<ZtreeVO> list = menuMapper.queryAllMenuList();
		List<SysMenu> menuList = menuMapper.queryGrantMenuList(map);
		for (ZtreeVO vo : list) {
			for (SysMenu sysMenu : menuList) {
				if(Long.valueOf(String.valueOf(vo.getId())).equals(sysMenu.getMenuId())){
					vo.setChecked(true);
				}
			}
		}
		return list;
	}

	public void grantRole(Long roleId, String menuIds) {
		SysRoleMenu roleMenu = new SysRoleMenu();
		roleMenu.setRoleId(roleId);
		roleMenuMapper.delete(roleMenu);
		
		if(menuIds!=null && !menuIds.equals("")){
			for (String menuId : menuIds.split(",")) {
				SysRoleMenu saveRoleMenu = new SysRoleMenu();
				saveRoleMenu.setRoleId(roleId);
				saveRoleMenu.setMenuId(Long.valueOf(menuId));
				roleMenuMapper.insertSelective(saveRoleMenu);
			}
		}
	}
}