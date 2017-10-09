<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 
 <!-- progress -->
<link rel="stylesheet" href="/resources/dist/css/jquery-ui.css">

  <script>
  $( function() {
    $( "#slider-range-min" ).slider({
      range: "min",
      min: 0,
      max: 100,
      slide: function( event, ui ) {
        $( "#amount" ).val(ui.value );
    	$( "#progress" ).css( "width", $( "#amount" ).val()+"%" );
      }
    });
    $( "#amount" ).val($( "#slider-range-min" ).slider( "value" ) );
    $( "#progress" ).css( "width",$( "#amount" ).val()+"%" );
  } );
  </script>

<!-- Progress -->
<div class="modal fade" id="progress-modal" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content" style="width: auto; height: auto;">
			<div class="modal-header" style="text-align: center;">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h3>Setting Progress</h3>
			</div>
			<div class="box-body">
				<div class="form-group">
				<form>
				  <div class="input-group date">
				  	<label for="amount">진행률:</label>
				  	<input type="text" id="amount" readonly style="border:0; color:#f6931f; font-weight:bold;">
				  </div>
				  <div id="slider-range-min" class="progress active">
				    <div id="progress" class="progress-bar progress-bar-primary progress-bar-striped" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100">
				      <span class="sr-only"></span>
				    </div>
				  </div>
				  <div class="btn-group" style="display: inline-flex; margin-top: 10px;">
					<button id="apply-btn" class="btn btn-success" style="margin-right: 40px;" type="button" onclick="#">Apply</button>
					<button id="close-btn" class="btn btn-default" data-dismiss="modal">Cancel</button>
				  </div>
				</form>
				</div>
			</div>
		</div>
	</div>
</div>

