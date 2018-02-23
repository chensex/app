<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="../easyui.jsp"%>
    <title>流程定义管理</title>
    <script type="text/javascript">
    
    </script>
</head>
<body>

	<div class="easyui-layout" fit="true">
		<!-- 查询条件 -->
		<div region="north" style="height:125px;padding:5px" border="false">
			<div class="easyui-panel" title="流程定义管理" style="padding:10px 0px 0px 20px" fit="true">
				
				<form id="form">
					<label>流程名称：</label>
					<input class="easyui-textbox" type="text" id="searchProcessName" name="searchProcessName" /> 
					<p>
						<%-- <a id="clear" href="<%=request.getContextPath()%>/app/system/start" class="easyui-linkbutton" iconCls="icon-clear">创建流程</a> 
						<a id="clear" href="<%=request.getContextPath()%>/app/system/test" class="easyui-linkbutton" iconCls="icon-clear">测试</a>  --%>
					</p>
				</form>
				
			</div>
		</div>
		
		<!-- 查询结果 -->
		<div region="center" border="false" style="padding:0px 5px">
			<div id="processGrid"  fit="true"></div>
		</div>
	</div>
	
	<!-- 添加、修改dialog -->
	<div id="dlg" class="easyui-dialog" style="width:500px;height:400px;display:none;" closed="true" buttons="#dlg-buttons">
		<form id="form2" action="<%=request.getContextPath()%>/app/systask/saveTask" method="post">
			<input type="hidden" id="taskId" name="taskId"/>
			<input type="hidden" id="saveState" name="saveState"/>
			<table class="t2" align="center" style="width: 94%;height: 100%;margin: 15px;">
				<tr>
					<th  width="100px;"><label for="taskName">任务名称：</label></th>
					<td><input class="easyui-textbox" style="width: 200px" type="text" data-options="required:true" id="taskName" name="taskName"/></td>
				</tr>
				<tr>
					<th><label for="state">任务状态：</label></th>
					<td>
						<select class="easyui-combobox" id="taskState" name="taskState" style="width: 200px;" data-options="required:true">
							<option value="ENABLE">启用</option>
							<option value="DISABLE">停用</option>
						</select>
					</td>
				</tr>
				<tr>
					<th><label for="loginPassword">时间表达式：</label></th>
					<td><input class="easyui-textbox" style="width: 200px" type="text" data-options="required:true" id="timeExpression" name="timeExpression"/></td>
				</tr>
				<tr>
					<th><label for="loginPassword">任务类：</label></th>
					<td><input class="easyui-textbox" style="width: 200px" type="text" data-options="required:true" id="taskService" name="taskService"/></td>
				</tr>
				<tr>
					<th><label for="loginPassword">任务方法：</label></th>
					<td><input class="easyui-textbox" style="width: 200px" type="text" data-options="required:true" id="taskMethod" name="taskMethod"/></td>
				</tr>
				<tr>	
					<th><label for="loginPasswordAgain">组名：</label></th>
					<td><input class="easyui-textbox" style="width: 200px" type="text" data-options="required:true" id="groupName" name="groupName"/></td>
				</tr>
			</table>
		</form>
		<div id="dlg-buttons">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok"  id="save" onclick="saveTask()">保存</a> 
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" id="close" onclick="colseDlg();">关闭</a>
		</div>
	</div>
	<!-- 添加、修改dialog 结束-->	
	
</body>
<script type="text/javascript">
$(function() {
	initDatagrid();//初始化，dataGrid
});

function initDatagrid(){
	$("#processGrid").datagrid({
    	title:"流程定义列表",
    	emptyMsg : "没有记录",
        url: "<%=request.getContextPath()%>/app/system/sysProcessList",
        method: "POST",
        rownumbers : true,
        singleSelect: true,
        pagination: true,
        pageSize: 10,
        singleSelect : true,
        pageList: [10, 20, 50],
        idField: "id",
        fit:true,
        columns: [[
            { field: "id", title: "流程定义ID", width: "12%", align: "center"},
            { field: "key", title: "流程定义KEY", width: "12%", align: "center"},
            { field: "name", title: "流程定义名称", width: "12%", align: "center"},
            { field: "version", title: "流程定义版本", width: "12%", align: "center"},
            { field: "deploymentId", title: "部署ID", width: "12%", align: "center"},
            { field: "resourceName", title: "文件名称", width: "12%", align: "center"},
            { field: "diagramResourceName", title: "图片名称", width: "12%", align: "center"},
            { field: "_edit", title: "操作", width: "12%", align: "center",formatter:edit}
        ]]
    });
}
function edit(val,row,index){
	var deleteUrl = "<a href=\"javascript:void(0)\" onclick=\"javascript:deleteProcess('"+row.id+"')\">删除  </a>";
	var startUrl = "<a href=\"<%=request.getContextPath()%>/app/system/getStartProcess?processDefinitionId="+row.id+"\">启动  </a>\t";
	//var startUrl = "<a href=\"javascript:void(0)\" onclick=\"javascript:startProcess('"+row.id+"','"+row.key+"')\">  启动</a>";
	return deleteUrl + startUrl;
}
function deleteProcess(processDefinitionId,processId) {
	$.post("<%=request.getContextPath()%>/app/system/deleteProcess", {
		processDefinitionId : processDefinitionId,
		processId  : processId
	}, function(data) {
		alert(data);
	}, "json");
}
function startProcess(processDefinitionId,processKey){
	$.post("<%=request.getContextPath()%>/app/system/startProcess", {
		processDefinitionId : processDefinitionId,
		processKey  : processKey
	}, function(data) {
		var data = eval( "(" + data + ")" );
		if(data.state==1){
			$.messager.alert("提示", data.content, "info");
			initDatagrid();
		}else{
			$.messager.alert("提示", "启动失败", "info");
		}
	}, "json");
}
</script>
</html>