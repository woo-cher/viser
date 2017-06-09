<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="./commons/top.jspf"%>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<div class="wrapper">

<body>



<button type="button" class="btn btn-danger" data-toggle="modal" data-target="#mycardmodal">Card info</button>




	<div class="modal fade file_modal" id="mycardmodal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content" style="padding: 20px;width: 500px;height: 650px;">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>  <!--종료버튼  -->
					</button>
		
		
					<c:set var = "actionUrl" value = "/card/createcard" />
					<c:if test="${isView}">
					<c:set var = "actionUrl" value = "/card/updatecard" />
					</c:if>
			

					<form id="card-field" action="${actionUrl}" method="post">
						<table class="table">
							<tr>
								<td align="center" colspan="2"> 
				
									<c:set var = "TitleName" value = "CREATE_JSP_card" />
									<c:if test = "${isView}" >
									<c:set var = "TitleName" value = "VIEW_card" />
									</c:if>
										<%-- <h2>${TitleName}</h2></td> --%>
										<h2>제 2회 SCPC</h2></td>
							</tr>
							
							<tr>
								<td >NUMBER</td>
								<td >
								<input type="hidden" name="num" value="${card.num}" />${card.num}</td>
							</tr>
							
							<tr>
								<td >USER</td>
								<td >
				
								<c:set var = "User" value = "${user.userId}" />
								<c:if test = "${isView}" >
								<c:set var = "User" value = "${card.userId}"/>
								</c:if>
								<input type="hidden" name="userId" value="${User}" /> ${User} </td>
								
							</tr>
							<tr>
							<td>SUBJECT</td>
							<td>
							
							<c:choose>
							<c:when test = "${isNotUser}"  >
							<input type="hidden"  class="form-control" name="subject"  style = "width: 302px;" value="${card.subject}">${card.subject}</td>
							</c:when>
							
							<c:otherwise>
							<input type="text" class="form-control" style = "width: 302px;" name="subject" value="${card.subject}"></td>
							</c:otherwise>
							</c:choose>
						</tr>
						<tr>
							<td>E-mail</td>
							<td >
							<input type="hidden" name="email" value="보드에 저장된 이메일 받아야 한다." />보드에 저장된 이메일</td>
						</tr>
						<tr>
							<td>CONTENT</td>
							<td>
							
							<c:choose>
							<c:when test = "${isNotUser}" >
							<textarea readonly name="content" class="form-control" rows="13" cols="40" style = "resize:none;">${card.content}</textarea>
							</td>
							</c:when>
							
							<c:otherwise>
							<textarea name="content"  class="form-control" rows = "13" cols="40">${card.content}</textarea></td>
							</c:otherwise>
							</c:choose>
							
						</tr>
						<tr>
							<td colspan="2"style = "padding-left: 98px;">
							
							<input type="hidden" name="listNum" value="${card.listNum }"/>
							<input type="hidden" name="cardOrder" value="${card.cardOrder }"/>
						</form>
						
						<c:choose>
						<c:when test = "${isView}">
							<c:if test = "${isNotUser}" >
							<a></a>
							</c:if>
						
							<c:if test = "${isUser}">
							<input type="button" class="btn btn-default" name="delete"  value="Delete" onclick="location.href='/card/removecard?num=${card.num}'" />
							<input type="submit" class="btn btn-default" name="modify" value="Modify"/>
							</c:if>
						</c:when>
						
						<c:otherwise>
							<input type="submit" class="btn btn-default" value="Submit" /> 
							<input type="reset"  class="btn btn-default" value="Reset" />
						</c:otherwise>
						</c:choose>
								<input type="button" class="btn btn-default"  value="List" onclick="location.href='/card/cardlist'" />
							</td>
						</tr>
						</div>
				</table>
				</div>  
			</div>
		</div>
	</div>
</div>  <!-- 환용 : 카드 정보 입력 -->  


</body>
<%-- <%@ include file="./commons/bottom.jspf"%> --%>