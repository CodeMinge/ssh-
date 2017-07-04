<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>学生信息管理系统登录</title>

<script type="text/javascript">
	function resetValue() {
		document.getElementById("userName").value = "";
		document.getElementById("password").value = "";
	}

	function loadimage() {
		document.getElementById("randImage").src = "image.jsp?" + Math.random();
	}
</script>

</head>
<body>
	<div align="center" style="padding-top: 50px;">
		<form action="login" method="post">
			<table width="740" height="500" background="images/login.jpg">
				<tr height="180">
					<td colspan="4"></td>
				</tr>
				<tr height="10">
					<td width="40%"></td>
					<td width="10%">用户名：</td>
					<td><input type="text" value="${user.userName }"
						name="user.userName" id="userName" /></td>
					<td width="30%"></td>
				</tr>
				<tr height="10">
					<td width="40%"></td>
					<td width="10%">密 码：</td>
					<td><input type="password" value="${user.password }"
						name="user.password" id="password" /></td>
					<td width="30%"></td>
				</tr>
				<tr height="10">
					<td width="40%"></td>
					<td width="10%">验证码：</td>
					<td>
						<input type="text" value="${imageCode }" name="imageCode" id="imageCode" size="10"/>
						<img onclick="javascript:loadimage();"  title="换一张试试" name="randImage" id="randImage" src="image.jsp" width="60" height="20" border="1" align="absmiddle">
					</td>
					<td width="30%"></td>
			    </tr>
			    <tr height="10">
					<td width="40%"></td>
					<td width="10%"><input type="submit" value="登录"/></td>
					<td><input type="button" value="重置" onclick="resetValue()"/></td>
					<td width="30%"></td>
			    </tr>
				<tr height="10">
					<td width="40%"></td>
					<td colspan="3"><font color="red">${error }</font></td>
				</tr>
				<tr>
					<td></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>