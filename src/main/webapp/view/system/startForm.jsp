<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="../easyui.jsp"%>
    <title>流程表单</title>
    <script type="text/javascript">
    
    </script>
</head>
<body>

	<div class="easyui-layout" fit="true">
		<div region="center" border="false" style="padding:5px 5px">
			<div class="easyui-panel" title="流程表单>> 新增流程" fit="true" style="padding-top: 50px;">
				<form id="form" action="<%=request.getContextPath()%>/app/system/create" method="post">
					
					<table class="t2" align="center">
						<c:forEach items="${formProperty}" var="pro">
							<tr>
								<th width="130px;"><label for="${pro.id}">${pro.name}：</label></th>
								<td width="300px;"><input class="easyui-textbox" type="text" data-options="required:true" maxlength="20" id="${pro.id}" name="${pro.id}"/></td>
							</tr>
						</c:forEach>
						<tr>
							<td colspan="4" style="text-align: center;">
								<a id="save" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="submit()">创建</a>
					        	<a id="back" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" onclick="back()">返回</a>
							</td>
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
	window.location.href="<%=request.getContextPath()%>/app/system/sysModelList";
}
</script>
</html>