var webSocket = new WebSocket('ws://localhost:7070/chat.jsp');
var textarea = document.getElementById("messageWindow");
var inputMessage = document.getElementById('inputMessage');

webSocket.onerror = function(event) {
  onError(event)
};
webSocket.onopen = function(event) {
  onOpen(event);
};
webSocket.onmessage = function(event) {
    onMessage(event)
  };
 
  
function onMessage(event) {  //형근: 클라이언트가 보낸 메세지를 받을때
	var jsonDecode=JSON.parse(event.data);  //클라이언트가 보낸 메세지를 해석함
	
	if(jsonDecode[0].header=='Image'){  //형근: 상대방이 전달한것이 이미지일때 
	console.log("이미지 받음 :"+jsonDecode[0].data);
	saveActions();  //undo를 위해 현재 상태를 저장한다.
	
	// 형근: 상대방에서 받은 이미지를 출력하기 위한 코드 시작
	 var undoImg = new Image(); //형근: 이전상태 저장할 변수생성
		$(undoImg).load(   //형근: 이전상태 불러와서 저장
				function() {
					var context = document.getElementById("chat-image-area-canvas").getContext("2d");  //canvas의 2d 그림 컨텍스트를 얻어온다.
					context.drawImage(undoImg, 0, 0); //형근: 가져온 이전상태 이미지로 캔버스를 변경함
				});
		undoImg.src = jsonDecode[0].data; //형근: 히스토리에서 이전 이미지 빼서 초기화
	}	
	 // 형근: 상대방에서 받은 이미지를 출력하기 위한 코드 끝
	 
	 else if(jsonDecode[0].header=='undo'){  //형근: 상대방이 전달한것이 undo요청이면
		 undoDraw();
	 }
	 else if(jsonDecode[0].header=='sync'){  //형근: 상대방이 전달한것이 현재이미지 요청이면
		console.log("요청 받음");
		 imageSend();
	 }
	 else if(jsonDecode[0].header=='Text'){
		 textarea.value+=jsonDecode[0].user +": "+jsonDecode[0].data+'\n'; 
		 
	 }
	 else{   //clear일때
		 canvasInit(); //형근: 캔버스 초기화
	 }
}
function onOpen(event) {  //형근: 사용자가 새로 들어왔을때
	console.log("세션 열림");
	 textarea.value += "연결 성공\n";
  requestNowImage();  //형근: 현재 작업중인 이미지 화면 요청
}
function onError(event) {
  alert(event.data);
}
function requestNowImage(){
	var jsonEncode =new Array(
		{header:'sync'}		
	);
	console.log("요청 보냄");
	webSocket.send(JSON.stringify(jsonEncode));	
}
function textSend(){
	textarea.value+=now_user+": "+inputMessage.value+"\n";
    
	jsonEncode=new Array(
		{header:'Text' ,
		user:now_user,
		data:inputMessage.value
		}
	);
	webSocket.send(JSON.stringify(jsonEncode));
	inputMessage.value = "";
}
function imageSend() {  //형근: 이미지를 전송할때 
	var jsonEncode = new Array(
		{header:'Image',
		data: document.getElementById("chat-image-area-canvas").toDataURL("image/png")	
		}
	);
	webSocket.send(JSON.stringify(jsonEncode));  //형근: 캔버스에 그림을 그릴 때마다 서버에 이미지를 전송
}
function undoSend() {  //형근: undo 명령을 전송할때
	var jsonEncode = new Array(
		{header:'undo'}		
	);
    webSocket.send(JSON.stringify(jsonEncode));  //형근: 캔버스에 변화를 줄때마다 상태가 저장된 히스토리 전송
}
function clearSend() {  //형근: clear명령을 전송할때
	var jsonEncode=new Array(
		{header:'clear'}	
	);
    webSocket.send(JSON.stringify(jsonEncode));  //형근: 캔버스에 변화를 줄때마다 상태가 저장된 히스토리 전송
}
