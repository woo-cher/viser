<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="viser.project.*"%>

<c:set var = "Target" value = "PROJECT" />
<c:set var = "Exist_msg" value = "NO EXIST PROJECT.." />
<c:set var = "Modal_target" value = "#CreateNewProJect" />
<c:if test = "${isReadBoard}">
<c:set var = "Target" value = "BOARD" />
<c:set var = "Exist_msg" value = "NO EXIST BOARD.." />
<c:set var = "Modal_target" value = "#CreateNewBoard" />
</c:if>
<c:choose>
	<c:when test = "${isReadBoard}">
		<% List list = (List) request.getAttribute("boardlist"); %>
	</c:when>
	<c:otherwise>
		<% List list = (List) request.getAttribute("projectlist"); %>
	</c:otherwise>
</c:choose>

<head>
<link href="/stylesheets/card.css" rel="stylesheet" type="text/css">
<!-- 환용 : 프로젝트 css 수정 -->
</head>


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
								<c:if test = "${false ne isReadBoard}">
								<td align="center" width="150">DATE</td>
								</c:if>
							</tr>
						</div>

							<div class="project-content-body">

								<!-- forEach문으로 바꾸고 깃헙에 올리기 -->
								<c:choose>
									<c:when test="${not empty projectlist }">
										<c:forEach var="list" items="${projectlist}" varStatus="status">
											<tr height="50">
												<td align="center">${status.count }</td> <!-- 형근:프로젝트 번호는 가져온 순서대로 -->
												<td>
													<a href="/board/boardlist?projectname=${list.projectName}"> <!-- 형근: 나중에 보드리스트로 이동하는걸로 변경할것 --> 
														${list.projectName}
													</a>
													<button type="button" class="btn btn-default" aria-label="Left Align">
  													<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
													</button>
												</td>
													<c:if test = "${false ne isReadBoard}">
													<td align="center"><fmt:formatDate value="${list.projectDate}" pattern="yyyy-MM-dd"/></td>
													</c:if>
											</tr>
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
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<%@ include file="./commons/bottom.jspf"%>
</html>