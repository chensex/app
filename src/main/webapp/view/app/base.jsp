<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<base href="<%=basePath%>">
<!-- 新 Bootstrap4 核心 CSS 文件 -->
<link rel="stylesheet" href="/resources/bootstrap-4.0.0/dist/css/bootstrap.min.css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="/resources/jquery/core/jquery-1.9.1.min.js"></script>
<!-- popper.min.js 用于弹窗、提示、下拉菜单 -->
<script src="/resources/bootstrap-4.0.0/assets/js/vendor/popper.min.js"></script>
<!-- 最新的 Bootstrap4 核心 JavaScript 文件 -->
<script src="/resources/bootstrap-4.0.0/dist/js/bootstrap.min.js"></script>