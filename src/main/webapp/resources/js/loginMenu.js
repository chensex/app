var isFirstMenu;
$(function() {
	var menulist;
	$.post(basePath+'/system/getMenuList', function(data) {
		if (data.state == 1) {
			menulist = data;
			var showlist = $("<ul class=\"sidebar-menu\"></ul>");
			isFirstMenu = menulist.object.length;
			showall(menulist.object, showlist);
			$("#div_menu").append(showlist);
		} else {
			alert('亲，请求失败！');
			return;
		}
	});
});

function showall(menu_list, parent) {
	for ( var menu in menu_list) {
		if (menu_list[menu].children.length > 0) {
			var li = $("<li></li>");
			if (isFirstMenu == 0) {
				li = $("<li></li>");
			} else {
				li = $("<li class=\"treeview\"></li>");
				isFirstMenu = isFirstMenu - 1;
			}
			$(li).append("<a href='javascript:void(0);'><i class=\"fa fa-share\"></i> <span>"
					+ menu_list[menu].name+ "</span><i class=\"fa fa-angle-right pull-right\"></i></a>");
			var nextParent = $("<ul class=\"treeview-menu\"></ul>");
			$(nextParent).appendTo(li);
			$(li).appendTo(parent);
			showall(menu_list[menu].children, nextParent);
		} else {
			$("<li><a href='javascript:void(0);' onclick=\"javascript:addTab('" + menu_list[menu].name + "','" + menu_list[menu].openUrl + "')\">" +
					"<i class=\"fa fa-circle-o\"></i>"+ menu_list[menu].name + "</a></li>").appendTo(parent);
		}
	}
}
function addTab(title, url) {
	if ($('#centerTab').tabs('exists', title)) {
		$('#centerTab').tabs('select', title);
		p.find('iframe').$('#centerTab').datagrid('reload');
	} else {
		var content = '<iframe scrolling="auto" frameborder="0"  src="'+ url + '" style="width:100%;height:100%;"></iframe>';
		$('#centerTab').tabs('add', {
			title : title,
			content : content,
			closable : true
		});
	}
}
