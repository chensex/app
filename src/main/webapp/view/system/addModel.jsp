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
			<div class="easyui-panel" title="流程模版关联>> 新增模版" fit="true" style="padding-top: 50px;">
				<form id="form" action="<%=request.getContextPath()%>/system/create" method="post">
					
					<table class="t2" align="center">
						<tr>
							<th width="130px;"><label for="modelName">模版名称：</label></th>
							<td width="300px;"><input class="easyui-textbox" type="text" data-options="required:true" maxlength="20" id="modelName" name="modelName"/></td>
						</tr>
						<tr>
							<th width="130px;"><label for="modelKey">模版KEY：</label></th>
							<td width="300px;"><input class="easyui-textbox" type="text" data-options="required:true" maxlength="10" id="modelKey" name="modelKey"/></td>
						</tr>
						<tr>
							<th width="130px;"><label for="modelDesc">模版描述：</label></th>
							<td width="300px;"><input class="easyui-textbox" type="text" data-options="required:true" maxlength="10" id="modelDesc" name="modelDesc"/></td>
						</tr>
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
	window.location.href="<%=request.getContextPath()%>/system/sysModelList";
}
</script>
</html>