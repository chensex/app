package com.app.model.system;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.base.model.BaseModel;

@Table(name = "sys_menu")
public class SysMenu extends BaseModel{
	private static final long serialVersionUID = 5149954618031069798L;

	/**
     * 主键
     */
    @Id
    @Column(name = "menu_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    /**
     * 菜单名称
     */
    @Column(name = "menu_name")
    private String menuName;

    /**
     * 菜单地址
     */
    @Column(name = "menu_url")
    private String menuUrl;

    /**
     * 父级菜单ID
     */
    @Column(name = "parent_id")
    private Long parentId;
    
    /**
     * 父级菜单名称
     */
    @Transient
    private String parentMenuName;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 排序号
     */
    private Integer sort;
    
    /**
     * 级别
     */
    private Integer level;
    
    /**
     * 是否打开(节点)
     */
    private String isOpen;
    
    /**
     * 打开窗口(节点)
     */
    private String target;
    
    /**
     * 节点图标
     */
    @Column(name = "icon")
    private String icon;
    
    /**
     * 节点打开图标
     */
    @Column(name = "icon_open")
    private String iconOpen;
    
    /**
     * 节点关闭图标
     */
    @Column(name = "icon_close")
    private String iconClose;
    
    /**
     * 节点个性化图标
     */
    @Column(name = "icon_skin")
    private String iconkin;

    @Transient
    private List<SysMenu> sysMenus;
    
    
    /*
     * 状态(1:有效 0：无效)
     */
    private Integer state;
    

    public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
     * 获取菜单名称
     *
     * @return menu_name - 菜单名称
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * 设置菜单名称
     *
     * @param menuName 菜单名称
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    /**
     * @return menu_url
     */
    public String getMenuUrl() {
        return menuUrl;
    }

    /**
     * @param menuUrl
     */
    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
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

    /**
     * 获取排序号
     *
     * @return sort - 排序号
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序号
     *
     * @param sort 排序号
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }
    
    /**
     * 获取是否打开
     *
     * @param sort 排序号
     */
	public String getIsOpen() {
		return isOpen;
	}

	 /**
     * 设置是否打开
     *
     * @param sort 排序号
     */
	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	public List<SysMenu> getSysMenus() {
		return sysMenus;
	}

	public void setSysMenus(List<SysMenu> sysMenus) {
		this.sysMenus = sysMenus;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Long getParentId() {
		return parentId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIconOpen() {
		return iconOpen;
	}

	public void setIconOpen(String iconOpen) {
		this.iconOpen = iconOpen;
	}

	public String getIconClose() {
		return iconClose;
	}

	public void setIconClose(String iconClose) {
		this.iconClose = iconClose;
	}

	public String getIconkin() {
		return iconkin;
	}

	public void setIconkin(String iconkin) {
		this.iconkin = iconkin;
	}

	public String getParentMenuName() {
		return parentMenuName;
	}

	public void setParentMenuName(String parentMenuName) {
		this.parentMenuName = parentMenuName;
	}
}