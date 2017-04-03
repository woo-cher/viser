<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<%@ include file="./commons/top.jspf" %>
<head>
	<title>Insert title here</title>
	<link href="stylesheets/index.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<div id="title">
			Beyond yourself with
			<p>"runtime"</p>
		</div>
		<div id="content">
			<form id="form-sign" action="/users/login" method="post">
				<c:if test="${not empty errorMessage }">
				<div class="control-group">
				<label class="error">${errorMessage}</label>
				</div>
				</c:if>
				<div class="control-group">
				<label class="control-label" for="userId">사용자 아이디</label> 
				<input type="text" name="userId" value="" />
				</div> 
				<div class="control-group">
				<label class="control-label" for="password">비밀번호</label> 
				<input type="password" name="password" value="" />
				</div>
				<div id="button">
					<div class="controls">
						<button type="button" onclick="location.href='/sign_up.jsp' " class="btn btn-primary">Sign up</button>
						<button type="submit" class="btn btn-primary">Sign in</button>
					</div>
				</div>
				<a href="#">
				forget your ID/password ?
				</a>
			</form>
		</div>
		
	</div>
</body>
</html>