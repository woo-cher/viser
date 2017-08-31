<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="box-body">
	<!-- Date -->
	<div class="form-group">
		<form>
			<label>Date:</label>
			<div class="input-group date">
				<div class="input-group-addon">
					<span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
				</div>
				<input type="text" class="form-control pull-right" id="datepicker" name="duedate">
			</div>
			<div class="btn-group" style="display: inline-flex; margin-top: 10px;">
				<button id="apply-btn" class="btn btn-success" style="margin-right: 40px;" type="button" onclick="cuDueDate();">Apply</button>
				<button id="close-btn" class="btn btn-default" data-dismiss="modal">Cancel</button>
			</div>
		</form>
	</div>
</div>

<!-- bootstrap datepicker -->
<script src="/resources/bower_components/bootstrap-datepicker/dist/js/bootstrap-datepicker.min.js"></script>
