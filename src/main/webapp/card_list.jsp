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
<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>

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
				<button type="button" class="btn btn-info" onclick="location.href='/board/boardlist?projectName=${projectName}'">
				목록
				</button>
				<button type="button" class="btn btn-info" href="#" data-toggle="modal" data-target="#invite">초대</button>
				<button type="button" class="btn btn-info" href="#" class="btn btn-default">EXIT</button>
				 <%@include file = "./modalpage/invite.jsp" %>
				
			  </div>
			</div>
		  </div>  
		<div id="card-container" style = "overflow-x: auto;"> 
			  	<div id="tt" style="overflow:auto; width:10000px;    text-align: left;">  
					<%@include file = "/modalpage/card.jsp"%>	
					<ul id="sortable_box" class = "boxsort">  	
						<c:forEach var="list" items="${lists}" varStatus="status">
							<li id="${list.listOrder }" class="ui-state-default card_margin currentListNum">
									<div class="card_wrap_top">
										<div>
											<textarea class="list_name" onkeydown="resize(this)" onkeyup="resize(this)" spellcheck="false" dir="auto" maxlength="512">${list.listName}</textarea>
										</div>
										<div>
											<a id="${list.listNum }-card-add-btn" class="btn btn-default" data-toggle="modal" href="#cardmodal">카드 추가</a>
											<script>
												//형근: 카드를 생성할 때
												$('#${list.listNum }-card-add-btn').click(function(){
													cardListNum=${list.listNum};
													currentCardOrder=$('#${list.listNum} li').length;
													console.log(cardOrder);
													console.log(cardListNum);
													addCard();
												});
											</script>
										</div>
							  		</div>
							  	
									<div >
										<div class="card_wrap">
											<ul id="${list.listNum}" class="connectedSortable sort_css">  
												<c:forEach var="card" items="${list.cards}" varStatus="status">
													<li id="${card.cardNum }"class="ui-state-default">
													<a id="${card.cardNum}-card-view-btn" class="card-modal-btn" data-toggle="modal" href="#cardmodal">${card.subject}</a>
													
													</li>
													<script>
														//형근: 카드 내용을 읽고 수정 할때
														$(document).ready(function(){
															$('#${card.cardNum}-card-view-btn').click(function(){
																currentCardNum=${card.cardNum};
																viewCard();
															});
														});
													</script> 
												</c:forEach>
											</ul>
										</div>
									</div>
									<button type="button" class="btn btn-info_c" href="#"  onclick="location.href='/lists/removeList?boardNum=${param.boardNum}&listOrder=${list.listOrder }'" style = "margin-bottom: 5px;">삭제</button>
								</li>		
							</c:forEach>
						</ul>
						<button id ="addbutton" class="btn btn-info_c" type="button" data-toggle="collapse" data-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample" >
							리스트 추가
						</button>
						<div class="collapse" id="collapseExample" style="float:left;">
						  <div class="well">
							<form method="post" action="/lists/addList">
							  <div class="form-group">
							    <label for="newListName">리스트 이름</label>
							    <input type="text" class="form-control" name="listName" placeholder="리스트 이름을 입력하세요">
							    <input type="hidden" class="form-control" name="boardNum" value="${param.boardNum }"/>
							    <input id="currentListNum" type="hidden" class="form-control" name="listOrder" />
								<script>document.getElementById('currentListNum').value=''+$('.currentListNum').length;</script>
							  </div>
							  <button type="submit" class="btn btn-default" >생성</button>
							 </form>
						  </div>
						</div>
						
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

<script>
//카드 ajax
var currentCardNum; //형근: 클릭한 카드 번호를 저장할 변수

function viewCard(){
	$.ajax({
		type:'get',
		data:{
			cardNum:currentCardNum
		},
		url:'/cards/viewcard',
		dataType:'json',
		success:function (data){
			$('#card-field').attr("action","/cards/updatecard");
			$('#Title').html("<h2>View_Card</h2>");
			$('#cardNum').val(data.cardNum);
			$('#card-user').html("<input type='hidden' name='userId' value='"+data.userId+"'>"+data.userId+"</input>");
			$('#cardSubject').val(data.subject);
			$('#cardContent').val(data.content);
			var btn="";
			btn+="<input type='submit' class='btn btn-default' value='Modify'/>";
			btn+="<input type='reset' class='btn btn-default' value='Reset' />";
			btn+="<input type='button' class='btn btn-default'  value='List' data-dismiss='modal' />";
			btn+="<input type='button' id='delete-card' class='btn btn-default' value='Delete' />";
			$('#btn-area').html(btn);
			$('#delete-card').attr("onclick","location.href='/cards/removecard?num="+data.cardNum+"'");
		},
		error:ajaxError
	});
}

var cardListNum;  //형근: 새로 생성할 카드의 리스트 번호를 저장할 변수
var currentCardOrder//형근: 리스트에 추가될 카드의 순서번호를 저장할 변수 

function addCard(){
	$.ajax({
		type:'get',
		data:{
			listNum:cardListNum,
			cardOrder:currentCardOrder
		},
		url:"/cards/createcardForm",
		dataType:'json',
		success:function (data){
			$('#cardSubject').val(data.subject); //카드 읽기에서 가져온 데이터는 삭제
			$('#cardContent').val(data.content);
			$('#card-field').attr("action","/cards/createcard");
			$('#Title').html("<h2>CREATE_Card</h2>");
			$('#card-user').html("<input type='hidden' name='userId' value='${userId}'>${userId}</input>");
			$('#cardListNum').val(cardListNum);
			$('#cardOrder').val(currentCardOrder);
			var btn="";
			btn+="<input type='submit' class='btn btn-default' value='Submit'/>";
			btn+="<input type='reset' class='btn btn-default' value='Reset' />";
			btn+="<input type='button' class='btn btn-default'  value='List' data-dismiss='modal' />";
			$('#btn-area').html(btn);
		},
		error:ajaxError
	});
}

function ajaxError(){
	alert("데이터 로딩 오류");
}
</script>

 <script>
 var listNum;
  //리스트의 이동
  $( function() {
       $( ".boxsort" ).sortable({
            update: function(event, ul) { 
            	//listNum=ul.item.index();
                 console.log('list update: '+ ul.item.index())
                 
             },
             start: function(event, ul) {
            	 //listNum=ul.item.index();
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

<script>
	
</script>