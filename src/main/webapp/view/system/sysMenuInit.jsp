<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="../easyui.jsp"%>
    <%@ include file="../app/ztree.jsp"%>
    <title>菜单管理</title>
<style type="text/css">
.ztree li span.button.add{margin-right:2px;background-position:-144px 0;vertical-align:top;*vertical-align:middle}
</style>
</head>
<body>
	<div class="easyui-layout" fit="true">
		<!-- 新闻tree -->
		<div data-options="region:'west',title:'菜单栏',split:true" border="false" style="width:300px;overflow:auto;padding: 8px">
			<ul id="treeDemo" class="ztree" style="width: 94%;height: 95%;overflow:auto;"></ul>
		</div>
		<!-- 查询列表 -->
		<div region="center" border="false" style="overflow:auto;padding: 8px 0px 8px 8px">
			<iframe id="testIframe" name="testIframe" frameborder="0" style="width: 100%;height: 99%" scrolling="auto" src=""></iframe>
		</div>
	</div>
	
	
	<!-- 添加、修改dialog -->
	<div id="dlg" class="easyui-dialog" style="width:550px;height:350px;display:none;" closed="true" buttons="#dlg-buttons">
		<form id="form2" action="<%=request.getContextPath()%>/publicity/newsTypeAdd" method="post">
			<input id="parentId" name="parentId"  type="hidden" />
			<input id="newsTypeId" name="newsTypeId"  type="hidden" />
			<table class="t2" align="center" style="width: 94%;height: 100%;margin: 15px;">
				<tr>
					<th style="width: 100px;"><span class="red">*&nbsp;</span>类型名称：</th>
					<td><input id="typeName" name="typeName" class="easyui-textbox width200" type="text" data-options="required:true"/></td>
				</tr>
				<tr>
					<th><span class="red">*&nbsp;</span>等级：</th>
					<td><input id="level" name="level" class="easyui-textbox width200" type="text" data-options="required:true,editable:false"/></td>
				</tr>
				<tr>
					<th><span class="red">*&nbsp;</span>排序：</th>
					<td><input id="seq" name="seq" class="easyui-textbox width200" type="text" data-options="required:true"/></td>
				</tr>
				<tr>
					<th><span class="red">*&nbsp;</span>是否列表：</th>
					<td>
						<select class="easyui-combobox" name="isCat" id="isCat" data-options="required:true,editable:false" style="width: 150px">
							<option value="0" selected="selected">栏目</option>
							<option value="1">列表</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<th><span class="red">*&nbsp;</span>是否启用：</th>
					<td>
						<select class="easyui-combobox" name="isUse" id="isUse" data-options="required:true,editable:false" style="width: 150px">
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<td colspan="2" style="text-align: center;">
						<span class="discribe" style="font-size: 12px;">栏目：一个新闻或一个公告，列表：多个栏目以列表显示。</span>
					</td>
				</tr>
			</table>
		</form>
		
		<div id="dlg-buttons">
			<a id="save" href="#" class="easyui-linkbutton" iconCls="icon-ok"  id="edit" onclick="submitForm('form2','save','saveType','')">保存</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="colseDlg();">关闭</a>
		</div>
	</div>
	<!-- 添加、修改dialog 结束-->
</body>
<script type="text/javascript">
	var setting = {
		view: {
			addHoverDom: false,
/* 			addHoverDom: addHoverDom, */
			removeHoverDom: removeHoverDom,
			selectedMulti: false,
			showIcon : true,
			dblClickExpand: true
		},
		edit: {
			enable: true,
			editNameSelectAll: true,
			showRemoveBtn: false,
			showRenameBtn: false
			/* showRemoveBtn: showRemoveBtn,
			showRenameBtn: showRenameBtn */
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeDrag: beforeDrag,
			beforeEditName: beforeEditName,
			beforeRemove: beforeRemove,
			beforeClick : beforeClick
		}
	};

	var zNodes;
	function beforeDrag(treeId, treeNodes) {
		return false;
	}
	function beforeEditName(treeId, treeNode) {
		editNewsType(treeNode);
		return false;
	}
	function beforeRemove(treeId, treeNode) {
		deleteNewsType(treeNode);
		return false;
	}
	function beforeClick(treeId, treeNode, clickFlag) {
		/* if(treeNode.id==0){
			return true;
		} */
		$("#testIframe").attr("src","<%=request.getContextPath()%>/system/addMenu?menuId="+treeNode.id);
	}
	function onNodeClick(e,treeId, treeNode) {  
	    var zTree = $.fn.zTree.getZTreeObj("treeDemo");  
	    zTree.expandNode(treeNode);  
	}  
	function showRemoveBtn(treeId, treeNode) {
		if (treeNode.id == 0) {
			return false;
		} else if(treeNode.isParent){
			return false
		}else{
			return true
		}
		
	}
	function showRenameBtn(treeId, treeNode) {
		if (treeNode.id != 0) {
			return true;
		}
		return false
	}

	var newCount = 1;
	function addHoverDom(treeId, treeNode) {
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0)
			return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId+ "' title='添加' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_" + treeNode.tId);
		if (btn)
			btn.bind("click", function() {
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				newAddNews(treeNode);
				$("#parentId").val(treeNode.id);
				return false;
			});
	};
	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_" + treeNode.tId).unbind().remove();
	};

	
	$(function() {
		$.post("<%=request.getContextPath()%>/system/sysMenuInit",
				function(data) {
				$.fn.zTree.init($("#treeDemo"), setting, data);
				zNodes = data;
			}, "json");
	});
	/**
	 * 弹出新增窗口
	 */
	function newAddMenu(treeNode) {
		modalBack();
		$("#level").textbox("setValue",treeNode.level+1);
		$("#dlg").dialog("open").dialog("setTitle", " [ <span style='color:blue'>"+treeNode.name+"</span> ] 添加子节点");
	}
	//打开编辑窗口，开启遮盖层
    function modalBack(){
    	$("#form2").form("clear");
		$("#dlg").dialog({ modal : true, top : $(document).scrollTop() + ($(window).height() - 380) * 0.5 });
    }
    function colseDlg() {
		$("#dlg").dialog("close");
		$("#form2").form("clear");
	}
    
    function editNewsType(node){
    	modalBack();
    	$("#dlg").dialog("open").dialog("setTitle", "修改节点 [ <span style='color:blue'>"+node.name+"</span> ] ");
    	$("#parentId").val(node.pId);
    	$("#newsTypeId").val(node.id);
    	$("#typeName").textbox("setValue",node.name);
    	$("#level").textbox("setValue",node.level);
    	$("#seq").textbox("setValue",node.seq);
    	$("#isCat").combobox("setValue",node.isCat);
    	$("#isUse").combobox("setValue",node.isUse);
    }
    
	function saveType(data) {
		if (data.success) {
			$.messager.alert("提示", "操作成功！", "info");
			//重新加载数据
			$.post("<%=request.getContextPath()%>/system/sysMenuInit", {},
				function(data) {
				$.fn.zTree.init($("#treeDemo"), setting, data);
			}, "json");
		} else {
			$.messager.alert("错误", data.errorMessage, "error");
		}
		colseDlg();
	}
	
	function fun(){
		//重新加载数据
		$.post("<%=request.getContextPath()%>/system/sysMenuInit", {},
			function(data) {
			$.fn.zTree.init($("#treeDemo"), setting, data);
		}, "json");
	}
	
   /**
   *删除
   */
   function deleteNewsType(node){
	   $.messager.confirm("提示信息", "确定要删除 [ <span style='color:red'>"+node.name+"</span> ] 吗?", function(r) {
			if (!r) {
				return;
			}
		  	$.post("<%=request.getContextPath()%>/publicity/deleteNewsType", {
		  		id : node.id
		  	},
				function(data) {
					if (data.success) {
						$.post("<%=request.getContextPath()%>/publicity/newsInit", {},
							function(data) {
							$.fn.zTree.init($("#treeDemo"), setting, data);
						}, "json");
						$.messager.show({ title : "提示", msg : "删除成功！", timeout : 5000, showType : "fade" });
					} else {
						$.messager.alert("错误", data.errorMessage, "error");
					}
				}, "json");
		});
   }
</script>
</html>