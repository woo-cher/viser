<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="stylesheets/index.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="header">
		<div id="logo">로고</div>
		<div id="user">유저</div>
	</div>

	<div id="container">
		<div id="title">
			Beyond yourself with
			<p>"runtime"</p>
		</div>
		<div id="content">
			<form id="form-sign" action="/users/login" method="post">
				<label class="control-label" for="userId">사용자 아이디</label> <input
					type="text" name="userId" value="" /> <label class="control-label"
					for="userId">비밀번호</label> <input type="password" name="password"
					value="" />
			</form>
		</div>
		<div id="button">
			<div class="controls">
				<button type="button" href="#" class="btn btn-primary">Sign
					up</button>
				<button type="submit" class="btn btn-primary">Sign in</button>
			</div>
		</div>
	</div>
</body>
</html>