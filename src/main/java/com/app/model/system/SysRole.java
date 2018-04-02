package com.app.model.system;

import java.util.Date;
import javax.persistence.*;

import com.base.model.BaseModel;

@Table(name = "sys_role")
public class SysRole extends BaseModel{
	private static final long serialVersionUID = -434164338810146930L;

	/**
     * 主键
     */
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    /**
     * 角色名称
     */
    @Column(name = "role_name")
    private String roleName;

    /**
     * 角色状态(0：禁用 1：正常)
     */
    private Integer state;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 获取主键
     *
     * @return role_id - 主键
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 设置主键
     *
     * @param roleId 主键
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取角色名称
     *
     * @return role_name - 角色名称
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 设置角色名称
     *
     * @param roleName 角色名称
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * 获取角色状态(0：禁用 1：正常)
     *
     * @return state - 角色状态(0：禁用 1：正常)
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置角色状态(0：禁用 1：正常)
     *
     * @param state 角色状态(0：禁用 1：正常)
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}