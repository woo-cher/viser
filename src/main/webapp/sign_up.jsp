<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>회원가입</title>
<link href="stylesheets/sign_up.css" rel="stylesheet" type="text/css">
</head>
<body>

	<div class="signup-container">
		<div class="signup-header">
			<h1>회원가입</h1>
		</div>
		<form id="form-sign" action="/users/create" method="post">
		<div class="first">
			<label class="" for="name">이름</label> 
			<input type="text" name="name"value="" />
		</div>

		<div>
			<label class="" for="age">나이</label> 
			<input type="text" name="age"value="" />
		</div>

		<div>
			<label class="" for="gender">성별</label> 
			<input type="checkbox" name="male" value="" />남
			<input type="checkbox" name="female" value="" />여
		</div>

		<div>
			<label class="" for="userId">Id</label> 
			<input type="text" name="userId" value="" />
			<button type="submit" class="Id_Check">ID 중복체크</button>

		</div>

		<div>
			<label class="" for="password">Password</label> 
			<input type="password" name="password" value="" />
		</div>
		
		<div>
			<label class="" for="password2">Password-check</label>
			<input type="password" name="password2" value="" />
		</div>

		<div class="signup-footer">
			<button type="submit" class="sign_up_button">가입하기</button>
		</div>
		</form>
	</div>

</body>
</html>