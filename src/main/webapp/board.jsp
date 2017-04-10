<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>board</title>
<link href="/stylesheets/board.css?v=1" rel="stylesheet" type="text/css">
<%@ include file="./commons/top.jspf"%>
</head>
<body>

	<div id="leftmenu">
		<%@ include file="./commons/left_sidemenu.jspf"%>
	</div>

	<div id="container">
		<div id="top">
			<div id="top_header">게시판</div>
			<div id="mini-menu">
				<button type="button" href="#" id="btn btn-primary">검색</button>
				<button type="button" href="#" id="btn btn-primary">필터</button>
				<button type="button" href="#" id="btn btn-primary">초대</button>
				<button type="button" href="#" id="btn btn-primary">알림</button>
				<button type="button" href="#" id="btn btn-primary">구독</button>
				<button type="button" href="#" id="btn btn-primary">EXIT</button>
			</div>
		</div>

		<div id="board-container">
			<div id="board-header">
				<table border="1" width="850px" cellpadding="0" cellspacing="0" align="center">
					<tr height="30">
						<td align="center" width="100">번 호</td>
						<td align="center" width="600">제 목</td>
						<td align="center" width="150">작성자</td>
						<td align="center" width="150">작성일</td>
						<td align="center" width="100">조 회</td>
					</tr>
				</table>
			</div>
			
			<div id="board-list" >
				<p align="center"> 게시판 항목 </p>
			</div>
		</div>
		<div id ="board-footer">
			<button id = "board-button" onclick= "location.href='/board/createBoardForm'" >글쓰기</button>
		</div>
	</div>

	<div id="chat-container">
		<div id="chat-user">채팅유저 List</div>
		<div id="chat-list">채팅 List</div>
		<div id="chat-input">채팅 Input</div>
	</div>


</body>
</html>