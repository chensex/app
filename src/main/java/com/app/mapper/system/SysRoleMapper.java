package com.app.mapper.system;

import java.util.List;
import java.util.Map;

import com.app.model.system.SysRole;

import tk.mybatis.mapper.common.Mapper;

/**
 * 类说明：角色
 * @author CHENWEI
 * 2016年9月5日
 */
public interface SysRoleMapper extends Mapper<SysRole> {
	/**
	 * 角色列表
	 * @param map
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<SysRole> querySysRoleList(Map<String, Object> map);
	
	/**
	 * 根据角色ID查询角色
	 * @param roleId
	 * @return
	 */
	public SysRole queryRoleByRoleId(Long roleId);
	
	/**
	 * 根据用户ID查询角色
	 * @param userId
	 * @return
	 */
	public List<SysRole> queryRoleByUserId(Long userId);
}