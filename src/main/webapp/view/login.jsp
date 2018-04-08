<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>登录</title>
<jsp:include page="app/base.jsp"/>
<script type="text/javascript">
	$(function(){
		$("#denglu").click(function(){
			$.post('<%=request.getContextPath()%>/system/login',{loginName:$("#loginName").val(),password:$("#password").val()},
					function(data){
					var data = eval( "(" + data + ")" );
					if(data.state==1){
						window.location.href = "<%=request.getContextPath()%>/system/main";
					}else{
						$("#tip").html(data.content);
					}
			});
		});
	});
</script>
<title>Signin Template for Bootstrap</title>
<link href="resources/css/signin.css" rel="stylesheet">
</head>
<body class="text-center">
    <form class="form-signin" id="form1">
      <img class="mb-4" src="https://getbootstrap.com/assets/brand/bootstrap-solid.svg" alt="" width="72" height="72">
      <h1 class="h3 mb-3 font-weight-normal">请登录</h1>
      <label for="inputEmail" class="sr-only">登录名</label>
      <input type="text" id="loginName" name="loginName" class="form-control" placeholder="登录名" required autofocus>
      <label for="inputPassword" class="sr-only">密码</label>
      <input type="password" id="password" name="password" class="form-control" placeholder="密码" required>
      <input type="button" class="btn btn-lg btn-primary btn-block" id="denglu" value="登录">
      <p class="mt-5 mb-3 text-muted">&copy; 2017-2018</p>
    </form>
  </body>
</html>