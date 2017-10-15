<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

    <table class="table table-striped">
      <tr>
        <th style="width: 10px">#</th>
        <th>Gantt</th>
      </tr>
      <c:forEach var="index" items="${list}" varStatus="status">
	      <tr>
	        <td>${status.count}</td>
	        <td><a href="/gantts/loadGantt?ganttNum=${index.ganttNum}">${index.boardName }</a></td>
	      </tr>
      </c:forEach>
    </table>

</body>
</html>