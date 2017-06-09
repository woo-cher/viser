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
						<h2>${Target} STATE</h2>
					</div>
					<div class="container" style = "length: 500px;">
							<table class="table table-hover">
							<thead>
							<tr >
								<!-- LIST -->
								<th>NUM</th>
								<th>${Target} NAME</th>
								<c:if test = "${isReadProject}">
								<th>DATE</th>
								</c:if>
							</tr>
							</thead>

							<div class="project-content-body">

								<c:choose>
									<c:when test="${not empty list }">
										<c:forEach var="list" items="${list}" varStatus="status">
											<tr>
												<td>${status.count}</td> <!-- 형근:프로젝트 번호는 가져온 순서대로 -->
												<td>
												<c:choose>
													<c:when test="${isReadBoard}">
													<a href="/lists/cardlist?boardNum=${list.boardNum}" > 
														${list.boardName}
													</a>
													
													<div class="btn-group" role="group" >
													    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
													    <span class="caret" aria-hidden="true"></span>
													    </button>
													    <ul class="dropdown-menu" role="menu">
													    <li><a href="#modifyAction${status.count}" data-toggle="collapse" aria-controls="modifyAction">Modify</a></li>
													    <li><a href="javascript:dropmsg('${list.boardName}', 'board')">Delete</a></li>
													    </ul>
												  	</div>
												  	
												  		<form class="form-inline" action="${ModifyUrl}" method="get">
															<div class="collapse" id="modifyAction${status.count}">
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
													<a href="/board/boardlist?projectName=${list.projectName}">
														${list.projectName}
													</a>
  													 
													<div class="btn-group" role="group" style="float: right; padding-right: 10px;">
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
												<td><fmt:formatDate value="${list.projectDate}" pattern="yyyy-MM-dd"/></td>
											</tr>
													</c:otherwise>
												</c:choose>
									
										</c:forEach>
									</c:when>								
									<c:otherwise>
										<tr>
											<td colspan="5">
											<h2>${Exist_msg }</h2>
											</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</div>
						</table>
				</div>
						
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