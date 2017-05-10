/**
 * 
 */
function press(f){
	 if(f.keyCode == 13){ //javascript에서는 13이 enter키를 의미함 
		 inputMessage.submit(); //formname에 사용자가 지정한 form의 name입력  
	 	}
	 } 


var textarea = document.getElementById("messageWindow");
 var webSocket = new WebSocket('ws://localhost:7070/card/cardlist');
 var inputMessage = document.getElementById('inputMessage');
    webSocket.onerror = function(event) {
      onError(event)
    };
    webSocket.onopen = function(event) {
      onOpen(event)
    };
    webSocket.onmessage = function(event) {
        onMessage(event)
      };
    function onMessage(event) {
        textarea.value += "상대 : " + event.data + "\n";
    }
    function onOpen(event) {
        textarea.value += "연결 성공\n";
    }
    function onError(event) {
      alert(event.data);
    }
    function send() {
        textarea.value += "나 : " + inputMessage.value + "\n";
        webSocket.send(inputMessage.value);
        inputMessage.value = "";
    }