<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>개인정보 수정 페이지</title>
			<link href="stylesheets/sign_up.css" rel="stylesheet" type="text/css">
	</head>
	
<body>

	<div class="signup-container">
		<div class="signup-header">
			<h1>개인정보수정</h1>
		</div>
		
		<form id="form-sign" action="/users/update" method="post">
			<input type="hidden" name ="userId" value="${user.userId }" />
			<input type="hidden" name ="name" value="${user.name }" />
			<div class="first">
				<label class="control-label" for="name">이름</label> 
					${user.name}
			</div>

			<div>
				<label class="control-label" for="age">나이</label>
				<input type="text" name="age" value="" />
			</div>

			<div>
				<label class="control-label" for="gender">성별</label> 
				<input type="checkbox" name="male" value="" />남
				<input type="checkbox" name="female"value="" />여
			</div>

			<div>
				<label class="control-label" for="userId">Id</label>
					${user.userId}
			</div>

			<div>
				<label class="control-label" for="password">Password
				</label> <input type="password" name="password" value="${user.password}" />
			</div>

			<div>
				<label class="control-label" for="password2">Password-check</label> 
				<input type="password" name="password2" value="" />
			</div>

			<div class="signup-footer">
				<button type="submit" class="sign_up_button">수정하기</button>
			</div>
		</form>
	</div>

</body>
</html>