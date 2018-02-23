<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="../easyui.jsp"%>
    <title>菜单管理</title>
</head>
<body>

	<div class="easyui-layout" fit="true">
		<!-- 查询条件 -->
		<div region="north" style="height:125px;padding:5px" border="false">
			<div class="easyui-panel" title="菜单信息" style="padding:10px 0px 0px 20px" fit="true">
				
				<form id="form">
					<label>菜单名称：</label>
					<input class="easyui-textbox" type="text" id="searchMenuName" name="searchMenuName" /> 
					<p>
						<a id="query" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="reloadGrid()">查询</a> 
						<a id="clear" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-clear" onclick="clearForm()">重置</a> 
					</p>
				</form>
				
			</div>
		</div>
		
		<!-- 查询结果 -->
		<div region="center" border="false" style="padding:0px 5px">
			<div id="menuGrid"  fit="true"></div>
		</div>
	</div>

	<!-- 添加、修改dialog -->
	<div id="dlg" class="easyui-dialog" style="width:500px;height:400px;display:none;" closed="true" buttons="#dlg-buttons">
		<form id="form2" action="<%=request.getContextPath()%>/app/system/saveMenu" method="post">
			<input type="hidden" id="menuId" name="menuId"/>
			<input type="hidden" id="saveState" name="saveState"/>
			<table class="t2" align="center" style="width: 94%;height: 100%;margin: 15px;">
				<tr>
					<th  width="100px;"><label for="taskName">菜单名称：</label></th>
					<td><input class="easyui-textbox" style="width: 200px" type="text" data-options="required:true" id="menuName" name="menuName"/></td>
				</tr>
				<tr>
					<th  width="100px;"><label for="taskName">菜单地址：</label></th>
					<td><input class="easyui-textbox" style="width: 200px" type="text" id="menuUrl" name="menuUrl"/></td>
				</tr>
				<tr>
					<th><label for="state">上级菜单：</label></th>
					<td>
						<select class="easyui-combobox" id="parentId" name="parentId" style="width: 200px;" data-options="required:true" editable="false">
							<option value="0" >根菜单</option>
							<c:forEach items="${menuList}" var="menu">
								<option value="${menu.menuId}" >${menu.menuName}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th><label for="state">是否打开节点：</label></th>
					<td>
						<select class="easyui-combobox" id="isOpen" name="isOpen" style="width: 200px;" data-options="required:true" editable="false">
								<option value="false" selected="selected">否</option>
								<option value="true">是</option>
						</select>
					</td>
				</tr>
				<tr>
					<th><label for="state">状态：</label></th>
					<td>
						<select class="easyui-combobox" id="state" name="state" style="width: 200px;" data-options="required:true" editable="false">
								<option value="1" selected="selected">有效</option>
								<option value="0">无效</option>
						</select>
					</td>
				</tr>
			</table>
		</form>
		<div id="dlg-buttons">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok"  id="save" onclick="saveMenu()">保存</a> 
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
		$("#menuGrid").datagrid({
	    	title:"任务信息列表",
	    	emptyMsg : "没有记录",
	        url: "<%=request.getContextPath()%>/app/system/sysMenuList",
	        method: "POST",
	        rownumbers : true,
	        singleSelect: true,
	        pagination: true,
	        pageSize: 10,
	        singleSelect : true,
	        pageList: [10, 20, 50],
	        idField: "menuId",
	        fit:true,
	        columns: [[
	            { field: "menuName", title: "菜单名称", width: "25%", align: "center"},
	            { field: "menuUrl", title: "菜单地址", width: "25%", align: "center"},
	            { field: "isOpen", title: "是否打开", width: "25%", align: "center",formatter:isOpenMap},
	            { field: "state", title: "状态", width: "25%", align: "center",formatter:stateMap}
	        ]],
	        toolbar : [{ iconCls : "icon-add",
				text : "新增",
				handler : function() {
					addMenu();
				}
			}, "-", {
				iconCls : "icon-edit",
				text : "编辑",
				handler : function() {
					editMenu();
				}
			},"-"]
	    });
	}
	
	/**
	 * 弹出新增窗口
	 */
	function addMenu() {
		$("#form2").form("clear");
		$("#saveState").val("add");
		$("#form2").attr("action", "<%=request.getContextPath()%>/app/system/saveMenu");
		$("#dlg").dialog({ 
			modal : true,
			top : $(document).scrollTop() + ($(window).height() - 430) * 0.5 
			});//弹出框位置，居中，430表示弹出框的高度
		$("#dlg").dialog("open").dialog("setTitle", "添加菜单信息");
	}
	
	/**
	 * 弹出编辑窗口
	 */
	function editMenu() {
		var row = $("#menuGrid").datagrid("getSelected");
		if (!row) {
			$.messager.alert("提示", "请选择一条数据！", "info");
			return;
		}
		$("#form2").form("clear");
		$("#saveState").val("update");
		$("#dlg").dialog({ modal : true, top : $(document).scrollTop() + ($(window).height() - 380) * 0.5 });
		$.post("<%=request.getContextPath()%>/app/system/getSysMenuById", {
			menuId : row.menuId
		}, function(data) {
			$("#menuId").val(data.menuId);
			$("#menuName").textbox("setValue", data.menuName);
			$("#menuUrl").textbox("setValue", data.menuUrl);
			$("#parentId").combobox("setValue", data.parentId);
			$("#isOpen").combobox("setValue", data.isOpen);
			$("#state").combobox("setValue", data.state);
		}, "json");
		$("#dlg").dialog("open").dialog("setTitle", "修改菜单信息");
	}
	
	function saveMenu(){
		var form = $("#form2");
		 if(!form.form('validate')){
			 return;
		 }
		 var url = form.attr("action");
		 var saveState = $("#saveState").val();
		 if(saveState=="add"){
		 }else if(saveState=="update"){
			 form.attr("action", "<%=request.getContextPath()%>/app/system/updateMenu");
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
	
	function updateMenu(){
		var form = $("#form2");
		form.attr("action", "<%=request.getContextPath()%>/app/system/updateMenu");
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
	
    function clearForm() {
        $("#form").form("clear");
    }
    
    function reloadGrid() {
        var searchMenuName = $("#searchMenuName").val();
        $("#menuGrid").datagrid("load",{
        	menuName: searchMenuName
        });
    }
    
    function isOpenMap(val,row,index){
    	if(val == "false"){
    		return "<span style='color: blue'>否</span>";
    	}else{
    		return "<span style='color: red '>是</span>";
    	}
    }
    function stateMap(val,row,index){
    	if(val == "1"){
    		return "<span style='color: blue'>有效</span>";
    	}else{
    		return "<span style='color: red '>无效</span>";
    	}
    }
	function colseDlg() {
		$("#dlg").dialog("close");
		$("#form2").form("clear");
	}
</script>
</html>