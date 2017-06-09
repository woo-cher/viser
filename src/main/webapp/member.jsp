<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>jQuery UI Menu - Default functionality</title>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#menu").menu();
	});
</script>
<style>
.ui-menu {
	width: 150px;
}
</style>
</head>

<body>
	<div id="chat-container">
		<div id="chat-user">
			<h2>유저목록</h2>
			<div id="chat-user-list">
				<ul id="menu">
				<c:forEach var="list" items="${memberlist }">
					<li>
						${list.userId }
						<ul>
							<c:choose>
							<c:when test="${isMaster==true}">
								<li><a href="추방url?userId=${list.userId }&projectName=${list.projectName}">추방</a></li>  <!-- 형근: 추방 servlet구현시 url변경 필요 -->
							</c:when>
							<c:otherwise>
								<li class="ui-state-disabled">추방</li>
							</c:otherwise>
							</c:choose>
							<li><div>쪽지보내기</div></li>
						</ul>
					</li>
				</c:forEach>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>