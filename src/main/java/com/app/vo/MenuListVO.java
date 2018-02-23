package com.app.vo;

import java.util.List;

public class MenuListVO {
	private int menuId;
	private int parentId;
	private String menuName;
	private String menuUrl;
	private List<MenuListVO> children;
	public int getMenuId() {
		return menuId;
	}
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public List<MenuListVO> getChildren() {
		return children;
	}
	public void setChildren(List<MenuListVO> children) {
		this.children = children;
	}
}
