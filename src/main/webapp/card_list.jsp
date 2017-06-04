<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="viser.card.*"%>

<%
	List list = (List) request.getAttribute("list");
	int count = ((Integer) request.getAttribute("count")).intValue();
	int nowpage = ((Integer) request.getAttribute("page")).intValue();
	int maxpage = ((Integer) request.getAttribute("maxpage")).intValue();
	int startpage = ((Integer) request.getAttribute("startpage")).intValue();
	int endpage = ((Integer) request.getAttribute("endpage")).intValue();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!---------------->



<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>jQuery UI Sortable - Display as grid</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<style>

#droppable {    width: 150px;
    height: 34px;
    padding: 0.2em;
    float: left;
    margin: 10px;
    border: 5px solid aquamarine;
    background: azure;
    border-radius: 10px;
    margin-right: 30px;
    position: absolute;
    top: 880px;
    left: 970px;
     }


#sortable_box {
	list-style-type: none;
	margin: 0;
	padding: 0;
}

#sortable_box li {
/*margin: 3px 3px 3px 0; */  
    padding: 1px;
    float: left;
    text-align: center;
}
</style>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#sortable_box").sortable();
		$("#sortable1").draggable();
		$("#sortable_box").disableSelection();
	    $( ".widget input[type=submit], .widget a, .widget button" ).button();
	    $( "button, input, a" ).click( function( event ) {
	      event.preventDefault();
	    } );
	});
	
	 $( function() {
		    $( "#droppable " ).droppable({
		      drop: function( event, ui ) {
		        $( this ).find("li").remove();
		      }
		    });
		  } );
	
</script>





<!--------------->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>card</title>
<link href="/stylesheets/card.css" rel="stylesheet" type="text/css">
</head>
<body>

	<div class="background">
		<%@ include file="./commons/top.jspf"%>
		<div class="wrap ac">
			<div id="card-container_wrap" style = "color: azure;">
				<div id="top">
					<div id="mini-menu">

						<div class="btn-group-sm" role="group" aria-label="...">  
							<div id="droppable" class="ui-widget-header">
									<p>Drop here</p>
								<ul id="sortable1" class="connectedSortable" style="border: 0px solid azure;">
  
  								</ul>
							</div>
							<button type="button" class="btn btn-info" href="#"
								class="btn btn-default">검색</button>
							<button type="button" class="btn btn-info" href="#"
								class="btn btn-default">필터</button>
							<button type="button" class="btn btn-info" href="#"
								class="btn btn-default">초대</button>
							<button type="button" class="btn btn-info" href="#"
								class="btn btn-default">알림</button>
							<button type="button" class="btn btn-info" href="#"
								class="btn btn-default">구독</button>
							<button type="button" class="btn btn-info" href="#"
								class="btn btn-default">EXIT</button>

						</div>

					</div>
				</div>  

				<div id="card-container" style = "overflow-x: auto;"> 
<!-- </div> --> <!-- $(this).parent().remove();-->
					<div id="card_wrap_indivisual_tlp" style="display: none;">
							<li class="ui-state-default  card_margin">

							<div id="card_wrap_top">
										<!-- <div class="ui-state-default" id="title-sortable"><textarea class="list-header-name mod-list-name js-list-name-input" spellcheck="false" dir="auto" maxlength="512" style="overflow: hidden; word-wrap: break-word; height: 24px;">Next Week</textarea>  </div> -->
										<div>
											<textarea class="change_name" spellcheck="false" dir="auto"
												maxlength="512"
												style="overflow: hidden; word-wrap: break-word; height: 24px; margin-top: 5px; width: 150px;">My goal</textarea>
										</div>
										<!-- <div class="ui-state-default" id="title-sortable">추가</div> -->
										<div>
											<button type="button" class="ui-state-default add_card" id="title-sortable">add..</button>  
										</div>
							</div>
								<div id="sortable3" class="connectedSortable_row">
									<!-- <div id="card_wrap_indivisual"> -->

									

										<div id="card_wrap">
											<ul id="sortable1" class="connectedSortable">
												<!-- <li id="title-sortable" class="ui-state-default">이름</li> -->

											</ul>
										</div>

								</div>
								<button type="button" class="btn btn-info_c delete" href="#"  onclick="" style = "margin-bottom: 5px;">삭제</button>
							</li> 
							
						</div>

    

			   	<div id="tt" style="width :1500px">  
						<ul id="sortable_box">  
						
							<li class="ui-state-default  card_margin">

							<div id="card_wrap_top">
										<!-- <div class="ui-state-default" id="title-sortable"><textarea class="list-header-name mod-list-name js-list-name-input" spellcheck="false" dir="auto" maxlength="512" style="overflow: hidden; word-wrap: break-word; height: 24px;">Next Week</textarea>  </div> -->
										<div>
											<textarea class="change_name" spellcheck="false" dir="auto"
												maxlength="512"
												style="overflow: hidden; word-wrap: break-word; height: 24px; margin-top: 5px; width: 150px;">My goal</textarea>
										</div>
										<!-- <div class="ui-state-default" id="title-sortable">추가</div> -->
										<div>
											<button type="button" class="ui-state-default add_card" id="title-sortable">add..</button>  
										</div>
						  </div>
								<div id="sortable3" class="connectedSortable_row">
									<!-- <div id="card_wrap_indivisual"> -->

										<div id="card_wrap">
											<ul id="sortable1" class="connectedSortable">
												<!-- <li id="title-sortable" class="ui-state-default">이름</li> -->

												<li id="sortable_card" class="ui-state-default">first</li>
												<li id="sortable_card" class="ui-state-default">Item 3</li>
												<li id="sortable_card" class="ui-state-default">Item 4</li>
												<li id="sortable_card" class="ui-state-default">Item 5</li>
											</ul>
										</div>

								</div>
								<button type="button" class="btn btn-info_c delete" href="#"  onclick="" style = "margin-bottom: 5px;">삭제</button>
							</li>
														<li  class="ui-state-default card_margin"><div
									id="sortable3" class="connectedSortable_row">
									<!-- <div id="card_wrap_indivisual"> -->

									<div id="card_wrap_top">
										<!-- <div class="ui-state-default" id="title-sortable"><textarea class="list-header-name mod-list-name js-list-name-input" spellcheck="false" dir="auto" maxlength="512" style="overflow: hidden; word-wrap: break-word; height: 24px;">Next Week</textarea>  </div> -->
										<div>
											<textarea class="change_name" spellcheck="false" dir="auto"
												maxlength="512"
												style="overflow: hidden; word-wrap: break-word; height: 24px; margin-top: 5px; width: 150px;">Ref.</textarea>
										</div>
										<!-- <div class="ui-state-default" id="title-sortable">추가</div> -->
										<div>
											<button type="button" class="ui-state-default11"
												id="title-sortable"
												onclick="location.href='/card/createcardForm'">add..</button>
										</div>

										<div id="card_wrap">
											<ul id="sortable1" class="connectedSortable">
												<!-- <li id="title-sortable" class="ui-state-default">이름</li> -->
 
												<li id="sortable_card" class="ui-state-default">last</li>
												<li id="sortable_card" class="ui-state-default">..</li>
												<li id="sortable_card" class="ui-state-default">Item 4</li>
												<li id="sortable_card" class="ui-state-default">Item 5</li>
											</ul>
										</div>
									</div>
								</div><button type="button" class="btn btn-info delete" href="#"  onclick="">검색</button>
							</li>
							<div id = "here">							
							</div>
							<li>
							<button id="addbutton" class="btn_c btn-info_c" style = "margin-top: 14px;">추가</button>
							</li>
						</ul>
					</div>
 
 

				</div>  
				

			<div id='project_user'>
				<iframe src="/project/memberlist" height="170" width="278" name=user> 
				 	<p>Your browser does not support iframes.</p>
				</iframe>
			</div>
			<div id='project-chat'>
				<iframe src="/project/imagelist" height="747" width="278" name=chat>
				 	<p>Your browser does not support iframes.</p>
				</iframe>
			</div>
			</div>
		</div>
	</div>
</body>
<%@ include file="./commons/bottom.jspf"%>
</div>
</html>