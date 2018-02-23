<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="../easyui.jsp"%>
    <title>待签收任务管理</title>
    <script type="text/javascript">
    
    </script>
</head>
<body>

	<div class="easyui-layout" fit="true">
		<!-- 查询条件 -->
		<div region="north" style="height:125px;padding:5px" border="false">
			<div class="easyui-panel" title="待签收任务管理" style="padding:10px 0px 0px 20px" fit="true">
				
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
			<div id="claimProcessGrid"  fit="true"></div>
		</div>
	</div>
</body>
<script type="text/javascript">
$(function() {
	initDatagrid();//初始化，dataGrid
});

function initDatagrid(){
	$("#claimProcessGrid").datagrid({
    	title:"待签收列表",
    	emptyMsg : "没有记录",
        url: "<%=request.getContextPath()%>/app/system/claimProcessList",
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
			{ field: "id", title: "ID", width: "11%", align: "center"},
			{ field: "executionId", title: "执行ID", width: "11%", align: "center"},
			{ field: "processInstanceId", title: "流程实例ID", width: "11%", align: "center"},
			{ field: "processDefinitionId", title: "流程定义ID", width: "11%", align: "center"},
            { field: "taskDefinitionKey", title: "流程定义KEY", width: "11%", align: "center"},
            { field: "name", title: "任务名称", width: "11%", align: "center"},
            { field: "assignee", title: "执行人", width: "11%", align: "center"},
            { field: "createTime", title: "创建时间", width: "11%", align: "center"},
            { field: "_edit", title: "操作", width: "11%", align: "center",formatter:edit}
        ]]
    });
}
function edit(val,row,index){
	var completeUrl = "<a href=\"javascript:void(0)\" onclick=\"javascript:completeTask('"+row.id+"')\">完成任务  </a>";
	var showUrl = "<a href=\"<%=request.getContextPath()%>/app/system/showTask?processInstanceId="+row.processInstanceId+"\">流程追踪  </a>\t";
	return completeUrl + showUrl;
}
function completeTask(taskId){
	$.post("<%=request.getContextPath()%>/app/system/completeTask", {
		taskId  : taskId
	}, function(data) {
		var data = eval( "(" + data + ")" );
		if(data.state==1){
			$.messager.alert("提示", data.content, "info");
			initDatagrid();
		}else{
			$.messager.alert("提示", "完成失败", "info");
		}
	});
}
</script>
</html>