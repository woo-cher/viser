<<<<<<< HEAD
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
	
<%-- 					<div id="card-header">
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
								if (list.size() > 0) {
									for (int i = 0; i < list.size(); i++) {
										Card card = (Card) list.get(i); // LIST를 BoradBean 타입으로 변환
							%>

							<tr height="50">
								<td align="center"><%=card.getNum()%></td>
								<td>
									<%
										if (card.getRe_lev() != 0) {
									%> <%
 	for (int a = 0; a <= card.getRe_lev() * 2; a++) {
 %> &nbsp; <%
 	}
 %> <%
 	} else {
 %> <%
 	}
 %> <a id="viewcard_js"
									href="/card/viewcard?num=<%=card.getNum()%>&card_userId=<%=card.getUserId()%>">
										<%=card.getSubject()%>
								</a>
								</td>
								<td align="center"><%=card.getUserId()%></td>
								<td align="center"><%=card.getDate().toString()%></td>
								<td align="center"><%=card.getReadcnt()%></td>
							</tr>
							<%
								}
								} else {
							%>
							<tr height="100">
								<td colspan="5" align="center">NO DATA.</td>
							</tr>
							<%
								}
							%>
							<tr>
								<td colspan="5">
									<form name="serach" action="/card/Searchlist" method="post">
										<select name="keyField">
											<option value="0">-- 선택 --</option>
											<option value="userId">아이디</option>
											<option value="SubJect">제목</option>
											<option value="Content">내용</option>
										</select> <input type="text" name="keyWord" /> <input type="submit"
											value="검색" onclick="/card/Searchlist" />
									</form>

									<button type="button" class="btn btn-primary" id="card-button"
										onclick="location.href='/card/createcardForm'">글쓰기</button>
										
							</tr>
							<!-- 							<div class="row">
								<div class="col-lg-6">
									<div class="input-group">
										<div class="input-group-btn">
											<button type="button" class="btn btn-default dropdown-toggle"
												data-toggle="dropdown" aria-expanded="false">
												Action <span class="caret"></span>
											</button>
											<ul class="dropdown-menu" role="menu">
												<li><a href="#">Action</a></li>
												<li><a href="#">Another action</a></li>
												<li><a href="#">Something else here</a></li>
												<li class="divider"></li>
												<li><a href="#">Separated link</a></li>
											</ul>
										</div>
										/btn-group
										<input type="text" class="form-control" aria-label="...">
									</div>
									/input-group
								</div>
								/.col-lg-6
							</div> 환용 : 검색 디자인 지워도 됨.-->


							<tr height="70">
								<!-- card PAGING -->
								<td colspan="7" align="center">
									<%
										if (nowpage <= 1) {
									%> [PREV]&nbsp; <%
 	} else {
 %> <a href="/card/cardlist?page=<%=nowpage - 1%>">[PREV]</a>&nbsp; <%
 	}
 %> <%
 	for (int a = startpage; a <= endpage; a++) {
 		if (a == nowpage) {
 %> [<%=a%>] <%
 	} else {
 %> <a href="/card/cardlist?page=<%=a%>">[<%=a%>]
								</a>&nbsp; <%
 	}
 %> <%
 	}
 %> <%
 	if (nowpage >= maxpage) {
 %> [NEXT] <%
 	} else {
 %> <a href="/card/cardlist?page=<%=nowpage + 1%>">[NEXT]</a> <%
 	}
 %>
								</td>
							</tr>
						</table>
					</div> --%>


<!-- 


<a href="javascript:popupOpen2();"> 업로드할래?! </a>

<div id="wrap-sortable">
	<!--카드박스의 이름(수정)  
	<div id="card_wrap">
		<li class="ui-state-default" id="title-sortable">카드이름</li>
		<li class="ui-state-default" id="title-sortable">추가</li>
		<ul id="sortable1" class="connectedSortable">
			<!-- <li id="title-sortable" class="ui-state-default">이름</li>

			<li class="ui-state-default">Item 2</li>
			<li class="ui-state-default">Item 3</li>
			<li class="ui-state-default">Item 4</li>
			<li class="ui-state-default">Item 5</li>
		</ul>
	</div>
	<ul id="sortable1" class="connectedSortable">
		<li class="ui-state-highlight">Item 1</li>
		<li class="ui-state-highlight">Item 2</li>
		<li class="ui-state-highlight">Item 3</li>
		<li class="ui-state-highlight">Item 4</li>
		<li class="ui-state-highlight">Item 5</li>
	</ul>
	<ul id="sortable1" class="connectedSortable">
		<li class="ui-state-highlight">Item 5</li>
	</ul>
</div>

 -->


<!-- <script type="text/javascript">


function popupOpen2(){

	var popUrl = "/upload.jsp?perposeURL=/fileUpload";	//팝업창에 출력될 페이지 URL

	var popOption = "width=500, height=250, resizable=no, scrollbars=no, status=no;";    //팝업창 옵션(optoin)

		window.open(popUrl,"",popOption);

	}
	
function popupOpen(){

	var popUrl = "/form.jsp";	//팝업창에 출력될 페이지 URL

	var popOption = "width=370, height=360, resizable=no, scrollbars=no, status=no;";    //팝업창 옵션(optoin)

		window.open(popUrl,"",popOption);

	}
	

</script> -->



<a href="javascript:popupOpen();"> 가입할래?! </a>




 -->
	</div>
	<%@ include file="./commons/bottom.jspf"%>
</body>
</html>
=======
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
>>>>>>> branch 'master' of https://github.com/gnu-runtime/viser.git
