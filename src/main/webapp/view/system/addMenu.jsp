<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="../easyui.jsp"%>
    <title>菜单管理</title>
    <script type="text/javascript">
    $(function() {
    });
    </script>
</head>
<body>

	<div class="easyui-layout" fit="true">
		<div region="center" border="false" style="padding:5px 5px">
			<div align="right">
			<a id="query" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveMenu()">保存</a> 
			<a id="clear" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addMenu()">添加菜单</a>
			</div>
			<div class="easyui-panel" title="菜单管理" fit="true" style="padding-top: 50px;">
				<form id="form" action="<%=request.getContextPath()%>/system/saveMenu" method="post">
					<input type="hidden" name="menuId" id="menuId" value="${sysMenu.menuId}">
					<input type="hidden" name="parentId" id="parentId" value="${sysMenu.parentId}">
					<table class="t2" align="center" style="width: 100%">
						<tr>
							<td style="width: 30%;" align="right"><label for="parentMenuName">父级菜单：</label></td>
							<td style="width: 70%;" align="left"><input class="easyui-textbox" readonly="true" type="text" maxlength="20" id="parentMenuName" name="parentMenuName" value="${sysMenu.parentMenuName}" style="width: 40%;"/></td>
						</tr>
						<tr>
							<td style="width: 30%;" align="right"><label for="menuName">菜单名称：</label></td>
							<td style="width: 70%;" align="left"><input class="easyui-textbox" type="text" data-options="required:true" maxlength="20" value="${sysMenu.menuName}" id="menuName" name="menuName" style="width: 40%;"/></td>
						</tr>
						<tr>
							<td style="width: 30%;" align="right"><label for="menuUrl">菜单URL：</label></td>
							<td style="width: 70%;" align="left"><input class="easyui-textbox" value="${sysMenu.menuUrl}" type="text" maxlength="20" id="menuUrl" name="menuUrl" style="width: 40%;"/></td>
						</tr>
						<tr>
							<td style="width: 30%;" align="right"><label for="level">级别：</label></td>
							<td style="width: 70%;" align="left"><input class="easyui-numberbox" readonly="true" type="text" value="${sysMenu.level}" data-options="required:true" maxlength="20" id="level" name="level" style="width: 40%;"/></td>
						</tr>
						<tr>
							<td style="width: 30%;" align="right"><label for="sort">排序号：</label></td>
							<td style="width: 70%;" align="left"><input class="easyui-numberbox" type="text" value="${sysMenu.sort}" data-options="required:true" maxlength="20" id="sort" name="sort" style="width: 40%;"/></td>
						</tr>
						<tr>
							<td style="width: 30%;" align="right"><label for="menuType">菜单类别：</label></td>
							<td style="width: 70%;" align="left">
							<select class="easyui-combobox" id="menuType" name="menuType" style="width: 200px;" data-options="required:true" editable="false">
								<option value="0" <c:if test="${sysMenu.menuType==0}">selected="selected"</c:if> >菜单</option>
								<option value="1" <c:if test="${sysMenu.menuType==1}">selected="selected"</c:if> >功能</option>
							</select>
							</td>
						</tr>
						<tr>
							<td style="width: 30%;" align="right"><label for="state">状态：</label></td>
							<td style="width: 70%;" align="left">
							<select class="easyui-combobox" id="state" name="state" style="width: 200px;" data-options="required:true" editable="false">
								<option value="1" <c:if test="${sysMenu.state==1}">selected="selected"</c:if> >正常</option>
								<option value="0" <c:if test="${sysMenu.state==0}">selected="selected"</c:if> >禁用</option>
							</select>
							</td>
						</tr>
						<tr>
							<td style="width: 30%;" align="right"><label for="isOpen">是否打开节点：</label></td>
							<td style="width: 70%;" align="left">
							<select class="easyui-combobox" id="isOpen" name="isOpen" style="width: 200px;" data-options="required:true" editable="false">
								<option value="true" <c:if test="${sysMenu.isOpen==true}">selected="selected"</c:if> >是</option>
								<option value="false" <c:if test="${sysMenu.isOpen==false}">selected="selected"</c:if> >否</option>
							</select>
							</td>
						</tr>
						
					</table>
				</form>
			</div>
		</div>
	</div>
</body>
<script>
function addMenu(){
	$("#parentId").val($("#menuId").val());
	$("#parentMenuName").textbox("setValue", $("#menuName").val());
	$("#level").textbox("setValue", parseInt($("#level").textbox("getValue"))+1);
	$("#menuId").val(null);
	$("#menuName").textbox("setValue", null);
	$("#menuUrl").textbox("setValue", null);
	$("#sort").textbox("setValue", null);
	$("#menuType").combobox("setValue", 0);
	$("#state").combobox("setValue", 1);
	$("#isOpen").combobox("setValue", "true");
}
function saveMenu(){
	var form = $("#form");
	 if(!form.form('validate')){
		 return;
	 }
	 var parentMenuName = $("#parentMenuName").textbox("getValue");
	 if(parentMenuName == null || parentMenuName==''){
		 $.messager.alert("提示", "根菜单不能修改", "info");
		 return ;
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
            		window.location.reload();
            	}else if(data.state==1){
            		$.messager.alert("提示", data.content, "info");
            		window.parent.fun();
             		window.location.reload();
             	}
            }
        });
}
</script>
</html>