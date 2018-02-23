package com.app.service.system.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.mapper.system.SysUserMapper;
import com.app.mapper.system.SysUserRoleMapper;
import com.app.model.system.SysMenu;
import com.app.model.system.SysUser;
import com.app.model.system.SysUserRole;
import com.app.service.system.SysUserService;
import com.base.service.impl.BaseServiceImpl;
import com.base.util.Cipher;
import com.github.pagehelper.PageHelper;

/**
 * 类说明：用户管理
 * @author CHENWEI
 * 2016年8月25日
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl implements SysUserService{
	@Autowired
	private SysUserMapper userMapper;
	
	@Autowired
	private SysUserRoleMapper userRoleMapper;
	
	public SysUser selectSysUser(Map<String, Object> map) {
		map.put("password", Cipher.encryption(String.valueOf(map.get("password"))));
		return userMapper.querySysUser(map);
	}
	
	public int updateUserByEntity(SysUser user){
		return userMapper.updateByPrimaryKey(user);
	}

	public SysUser getSysUserByEntity(SysUser user) {
		return userMapper.selectOne(user);
	}

	public List<SysMenu> selectSysUserList(Map<String, Object> map, int page, int pageSize) {
		PageHelper.startPage(page, pageSize);
		return userMapper.querySysUserList(map);
	}

	public void saveAndEditUser(SysUser sysUser,String roleIds) {
		if(sysUser.getUserId()==null){
			userMapper.insertSelective(sysUser);
		}else{
			userMapper.updateByPrimaryKeySelective(sysUser);
		}
		
		//插入用户角色
		if(roleIds!=null && roleIds!=""){
			//删除原来存在的用户角色
			SysUserRole userRole = new SysUserRole();
			userRole.setUserId(sysUser.getUserId());
			userRoleMapper.delete(userRole);
			
			for (String roleId : roleIds.split(",")) {
				SysUserRole saveRole = new SysUserRole();
				saveRole.setUserId(sysUser.getUserId());
				saveRole.setRoleId(Long.valueOf(roleId));
				userRoleMapper.insertSelective(saveRole);
			}
		}
	}

	public SysUser selectUserByUserId(Long userId) {
		return userMapper.queryUserByUserId(userId);
	}

	public SysUser selectUserByLoginName(String loginName) {
		return userMapper.queryUserByLoginName(loginName);
	}
}