package com.app.service.system;

import java.util.List;
import java.util.Map;

import com.app.model.system.SysMenu;
import com.app.model.system.SysUser;

/**
 * 类说明：用户管理
 * @author CHENWEI
 * 2016年8月25日
 */
public interface SysUserService {
	
	/**
	 * 用户列表
	 * @param map
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<SysMenu> selectSysUserList(Map<String, Object> map,int page,int pageSize);
	
	/**
	 * 新增和修改用户
	 * @param sysMenu
	 */
	public void saveAndEditUser(SysUser sysUser,String roleIds);
	
	/**
	 * 根据用户ID查询用户
	 * @param menuId
	 * @return
	 */
	public SysUser selectUserByUserId(Long userId);
	
	/**
	 * 根据登录帐号查询用户
	 * @param menuId
	 * @return
	 */
	public SysUser selectUserByLoginName(String loginName);
	
	/**
	 * 方法说明：获取用户信息
	 * @author CHENWEI
	 * @return
	 * 2016年8月25日
	 */
	public SysUser selectSysUser(Map<String, Object> map);
	
	public SysUser getSysUserByEntity(SysUser user);
	
	public int updateUserByEntity(SysUser user);
}
