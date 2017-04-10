<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시판 작성</title>
</head>

<body>
	<form id = "board-field" action="/board/createBoard" method="post">
	<table width="400" border="1" cellspacing="0" cellpadding="0" align="center" >
		<tr>
			<td align="center" colspan="2">
			<a href="board.jsp">글 목록</a>
			</td>
		</tr>
		<tr>
			<td width="70" align="center">글쓴이</td>
			<td width="330">
			<input type="hidden" name ="userId" value="${user.userId}" />
				${userId}
			</td>
		</tr>
		<tr>
			<td width="70" align="center">제 목</td>
			<td width="330">

			<input type="text" size="40" maxlength="50" name="subject" value="${board.subject}">
			</td>
		</tr>
		<tr>
			<td width="70" align="center">E-mail</td>
			<td width="330">
			
			<input type="hidden" name ="email" value="${user.email}" />
			${user.email}
			
			</td>
		</tr>
		<tr>
			<td width="70" align="center">내 용</td>
			<td width="330">
			<textarea name="content" rows="13" cols="40" value="${board.content}"></textarea>
			</td>
		</tr>
		<tr>
			<td width="70" align="center">비밀번호</td>
			<td width="330">
			<input type="password" size="8" maxlength="12" name="password" value="">
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
			<input type="submit" value="글 쓰기" onclick="location.href='/board/createBoard'> 
			<input type="reset" value="다시 작성">
			<input type="button" value="목록보기" onclick="location.href='/board.jsp'">
			</td>
		</tr>
	</table>
	</form>


</body>
</html>