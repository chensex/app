<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>登录</title>
<jsp:include page="app/base.jsp" />
<script type="text/javascript">
	$(function(){
		$("#denglu").click(function(){
			$.post('<%=request.getContextPath()%>/system/login',{loginName:$("#loginName").val(),password:$("#password").val()},
					function(data){
					var data = eval( "(" + data + ")" );
					if(data.state==1){
						window.location.href = "<%=request.getContextPath()%>/system/main";
					} else {
						alert(data.content);
						$("#tip").html(data.content);
					}
				});
			});
	});
</script>
<style type="text/css">
body {
	background-image: url("../images/back_ground.jpg");
	background-repeat: no-repeat;
	background-size: 100%;
}

.mycenter {
	margin-top: 100px;
	margin-left: auto;
	margin-right: auto;
	height: 350px;
	width: 500px;
	padding: 5%;
	padding-left: 5%;
	padding-right: 5%;
}

.mycenter mysign {
	width: 440px;
}

.mycenter input, checkbox, button {
	margin-top: 2%;
	margin-left: 10%;
	margin-right: 10%;
}

.mycheckbox {
	margin-top: 10px;
	margin-left: 40px;
	margin-bottom: 10px;
	height: 10px;
}
</style>
<title>Signin Template for Bootstrap</title>
<link href="resources/css/signin.css" rel="stylesheet">
</head>
<body>
	<form id="form1">
		<div class="mycenter">
			<div class="mysign">
				<div class="col-lg-11 text-center text-info">
					<h2>请登录</h2>
				</div>
				<div class="col-lg-10">
					<input type="text" class="form-control" id="loginName"
						name="loginName" placeholder="请输入账户名" required autofocus />
				</div>
				<div class="col-lg-10"></div>
				<div class="col-lg-10">
					<input type="password" class="form-control" id="password"
						name="password" placeholder="请输入密码" required autofocus />
				</div>
				<div class="col-lg-10"></div>
				<div class="col-lg-10">
					<button type="button" class="btn btn-success col-lg-12" id="denglu">登录</button>
				</div>
			</div>
		</div>
	</form>
</body>
</html>