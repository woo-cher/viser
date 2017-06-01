<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Runtime</title>
</head>
<body>
		<c:set var = "actionUrl" value = "/card/createcard" />
		
		<c:if test="${isView}">
		<c:set var = "actionUrl" value = "/card/updatecard" />
		</c:if>
		
		<div id="container">	
		<form id="card-field" action="${actionUrl}" method="post">
		
		<table width="400" border="1" cellspacing="0" cellpadding="0" align="center">
			<tr>
			<td align="center" colspan="2"> 
				
				<c:set var = "TitleName" value = "CREATE_JSP_card" />
				<c:if test = "${isView}" >
				<c:set var = "TitleName" value = "VIEW_card" />
				</c:if>
					<h2>${TitleName}</h2></td>    
			</tr>
			<tr>
				<td width="70" align="center">NUMBER</td>
				<td width="150">
				<input type="hidden" name="num" value="${card.num}" />${card.num}</td>
			</tr>
			<tr>
				<td width="70" align="center">USER</td>
				<td width="330">
				
				<c:set var = "User" value = "${user.userId}" />
				<c:if test = "${isView}" >
				<c:set var = "User" value = "${card.userId}"/>
				</c:if>
				<input type="hidden" name="userId" value="${User}" /> ${User} </td>
				
			</tr>
			<tr>
				<td width="70" align="center">SUBJECT</td>
				<td width="330">
				
				<c:choose>
				<c:when test = "${isNotUser}" >
				<input type="hidden" size="40" maxlength="50" name="subject" value="${card.subject}">${card.subject}</td>
				</c:when>
				
				<c:otherwise>
				<input type="text" size="40" maxlength="50" name="subject" value="${card.subject}"></td>
				</c:otherwise>
				</c:choose>
			</tr>
			<tr>
				<td width="70" align="center">E-mail</td>
				<td width="330">
				<input type="hidden" name="email" value="보드에 저장된 이메일 받아야 한다." />보드에 저장된 이메일</td>
			</tr>
			<tr>
				<td width="70" align="center">CONTENT</td>
				<td width="330">
				
				<c:choose>
				<c:when test = "${isNotUser}" >
				<textarea readonly name="content" rows="16" cols="50" style = "resize:none;">${card.content}</textarea>
				</td>
				</c:when>
				
				<c:otherwise>
				<textarea name="content"  rows = "13" cols="40">${card.content}</textarea></td>
				</c:otherwise>
				</c:choose>
				
			</tr>
			<tr>
				<td colspan="2" align="center">
			</form>
			
			<c:choose>
			<c:when test = "${isView}">
				<c:if test = "${isNotUser}" >
				<a></a>
				</c:if>
			
				<c:if test = "${isUser}">
				<input type="button" name="delete"  value="Delete" onclick="location.href='/card/removecard?num=${card.num}'" />
				<input type="submit" name="modify" value="Modify"/>
				</c:if>
			</c:when>
			
			<c:otherwise>
				<input type="submit" value="Submit" /> 
				<input type="reset" value="Reset" />
			</c:otherwise>
			</c:choose>
					<input type="button" value="List" onclick="location.href='/card/cardlist'" />
				</td>
			</tr>
	</table>
</form>
</div>
</body>
</html>