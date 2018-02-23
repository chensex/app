<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="../easyui.jsp"%>
    <title>任务管理</title>
</head>
<body>

	<div class="easyui-layout" fit="true">
		<!-- 查询条件 -->
		<div region="north" style="height:125px;padding:5px" border="false">
			<div class="easyui-panel" title="任务信息" style="padding:10px 0px 0px 20px" fit="true">
				
				<form id="form">
					<label>任务名称：</label>
					<input class="easyui-textbox" type="text" id="searchTaskName" name="searchTaskName" /> 
					<p>
						<a id="query" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="reloadGrid()">查询</a> 
						<a id="clear" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-clear" onclick="clearForm()">重置</a> 
					</p>
				</form>
				
			</div>
		</div>
		
		<!-- 查询结果 -->
		<div region="center" border="false" style="padding:0px 5px">
			<div id="taskGrid"  fit="true"></div>
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
<script>

	$(function() {
		initDatagrid();//初始化，dataGrid
		$('#taskService').combobox({
	        url:'<%=request.getContextPath()%>/app/system/taskClass',
	        editable:false,
	        valueField:'className',
	        textField:'value',
	        onSelect:function(record){
	            $('#taskMethod').combobox({
	            	editable:false,
	                url:'<%=request.getContextPath()%>/app/system/taskMethod?className='+record.className,
	                valueField:'methodName',
	                textField:'value'
	            }).combobox('clear');
	        }
	    });
	});
	
	function initDatagrid(){
		$("#taskGrid").datagrid({
	    	title:"任务信息列表",
	    	emptyMsg : "没有记录",
	        url: "<%=request.getContextPath()%>/app/system/sysTaskList",
	        method: "POST",
	        rownumbers : true,
	        singleSelect: true,
	        pagination: true,
	        pageSize: 10,
	        singleSelect : true,
	        pageList: [10, 20, 50],
	        idField: "taskId",
	        fit:true,
	        columns: [[
	            { field: "taskName", title: "任务名称", width: "16%", align: "center"},
	            { field: "taskState", title: "任务状态", width: "16%", align: "center", sortable:"true",formatter:taskStateMap},
	            { field: "runState", title: "运行状态", width: "16%", align: "center", sortable:"true",formatter:runStateMap},
	            { field: "timeExpression", title: "时间表达式", width: "16%", align: "center"},
	            { field: "createTime", title: "创建时间", width: "16%", align: "center"},
	            { field: "groupName", title: "组名", width: "16%", align: "center"}
	        ]],
	        toolbar : [{ iconCls : "icon-add",
				text : "新增",
				handler : function() {
					addTask();
				}
			}, "-", {
				iconCls : "icon-edit",
				text : "编辑",
				handler : function() {
					editTask();
				}
			},"-"]
	    });
	}
	
	/**
	 * 弹出新增窗口
	 */
	function addTask() {
		$("#form2").form("clear");
		$("#saveState").val("add");
		$("#form2").attr("action", "<%=request.getContextPath()%>/app/system/saveTask");
		$("#dlg").dialog({ 
			modal : true,
			top : $(document).scrollTop() + ($(window).height() - 430) * 0.5 
			});//弹出框位置，居中，430表示弹出框的高度
		$("#dlg").dialog("open").dialog("setTitle", "添加任务信息");
	}
	
	/**
	 * 弹出编辑窗口
	 */
	function editTask() {
		var row = $("#taskGrid").datagrid("getSelected");
		if (!row) {
			$.messager.alert("提示", "请选择一条数据！", "info");
			return;
		}
		$("#form2").form("clear");
		$("#saveState").val("update");
		$("#dlg").dialog({ modal : true, top : $(document).scrollTop() + ($(window).height() - 380) * 0.5 });
		$.post("<%=request.getContextPath()%>/app/system/selectSysTaskById", {
			taskId : row.taskId
		}, function(data) {
			$("#taskId").val(data.taskId);
			$("#taskName").textbox("setValue", data.taskName);
			$("#taskState").combobox("setValue", data.taskState);
			$("#timeExpression").textbox("setValue", data.timeExpression);
			$("#taskService").combobox("setValue", data.taskService);
			$("#taskMethod").combobox("setValue", data.taskMethod);
			$("#groupName").textbox("setValue", data.groupName);
		}, "json");
		$("#dlg").dialog("open").dialog("setTitle", "修改任务信息");
	}
	
	function saveTask(){
		var form = $("#form2");
		 if(!form.form('validate')){
			 return;
		 }
		 var url = form.attr("action");
		 var saveState = $("#saveState").val();
		 if(saveState=="add"){
		 }else if(saveState=="update"){
			 form.attr("action", "<%=request.getContextPath()%>/app/system/updateTask");
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
	
	function updateTask(){
		var form = $("#form2");
		form.attr("action", "<%=request.getContextPath()%>/app/system/updateTask");
		 if(!form.form('validate')){
			 return;
		 }
		 alert(form.attr("action"));
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
	
    function clearForm() {
        $("#form").form("clear");
    }
    
    function reloadGrid() {
        var searchTaskName = $("#searchTaskName").val();
        $("#taskGrid").datagrid("load",{
        	taskName: searchTaskName
        });
    }
    
    function taskStateMap(val,row,index){
    	if(val == "ENABLE"){
    		return "<span style='color: blue'>启用</span>";
    	}else{
    		return "<span style='color: red '>停用</span>";
    	}
    }
    function runStateMap(val,row,index){
    	if(val == "RUN"){
    		return "<span style='color: blue'>运行中</span>";
    	}else{
    		return "<span style='color: red '>等待中</span>";
    	}
    }
	function colseDlg() {
		$("#dlg").dialog("close");
		$("#form2").form("clear");
	}
</script>
</html>