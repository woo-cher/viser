<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="viser.project.*" %>	
	
<%
	List projectlist=(List)request.getAttribute("projectlist");

%>	
	
<head>	
<link href="/stylesheets/card.css" rel="stylesheet" type="text/css">  <!-- 환용 : 프로젝트 css 수정 -->
</head>


<body>
	<%@ include file="./commons/top.jspf"%>
		<div class="project-container">
			<div class="project-header" style="padding:10pt;" >
				<h3>프로젝트 현황</h3>
			</div>
			<div id="project-content-header">
					<table border="1px" cellpadding="0" cellspacing="0">
						<tr height="30">
							<!-- card LIST -->
							<td align="center" width="100">NUM</td>
							<td align="center" width="600">프로젝트 명</td>
							<td align="center" width="150">생성날짜</td>
						</tr>
	</div>
	
			
	<div class="project-content-body">
			
				
				<%
					if(projectlist.size() > 0){
						for(int i=0 ; i < projectlist.size(); i++){
							Project project = (Project)projectlist.get(i); // LIST를 BoradBean 타입으로 변환
				%>
		
							<tr height="50">
								<td align="center"><%=i+1%></td> <!-- 형근:프로젝트 번호는 가져온 순서대로 -->
								<td>
								<a href="/card/cardlist?projectname=<%=project.getProject_Name()%>"> <!-- 형근: 나중에 보드리스트로 이동하는걸로 변경할것 -->
								<%=project.getProject_Name()%>
								</a>
								</td>
								<td align="center"><%=project.getProject_Date().toString() %></td>
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
			</div>
	</div>
<%@ include file="./commons/bottom.jspf"%>