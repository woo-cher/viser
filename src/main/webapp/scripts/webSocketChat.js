var webSocket = new WebSocket('ws://localhost:7070/chat.jsp');
var textarea = document.getElementById("messageWindow");
var inputMessage = document.getElementById('inputMessage');
var imageId;

webSocket.onerror = function(event) {
  onError(event)
};
webSocket.onopen = function(event) {
  onOpen(event);
};
webSocket.onmessage = function(event) {
    onMessage(event)
  };
 
  
function onMessage(event) {
	var jsonDecode=JSON.parse(event.data);
	
	if(jsonDecode[0].header=='Image'){
	console.log("이미지 받음 :"+jsonDecode[0].data);
	saveActions();
	
	 var undoImg = new Image();
		$(undoImg).load(
				function() {
					var context = document.getElementById("chat-image-area-canvas").getContext("2d");
					context.drawImage(undoImg, 0, 0);
				});
		undoImg.src = jsonDecode[0].data;
	}	
	 
	 else if(jsonDecode[0].header=='undo'){
		 undoDraw();
	 }
	 else if(jsonDecode[0].header=='sync'){
		console.log("요청 받음");
		 imageSend();
		 clickSync();
	 }
	 else if(jsonDecode[0].header=='Text'){
		 textarea.value+=jsonDecode[0].user +": "+jsonDecode[0].data+'\n'; 
		 
	 }
	 else if(jsonDecode[0].header=='click'){
		 imageId=jsonDecode[0].data;
		 if(imageId!=''&&imageId!=null){
		 $('#chat-image-list-display button').removeClass('selected');
		 var clickcontext=document.getElementById(imageId);
		 clickcontext.className='selected';
		 }
	 }
	 else if(jsonDecode[0].header=='reload'){
		 window.location.reload();
	 }
	 else{
		 canvasInit();
		 $('#chat-image-list-display button').removeClass('selected');
	 }
}
function onOpen(event) {
	console.log("세션 열림");
	 textarea.value += "연결 성공\n";
  requestNowImage();
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
function imageSend() {
	var jsonEncode = new Array(
		{header:'Image',
		data: document.getElementById("chat-image-area-canvas").toDataURL("image/png")	
		}
	);
	webSocket.send(JSON.stringify(jsonEncode));
}
function undoSend() {
	var jsonEncode = new Array(
		{header:'undo'}		
	);
    webSocket.send(JSON.stringify(jsonEncode));
}
function clearSend() {
	var jsonEncode=new Array(
		{header:'clear'}	
	);
    webSocket.send(JSON.stringify(jsonEncode));
}
function clickSync(){
	var jsonEncode=new Array(
		{header:'click',
		data:imageId
		}	
	);
	webSocket.send(JSON.stringify(jsonEncode));
}
function reloadSend(){
	var jsonEncode=new Array(
		{header:'reload'}	
	);
	webSocket.send(JSON.stringify(jsonEncode));
}
