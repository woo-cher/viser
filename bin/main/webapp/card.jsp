<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="viser.card.*"%>

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
<title>card</title>
<link href="/stylesheets/card.css?v=1" rel="stylesheet" type="text/css">
<%@ include file="./commons/top.jspf"%>
</head>
<body>

	<div id="leftmenu">
		<%@ include file="./commons/left_sidemenu.jspf"%>
	</div>

	<div id="container">
		<div id="top">
			<div id="top_header">JSP_card_LIST</div>
			<div id="mini-menu">
				<button type="button" href="#" id="btn btn-primary">검색</button>
				<button type="button" href="#" id="btn btn-primary">필터</button>
				<button type="button" href="#" id="btn btn-primary">초대</button>
				<button type="button" href="#" id="btn btn-primary">알림</button>
				<button type="button" href="#" id="btn btn-primary">구독</button>
				<button type="button" href="#" id="btn btn-primary">EXIT</button>
			</div>
		</div>

		<div id="card-container">
			<div id="card-header">
				<table border="1px" cellpadding="0" cellspacing="0" align="center">
					<tr height="30">
						<!-- card LIST -->
						<td align="center" width="100">NUM</td>
						<td align="center" width="600">SUBJECT</td>
						<td align="center" width="150">USER</td>
						<td align="center" width="150">DATE</td>
						<td align="center" width="100">READCOUNT</td>
					</tr>

		<%
			if(list.size() > 0){
				for(int i=0 ; i < list.size(); i++){
					Card card = (Card)list.get(i); // LIST를 BoradBean 타입으로 변환
		%>

					<tr height="50">
						<td align="center"><%=card.getNum()%></td>
						<td>
							<%if(card.getRe_lev()!=0){ %> 
							<%for(int a=0;a<=card.getRe_lev()*2;a++){ %>
							&nbsp; <%} %> 
						
							<%}else{ %>
							<%} %> 
						<a href="/card/viewcard?num=<%=card.getNum()%>"> 
						<%=card.getSubject()%>
						</a>
						</td>
						<td align="center"><%=card.getUserId() %></td>
						<td align="center"><%=card.getDate() %></td>
						<td align="center"><%=card.getReadcnt() %></td>
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
			
			<form name="serach" action = "/card/Searchlist" method ="post">
            <select name="keyField">
                <option value="0"> -- 선택 --</option>
                <option value="userId">아이디</option>
                <option value="SubJect">제목</option>
                <option value="Content">내용</option>  
            </select>
            <input type="text" name="keyWord" />
            <input type="submit" value="검색" onclick="/card/Searchlist" />
            </form>
            
					<tr height="70">
						<!-- card PAGING -->
						<td colspan="7" align="center">
							<%if(nowpage<=1){ %> [PREV]&nbsp; 
							
							<%}else{ %> 
							<a href="/card/cardlist?page=<%=nowpage-1 %>">[PREV]</a>&nbsp; 
							<%} %>

						<%for(int a=startpage;a<=endpage;a++){
							if(a==nowpage){%> [<%=a %>] 
						
							<%}else{ %> 
							<a href="/card/cardlist?page=<%=a %>">[<%=a%>]
							</a>&nbsp; <%} %> <%} %> <%if(nowpage>=maxpage){ %> [NEXT] <%}
							else{ %> 
							<a	href="/card/cardlist?page=<%=nowpage+1 %>">[NEXT]</a> 
							<%}%>
							</td>
				</table>
			</div>
		</div>

		<div id="card-footer">
           <button id="card-button" onclick="location.href='/card/createcardForm'">글쓰기</button>
		</div>
	</div>
<%@ include file="./commons/chat.jspf"%>
</body>
</html>