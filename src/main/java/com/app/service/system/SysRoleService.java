package com.app.service.system;

import java.util.List;
import java.util.Map;

import com.app.model.system.SysRole;
import com.base.model.ZtreeVO;

/**
 * 类说明：角色管理
 * @author CHENWEI
 * 2016年8月25日
 */
public interface SysRoleService {
	
	/**
	 * 角色列表
	 * @param map
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<SysRole> selectSysRoleList(Map<String, Object> map,int page,int pageSize);
	
	/**
	 * 角色列表
	 * @param map
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<SysRole> selectSysRoleList(Map<String, Object> map);
	
	/**
	 * 新增和修改角色
	 * @param sysRole
	 */
	public void saveAndEditRole(SysRole sysRole);
	
	/**
	 * 根据角色ID查询角色
	 * @param roleId
	 * @return
	 */
	public SysRole selectRoleByRoleId(Long roleId);
	
	/**
	 * 根据用户ID查询角色
	 * @param roleId
	 * @return
	 */
	public List<SysRole> selectRoleByUserId(Long userId);
	
	/**
	 * 根据用户ID查询角色(返回String)
	 * @param roleId
	 * @return
	 */
	public List<String> selectRoleNameByUserId(Long userId);
	
	
	public List<ZtreeVO> getGrantMenuList(Map<String, Object> map);
	
	public void grantRole(Long roleId,String menuIds);
}
