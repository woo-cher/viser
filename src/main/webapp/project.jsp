<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="viser.project.*"%>

<%
	List projectlist = (List) request.getAttribute("projectlist");
%>

<head>
<link href="/stylesheets/card.css" rel="stylesheet" type="text/css">
<!-- 환용 : 프로젝트 css 수정 -->
</head>


<body>
	<div class="background">
		<%@ include file="./commons/top.jspf"%>
		<div class="wrap ac">
			<div id="card-container_wrap">
				<div class="project-container">
					<div class="project-header" style="padding: 10pt;">
						<h3>프로젝트 현황</h3>
					</div>
						<div id="project-content-header" style="margin: 0 auto;">
							<table border="1px" cellpadding="0" cellspacing="0">
							<tr height="30">
								<!-- card LIST -->
								<td align="center" width="100">NUM</td>
								<td align="center" width="600">프로젝트 명</td>
								<td align="center" width="150">생성날짜</td>
							</tr>
						</div>

							<div class="project-content-body">

								<!-- forEach문으로 바꾸고 깃헙에 올리기 -->
								<c:choose>
									<c:when test="${not empty projectlist }">
										<c:forEach var="list" items="${projectlist }" varStatus="status">
											<tr height="50">
												<td align="center">${status.count }</td> <!-- 형근:프로젝트 번호는 가져온 순서대로 -->
												<td>
													<a href="/card/cardlist?projectname=${list.projectName}"> <!-- 형근: 나중에 보드리스트로 이동하는걸로 변경할것 --> 
														${list.projectName}
													</a>
												</td>
												<td align="center"><fmt:formatDate value="${list.projectDate}" pattern="yyyy-MM-dd"/></td>
											</tr>
										</c:forEach>
									</c:when>								
									<c:otherwise>
										<tr height="100">
											<td colspan="5" align="center">NO DATA.</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</div>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<%@ include file="./commons/bottom.jspf"%>
</html>