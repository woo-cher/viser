<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="./commons/top.jspf"%>
<title>Main.jsp</title>
</head>
<body>
<div class = "background">
	<!-- Button trigger modal -->
	<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">Launch demo modal </button>


	
	<div id=main_content>
		<div>ProJect 목록 JSP</div>
		<a href="/card/cardlist"> 게시판 가기 - yong's space</a>
			</p>
	</div>
	
				<div id='project_user'>
				<iframe src="/user.jsp" height="200" width="278" name=user>
					<p>Your browser does not support iframes.
				</iframe>
			</div>
			<div id='project_chat'>
				<iframe src="/chat.jsp" height="747" width="278" name=chat>
					<p>Your brower does not support iframes.</p>
				</iframe>
			</div>
	
	</div>
	<%@ include file="./commons/bottom.jspf"%>
</body>
</html>
