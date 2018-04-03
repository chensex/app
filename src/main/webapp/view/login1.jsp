<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
<jsp:include page="easyui.jsp"/>
<script type="text/javascript">
	$(function(){
		$("#denglu").click(function(){
			$.post('<%=request.getContextPath()%>/app/system/login',{loginName:$("#loginName").val(),password:$("#password").val()},
					function(data){
					var data = eval( "(" + data + ")" );
					if(data.state==1){
						$("#form1").submit();
					}else{
						$("#tip").html(data.content);
					}
			});
		});
	});
</script>
<style type="text/css">
</style>
</head>
<body background="images/beijing_111.jpg">
<form action="<%=request.getContextPath()%>/app/system/main" method="post" id="form1">
<h2 align="center"><font style="color: blue;" face="楷体">欢迎使用<font style="color: blue;" size="10"><!-- 个人信息管理系统 --></font></font></h2>
<h3 align="right"><span style="color: red;" id="localtime"></span></h3>
<script type="text/javascript">
function showLocale(objD)
{
	var str,colorhead,colorfoot;
	var yy = objD.getYear();
	if(yy<1900) yy = yy+1900;
	var MM = objD.getMonth()+1;
	if(MM<10) MM = '0' + MM;
	var dd = objD.getDate();
	if(dd<10) dd = '0' + dd;
	var hh = objD.getHours();
	if(hh<10) hh = '0' + hh;
	var mm = objD.getMinutes();
	if(mm<10) mm = '0' + mm;
	var ss = objD.getSeconds();
	if(ss<10) ss = '0' + ss;
	var ww = objD.getDay();
	if  ( ww==0 )  colorhead="<font color=\"#0099FF\">";
	if  ( ww > 0 && ww < 6 )  colorhead="<font color=\"#0099FF\">";
	if  ( ww==6 )  colorhead="<font color=\"#0099FF\">";
	if  (ww==0)  ww="星期日";
	if  (ww==1)  ww="星期一";
	if  (ww==2)  ww="星期二";
	if  (ww==3)  ww="星期三";
	if  (ww==4)  ww="星期四";
	if  (ww==5)  ww="星期五";
	if  (ww==6)  ww="星期六";
	colorfoot="</font>";
	str = colorhead + yy + "-" + MM + "-" + dd + " " + hh + ":" + mm + ":" + ss + "  " + ww + colorfoot;
	return(str);
}
function tick()
{
	var today;
	today = new Date();
	document.getElementById("localtime").innerHTML = showLocale(today);
	window.setTimeout("tick()", 1000);
}
tick();
</script>

<hr color="#6AE9EF" />

<br></br>
<br></br>
<br></br>
<table  width="800" height="200" align="center" cellspacing="10" class="q">
<tr>
  <td>
    <table width="350" height="200" align="right">
    <tr>
      <td><table width="1" align="right" class="w">
        <tr>
          <td>
         
          
          </td>
        </tr>
      </table>
</td>
      </tr>
    </table>
  </td>
  <td>&nbsp;</td>
  <td><table width="350" height="200" align="left" cellpadding="0" cellspacing="0" bordercolor="#AEF6F5" class="ta">
    <tr>
      <td class="STYLE17"><div align="left">登录名</div></td>
      <td class="STYLE17"><div align="left">
        <input class="as" type="text" id="loginName" name="loginName"/>
        </div></td>
      </tr>
    <tr>
      <td class="STYLE17"><div align="left">密    码</div></td>
      <td class="STYLE17"><div align="left">
        <input class="as" type="password" id="password" name="password"/>
      </div></td>
      </tr>
    <tr>
      <td class="STYLE17"><div align="left"></div></td>
      <td>
        <span style="color:red;" id="tip"></span>
        </td>
      </tr>
    <tr>
      <td class="STYLE17"><div align="left"></div></td>
      <td class="STYLE17"><div align="left">
        <input class="as3" type="button" name="tijiao" id="denglu" value="&nbsp;登&nbsp;录&nbsp;"/>
        <input class="as3" type="reset" name="chongzhi"  value="&nbsp;取&nbsp;消&nbsp;"/>
       <!--  &nbsp;  &nbsp;&nbsp;&nbsp;<font face="楷体" ><a class="as3" id="addDig">&nbsp;注&nbsp;册&nbsp;</a></font> --></div>
        </td>
      </tr>
  </table></td>
</tr>

</table>
</form>
</body>
</html>