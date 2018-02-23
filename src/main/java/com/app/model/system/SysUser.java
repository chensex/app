package com.app.model.system;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Table(name = "sys_user")
public class SysUser {
    /**
     * 主键
     */
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    /**
     * 登录帐号
     */
    @Column(name = "login_name")
    private String loginName;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 电话
     */
    private String phone;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 注册时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * IP
     */
    private String ip;

    /**
     * 最后一次登录时间
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    /**
     * 状态(0：注销 1：正常 2：锁定 3：禁用)
     */
    private String state;

    /**
     * 错误登录次数
     */
    private Integer num;
    
    @Transient
    private List<SysRole> roles;
    
    @Transient
    private List<SysRole> useRoles;

    /**
     * 获取主键
     *
     * @return user_id - 主键
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置主键
     *
     * @param userId 主键
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取登录帐号
     *
     * @return login_name - 登录帐号
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * 设置登录帐号
     *
     * @param loginName 登录帐号
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取姓名
     *
     * @return user_name - 姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置姓名
     *
     * @param userName 姓名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取电话
     *
     * @return phone - 电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话
     *
     * @param phone 电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取性别
     *
     * @return sex - 性别
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 设置性别
     *
     * @param sex 性别
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 获取注册时间
     *
     * @return create_time - 注册时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置注册时间
     *
     * @param createTime 注册时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取IP
     *
     * @return ip - IP
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置IP
     *
     * @param ip IP
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 获取最后一次登录时间
     *
     * @return last_login_time - 最后一次登录时间
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 设置最后一次登录时间
     *
     * @param lastLoginTime 最后一次登录时间
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * 获取状态(0：注销 1：正常 2：锁定 3：禁用)
     *
     * @return state - 状态(0：注销 1：正常 2：锁定 3：禁用)
     */
    public String getState() {
        return state;
    }

    /**
     * 设置状态(0：注销 1：正常 2：锁定 3：禁用)
     *
     * @param state 状态(0：注销 1：正常 2：锁定 3：禁用)
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 获取错误登录次数
     *
     * @return num - 错误登录次数
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置错误登录次数
     *
     * @param num 错误登录次数
     */
    public void setNum(Integer num) {
        this.num = num;
    }

	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}

	public List<SysRole> getUseRoles() {
		return useRoles;
	}

	public void setUseRoles(List<SysRole> useRoles) {
		this.useRoles = useRoles;
	}
}