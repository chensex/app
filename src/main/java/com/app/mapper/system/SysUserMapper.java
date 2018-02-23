package com.app.mapper.system;

import java.util.List;
import java.util.Map;

import com.app.model.system.SysMenu;
import com.app.model.system.SysUser;
import tk.mybatis.mapper.common.Mapper;
/**
 * 类说明：用户管理
 * @author CHENWEI
 * 2016年8月25日
 */
public interface SysUserMapper extends Mapper<SysUser> {
	
	/**
	 * 用户列表
	 * @param map
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<SysMenu> querySysUserList(Map<String, Object> map);
	
	/**
	 * 根据用户ID查询用户
	 * @param userId
	 * @return
	 */
	public SysUser queryUserByUserId(Long userId);
	
	/**
	 * 根据登录帐号查询用户
	 * @param userId
	 * @return
	 */
	public SysUser queryUserByLoginName(String loginName);
	
	public SysUser querySysUser(Map<String, Object> map);
}