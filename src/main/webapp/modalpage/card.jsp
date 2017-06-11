<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  
<div style="color:black;" class="wrapper">
	<div class="modal fade file_modal" id="cardmodal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content" style="padding: 20px;width: 500px;height: 650px;">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>  <!--종료버튼  -->
					</button>
		
					<form id="card-field" method="post">
						<table class="table">
							<tr>
								<td align="center" colspan="2">
									<div id="Title">
									 
									</div>
								</td>
							</tr>
							
							
							<input id="cardNum" type="hidden" name="num" />
					
							
							<tr>
								<td>USER</td>
								<td>
									<div id=card-user>
									
									</div>
								</td>
							</tr>
							
							<tr>
								<td>SUBJECT</td>
								<td>
									<input id="cardSubject" type="text" class="form-control" style = "width: 302px;" name="subject">
								</td>
							</tr>
							<tr>
								<td>CONTENT</td>
								<td>
									<textarea id="cardContent" name="content"  class="form-control" rows = "13" cols="40"></textarea>
								</td>
							</tr>
							<tr>
								<td colspan="2"style = "padding-left: 98px;">
								
								<input id="cardListNum" type="hidden" name="listNum"/>
								<input id="cardOrder" type="hidden" name="cardOrder"/>
								
								</td>
							</tr>
						</table>
						<div id="btn-area">
							<input type="submit" id="submit-btn" class="btn btn-default"  />
							<input type="reset" class="btn btn-default" value="Reset" />
							<input type="button" class="btn btn-default"  value="List" data-dismiss="modal" />
						</div>
					</form>
				</div>  
			</div>
		</div>
	</div>
</div>