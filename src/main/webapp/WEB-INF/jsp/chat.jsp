<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<head>
<link href="/stylesheets/index.css?" rel="stylesheet" type="text/css">
<link rel='stylesheet' href='/stylesheets/chat.css' type='text/css'>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
	
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
	integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
	crossorigin="anonymous">
</script>

<script>
	var now_user="<%=(String)session.getAttribute("userId") %>;"
</script>
</head>
<body style="background: azure;">
	<div id="chat-image">
		<div id="chat-image-area" >
			<canvas id="chat-image-area-canvas" width="270" height="220"></canvas>
			<ul id="chat-image-area-colors">
				<li style="background-color: black;"></li>
				<li style="background-color: red;"></li>
				<li style="background-color: green;"></li>
				<li style="background-color: brown;"></li>
				<li style="background-color: #d2232a;"></li>
				<li style="background-color: #fcb017;"></li>
				<li style="background-color: #fff460;"></li>
				<li style="background-color: #F43059;"></li>
				<li style="background-color: #82B82C;"></li>
				<li style="background-color: #0099FF;"></li>
				<li style="background-color: #ff00ff;"></li>
			</ul>
			<div id="chat-image-area-tool">
				<div id="chat-image-area-tool-brush">
				<label for="brush" style = "color:black">  선의 두께:</label> 
 				<input name="brush"  id="brush_size" type="range" value="5" min="0" max="100" style = "margin-bottom: 5px"/>
				</div>
				<div id="chat-image-area-tool-control">
					<button id="undo" class="btn-chat btn-info" href="#" disabled="disabled">Undo</button>
					<button id="clear" class="btn-chat btn-info" href="#">Reset</button>
					<button id="export" class="btn-chat btn-info" href="#">Export as Image</button>
				</div>
			</div>
			<div id="chat-image-list">
				<div id="chat-image-list-header">이미지 목록</div>
				<div id="chat-image-list-display">
				  <c:forEach var="path" items="${imageList}">
				  <button type="button" id="${path }"><img src="${path }"></button>
				  </c:forEach>
				</div>
				<div id="chat-image-list-control">
					<button id="image_add" class="btn-chat btn-info" data-toggle="modal" href="#uploadmodal">이미지 추가</button>
					<button id="image_delete" class="btn-chat btn-info" onclick=deleteImage()>이미지 삭제</button>
				</div>
			</div>
		</div>
		<div id="chat-dialogue" style = "color : black">
			<div id="chat-dialogue-list">
				<textarea id="messageWindow" class="form-control" rows="12" cols="26" readonly="true" style = "background-color:white"></textarea>
				<br />
			</div>
			<div id="chat-dialogue-input">
				<input id="inputMessage" class="form-control" type="text" onKeyPress="javascript:if(event.keyCode == 13) { textSend() }" style = "width : 70%; display:inline-table" />
				<input id="send" class="btn-chat btn-info" type="submit" value="send" onclick="textSend()" />
			</div>
		</div>
	</div>
</body>

<script>
	$('#image_add').click(function(){
		parent.upload_popup();
	})
</script>

<script src="http://reali.kr/js/jquery.min.js"></script>  
<script src="/scripts/paintCanvas.js"></script>
<script src="/scripts/webSocketChat.js"></script>

<script>
	function message(){
		alert('업로드 성공');
		reloadSend();
	}
	function popupOpen() {
		var popUrl = "/upload.jsp?perposeURL=/imageUpload";
		var popOption = "width=500, height=250, resizable=no, scrollbars=no, status=no;";
	window.open(popUrl,"",popOption);
 }
</script>

<script>
 $('#chat-image-list-display button').click(function(e){
	e.preventDefault();
	$('#chat-image-list-display button').removeClass('selected');
	$(this).addClass('selected');
	imageId = this.getAttribute('id');
	var clickImg = new Image();
	clickImg.src =imageId;
	var context = document.getElementById("chat-image-area-canvas").getContext("2d");
	context.drawImage(clickImg, 0, 0,270,240);
	
	imageSend();
	clickSync();
 });
 
 function deleteImage(){
	 location.href='/project/imagedelete?Image_Path='+imageId;
	 canvasInit();
	 imageId='';
	 clearSend();
	 clickSync();
	 reloadSend();
 }
</script>
</html>