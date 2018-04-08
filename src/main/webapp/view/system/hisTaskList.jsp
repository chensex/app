<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="../easyui.jsp"%>
    <title>历史任务管理</title>
    <script type="text/javascript">
    
    </script>
</head>
<body>

	<div class="easyui-layout" fit="true">
		<!-- 查询条件 -->
		<div region="north" style="height:125px;padding:5px" border="false">
			<div class="easyui-panel" title="历史任务管理" style="padding:10px 0px 0px 20px" fit="true">
				
				<form id="form">
					<label>任务名称：</label>
					<input class="easyui-textbox" type="text" id="searchProcessName" name="searchProcessName" /> 
					<p>
						<a id="clear" href="<%=request.getContextPath()%>/system/start" class="easyui-linkbutton" iconCls="icon-query">查询</a> 
					</p>
				</form>
				
			</div>
		</div>
		
		<!-- 查询结果 -->
		<div region="center" border="false" style="padding:0px 5px">
			<div id="hisTaskGrid"  fit="true"></div>
		</div>
	</div>
</body>
<script type="text/javascript">
$(function() {
	initDatagrid();//初始化，dataGrid
});

function initDatagrid(){
	$("#hisTaskGrid").datagrid({
    	title:"历史任务列表",
    	emptyMsg : "没有记录",
        url: "<%=request.getContextPath()%>/system/hisTaskList",
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
            { field: "name", title: "任务名称", width: "20%", align: "center"},
            { field: "processInstanceId", title: "流程实例ID", width: "20%", align: "center"},
            { field: "startTime", title: "开始时间", width: "15%", align: "center"},
            { field: "endTime", title: "完成时间", width: "15%", align: "center"},
            { field: "userName", title: "处理人", width: "15%", align: "center"},
            { field: "_edit", title: "操作", width: "15%", align: "center",formatter:edit}
        ]]
    });
}
function edit(val,row,index){
	var showUrl = "<a href=\"<%=request.getContextPath()%>/system/showTask?processInstanceId="+row.processInstanceId+"\">流程追踪  </a>\t";
	//var showUrl = "<a href=\"javascript:void(0)\" onclick=\"javascript:showTask('"+row.processInstanceId+"')\">流程追踪</a>";
	return showUrl;
}
<%-- function showTask(processInstanceId){
	$.messager.confirm("提示", "您确定要放款？", function (r) {
		if (r) {
			$.messager.progress({ 
				title: '汇付放款', 
			    msg: '正在放款，请稍等...', 
			    text: 'Loading.......' 
			});
	    	$.post("<%=request.getContextPath()%>/app/system/showTask",{processInstanceId:processInstanceId},function(data){
	    		$.messager.progress('close');
	    		$.messager.alert("提示",data, "info");
	    		//reloadGrid();
	    	});
		}
    }); 
} --%>
<%-- function showTask(processInstanceId) {
	$("#dlg").dialog({ modal : true, top : $(document).scrollTop() + ($(window).height() - 380) * 0.5 });
	$.post("<%=request.getContextPath()%>/app/system/showTask", {
		processInstanceId : processInstanceId
	}, function(data) {
		$("#dlg").html(data);
	});
	$("#dlg").dialog("open").dialog("setTitle", "修改角色信息");
} --%>
</script>
</html>