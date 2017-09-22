<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="viser.project.*"%>
<%@ page import="viser.user.*"%>

<script>
	function popup_msg(user) {
		console.log("user" + user);
		if (confirm(user + "님을 초대 하시겠습니까?")) {
			location.href = "/projects/inviteUser?userId=" + user;
		} else {
			return;
		}
	}
</script>

<div class="modal fade" id="invite-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header" style="color: black;">
				<h2 class="modal-title" id="MyModalLabel">Invite User</h2>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label for="exampleInputName2" style="color: black; padding-right: 10px"><h2>Search</h2></label> <input type="text" id="keyword" class="form-control" name="keyword" placeholder="Input userId.." style="width: 300px">
					<button id="search-btn" type="button" class="btn btn-primary">검색</button>
				</div>
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
						<tbody id="data"></tbody>
					</table>
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">나가기</button>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
$('#search-btn').click(function(){
	$.ajax({
		type:'get',
		data:{
			keyword:$('#keyword').val()
		},
		dataType: "json",
		url:"/projects/searchUser",
		success:function(data){
			var str='';
			if(data.length>0){
				console.log("enter");
				var i;
				data.forEach(function(item){
					str+="<tr>";
					str+="<td>";
					str+="<button type='button' id='user-btn' class='btn btn-default' aria-label='Left Align' onclick='popup_msg(`"+item.userId+"`)'>";
					str+="<span class='glyphicon glyphicon-envelope' aria-hidden='true' ></span>";
					str+="</button>";
					str+="</td>";
					str+='<td>'+item.userId+'</td>';
					str+='<td>'+item.name+'</td>';
					str+='<td>'+item.age+'</td>';
					str+='<td>'+item.gender+'</td>';
					str+="</tr>";
					$('#data').html(str);
				});	
					
				}
	
			else{
				str+='<tr>';
				str+='<td colspan="5"><h2>'+'NO DATA.'+'</h2></td>';
				str+='</tr>';
			}
		},
		error:ajaxError
	});
});	
</script>