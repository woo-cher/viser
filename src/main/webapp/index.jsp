<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="stylesheets/index.css" rel="stylesheet" type="text/css">
<%@ include file="./commons/top.jspf" %>
</head>
<body>
	<div id="container">
		<div id="title">
			Beyond yourself with
			<p>"runtime"</p>
		</div>
		<div id="content">
			<form id="form-sign" action="/users/login" method="post">
				<label class="control-label" for="userId">사용자 아이디</label> 
				<input type="text" id="userId" value="" /> 
				<label class="control-label" for="userId">비밀번호</label> 
				<input type="password" id="password" value="" />
				<div id="button">
					<div class="controls">
						<button type="button" href="#" class="btn btn-primary">Sign up</button>
						<button type="submit" class="btn btn-primary">Sign in</button>
					</div>
				</div>
				<a href="#">
				<p>forget your ID/password ?</p>
				</a>
			</form>
		</div>
		
	</div>
</body>
</html>