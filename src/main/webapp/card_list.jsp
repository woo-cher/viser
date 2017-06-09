<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="viser.card.*"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>jQuery UI Sortable - Display as grid</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

 <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
 <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>




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

			   	<div id="tt" style="width:10000px;overflow-x: auto;    text-align: left;">  
					<ul id="sortable_box" class = "boxsort">  
						<c:forEach var="list" items="${lists}" varStatus="status">
							<li id = "${status.index }" class="ui-state-default  card_margin">
									<div class="card_wrap_top">
										<div>
											<textarea class="list_name" onkeydown="resize(this)" onkeyup="resize(this)" spellcheck="false" dir="auto" maxlength="512">${list.listName}</textarea>
										</div>
										<div>
											<button type="button" class="ui-state-default add_card" id="title-sortable" onclick="location.href='/card/createcardForm?listNum=&cardOrder='">add..</button>  
										</div>
							  		</div>
							  	
									<div >
										<div class="card_wrap">
											<ul id=" ${list.listName }" class="connectedSortable sort_css">  
												<c:forEach var="card" items="${list.cards}">
													<li class="ui-state-default"><a href="/card/viewcard?num=${card.cardNum }">${card.subject}</a></li>
												</c:forEach>
											</ul>
										</div>
									</div>
									<button type="button" class="btn btn-info_c delete" href="#"  onclick="" style = "margin-bottom: 5px;">삭제</button>
								</li>		
							</c:forEach>
						</ul>
						<button type="button" id ="addbutton" class="btn btn-info_c" href="#"  onclick="" style = "margin-top: 16px;">추가</button>
				</div>
			
 		</div>  
				

			<div id='project_user'>
				<iframe src="/project/memberlist" height="170" width="290" name=user> 
				 	<p>Your browser does not support iframes.</p>
				</iframe>
			</div>
			<div id='project-chat'>
				<iframe src="/project/imagelist" height="850" width="290" name=chat>
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



<!-- 근    아래    짱 -->
 <script>
 
  //리스트의 이동
  $( function() {
       $( ".boxsort" ).sortable({
            update: function(event, ul) { 
                 console.log('list update: '+ ul.item.index())
             },
             start: function(event, ul) { 
                 console.log('list start: ' + ul.item.index())
             }
         });
       
       $(".card_wrap_top").disableSelection();
  } );
  
  //리스트간 카드 이동
  $( function() {
    $( ".connectedSortable" ).sortable({
      connectWith: ".connectedSortable"
    }).disableSelection();

  } ); 
  
  
  </script>
  
  <!-- 형근: textarea 영역을 늘여주는 함수 --> 
  <script>
function resize(obj) {
  obj.style.height = "1px";
  obj.style.height = (12+obj.scrollHeight)+"px";
}
</script>
