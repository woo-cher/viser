<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Runtime</title>

<link href="stylesheets/form.css" rel="stylesheet" type="text/css">
</head>
<body>
		<div class="background">
		<%@ include file="./commons/top.jspf"%>
		<div class="wrap ac">
			<div id="card-container_wrap">
	<div class="signup-container">
		<div class="signup-header" style = "padding-bottom: 5px">
		
			<c:set var = "pageName" value = "회원가입" />
			<c:if test = "${isUpdate}" >  <!-- userId 유무로써 판단했던 것을 서블릿 정보로써 판단  -->
			<c:set var = "pageName" value = "개인정보수정" />
			</c:if>
			<h1>${pageName}</h1>			
		</div>
			
			<c:set var = "actionUrl" value = "/users/create" />
			<c:if test="${isUpdate}">
			<c:set var = "actionUrl" value = "/users/update" />
			</c:if>
		
		<form id="form-sign" action="${actionUrl}" method="post">
	<div
					style="width: 300px; margin: 0 auto; text-align: right; margin-bottom: 20px;">
		<!-- <div class="first"> -->
		<div class="input-group">
			<span class="input-group-addon"id="basic-addon2" for="name">이름 </span> 
		
		<c:choose>
			<c:when test="${isUpdate}">
				<input type="hidden" class="form-control" aria-describedby="basic-addon2" name ="name" value="${user.name}" />
				<div style="padding-left: 140px;">${user.name}</div>
			</c:when>
			
			<c:otherwise>
				<input type="text" class="form-control" aria-describedby="basic-addon2" name="name" value="${user.name}" />
			</c:otherwise>
		</c:choose>
		</div>
<!-- 		</div> -->
		<div class="input-group">
				<label class="input-group-addon"id="basic-addon2" for="age">나이</label> 
		<c:choose>
			<c:when test="${isUpdate}">
				<input type="hidden" name ="age" value="${user.age}" />
				<div id="hidden" "style ="text-align: center;">${user.age}</div>	
			</c:when>
			
			<c:otherwise>
				<input type="text" class="form-control" aria-describedby="basic-addon2" name="age" value="${user.age}" />
			</c:otherwise>
		</c:choose>
</div>
		<div>
				<label  for="gender">성별</label> 
		<c:choose>
			<c:when test="${isUpdate}">
				<input type="hidden" class="form-control" aria-describedby="basic-addon2" name ="gender" value="${user.gender}" />
				${user.gender}
			</c:when>
			
			<c:otherwise>
				<input type="checkbox" name="gender" value="Man" />남
				<input type="checkbox" name="gender" value="Women" />여
			</c:otherwise>
		</c:choose>
		</div>

		<div class="input-group">
			<label class="input-group-addon"id="basic-addon2" for="userId">Id</label> 
			
		<c:choose>
			<c:when test="${isUpdate}">
				<%-- <input type="hidden" style ="text-align: center;" name ="userId" value="${user.userId}" /> --%>
				<div style ="padding-left: 121px;">${user.userId}</div>
			</c:when>
			
			<c:otherwise>
				<input type="text" class="form-control" aria-describedby="basic-addon2" name="userId" value="${user.userId}" />				
				
			</c:otherwise>
		</c:choose>  
		</div> 

		<div class="input-group">
			<label class="input-group-addon"id="basic-addon2" for="password">Password</label> 
			<input type="password" class="form-control" aria-describedby="basic-addon2" name="password" value="${user.password}" />
		</div>
		
		<div class="input-group">
			<label class="input-group-addon"id="basic-addon2" for="password2">Password-check</label>
			<input type="password" class="form-control" aria-describedby="basic-addon2" name="password2" value="${user.password}" />
		</div>
		
		<div class="input-group">
			<label class="input-group-addon"id="basic-addon2" for="email">이메일</label>
			<input type="text" class="form-control" aria-describedby="basic-addon2" name="email" value="${user.email}" />
		</div>

		<div class="signup-footer">
			<button type="submit" class="btn btn-info">
			
				<c:set var = "buttonName" value = "가입하기" />
				<c:if test = "${isUpdate}">
				<c:set var = "buttonName" value = "수정하기" />
				</c:if>
			
				${ buttonName }
			</button>
		</div>  
		</div>
		</form>
	</div>
	</div>
	</div>
	</div>

</body>
</html>