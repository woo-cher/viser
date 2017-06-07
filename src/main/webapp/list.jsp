<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="viser.project.*"%>
<%@ page import="viser.board.*" %>

<c:set var = "Target" value = "PROJECT" />
<c:set var = "Exist_msg" value = "NO EXIST PROJECT.." />
<c:set var = "Modal_target" value = "#CreateNewProJect" />
<c:set var = "ModifyUrl" value = "/project/updateProject" />

<c:if test = "${isReadBoard}">
<c:set var = "ModifyUrl" value = "#" />
<c:set var = "Target" value = "BOARD" />
<c:set var = "Exist_msg" value = "NO EXIST BOARD.." />
<c:set var = "Modal_target" value = "#CreateNewBoard" />
<c:set var = "ModifyUrl" value = "/board/updateBoard" />
</c:if>

<% List list = (List) request.getAttribute("list"); %>

<head>
<link href="/stylesheets/card.css" rel="stylesheet" type="text/css">
<!-- 환용 : 프로젝트 css 수정 -->
</head>

<script>
		function dropmsg(value, list) {
			var url;
			if(list == 'project') {
				url = '/project/deleteProject?projectName=';
			}
			else url = '/board/deleteBoard?boardName=';
			
			if (confirm("정말 삭제 하시겠습니까?")) {
				location.href = url+value;
			} else {
				return;
			}
		}
</script>
	
<body>
	<div class="background">
		<%@ include file="./commons/top.jspf" %>
		<div class="wrap ac">
			<div id="card-container_wrap">
				<div class="project-container">
					<div class="project-header" style="padding: 10pt;">
						<h3>${Target} STATE</h3>
					</div>
						<div id="project-content-header" style="margin: 0 auto;">
							<table class="table" border="1px" cellpadding="0" cellspacing="0">
							<tr height="30">
								<!-- LIST -->
								<td align="center" width="100">NUM</td>
								<td align="center" width="600">${Target} NAME</td>
								<c:if test = "${isReadProject}">
								<td align="center" width="150">DATE</td>
								</c:if>
							</tr>
						</div>

							<div class="project-content-body">

								<!-- forEach문으로 바꾸고 깃헙에 올리기 -->
								<c:choose>
									<c:when test="${not empty list }">
										<c:forEach var="list" items="${list}" varStatus="status">
											<tr height="50">
												<td align="center">${status.count }</td> <!-- 형근:프로젝트 번호는 가져온 순서대로 -->
												<td>
												<c:choose>
													<c:when test="${isReadBoard}">
													<a href="/card/cardlist?boardName=${list.boardName}" > <!-- 형근: 나중에 보드리스트로 이동하는걸로 변경할것 : 완료 --> 
														${list.boardName}
													</a>
													
													<div class="btn-group" role="group" style="float: right;">
													    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
													    <span class="caret" aria-hidden="true"></span>
													    </button>
													    <ul class="dropdown-menu" role="menu">
													    <li><a href="#modifyAction${status.count}" data-toggle="collapse" aria-controls="modifyAction">Modify</a></li>
													    <li><a href="javascript:dropmsg('${list.boardName}', 'board')">Delete</a></li>
													    </ul>
												  	</div>
												  		<form class="form-inline" action="${ModifyUrl}" method="get">
															<div class="collapse" id="modifyAction${status.count}" style="float: right;">
															 <div class="input-group" >
																<span class="input-group-btn">
																<button class="btn btn-default" type="submit">Modify</button>
																</span>
																<input type="text" class="form-control" id="exampleInputName2" name = "newBoardName" placeholder="Input new name. . .">
																<input type="hidden" name = "preBoardName" value="${list.boardName}">
										 						<span class="input-group-btn">
																	<button type="button" class="close" aria-label="Close">
																	<a href="#modifyAction${status.count}" data-toggle="collapse" aria-controls="modifyAction${status.count}"> X </a>
																	</button>
																</span>
															 </div>
															</div>
														</form>
													</c:when>
													<c:otherwise>
													<a href="/board/boardlist?projectName=${list.projectName}"> <!-- 형근: 나중에 보드리스트로 이동하는걸로 변경할것 : 완료--> 
														${list.projectName}
													</a>
  													 
													<div class="btn-group" role="group" style="float: right;">
													    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
													    <span class="caret" aria-hidden="true"></span>
													    </button>
													    <ul class="dropdown-menu" role="menu">
													    <li><a href="#modifyAction${status.count}" data-toggle="collapse" aria-controls="modifyAction">Modify</a></li>
													    <li><a href="javascript:dropmsg('${list.projectName}', 'project')">Delete</a></li>
													    </ul>
												  	</div>
														<form class="form-inline" action="${ModifyUrl}" method="get">
															<div class="collapse" id="modifyAction${status.count}" style="float: right;">
															 <div class="input-group" >
																<span class="input-group-btn">
																<button class="btn btn-default" type="submit">Modify</button>
																</span>
																<input type="text" class="form-control" id="exampleInputName2" name = "newProjectName" placeholder="Input new name. . .">
																<input type="hidden" name = "preProjectName" value="${list.projectName}">
										 						<span class="input-group-btn">
																	<button type="button" class="close" aria-label="Close">
																	<a href="#modifyAction${status.count}" data-toggle="collapse" aria-controls="modifyAction${status.count}"> X </a>
																	</button>
																</span>
															 </div>
															</div>
														</form>
												</td>
												<td align="center"><fmt:formatDate value="${list.projectDate}" pattern="yyyy-MM-dd"/></td>
											</tr>
													</c:otherwise>
												</c:choose>
									
										</c:forEach>
									</c:when>								
									<c:otherwise>
										<tr height="100">
											<td colspan="5" align="center">
											<h2>${Exist_msg }</h2>
											</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</div>
						</table>
				
						
						<!-- Create Project Modal Field -->
						<button type="button" class="btn btn-default btn-lg" data-toggle="modal" data-target="${Modal_target}" style="margin: 50;">
  						Create New ${Target}
						</button>
								<%@include file="./modalpage/form.jsp" %>
								
								<c:if test = "${isReadBoard}">
								<button type="button" onclick="location.href='/project/projectlist'" class="btn btn-default btn-lg">GO TO BACK</button>
								</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<%@ include file="./commons/bottom.jspf"%>
</html>