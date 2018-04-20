package com.app.servlet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.app.model.system.SysUser;
import com.app.service.system.SysMenuService;
import com.app.service.system.SysRoleService;
import com.app.service.system.SysUserService;
import com.base.util.CommonConstant;

public class AuthorityRealm extends AuthorizingRealm{

	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired
	private SysMenuService sysMenuService;
	
	@Override
	public String getName() {
		return "customRealm";
	}
	
	/**
	 * 登录认证，创建用户的登录信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		String loginName = token.getUsername();
		 SysUser user = null;
		 try {
			 user = sysUserService.selectUserByLoginName(loginName);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		if(user == null) {
			return null;
		}
		
		if(user.getNum() > 3) {
			return null;
		}
		
		String password = user.getPassword();
		
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(loginName, password,getName());
		
		
		
		ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		ra.getRequest().getSession().setAttribute(CommonConstant.SESSION_USER, user);
		return simpleAuthenticationInfo;
	}
	
	/**
	 * 权限认证，获取登录用户的权限
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String loginName=(String) principals.fromRealm(getName()).iterator().next();
		SysUser user = sysUserService.selectUserByLoginName(loginName);
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
		if(user != null) {
			//添加角色
			List<String> roles = sysRoleService.selectRoleNameByUserId(user.getUserId());
			info.addRoles(roles);
			
			//添加权限
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("userId", user.getUserId());
			info.addStringPermissions(sysMenuService.selectMenuNameList(map));
		}
		return info;
	}

}
