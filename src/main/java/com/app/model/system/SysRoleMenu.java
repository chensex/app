package com.app.model.system;

import javax.persistence.*;

@Table(name = "sys_role_menu")
public class SysRoleMenu {
    /**
     * 主键
     */
    @Id
    @Column(name = "role_menu_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleMenuId;

    /**
     * 角色id
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 菜单id
     */
    @Column(name = "menu_id")
    private Long menuId;

    /**
     * 获取主键
     *
     * @return role_menu_id - 主键
     */
    public Long getRoleMenuId() {
        return roleMenuId;
    }

    /**
     * 设置主键
     *
     * @param roleMenuId 主键
     */
    public void setRoleMenuId(Long roleMenuId) {
        this.roleMenuId = roleMenuId;
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
     * 获取菜单id
     *
     * @return menu_id - 菜单id
     */
    public Long getMenuId() {
        return menuId;
    }

    /**
     * 设置菜单id
     *
     * @param menuId 菜单id
     */
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}