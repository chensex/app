package com.app.model.system;

import javax.persistence.*;

import com.base.model.BaseModel;

@Table(name = "sys_user_role")
public class SysUserRole extends BaseModel{
	private static final long serialVersionUID = -5139554305881409321L;

	/**
     * 主键
     */
    @Id
    @Column(name = "user_role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userRoleId;

    /**
     * 角色id
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 获取主键
     *
     * @return user_role_id - 主键
     */
    public Long getUserRoleId() {
        return userRoleId;
    }

    /**
     * 设置主键
     *
     * @param userRoleId 主键
     */
    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }

    /**
     * 获取角色id
     *
     * @return role_id - 角色id
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 设置角色id
     *
     * @param roleId 角色id
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}