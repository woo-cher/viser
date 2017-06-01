<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel='stylesheet' href='/stylesheets/chat.css' type='text/css'>




<script>
	var now_user="<%=(String) session.getAttribute("userId")%>"; //형근: 채팅에 유저아이디를 출력 하기위해 사용하는 변수
</script>
</head><body>
	<div id="chat-image">
		<div id="chat-image-area">
			<canvas id="chat-image-area-canvas" width="250" height="250"></canvas>
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
					<label for="brush">선의 두께:</label> <input name="brush"
						id="brush_size" type="range" value="5" min="0" max="100" />
				</div>
				<div id="chat-image-area-tool-control">
					<button id="undo" href="#" disabled="disabled">Undo</button>
					<button id="clear" href="#">Reset</button>
					<button id="export" href="#">Export as Image</button>
				</div>
			</div>
			<div id="chat-image-list">
				<div id="chat-image-list-header">이미지 목록</div>
				<div id="chat-image-list-display">이미지 1 이미지 2</div>
				<div id="chat-image-list-control">
					<button id="image_add" onclick=popupOpen()>이미지 추가</button>
					<button id="image_delete" onclick>이미지 삭제</button> 
<!-- 					 <div id="button">
						<div class="controls">
												<button type="button" onclick="location.href='/users/createForm'" class="btn btn-primary">Sign up</button>
							<button type="button" onclick="popupOpen();" class="btn btn-danger">Sign up</button>
							<button type="button" class="btn btn-danger" data-toggle="modal"
								data-target="#myModal">Sign up</button>
							<button type="submit" class="btn btn-success">Sign in</button>
							환용 : 어떻게 아이프레임에 모달을 적용할 것인가?
						</div>
					</div>  -->
				</div>
			</div>
		</div>
		<div id="chat-dialogue">
			<div id="chat-dialogue-list">
				<textarea id="messageWindow" rows="15" cols="35" readonly="true"></textarea>
				<br />
			</div>
			<div id="chat-dialogue-input">
				<!-- <input id="inputMessage" type="text" onKeyPress="javascript:if(event.keyCode == 13) { send() }"/>
          -->
				<input id="inputMessage" type="text"
					onKeyPress="javascript:if(event.keyCode == 13) { textSend() }" />
				<input id="send" type="submit" value="send" onclick="textSend()" />
			</div>
		</div>
	</div>
</body>

<script>
	function popupOpen() { //형근: 이미지 업로드창을 띄워줄 스크립트 함수

		var popUrl = "/upload.jsp?perposeURL=/imageUpload"; //팝업창에 출력될 페이지 URL

		var popOption = "width=500, height=250, resizable=no, scrollbars=no, status=no;"; //팝업창 옵션(optoin)

		window.open(popUrl, "", popOption);
	};
</script>

<!-- 채팅 js파일 -->
<script src="/scripts/webSocketChat.js"></script>
<!-- 그림판을 위한 js파일 -->
<script src="http://reali.kr/js/jquery.min.js"></script>
<script src="/scripts/paintCanvas.js"></script>


</html>