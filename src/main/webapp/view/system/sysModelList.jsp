<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="../easyui.jsp"%>
    <title>流程模版管理</title>
    <script type="text/javascript">
    
    </script>
</head>
<body>

	<div class="easyui-layout" fit="true">
		<!-- 查询条件 -->
		<div region="north" style="height:125px;padding:5px" border="false">
			<div class="easyui-panel" title="流程模版管理" style="padding:10px 0px 0px 20px" fit="true">
				
				<form id="form">
					<label>流程名称：</label>
					<input class="easyui-textbox" type="text" id="searchModelName" name="searchModelName" /> 
					<p>
						<a id="clear" href="<%=request.getContextPath()%>/system/addModel" class="easyui-linkbutton" iconCls="icon-add">创建模型</a> 
					</p>
				</form>
				
			</div>
		</div>
		
		<!-- 查询结果 -->
		<div region="center" border="false" style="padding:0px 5px">
			<div id="modelGrid"  fit="true"></div>
		</div>
	</div>
</body>
<script type="text/javascript">
$(function() {
	initDatagrid();//初始化，dataGrid
});

function initDatagrid(){
	$("#modelGrid").datagrid({
    	title:"模版信息列表",
    	emptyMsg : "没有记录",
        url: "<%=request.getContextPath()%>/system/sysModelList",
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
            { field: "id", title: "模型ID", width: "14%", align: "center"},
            { field: "key", title: "模型KEY", width: "14%", align: "center"},
            { field: "name", title: "模型名称", width: "14%", align: "center"},
            { field: "createTime", title: "创建时间", width: "14%", align: "center"},
            { field: "lastUpdateTime", title: "最后更新时间", width: "14%", align: "center"},
            { field: "version", title: "版本", width: "14%", align: "center"},
            { field: "_edit", title: "操作", width: "14%", align: "center",formatter:edit}
        ]]
    });
}
function edit(val,row,index){
	var updateUrl = "<a href=\"<%=request.getContextPath()%>/modeler.html?modelId="+row.id+"\">编辑  </a>\t";
	
	var deployUrl = "<a href=\"javascript:void(0)\" onclick=\"javascript:deploy('"+row.id+"')\">部署</a>";
	return updateUrl + deployUrl;
}
function updateModel(modelId) {
	window.open("<%=request.getContextPath()%>/modeler.html?modelId="+modelId);
}
function deploy(modelId){
	$.post("<%=request.getContextPath()%>/system/deploy", {
		modelId  : modelId
	}, function(data) {
		var data = eval( "(" + data + ")" );
		if(data.state==1){
			$.messager.alert("提示", data.content, "info");
			initDatagrid();
		}else{
			$.messager.alert("提示", "发布失败", "info");
		}
	}, "json");
}
</script>
</html>