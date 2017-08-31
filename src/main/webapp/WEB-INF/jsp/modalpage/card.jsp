<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta charset="utf-8">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<meta name="viewport" content="width=device-width, initial-scale=1">

<div class="modal fade" id="cardmodal" tabindex="-1" role="dialog" data-focus-on="input:first"aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content" id="cardmodal-body" style="padding: 20px; width: 630px; height: 650px;">
			<div class="modal-header">
				<button id="close-card-btn" type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<p id="Title" style="margin-top: -25px; text-align: center;"></p>
			</div>
			<form id="card-field" method="post" style="float: left;">
				<input id="cardNum" type="hidden" name="num" />
				<table class="table">
					<tr>
						<td>USER</td>
						<td>
							<div id=card-user></div>
						</td>
					</tr>
					<tr id="card-duedate">
					</tr>
					<tr>
						<td>SUBJECT</td>
						<td>
							<input id="cardSubject" type="text" class="form-control" style="width: 358px;" name="subject">
						</td>
					</tr>
					<tr>
						<td>CONTENT</td>
						<td>
							<textarea id="cardContent" name="content" class="form-control" rows="13" cols="40"></textarea>
						</td>
					</tr>
					<input id="cardListNum" type="hidden" name="listNum" />
					<input id="cardOrder" type="hidden" name="cardOrder" />
				</table>
				<div id="btn-area" style="text-align: center; margin-left: 100px"></div>
			</form>
			<div id="add-menu" style="float: right; display: inline-grid;">
				<h3>Add</h3>

				<!-- due date -->
				<button id="duedate-btn" type="button" class="btn btn-primary btn-lg" data-toggle="modal" onclick="showSecondaryModal();" style="margin-bottom: 7px;">Due Date</button>
				<button type="button" class="btn btn-primary btn-lg" style="margin-bottom: 7px;">CheckList</button>
				<button type="button" class="btn btn-primary btn-lg" style="margin-bottom: 7px;">Labels</button>
			</div>
		</div>
	</div>
</div>

<!-- due date 모달 -->
<div class="modal fade" id="duedate-modal" data-backdrop="" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content" style="width: auto; height: auto;">
			<div class="modal-header" style="text-align: center;">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h3>Change Due Date</h3>
				<%@ include file="/datepicker.jsp"%>
			</div>
		</div>
	</div>
</div>

<script>
function showSecondaryModal() {
	$('#duedate-modal').find('form')[0].reset();
 	$('#duedate-modal').modal('show');
}

<!-- Fix : Bug when using multiple modal -->
var recentModalList = []; $(document).ready(function () { 
	$.fn.modal.Constructor.prototype.enforceFocus = function () { }; 
	$('.modal').on('shown.bs.modal', function (e) { recentModalList.push(e.target); }); 
	$('.modal').on('hide.bs.modal', function (e) { customModalClosed(e); console.log(recentModalList.length); }); }); 
	var customModalClosed = function (e) {
		for (var i = recentModalList.length - 1; i >= 0; i--) { 
			if (recentModalList[i] == e.target) { recentModalList.splice(i, 1); } 
		}
		if (recentModalList.length > 0) { recentModalList[recentModalList.length - 1].focus(); } 
};
</script>