<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="viser.project.*"%>
<%@ page import="viser.user.*" %>
<script>
	function msg(user) {
		console.log("user" + user);
		if (confirm(user + "님을 초대 하시겠습니까?")) {
			location.href = "/projects/inviteUser?userId=" + user;
		} else {
			return;
		}
	}
</script>

<div class="modal fade" id="invite" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header" style="color: black;">
        <h2 class="modal-title" id="MyModalLabel">Invite User</h2>
      </div>  
      <div class="modal-body">
		 <form class="form-inline" action="/projects/searchUser" method="get" style="padding-right: 10px;">
		 	 <div class="form-group">
		 	   <label for="exampleInputName2" style="color: black;padding-right: 10px"><h2>Search</h2></label>
			   <input type="text" class="form-control" name="keyword" placeholder="Input userId.." style="width:300px">
			   <button type="submit" class="btn btn-primary">검색</button>
			  </div>
		</form>	
		 	 <div>
							<table class="table" style="color: black; text-align: center;">
							<thead>
							<tr>
								<!-- LIST -->
							   <td align="center" width="10">#</td>
			                   <td align="center" width="60">ID</td>
			                   <td align="center" width="15">NAME</td>
			                   <td align="center" width="15">AGE</td>
			                   <td align="center" width="10">SEX</td>
							</tr>
							</thead>
							  	<c:choose>
								   <c:when test="${not empty list}">
									 <c:forEach var="list" items="${list}" varStatus="status">
										<tr>
											<td>
												  <button type="button" class="btn btn-default" aria-label="Left Align" onclick="location.href='javascript:msg(`${list.userId}`)'">
												  <span class="glyphicon glyphicon-envelope" aria-hidden="true" ></span>
											   	  </button>
											</td>
											<td>${list.userId}</td>
											<td>${list.name}</td>												  	
											<td>${list.age}</td>
											<td>${list.gender}</td>
									</c:forEach>
								</c:when>								
									<c:otherwise>
										<tr>
											<td colspan="5"><h2>NO DATA.</h2></td>
										</tr>
									</c:otherwise>
							  </c:choose>
						</table>
   	   </div>
		  
     <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">나가기</button>
  	 </div>
   </div>
  </div>
</div>