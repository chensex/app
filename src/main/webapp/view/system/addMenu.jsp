<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="../easyui.jsp"%>
    <title>新增模版</title>
    <script type="text/javascript">
    
    </script>
</head>
<body>

	<div class="easyui-layout" fit="true">
		<div region="center" border="false" style="padding:5px 5px">
			<div class="easyui-panel" title="菜单管理" fit="true" style="padding-top: 50px;">
				<form id="form" action="<%=request.getContextPath()%>/system/create" method="post">
					<table class="t2" align="center" style="width: 100%">
						<tr>
							<td style="width: 30%;" align="right"><label for="parentMenuName">父级菜单：</label></td>
							<td style="width: 70%;" align="left"><input class="easyui-textbox" readonly="true" type="text" data-options="required:true" maxlength="20" id="parentMenuName" name="parentMenuName" style="width: 70%;"/></td>
						</tr>
						<tr>
							<td style="width: 30%;" align="right"><label for="menuName">菜单名称：</label></td>
							<td style="width: 70%;" align="left"><input class="easyui-textbox" type="text" data-options="required:true" maxlength="20" id="modelName" name="modelName" style="width: 70%;"/></td>
						</tr>
						<tr>
							<td style="width: 30%;" align="right"><label for="menuUrl">菜单URL：</label></td>
							<td style="width: 70%;" align="left"><input class="easyui-textbox" type="text" data-options="required:true" maxlength="20" id="menuUrl" name="menuUrl" style="width: 70%;"/></td>
						</tr>
						<tr>
							<td style="width: 30%;" align="right"><label for="level">级别：</label></td>
							<td style="width: 70%;" align="left"><input class="easyui-numberbox" type="text" data-options="required:true" maxlength="20" id="level" name="level" style="width: 70%;"/></td>
						</tr>
						<tr>
							<td style="width: 30%;" align="right"><label for="sort">排序号：</label></td>
							<td style="width: 70%;" align="left"><input class="easyui-numberbox" type="text" data-options="required:true" maxlength="20" id="sort" name="sort" style="width: 70%;"/></td>
						</tr>
						<tr>
							<td style="width: 30%;" align="right"><label for="sort">状态：</label></td>
							<td style="width: 70%;" align="left"><input class="easyui-numberbox" type="text" data-options="required:true" maxlength="20" id="sort" name="sort" style="width: 70%;"/></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</body>
<script>
function submit(){
	var form = $("#form");
	if(!form.form('validate')){
        return;
    }
	form.submit();
}

function back(){
	window.location.href="<%=request.getContextPath()%>/system/sysModelList";
}
</script>
</html>