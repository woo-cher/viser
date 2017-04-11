<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="viser.board.*"%>

<%
	List list=(List)request.getAttribute("list");
	int count=((Integer)request.getAttribute("count")).intValue();
	int nowpage=((Integer)request.getAttribute("page")).intValue();
	int maxpage=((Integer)request.getAttribute("maxpage")).intValue();
	int startpage=((Integer)request.getAttribute("startpage")).intValue();
	int endpage=((Integer)request.getAttribute("endpage")).intValue();
%>

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
			<div id="top_header">JSP_BOARD_LIST</div>
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
				<table border="1px" cellpadding="0" cellspacing="0" align="center">
					<tr height="30">
						<!-- BOARD LIST -->
						<td align="center" width="100">NUM</td>
						<td align="center" width="600">SUBJECT</td>
						<td align="center" width="150">USER</td>
						<td align="center" width="150">DATE</td>
						<td align="center" width="100">READCOUNT</td>
					</tr>

		<%
			if(list.size() > 0){
				for(int i=0 ; i < list.size(); i++){
					Board board = (Board)list.get(i); // LIST를 BoradBean 타입으로 변환
		%>

					<tr height="50">
						<td align="center"><%=board.getNum()%></td>
						<td>
							<%if(board.getRe_lev()!=0){ %> 
							<%for(int a=0;a<=board.getRe_lev()*2;a++){ %>
							&nbsp; <%} %> 
						
							<%}else{ %>
							<%} %> 
						<a href="./BoardDetailAction.do?num=<%=board.getNum()%>"> 
						<%=board.getSubject()%>
						</a>
						</td>
						<td align="center"><%=board.getUserId() %></td>
						<td align="center"><%=board.getDate() %></td>
						<td align="center"><%=board.getReadcount() %></td>
					</tr>
			<%	
				}
			}else{	
			%>
					<tr height="100">
						<td colspan="5" align="center">NO DATA.</td>
					</tr>
					<%
			}
			%>
					<tr height="70">
						<!-- BOARD PAGING -->
						<td colspan="7" align="center">
							<%if(nowpage<=1){ %> [PREV]&nbsp; <%}else{ %> <a
							href="./Board.jsp?page=<%=nowpage-1 %>">[PREV]</a>&nbsp; <%} %>

							<%for(int a=startpage;a<=endpage;a++){
						if(a==nowpage){%> [<%=a %>] 
						
						<%}else{ %> <a
							href="./Board.jsp?page=<%=a %>">[<%=a %>]
						</a>&nbsp; <%} %> <%} %> <%if(nowpage>=maxpage){ %> [NEXT] <%}
						else{ %> 
						<a	href="./Board.jsp?page=<%=nowpage+1 %>">[NEXT]</a> <%}%>
						</td>
				</table>
			</div>
		</div>

		<div id="board-footer">
			<button id="board-button"
				onclick="location.href='/board/createBoardForm'">글쓰기</button>
		</div>
	</div>

	<div id="chat-container">
		<div id="chat-user">채팅유저 List</div>
		<div id="chat-list">채팅 List</div>
		<div id="chat-input">채팅 Input</div>
	</div>
</body>
</html>