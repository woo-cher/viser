<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Font Awesome -->
<link rel="stylesheet" href="/resources/bower_components/font-awesome/css/font-awesome.min.css">

<!-- Assignee -->
<div class="modal fade" id="assignee-modal" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content" style="width: 450px;height: auto;margin-top: 100px;margin-left: -65px;">
			<div class="modal-header" style="text-align: center;">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h3>Assignee</h3>
				<div class="box-body">
					<table class="table" style="color: black;text-align: center;margin-bottom: 20px;">
						<tr>
							<!-- Assignee & Role LIST -->
							<td align="center" width="20%">ASSIGNEE</td>
							<td align="center" width="20%">ROLE</td>
							<td align="center" width="3%">
								<a href="javascript:createAssigneeTable();" class="text-muted"> 
								<i class="glyphicon glyphicon-plus" style="margin-top: 3px;"></i>
								</a>
							</td>
							<td align="center" width="3%">
							</td>
						</tr>
						<tbody id="assign-form"></tbody>
					</table>
					<button id="#" class="btn btn-success" style="margin-right: 40px;" type="button">Apply</button>
					<button id="#" class="btn btn-default" data-dismiss="modal">Cancel</button>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
var temporarilyNum = 0;
</script>

<script>
<!-- Commons Function -->
function deleteAssigneeTable(target) {
	target.parents('tr.tr-table').remove();
}

function updateAssigneeTable(target, assigneeNum) {
	$.ajax({
		type:'get',
		url:'/assignees/CreateFormAssignee',
		dataType:'json',
		success:function (data) {
			var str='';
			<!-- assignee select -->
			str+="<input class='assigneeNum' name='assigneeNum' type='hidden' value='" + assigneeNum + "'>";
			str+="<td>";
			str+="<p class='assignee-area'></p>";
			str+="<div class='assignee-selectBox' style='margin-top: -7px;'>"
			str+="<select id='assignee-select' class='form-control'>";
			str+="<option selected disabled hidden>Member</option>";
				var members = JSON.parse(data[0]);
				console.log("JS members : ", members)
				members.forEach(function(member){
					str+="<option value=" + member.userId + ">" + member.userId +"</option>";
				});
			str+="</select>";
			str+="</div>"
			str+="</td>";
			<!-- role select -->
	 		str+="<td>";
			str+="<p class='role-area'></p>";
			str+="<div class='role-selectBox' style='margin-top: -7px;'>";
			str+="<select id='role-select' class='form-control'>";
			str+="<option selected disabled hidden>Role</option>";
				var roles = JSON.parse(data[1]);
				console.log("JS roles : ", roles)
				roles.forEach(function(role){
					str+="<option value=" + role.roleName + ">" + role.roleName +"</option>";
				});
			str+="</select>";
			str+="</div>";
			str+="</td>"; 
			<!-- Icon -->
			str+="<td>";
			if($('#cardNum').val() == 0) {
				str+="<a class='text-muted' href='#' onclick='javascript:deleteAssigneeTableAndLabel($(this), " + assigneeNum + ")'>";
			}
			else {
				str+="<a href='#' onclick='javascript:deleteAssignee($(this))' class='text-muted'>";
			}
			str+="<i class='glyphicon glyphicon-trash' style='margin-top: 11px; margin-right: 2px;'></i>";
			str+="</a>";
			str+="</td>";
			str+="<td>";
			if($('#cardNum').val() == 0) {
				str+="<a class='i-class' href='#' onclick='updateAssigneeToCard($(this)," + assigneeNum + ")'>";
			}
			else {
				str+="<a class='Iicon' href='#' onclick='javascript:updateAssignee($(this))'>";
			}
			str+="<i class='glyphicon glyphicon glyphicon-ok' style='margin-top: 9px; margin-right: 2px;'></i>";
			str+="</a>";
			str+="</td>";
			target.parents('tr.tr-table').html(str);
		},
		error:ajaxError
	});
}

function createAssigneeTable() {
	$.ajax({
		type:'get',
		url:'/assignees/CreateFormAssignee',
		dataType:'json',
		success:function (data) {
			var str='';
			str+="<tr class='tr-table'>";
			if($('#cardNum').val() == 0) {
				str+="<input class='temporarilyNum' name='temporarilyNum' type='hidden' value='" + temporarilyNum + "'>";
				temporarilyNum++;
			}
			<!-- assignee select -->
			str+="<td>";
			str+="<p class='assignee-area'></p>";
			str+="<div class='assignee-selectBox' style='margin-top: -7px;'>";
			str+="<select id='assignee-select' class='form-control'>";
			str+="<option selected disabled hidden>Member</option>";
				var members = JSON.parse(data[0]);
				console.log("JS members : ", members)
				members.forEach(function(member){
					str+="<option value=" + member.userId + ">" + member.userId +"</option>";
				});
			str+="</select>";
			str+="</div>";
			str+="</td>";
			<!-- role select -->
	 		str+="<td>";
			str+="<p class='role-area'></p>";
			str+="<div class='role-selectBox' style='margin-top: -7px;'>";
			str+="<select id='role-select' class='form-control'>";
			str+="<option selected disabled hidden>Role</option>";
				var roles = JSON.parse(data[1]);
				console.log("JS roles : ", roles)
				roles.forEach(function(role){
					str+="<option value=" + role.roleName + ">" + role.roleName +"</option>";
				});
			str+="</select>";
			str+="</div>"
			str+="</td>"; 
			<!-- Icon -->
			str+="<td>";
			str+="<p class='trashIcon-control'>";
			str+="<a class='text-muted' href='#' onclick='javascript:deleteAssigneeTable($(this))'>";
			str+="<i class='glyphicon glyphicon-trash' style='margin-top: 11px; margin-right: 2px;'></i>";
			str+="</a>";
			str+="</p>";
			str+="</td>";
			str+="<td>";
			str+="<p class='pencilIcon-control'>";
			
			if($('#cardNum').val() == 0) {
				str+="<a class='i-class' href='#' onclick='applyAssigneeToCard($(this))'>";
			}
			else {
				str+="<a class='i-class' href='#' onclick='javascript:createAssignee($(this))'>";
			}
			
			str+="<i class='glyphicon glyphicon-ok' style='margin-top: 9px; margin-right: 2px;'></i>";
			str+="</a>";
			str+="</p>";
			str+="</td>"
			str+="</tr>";
			$('#assign-form').append(str);
		},
		error:ajaxError
	});
}
<!-- /Commons Function -->

<!-- When Create Card Action -->
function applyAssigneeToCard(target) {
	var assigneeMember = target.parents('tr.tr-table').find('div.assignee-selectBox').find('select > option:selected').val();
	var roleName = target.parents('tr.tr-table').find('div.role-selectBox').find('select > option:selected').val();
	var temporarilyNum = target.parents('tr.tr-table').find('input').val()
	$.ajax({
		success:function() {
			var str='';
			target.parents('tr.tr-table').attr("id","assignee" + temporarilyNum);
			str+="<input class='temporarilyNum' type='hidden' name='temporarilyNum' value=" + temporarilyNum + ">";
			str+="<td>";
			str+="<p class='assignee-area' style='margin-top: 8px;'>" + assigneeMember + "</p>";
			str+="</td>";
	 		str+="<td>";
	 		str+="<p class='role-area' style='margin-top: 8px;'>" + roleName + "</p>";
			str+="</td>"; 
			<!-- Icon -->
			str+="<td>";
			str+="<a class='text-muted' href='#' onclick='javascript:deleteAssigneeTableAndLabel($(this), " + temporarilyNum + ")'>";
			str+="<i class='glyphicon glyphicon-trash' style='margin-top: 11px; margin-right: 2px;'></i>";
			str+="</a>";
			str+="</td>";
			str+="<td>";
			str+="<a class='i-class' class='i-class'href='#' onclick='updateAssigneeTable($(this)," + temporarilyNum + ")'>";
			str+="<i class='glyphicon glyphicon-pencil' style='margin-top: 9px; margin-right: 2px;'></i>";
			str+="</a>";
			str+="</td>";
			target.parents('tr.tr-table').html(str);
			
			var cardStr='';
			cardStr+="<input class='assigneeMember' type='hidden' name='assigneeMember' value=" + assigneeMember + ">";
			cardStr+="<input class='roleName' type='hidden' name='roleName' value=" + roleName + ">";
			$('#card-field').append(cardStr);
			
			var label ='';
			$('#assignee-label' + temporarilyNum).remove();
	      	label+="<span id='assignee-label" + temporarilyNum +"' class='label label-warning' style='margin-right:20px; margin-left:-15px;'>" + assigneeMember + " : '" + roleName + " '</span>";
	      	$('#cardAssignee-field').append(label);
		},
		error:ajaxError
	});
}

function deleteAssigneeTableAndLabel(target, targetNum) {
	$('#assignee-label' + targetNum).remove();
	target.parents('tr.tr-table').remove();
}

function updateAssigneeToCard(target, targetNum) {
	var assigneeMember = target.parents('tr.tr-table').find('div.assignee-selectBox').find('select > option:selected').val();
	var roleName = target.parents('tr.tr-table').find('div.role-selectBox').find('select > option:selected').val();
	var temporarilyNum = targetNum
	$.ajax({
		success:function() {
			var str='';
			target.parents('tr.tr-table').attr("id","assignee" + temporarilyNum);
			str+="<input class='temporarilyNum' type='hidden' name='temporarilyNum' value=" + temporarilyNum + ">";
			str+="<td>";
			str+="<p class='assignee-area' style='margin-top: 8px;'>" + assigneeMember + "</p>";
			str+="</td>";
	 		str+="<td>";
	 		str+="<p class='role-area' style='margin-top: 8px;'>" + roleName + "</p>";
			str+="</td>"; 
			<!-- Icon -->
			str+="<td>";
			str+="<a class='text-muted' href='#' onclick='javascript:deleteAssigneeTableAndLabel($(this), " + temporarilyNum + ")'>";
			str+="<i class='glyphicon glyphicon-trash' style='margin-top: 11px; margin-right: 2px;'></i>";
			str+="</a>";
			str+="</td>";
			str+="<td>";
			str+="<a class='i-class' class='i-class'href='#' onclick='updateAssigneeTable($(this)," + temporarilyNum + ")'>";
			str+="<i class='glyphicon glyphicon-pencil' style='margin-top: 9px; margin-right: 2px;'></i>";
			str+="</a>";
			str+="</td>";
			target.parents('tr.tr-table').html(str);
			
			var cardStr='';
			cardStr+="<input class='assigneeMember' type='hidden' name='assigneeMember' value=" + assigneeMember + ">";
			cardStr+="<input class='roleName' type='hidden' name='roleName' value=" + roleName + ">";
			$('#card-field').append(cardStr);
			
			var label ='';
			$('#assignee-label' + temporarilyNum).remove();
	      	label+="<span id='assignee-label" + temporarilyNum +"' class='label label-warning' style='margin-right:20px; margin-left:-15px;'>" + assigneeMember + " : '" + roleName + " '</span>";
	      	$('#cardAssignee-field').append(label);
		},
		error:ajaxError
	});
}
<!-- /When Create Card Action -->

<!-- When View Card Action -->
function createFormAssignee(taget) {
	$.ajax({
		data:{
			assigneeMember : target.parents('tr.tr-table').find('div.assignee-selectBox').find('select > option:selected').val(),
			roleName : target.parents('tr.tr-table').find('div.role-selectBox').find('select > option:selected').val(),
		},
		
		success:function(data) {
			var str='';
			target.parents('tr.tr-table').attr("id","assignee" + data[0]);
			str+="<input class='assigneeNum' type='hidden' name='assigneeNum' value="+data[0]+">";
			str+="<td>";
			str+="<p class='assignee-area' style='margin-top: 8px;'>" + data[1] + "</p>";
			str+="</td>";
	 		str+="<td>";
	 		str+="<p class='role-area' style='margin-top: 8px;'>" + data[2] + "</p>";
			str+="</td>"; 
			<!-- Icon -->
			str+="<td>";
			str+="<a class='text-muted' href='#' onclick='javascript:deleteAssignee($(this))'>";
			str+="<i class='glyphicon glyphicon-trash' style='margin-top: 11px; margin-right: 2px;'></i>";
			str+="</a>";
			str+="</td>";
			str+="<td>";
			str+="<a class='i-class' class='i-class'href='#' onclick='javascript:updateAssigneeTable($(this)," + data[0] + ")'>";
			str+="<i class='glyphicon glyphicon-pencil' style='margin-top: 9px; margin-right: 2px;'></i>";
			str+="</a>";
			str+="</td>";
			target.parents('tr.tr-table').html(str);
			
			var label ='';
	      	label+="<span id='assignee-label" + data[0] +"' class='label label-warning' style='margin-right:20px; margin-left:-15px;'>" + data[1] + " : '" + data[2] + " '</span>";
			$('#cardAssignee-field').append(label);
		},
		error:ajaxError
	});
}

function createAssignee(target) {
	var thisClass = this;
	console.log("target ? : ", target);
	$.ajax({
		type:'get',
		data:{
			assigneeMember : target.parents('tr.tr-table').find('div.assignee-selectBox').find('select > option:selected').val(),
			roleName : target.parents('tr.tr-table').find('div.role-selectBox').find('select > option:selected').val(),
			cardNum : thisClass.$('#cardNum').val()
		},
		url:'/assignees/createAssignee',
		dataType:'json',
		
		success:function(data) {
			var str='';
			target.parents('tr.tr-table').attr("id","assignee" + data[0]);
			str+="<input class='assigneeNum' type='hidden' name='assigneeNum' value="+data[0]+">";
			str+="<td>";
			str+="<p class='assignee-area' style='margin-top: 8px;'>" + data[1] + "</p>";
			str+="</td>";
	 		str+="<td>";
	 		str+="<p class='role-area' style='margin-top: 8px;'>" + data[2] + "</p>";
			str+="</td>"; 
			<!-- Icon -->
			str+="<td>";
			str+="<a class='text-muted' href='#' onclick='javascript:deleteAssignee($(this))'>";
			str+="<i class='glyphicon glyphicon-trash' style='margin-top: 11px; margin-right: 2px;'></i>";
			str+="</a>";
			str+="</td>";
			str+="<td>";
			str+="<a class='i-class' class='i-class'href='#' onclick='javascript:updateAssigneeTable($(this)," + data[0] + ")'>";
			str+="<i class='glyphicon glyphicon-pencil' style='margin-top: 9px; margin-right: 2px;'></i>";
			str+="</a>";
			str+="</td>";
			target.parents('tr.tr-table').html(str);
			
			var label ='';
	      	label+="<span id='assignee-label" + data[0] +"' class='label label-warning' style='margin-right:20px; margin-left:-15px;'>" + data[1] + " : '" + data[2] + " '</span>";
			$('#cardAssignee-field').append(label);
		},
		error:ajaxError
	});
}

function deleteAssignee(target) {
	var thisAssigneeNum = target.parents('tr').find('input.assigneeNum').val()
	$.ajax({
		type:'get',
		data:{
			assigneeNum:thisAssigneeNum
		},
		url:"/assignees/deleteAssignee",
		
		success:function () {
			target.parents('tr.tr-table').remove();
			$('#assignee-label' +thisAssigneeNum).remove();
		},
		error:ajaxError
	});
}

function updateAssignee(target) {
	$.ajax({
		type:'get',
		data:{
			assigneeNum:target.parents('tr.tr-table').find('input.assigneeNum').val(),
			assigneeMember : target.parents('tr.tr-table').find('div.assignee-selectBox').find('select > option:selected').val(),
			roleName : target.parents('tr.tr-table').find('div.role-selectBox').find('select > option:selected').val()
		},
		url:"/assignees/updateAssignee",
		dataType:'json',
		
		success:function (data) {
			console.log("TEST : ", data);
			var str='';
			target.parents('tr.tr-table').attr("id","assignee" + data.assigneeNum);
			str+="<input class='assigneeNum' type='hidden' name='assigneeNum' value="+data.assigneeNum+">";
			str+="<td>";
			str+="<p class='assignee-area' style='margin-top: 8px;'>" + data.userId + "</p>";
			str+="</td>";
	 		str+="<td>";
	 		str+="<p class='role-area' style='margin-top: 8px;'>" + data.roleName + "</p>";
			str+="</td>"; 
			<!-- Icon -->
			str+="<td>";
			str+="<a class='text-muted' href='#' onclick='javascript:deleteAssignee($(this))'>";
			str+="<i class='glyphicon glyphicon-trash' style='margin-top: 11px; margin-right: 2px;'></i>";
			str+="</a>";
			str+="</td>";
			str+="<td>";
			str+="<a class='i-class' class='i-class'href='#' onclick='javascript:updateAssigneeTable($(this)," + data.assigneeNum + ")'>";
			str+="<i class='glyphicon glyphicon-pencil' style='margin-top: 9px; margin-right: 2px;'></i>";
			str+="</a>";
			str+="</td>";
			target.parents('tr.tr-table').html(str);
			var label ='';
			label+="<span id='assignee-label" + data.assigneeNum +"' class='label label-warning' style='margin-right:20px; margin-left:-15px;'>" + data.userId + " : '" + data.roleName; + " '</span>";
			$('#cardAssignee-field').html(label);
		},
		error:ajaxError
	});
}
<!-- /When View Card Action -->
</script>