<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="<%=request.getContextPath()%>/resources/jquery/core/jquery-1.9.1.min.js"></script>
<!-- popper.min.js 用于弹窗、提示、下拉菜单 -->
<script src="<%=request.getContextPath()%>/resources/bootstrap-4.0.0/assets/js/vendor/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/bootstrap-4.0.0/dist/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/metismenu/dist/metisMenu.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/bootstrap-4.0.0/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/metismenu/dist/metisMenu.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/font-awesome/css/font-awesome.min.css">