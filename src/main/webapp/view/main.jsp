<%@page import="com.base.util.CommonConstant"%>
<%@page import="com.app.model.system.SysUser"%>
<%
	SysUser user = (SysUser) request.getSession().getAttribute(CommonConstant.SESSION_USER);
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人微博信息管理系统</title>
<jsp:include page="easyui.jsp"></jsp:include>
<script src="resources/js/loginMenu.js"></script>
<script src="resources/js/sidebar-menu.js"></script>
<script type="text/javascript">
var basePath = "<%=request.getContextPath()%>";
</script>
<link rel="stylesheet" href="resources/bootstrap-4.0.0/dist/css/bootstrap.min.css">
<link href="http://www.jq22.com/jquery/font-awesome.4.6.0.css" rel="stylesheet" media="screen">
<link rel="stylesheet" href="resources/css/sidebar-menu.css">
<link rel="stylesheet" href="resources/css/main.css">
<style type="text/css">
.header {

	background-image: url("images/top-bj.jpg");
}
.menubg{
background-image: url("images/menu-bg.jpg");
}
</style>
</head>
<body class="easyui-layout">
	<!-- top 顶部面板 -->
	<div data-options="region:'north'" style="height: 60px;" class="header">
		<div align="right">
			<br>
			<span class="userN" id="localtime" style="text-align: center;"></span>
			<span class="userN">欢迎：<%=user.getLoginName()%></span>
			<a class="userN" href="<%=request.getContextPath()%>/system/userLoginOut">退出</a>&nbsp;&nbsp;&nbsp;
		</div>

	</div>

	<!-- menu 菜单栏 -->
	<div data-options="region:'west',title:'菜单导航',split:true" style="width: 230px;height: 15px;" class="menubg">

		<aside class="main-sidebar"> <section id="div_menu"
			class="sidebar"> </section> </aside>
	</div>

	<!-- function 功能区 -->
	<div data-options="region:'center'"
		style="padding: 5px; background: #27A6C9;">
		<div class="easyui-tabs" id="centerTab" fit="true" border="false">
			<div title="欢迎页" style="padding: 50px; overflow: hidden;">
				<div style="margin-top: 50px;">
					<h3>欢迎使用</h3>
				</div>
			</div>
		</div>
	</div>
	<div id="mm" class="easyui-menu" style="width:120px;display:none;">
		<div id="closeCurrent" data-options="iconCls:'icon-no'">关闭当前</div>
		<div id="closeOthers" data-options="iconCls:'icon-no'">关闭其他</div>
		<div id="closeAll" data-options="iconCls:'icon-cancel'">关闭全部</div>
		<div id="reloadAll" data-options="iconCls:'icon-reload'">刷新当前</div>
	</div>
	<!-- 正下方panel -->
	<div region="south" class="header" style="height: 60px; background-color: #27A6C9;"
		align="center">
		<label> <br /> 后台管理 © / All Rights Reserved
		</label>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		//生成tab标签
		  $('#centerTab').tabs({
		   border : true,
		   /* onSelect : function(title) {
		    alert(title + ' is selected');
		   } */
		  });

		   //生成右键菜单 
		   $('#centerTab').tabs({
			   onLoad:function(panel){
	    			var sw = $(window).width();
	    			if (sw < 767){
						$('body').layout('collapse', 'west');
					}
	    		},
		    onContextMenu: function(e, title, index){
		    	
		    	e.preventDefault();
    			if(index>0){
    				$('#mm').menu('show', {
    					left: e.pageX,
    					top: e.pageY
    				}).data("tabTitle", title);
    			}
		    }
		   });
		   
		   //为每个菜单绑定点击事件
		   //关闭选中的标签
		   $("#closeCurrent").click(function(){
			   var currtab_title = $('#mm').data("tabTitle");
	    		$('#centerTab').tabs('close',currtab_title);
		   });
		   //关闭选中标签之外的标签
		   $("#closeOthers").click(function(){
			   var curTabTitle = $('#mm').data("tabTitle");
	    		var allTabtitle = [];
	    		var allTabs = $("#centerTab").tabs('tabs');
	    		$.each(allTabs,function(i,n){
	                var opt=$(n).panel('options');
	                if(opt.closable)
	                    allTabtitle.push(opt.title);
	            });
	    		for(var i=0;i<allTabtitle.length;i++){
	    			if(allTabtitle[i] != curTabTitle){
	    				$('#centerTab').tabs('close', allTabtitle[i]);
	    			}
	    		}
		   });
		   //关闭所有标签
		   $("#closeAll").click(function(){
			   var allTabtitle = [];
	    		var allTabs = $("#centerTab").tabs('tabs');
	    		$.each(allTabs,function(i,n){
	                var opt=$(n).panel('options');
	                if(opt.closable)
	                    allTabtitle.push(opt.title);
	            });
	    		for(var i=0;i<allTabtitle.length;i++){
	    			$('#centerTab').tabs('close', allTabtitle[i]);
	    		}
		   });
		   $("#reloadAll").click(function(){
			   var currTab = $('#centerTab').tabs('getSelected');
			   var url = $(currTab.panel('options').content).attr('src');
			   $('#centerTab').tabs('update', {
		            tab: currTab,
		            options: {
		                content: '<iframe scrolling="auto" frameborder="0" src='+url+' style="width:100%;height:100%;"></iframe>'
		            }
		        });
		   });
   
	});
	function showLocale(objD) {
		var str, colorhead, colorfoot;
		var yy = objD.getYear();
		if (yy < 1900)
			yy = yy + 1900;
		var MM = objD.getMonth() + 1;
		if (MM < 10)
			MM = '0' + MM;
		var dd = objD.getDate();
		if (dd < 10)
			dd = '0' + dd;
		var hh = objD.getHours();
		if (hh < 10)
			hh = '0' + hh;
		var mm = objD.getMinutes();
		if (mm < 10)
			mm = '0' + mm;
		var ss = objD.getSeconds();
		if (ss < 10)
			ss = '0' + ss;
		var ww = objD.getDay();
		if (ww == 0)
			colorhead = "<font color=\"#FFFFFF\">";
		if (ww > 0 && ww < 6)
			colorhead = "<font color=\"#FFFFFF\">";
		if (ww == 6)
			colorhead = "<font color=\"#FFFFFF\">";
		if (ww == 0)
			ww = "星期日";
		if (ww == 1)
			ww = "星期一";
		if (ww == 2)
			ww = "星期二";
		if (ww == 3)
			ww = "星期三";
		if (ww == 4)
			ww = "星期四";
		if (ww == 5)
			ww = "星期五";
		if (ww == 6)
			ww = "星期六";
		colorfoot = "</font>";
		str = colorhead + yy + "-" + MM + "-" + dd + " " + hh + ":" + mm + ":"
				+ ss + "  " + ww + colorfoot;
		return (str);
	}
	function tick() {
		var today;
		today = new Date();
		document.getElementById("localtime").innerHTML = showLocale(today);
		window.setTimeout("tick()", 1000);
	}
	tick();
</script>
</html>