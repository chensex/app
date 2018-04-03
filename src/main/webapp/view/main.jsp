<%@page import="com.base.util.CommonConstant"%>
<%@page import="com.app.model.system.SysUser"%>
<%
	SysUser user = (SysUser)request.getSession().getAttribute(CommonConstant.SESSION_USER);
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人微博信息管理系统</title>
<script type="text/javascript" src="../jquery/core/jquery-1.9.1.min.js"></script>
<jsp:include page="easyui.jsp"></jsp:include>
<link rel="stylesheet" href="resources/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="resources/zTree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript">

$(document).ready(function(){
    onLoadZTree();
});
var setting={
		
		view: {
			dblClickExpand: false,
			showLine: true,
			showIcon:true,
			selectedMulti: true
		},
		data: {
 			key:{
 				openUrl:'_url'
 			}

			},
		data: {
			simpleData: {
				enable:true,
				idKey: "id",
				pIdKey: "pId",
				rootPId: ""
			}
		},
		callback: {
	        beforeClick: function(treeId, treeNode) {
	            zTree = $.fn.zTree.getZTreeObj("tree");
	            if (treeNode.isParent) {
	                zTree.expandNode(treeNode);//如果是父节点，则展开该节点
	            }else{
	            	addTab(treeNode.name,treeNode.openUrl);
	            }
	        }
	    }
};
function onLoadZTree(){
    var treeNodes;
    $.ajax({
        async:false,//是否异步
        cache:false,//是否使用缓存
        type:'POST',//请求方式：post
        dataType:'json',//数据传输格式：json
        url:"<%=request.getContextPath()%>/app/system/getMenuList",//请求的action路径
        error:function(){
            //请求失败处理函数
            alert('亲，请求失败！');
        },
        success:function(data){
            //请求成功后处理函数
            treeNodes = data;//把后台封装好的简单Json格式赋给treeNodes
        }
    });
     
    var t = $("#tree");
    t = $.fn.zTree.init(t, setting, treeNodes);
   
}

function addTab(title, url){
	if ($('#centerTab').tabs('exists', title)){
		 $('#centerTab').tabs('select', title);
		 p.find('iframe').$('#centerTab').datagrid('reload'); 
	} else {
		var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
		$('#centerTab').tabs('add',{
			title:title,
			content:content,
			closable:true
		});
	}
}
</script>
<style type="text/css">
.userN{
	color: #0099FF
}
</style>
</head>
    <body class="easyui-layout">
    	<!-- top 顶部面板 -->
        <div data-options="region:'north',title:'WEB管理系统 ',split:true" 
        style="height:60px;background-color:#cce4ef;">
        	<div align="right">
        	<!-- <font color="black" size="6">WEB管理系统</font> -->
        	<span class="userN">欢迎：<%=user.getLoginName()%></span>
        	<span  id="localtime" style="text-align:center;"></span>
        	<a href="<%=request.getContextPath()%>/app/system/userLoginOut">退出</a>&nbsp;&nbsp;&nbsp;
        	</div>
        	
        </div>
        
        <!-- menu 菜单栏 -->
        <div data-options="region:'west',title:'菜单栏',split:true" style="width:200px;background-color:#cce4ef;">
        	
        	<div>
        		<ul id="tree" class="ztree"></ul>
        	</div>
        </div>
        
        <!-- function 功能区 -->
        <div data-options="region:'center',title:'功能区'" style="padding:5px;background:#eee;">
        	<div class="easyui-tabs" id="centerTab" fit="true" border="false">  
            <div title="欢迎页" style="padding:20px;overflow:hidden;">   
                <div style="margin-top:20px;">  
                    <h3>欢迎使用</h3>  
                </div>  
            </div>  
        </div>  
        </div>
         <!-- 正下方panel -->  
	    <div region="south" style="height:60px;background-color:#cce4ef;" align="center">  
	        <label>  
			            作者：陈伟<br/>  
			            时间：2015-10-10<br/>  
			           微博管理  
	        </label>  
	    </div>  
    </body>
    <script type="text/javascript">
    function showLocale(objD)
    {
    	var str,colorhead,colorfoot;
    	var yy = objD.getYear();
    	if(yy<1900) yy = yy+1900;
    	var MM = objD.getMonth()+1;
    	if(MM<10) MM = '0' + MM;
    	var dd = objD.getDate();
    	if(dd<10) dd = '0' + dd;
    	var hh = objD.getHours();
    	if(hh<10) hh = '0' + hh;
    	var mm = objD.getMinutes();
    	if(mm<10) mm = '0' + mm;
    	var ss = objD.getSeconds();
    	if(ss<10) ss = '0' + ss;
    	var ww = objD.getDay();
    	if  ( ww==0 )  colorhead="<font color=\"#0099FF\">";
    	if  ( ww > 0 && ww < 6 )  colorhead="<font color=\"#0099FF\">";
    	if  ( ww==6 )  colorhead="<font color=\"#0099FF\">";
    	if  (ww==0)  ww="星期日";
    	if  (ww==1)  ww="星期一";
    	if  (ww==2)  ww="星期二";
    	if  (ww==3)  ww="星期三";
    	if  (ww==4)  ww="星期四";
    	if  (ww==5)  ww="星期五";
    	if  (ww==6)  ww="星期六";
    	colorfoot="</font>";
    	str = colorhead + yy + "-" + MM + "-" + dd + " " + hh + ":" + mm + ":" + ss + "  " + ww + colorfoot;
    	return(str);
    }
    function tick()
    {
    	var today;
    	today = new Date();
    	document.getElementById("localtime").innerHTML = showLocale(today);
    	window.setTimeout("tick()", 1000);
    }
    tick();
    </script>
</html>