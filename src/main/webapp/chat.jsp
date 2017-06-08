<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<head>
<link href="/stylesheets/index.css?" rel="stylesheet" type="text/css">
<link rel='stylesheet' href='/stylesheets/chat.css' type='text/css'>


<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js">
</script>


<!-- jquery -->
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<!-- jquery ui -->
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<!-- bootstrap -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous"> -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
	integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
	crossorigin="anonymous"></script>




<script>
	var now_user="<%=(String)session.getAttribute("userId") %>;" //형근: 채팅에 유저아이디를 출력 하기위해 사용하는 변수
</script>
</head><body style="background: azure;">
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
					<label for="brush" style = "color:black">  선의 두께:</label> <input name="brush"
						id="brush_size" type="range" value="5" min="0" max="100" />
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
					<button id="image_add" onclick=popupOpen()>이미지 추가</button>
					<button id="image_delete" onclick=deleteImage()>이미지 삭제</button>
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

<!-- 그림판을 위한 js파일 -->  
<script src="http://reali.kr/js/jquery.min.js"></script>  
<script src="/scripts/paintCanvas.js"></script>
<!-- 채팅 js파일 -->
<script src="/scripts/webSocketChat.js"></script>
<script>
	function message(){
		alert('업로드 성공');
		reloadSend(); //형근: 다른 사용자들 화면도 갱신 
	}

	function popupOpen() { //형근: 이미지 업로드창을 띄워줄 스크립트 함수

		var popUrl = "/upload.jsp?perposeURL=/imageUpload"; //팝업창에 출력될 페이지 URL

		var popOption = "width=500, height=250, resizable=no, scrollbars=no, status=no;"; //팝업창 옵션(optoin)

	window.open(popUrl,"",popOption);
 }
</script>

<!-- 형근: 이미지 클릭과 제거에 대한 스크립트 -->
<script>
//형근: 이미지 버튼을 눌렀을때
 $('#chat-image-list-display button').click(function(e){
	e.preventDefault();
	$('#chat-image-list-display button').removeClass('selected'); //형근: 기존에 선택된 효과 제거
	$(this).addClass('selected');  //형근: 클릭한 요소에 selected클래스 추가하여 클릭효과 적용
	imageId = this.getAttribute('id');  //형근: 클릭한 버튼의 id로 경로를 가져옴
	var clickImg = new Image(); //형근: 저장할 이미지 객체생성
	clickImg.src =imageId; //형근: 클릭한 이미지url로 설정
	var context = document.getElementById("chat-image-area-canvas").getContext("2d");  //canvas의 2d 그림 컨텍스트를 얻어온다.
	context.drawImage(clickImg, 0, 0,270,240); //형근: 클릭한 이미지로 배경을 변경함
	
	imageSend();   //형근 : 클릭한 그림 다른 클라이언트들에게도 전송
	clickSync();   //형근 : 클릭한 그림 클릭 효과 맞춤
 });
 
 //형근: 이미지 삭제를 눌렀을때
 function deleteImage(){
	 location.href='/project/imagedelete?Image_Path='+imageId;
	 canvasInit(); //형근: 캔버스 초기화
	 imageId='';  //형근:클릭된 이미지 경로 초기화
	 clearSend();  //형근: 클라이언트 들의 페이지도 초기화
	 clickSync();   //형근 : 클릭한 그림 클릭 효과 맞춤
	 reloadSend(); //형근: 다른 사용자들 화면도 갱신 
 }
</script>

</html>