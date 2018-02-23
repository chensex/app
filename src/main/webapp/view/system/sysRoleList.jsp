<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="../easyui.jsp"%>
    <title>角色管理</title>
</head>
<body>

	<div class="easyui-layout" fit="true">
		<!-- 查询条件 -->
		<div region="north" style="height:125px;padding:5px" border="false">
			<div class="easyui-panel" title="角色信息" style="padding:10px 0px 0px 20px" fit="true">
				
				<form id="form">
					<label>角色名称：</label>
					<input class="easyui-textbox" type="text" id="searchRoleName" name="searchRoleName" /> 
					<p>
						<a id="query" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="reloadGrid()">查询</a> 
						<a id="clear" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-clear" onclick="clearForm()">重置</a> 
					</p>
				</form>
				
			</div>
		</div>
		
		<!-- 查询结果 -->
		<div region="center" border="false" style="padding:0px 5px">
			<div id="roleGrid"  fit="true"></div>
		</div>
	</div>

	<!-- 添加、修改dialog -->
	<div id="dlg" class="easyui-dialog" style="width:500px;height:400px;display:none;" closed="true" buttons="#dlg-buttons">
		<form id="form2" action="<%=request.getContextPath()%>/app/system/saveRole" method="post">
			<input type="hidden" id="roleId" name="roleId"/>
			<input type="hidden" id="saveState" name="saveState"/>
			<table class="t2" align="center" style="width: 94%;height: 100%;margin: 15px;">
				<tr>
					<th  width="100px;"><label for="roleName">角色帐号：</label></th>
					<td><input class="easyui-textbox" style="width: 200px" type="text" data-options="required:true" id="roleName" name="roleName"/></td>
				</tr>
				<tr>
					<th><label for="state">状态：</label></th>
					<td>
						<select class="easyui-combobox" id="state" name="state" style="width: 200px;" data-options="required:true" editable="false">
								<option value="0">禁用</option>
								<option value="1">正常</option>
						</select>
					</td>
				</tr>
			</table>
		</form>
		<div id="dlg-buttons">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok"  id="save" onclick="saveRole()">保存</a> 
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" id="close" onclick="colseDlg();">关闭</a>
		</div>
	</div>
	<!-- 添加、修改dialog -->
	<div id="grantDialog" class="easyui-dialog" style="width:350px;height:400px;display:none;" closed="true" buttons="#dlg-buttonsGrant">
		<input type="hidden" id="roleMenuId" name="roleMenuId"/>
		<div>
        	<ul id="tree" class="ztree"></ul>
        </div>
        <div id="dlg-buttonsGrant">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok"  id="saveGrant" onclick="saveRoleMenu()">授权</a> 
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" id="closeGrant" onclick="colseGrantDlg();">关闭</a>
		</div>
	</div>
	<!-- 添加、修改dialog 结束-->
</body>
	<script type="text/javascript" src="resources/zTree/js/jquery.ztree.core-3.5.min.js"></script>
	<script type="text/javascript" src="resources/zTree/js/jquery.ztree.excheck-3.5.min.js"></script>
	<link rel="stylesheet" href="resources/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script>
	$(function() {
		initDatagrid();//初始化，dataGrid
	});
	function initDatagrid(){
		$("#roleGrid").datagrid({
	    	title:"角色信息列表",
	    	emptyMsg : "没有记录",
	        url: "<%=request.getContextPath()%>/app/system/sysRoleList",
	        method: "POST",
	        rownumbers : true,
	        singleSelect: true,
	        pagination: true,
	        pageSize: 10,
	        singleSelect : true,
	        pageList: [10, 20, 50],
	        idField: "roleId",
	        fit:true,
	        columns: [[
	            { field: "roleName", title: "角色", width: "25%", align: "center"},
	            { field: "createTime", title: "创建时间", width: "25%", align: "center"},
	            { field: "state", title: "状态", width: "25%", align: "center",formatter:stateMap},
	            { field: "_edit", title: "操作", width: "25%", align: "center",formatter:edit}
	        ]],
	        toolbar : [{ iconCls : "icon-add",
				text : "新增",
				handler : function() {
					addRole();
				}
			}, "-", {
				iconCls : "icon-edit",
				text : "编辑",
				handler : function() {
					editRole();
				}
			}, "-", {
				iconCls : "icon-edit",
				text : "授权",
				handler : function() {
					grantRole();
				}
			}]
	    });
	}
	
	function edit(){
		return "";
	}
	
	/**
	 * 弹出新增窗口
	 */
	function addRole() {
		$("#form2").form("clear");
		$("#saveState").val("add");
		$("#form2").attr("action", "<%=request.getContextPath()%>/app/system/saveRole");
		$("#dlg").dialog({ 
			modal : true,
			top : $(document).scrollTop() + ($(window).height() - 430) * 0.5 
			});//弹出框位置，居中，430表示弹出框的高度
		$("#dlg").dialog("open").dialog("setTitle", "添加角色信息");
	}
	
	/**
	 * 弹出编辑窗口
	 */
	function editRole() {
		var row = $("#roleGrid").datagrid("getSelected");
		if (!row) {
			$.messager.alert("提示", "请选择一条数据！", "info");
			return;
		}
		$("#form2").form("clear");
		$("#saveState").val("update");
		$("#dlg").dialog({ modal : true, top : $(document).scrollTop() + ($(window).height() - 380) * 0.5 });
		$.post("<%=request.getContextPath()%>/app/system/getSysRoleById", {
			roleId : row.roleId
		}, function(data) {
			$("#roleId").val(data.roleId);
			$("#roleName").textbox("setValue", data.roleName);
			$("#state").combobox("setValue", data.state);
		}, "json");
		$("#dlg").dialog("open").dialog("setTitle", "修改角色信息");
	}
	
	/**
	 * 角色授权
	 */
	function grantRole() {
		var row = $("#roleGrid").datagrid("getSelected");
		if (!row) {
			$.messager.alert("提示", "请选择一条数据！", "info");
			return;
		}
		$("#grantDialog").dialog({ modal : true, top : $(document).scrollTop() + ($(window).height() - 380) * 0.5 });
		var setting={
				
				view: {
					selectedMulti: true
				},
				check: {
					enable: true
				},
				data: {
		 			key:{
		 				title: "name",
		 				name: "name"
		 			},
		 			simpleData: {
						enable:true,
						idKey: "id",
						pIdKey: "pId",
						rootPId: ""
					}
				}
		};
		$.ajax({
	        async:true,//是否异步
	        cache:false,//是否使用缓存
	        type:'POST',//请求方式：post
	        dataType:'json',//数据传输格式：json
	        url:"<%=request.getContextPath()%>/app/system/grantMenuList?roleId="+row.roleId,//请求的action路径
	        error:function(){
	            //请求失败处理函数
	            alert('亲，请求失败！');
	        },
	        success:function(data){
	            //请求成功后处理函数
	            $.fn.zTree.init($("#tree"), setting, data);//把后台封装好的简单Json格式赋给treeNodes
	        }
	    });
		$("#roleMenuId").val(row.roleId);
		$("#grantDialog").dialog("open").dialog("setTitle", "角色授权");
	}
	
	function saveRole(){
		var form = $("#form2");
		 if(!form.form('validate')){
			 return;
		 }
		 var url = form.attr("action");
		 var saveState = $("#saveState").val();
		 if(saveState=="add"){
		 }else if(saveState=="update"){
			 form.attr("action", "<%=request.getContextPath()%>/app/system/updateRole");
		 }
		 
		 $.ajax({
	            url: form.attr("action"),
	            cache: false, //cache的作用就是第一次请求完毕之后，如果再次去请求，可以直接从缓存里面读取而不是再到服务器端读取。
	            type: 'post',//提交的方式
	            data: form.serialize(),
	            async:false,//async默认的设置值为true，这种情况为异步方式，就是说当ajax发送请求后，在等待server端返回的这个过程中，前台会继续执行ajax块后面的脚本
	            success: function(result) {
	            	var data = eval( "(" + result + ")" );
	            	if(data.state==0){
	            		$.messager.alert("提示", data.content, "info");
	            	}else if(data.state==1){
	            		$.messager.alert("提示", data.content, "info");
	            		$("#dlg").dialog("close");
	            		$("#form2").form("clear");
	            		initDatagrid();
	            	}
	            }
	        });
	}
	
	function updateRole(){
		var form = $("#form2");
		form.attr("action", "<%=request.getContextPath()%>/app/system/updateRole");
		 if(!form.form('validate')){
			 return;
		 }
		 $.ajax({
	            url: form.attr("action"),
	            cache: false, //cache的作用就是第一次请求完毕之后，如果再次去请求，可以直接从缓存里面读取而不是再到服务器端读取。
	            type: 'post',//提交的方式
	            data: form.serialize(),
	            async:false,//async默认的设置值为true，这种情况为异步方式，就是说当ajax发送请求后，在等待server端返回的这个过程中，前台会继续执行ajax块后面的脚本
	            success: function(result) {
	            	var data = eval( "(" + result + ")" );
	            	if(data.state==0){
	            		$.messager.alert("提示", data.content, "info");
	        			return;
	            	}else if(data.state==1){
	            		$.messager.alert("提示", data.content, "info");
	            		$("#dlg").dialog("close");
	            		$("#form2").form("clear");
	            		initDatagrid();
	            	}
	            }
	        });
	}
	
	function saveRoleMenu(){
		 $.post('<%=request.getContextPath()%>/app/system/grantRole',{
			 menuIds : getCheckedId(),
			 roleId : $("#roleMenuId").val()
			 },function(data){
					var data = eval( "(" + data + ")" );
					if(data.state==1){
						$.messager.alert("提示", data.content, "info");
	            		$("#grantDialog").dialog("close");
	            		initDatagrid();
					}else{
						$.messager.alert("提示", data.content, "info");
	        			return;
					}
			});
	}
	
    function clearForm() {
        $("#form").form("clear");
    }
    
    function reloadGrid() {
        var searchRoleName = $("#searchRoleName").val();
        $("#roleGrid").datagrid("load",{
        	roleName: searchRoleName
        });
    }
    
    function stateMap(val,row,index){
    	if(val == "0"){
    		return "<span style='color: red'>禁用</span>";
    	}else if(val=='1'){
    		return "<span style='color: blue '>正常</span>";
    	}
    }
	function colseDlg() {
		$("#dlg").dialog("close");
		$("#form2").form("clear");
	}
	function colseGrantDlg() {
		$("#grantDialog").dialog("close");
	}
	function getCheckedId() {
		var zTree = $.fn.zTree.getZTreeObj("tree");
		var checkCount = zTree.getCheckedNodes(true);
		var array = new Array();
		for (var i=0;i<checkCount.length; i++){
			array.push(checkCount[i].id);
		}
		return array.toString();
	}
</script>
</html>