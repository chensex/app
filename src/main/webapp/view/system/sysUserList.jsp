<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="../easyui.jsp"%>
    <title>用户管理</title>
</head>
<body>

	<div class="easyui-layout" fit="true">
		<!-- 查询条件 -->
		<div region="north" style="height:125px;padding:5px" border="false">
			<div class="easyui-panel" title="用户信息" style="padding:10px 0px 0px 20px" fit="true">
				
				<form id="form">
					<label>登录帐号：</label>
					<input class="easyui-textbox" type="text" id="searchLoginName" name="searchLoginName" />
					<label>用户名称：</label>
					<input class="easyui-textbox" type="text" id="searchUserName" name="searchUserName" /> 
					<p>
						<a id="query" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="reloadGrid()">查询</a> 
						<a id="clear" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-clear" onclick="clearForm()">重置</a> 
					</p>
				</form>
				
			</div>
		</div>
		
		<!-- 查询结果 -->
		<div region="center" border="false" style="padding:0px 5px">
			<div id="userGrid"  fit="true"></div>
		</div>
	</div>

	<!-- 添加、修改dialog -->
	<div id="dlg" class="easyui-dialog" style="width:500px;height:400px;display:none;" closed="true" buttons="#dlg-buttons">
		<form id="form2" action="<%=request.getContextPath()%>/system/saveMenu" method="post">
			<input type="hidden" id="userId" name="userId"/>
			<input type="hidden" id="roleIds" name="roleIds"/>
			<input type="hidden" id="saveState" name="saveState"/>
			<table class="t2" align="center" style="width: 94%;height: 100%;margin: 15px;">
				<tr>
					<th  width="100px;"><label for="loginName">登录帐号：</label></th>
					<td><input class="easyui-textbox" style="width: 200px" type="text" data-options="required:true" id="loginName" name="loginName"/></td>
				</tr>
				<tr>
					<th  width="100px;"><label for="password">密码：</label></th>
					<td><input class="easyui-textbox" style="width: 200px" type="password" id="password" name="password" data-options="required:true"/></td>
				</tr>
				<tr>
					<th  width="100px;"><label for="userName">用户名：</label></th>
					<td><input class="easyui-textbox" style="width: 200px" type="text" id="userName" name="userName"/></td>
				</tr>
				<tr>
					<th  width="100px;"><label for="phone">电话：</label></th>
					<td><input class="easyui-textbox" style="width: 200px" type="text" id="phone" name="phone"/></td>
				</tr>
				<tr>
					<th><label for="sex">性别：</label></th>
					<td>
						<select class="easyui-combobox" id="sex" name="sex" style="width: 200px;" data-options="required:true" editable="false">
								<option value="1" selected="selected">男</option>
								<option value="0">女</option>
						</select>
					</td>
				</tr>
				<tr>
					<th><label for="state">状态：</label></th>
					<td>
						<select class="easyui-combobox" id="state" name="state" style="width: 200px;" data-options="required:true" editable="false">
								<option value="1">正常</option>
								<option value="0">注销</option>
								<option value="2">锁定</option>
								<option value="3">禁用</option>
						</select>
					</td>
				</tr>
				<tr>
					<th><label for="roleIds">角色：</label></th>
					<td>
						<div id="roles"></div>
					</td>
				</tr>
			</table>
		</form>
		<div id="dlg-buttons">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok"  id="save" onclick="saveUser()">保存</a> 
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" id="close" onclick="colseDlg();">关闭</a>
		</div>
	</div>
	<!-- 添加、修改dialog 结束-->
</body>
<script>

	$(function() {
		initDatagrid();//初始化，dataGrid
	});
	
	function initDatagrid(){
		$("#userGrid").datagrid({
	    	title:"用户信息列表",
	    	emptyMsg : "没有记录",
	        url: "<%=request.getContextPath()%>/system/sysUserList",
	        method: "POST",
	        rownumbers : true,
	        singleSelect: true,
	        pagination: true,
	        pageSize: 10,
	        singleSelect : true,
	        pageList: [10, 20, 50],
	        idField: "userId",
	        fit:true,
	        columns: [[
	            { field: "loginName", title: "登录帐号", width: "16%", align: "center"},
	            { field: "userName", title: "用户名", width: "16%", align: "center"},
	            { field: "phone", title: "电话", width: "16%", align: "center"},
	            { field: "sex", title: "性别", width: "16%", align: "center",formatter:sexMap},
	            { field: "createTime", title: "注册时间", width: "16%", align: "center"},
	            { field: "state", title: "状态", width: "16%", align: "center",formatter:stateMap}
	        ]],
	        toolbar : [{ iconCls : "icon-add",
				text : "新增",
				handler : function() {
					addUser();
				}
			}, "-", {
				iconCls : "icon-edit",
				text : "编辑",
				handler : function() {
					editUser();
				}
			},"-", {
				iconCls : "icon-edit",
				text : "错误次数清零",
				handler : function() {
					clearUser();
				}
			},"-"]
	    });
	}
	
	/**
	 * 弹出新增窗口
	 */
	function addUser() {
		$("#form2").form("clear");
		$("#saveState").val("add");
		$("#form2").attr("action", "<%=request.getContextPath()%>/system/saveUser");
		$("#dlg").dialog({ modal : true,top : $(document).scrollTop() + ($(window).height() - 430) * 0.5 });//弹出框位置，居中，430表示弹出框的高度
		$.post("<%=request.getContextPath()%>/system/getAddSysUserById",function(data) {
			var str = "";
			for(var i =0;i<data.length;i++){
				var obj = data[i];
				str +="<input id='"+obj.roleId+"' value='"+obj.roleId+"' type='checkbox' name='roleId'/><label for='"+obj.roleId+"'>"+obj.roleName+"</label>";
			}
			$("#roles").html(str);
		}, "json");
		$("#dlg").dialog("open").dialog("setTitle", "添加用户信息");
	}
	
	/**
	 * 弹出编辑窗口
	 */
	function editUser() {
		var row = $("#userGrid").datagrid("getSelected");
		if (!row) {
			$.messager.alert("提示", "请选择一条数据！", "info");
			return;
		}
		$("#form2").form("clear");
		$("#saveState").val("update");
		$("#dlg").dialog({ modal : true, top : $(document).scrollTop() + ($(window).height() - 380) * 0.5 });
		$.post("<%=request.getContextPath()%>/system/getSysUserById", {
			userId : row.userId
		}, function(data) {
			$("#userId").val(data.userId);
			$("#loginName").textbox("setValue", data.loginName);
			$("#userName").textbox("setValue", data.userName);
			$("#password").textbox("setValue", data.password);
			$("#phone").textbox("setValue", data.phone);
			$("#sex").combobox("setValue", data.sex);
			$("#state").combobox("setValue", data.state);
			var str = "";
			for(var i =0;i<data.roles.length;i++){
				var obj = data.roles[i];
				var checked = "";
				for(var j = 0;j<data.useRoles.length;j++){
					var useObj = data.useRoles[j];
					if(obj.roleId == useObj.roleId){
						checked = "checked='true'";
						break;
					}
				}
				str +="<input id='"+obj.roleId+"' value='"+obj.roleId+"' "+checked+" type='checkbox' name='roleId'/><label for='"+obj.roleId+"'>"+obj.roleName+"</label>";
			}
			$("#roles").html(str);
		}, "json");
		$("#dlg").dialog("open").dialog("setTitle", "修改用户信息");
	}
	
	function saveUser(){
		 var form = $("#form2");
		 
		 if(!form.form('validate')){
			 return;
		 }
		 
		 var ids = getIds();
		 if(ids==null || ids==""){
			 $.messager.alert("提示","角色选择错误！", "info");
			 return ;
		 }
		 
		 $("#roleIds").val(ids);
		 var url = form.attr("action");
		 var saveState = $("#saveState").val();
		 if(saveState=="add"){
		 }else if(saveState=="update"){
			 form.attr("action", "<%=request.getContextPath()%>/system/updateUser");
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
	
	function clearUser(){
		var row = $("#userGrid").datagrid("getSelected");
		if (!row) {
			$.messager.alert("提示", "请选择一条数据！", "info");
			return;
		}
		 $.post('<%=request.getContextPath()%>/system/clearUser',{
			 userId:row.userId
			 },function(data){
					var data = eval( "(" + data + ")" );
					if(data.state==1){
						$.messager.alert("提示", data.content, "info");
					}else{
						$.messager.alert("提示", "更新失败", "info");
					}
			});
	}
	
    function clearForm() {
        $("#form").form("clear");
    }
    
    function reloadGrid() {
        var searchLoginName = $("#searchLoginName").val();
        var searchUserName = $("#searchUserName").val();
        $("#userGrid").datagrid("load",{
        	loginName: searchLoginName,
        	userName: searchUserName
        });
    }
    
    function sexMap(val,row,index){
    	if(val == "0"){
    		return "女";
    	}else{
    		return "男";
    	}
    }
    function stateMap(val,row,index){
    	if(val == "0"){
    		return "<span style='color: red'>注销</span>";
    	}else if(val=='1'){
    		return "<span style='color: blue '>正常</span>";
    	}else if(val=='2'){
    		return "<span style='color: red '>锁定</span>";
    	}else if(val=='3'){
    		return "<span style='color: red '>禁用</span>";
    	}
    }
	function colseDlg() {
		$("#dlg").dialog("close");
		$("#form2").form("clear");
	}
	function getIds(){
		var roleIds = document.getElementsByName('roleId');
		 var array = new Array();
		 for(var i = 0;i < roleIds.length; i++){
			 if(roleIds[i].checked == true){
				 array.push(roleIds[i].value);
			 }
		 }
		return array.toString();
	}
</script>
</html>