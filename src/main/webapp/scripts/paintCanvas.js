//그림판 js파일   

var undoHistory = [];  //형근: 그림상태 히스토리 변수 초기화
function saveActions() {  //형근: 그릴때마다 히스토리에 저장하는 함수
	var imgData = document.getElementById("chat-image-area-canvas").toDataURL("image/png"); 
	undoHistory.push(imgData); //형근: 현재 캔버스의 그림 상태를 imgData에 저장
	$('#undo').removeAttr('disabled'); //형근: 히스토리에 현재 상태가 추가되므로 undo버튼 무력화 해제
}

function undoDraw() {  //형근: undo를 눌렀을때 수행하는 그림 되돌리기 함수
	if (undoHistory.length > 0) { //형근: history에 담긴게 있으면
		var undoImg = new Image(); //형근: 이전상태 저장할 변수생성
		$(undoImg).load(   //형근: 이전상태 불러와서 저장
				function() {
					var context = document.getElementById("chat-image-area-canvas").getContext("2d");  //canvas의 2d 그림 컨텍스트를 얻어온다.
					context.drawImage(undoImg, 0, 0); //형근: 가져온 이전상태 이미지로 상태를 변경함
				});
		undoImg.src = undoHistory.pop(); //형근: 히스토리에서 이전 이미지 제거
		if (undoHistory.length == 0)  //형근: 만약 히스토리가 0이되면 undo 버튼 무력화
			$('#undo').attr('disabled', 'disabled');
	}
}

function canvasInit() {  //형근: 캔버스 초기화 함수
	context = document.getElementById("chat-image-area-canvas").getContext("2d"); //형근: 캔버스의 2d context를 불러온다. 
	context.lineCap = "round"; //형근: 엔드 캡을 원선으로
	context.fillStyle = '#fff';  //형근: 캔버스 배경을 채울색깔= 흰색
	context.fillRect(0, 0, context.canvas.width, context.canvas.height);  //형근: 캔버스 크기만큼 사각형 그림
	$('#undo').attr('disabled','disabled'); //형근: 히스토리에 값이 비므로 버튼 무력화 시킴 
}

function draw(event) {  //형근: 그리기에 쓰이는 터치 이벤트 리스너
	var coors = {
		x : event.targetTouches[0].pageX,  //현재 위치
		y : event.targetTouches[0].pageY
	};
	drawer[event.type](coors);  //형근: 이벤트에 따라 마우스의 좌표를 저장
}

$(function() {  //형근: 그림을 그리는데 필요한 리스너들에 대한 설정
	var canvas, cntxt, top, left, draw = 0;
	canvas = document.getElementById("chat-image-area-canvas"); //형근: 캔버스 요소를 가져옴
	cntxt = canvas.getContext("2d"); //형근: 캔버스의 2d context를 불러옴
	top = $('#chat-image-area-canvas').offset().top;  //형근: y=0
	left = $('#chat-image-area-canvas').offset().left;  //형근: x=0
	canvasInit();

	canvas.addEventListener('touchstart', draw, false);
	canvas.addEventListener('touchmove', draw, false);
	canvas.addEventListener('touchend', draw, false);

	var drawer = {
		isDrawing : false,
		touchstart : function(coors) {  //형근: 터치가 시작될때
			cntxt.beginPath();  //형근: 그리기 시작
			cntxt.moveTo(coors.x, coors.y);  //형근: coors.x,coors.y 좌표로 시작점 지정
			this.isDrawing = true; //형근: 그리는중 표시
		},
		touchmove : function(coors) {  //형근: 터치중 움직일때
			if (this.isDrawing) {
				cntxt.lineTo(coors.x, coors.y);  //형근: coors.x,coors.y 좌표로 이동경로 지정
				cntxt.stroke();   //형근: 이동경로에 윤곽선 그리기
			}
		},
		touchend : function(coors) {  //형근: 터치가 끝나면
			if (this.isDrawing) {  //형근: 그리는 중일때
				this.touchmove(coors);  //형근: 마지막 터치 움직임
				this.isDrawing = false;  //형근: 그리기 종료 표시
			}
		}
	};

	document.body.addEventListener('touchmove', function(event) {  //형근: 터치하며 움직이는 중일때 다른 요소에는 스크롤이 안되도록 제한
		event.preventDefault();
	}, false);

	//형근: 그리기에 대한 코드들
	$('#chat-image-area-canvas').mousedown(function(e) {  //형근: 마우스 다운 리스너  
		if (e.button == 0) { //형근: 마우스를 눌렀을때
			draw = 1;  //형근: 그리기
			saveActions();  //형근: 현재의 그림 상태 저장  (원래 함수위치)
			cntxt.beginPath();  //형근: 그리기 시작
			cntxt.moveTo(e.pageX - left, e.pageY - top); //형근: 좌표로 시작점 지정
		} else { //형근: 마우스 누름 땠을때
			draw = 0; //형근: 그리기 종료 
		}
	}).mouseup(function(e) {  //형근: 마우스 다운 리스너 안에 마우스 업 리스너
		if (e.button != 0) {  //형근: 마우스 때기 안했으면  
			draw = 1; //형근: 그리는중표시
		} else {   
			draw = 0;  //형근: 그리기 종료표시
			cntxt.lineTo(e.pageX - left + 1, e.pageY - top + 1);  //형근: 좌표의 이동경로
			cntxt.stroke();  //형근: 윤곽선 그리기
			cntxt.closePath();  //형근: 그리기 끝을 알림 시작좌표와 마지막 좌표를 자동으로 연결해준다.
			console.log("마우스땜");
			imageSend();  //형근: 현재 이미지 상태를 전송
		}
	}).mousemove(function(e) {//형근: 마우스 다운 리스너안에 마우스 업리스너 안에 마우스 무브 리스너
		if (draw == 1) { //형근: 그리는 중일때
			cntxt.lineTo(e.pageX - left + 1, e.pageY - top + 1); //형근: 좌표의 이동경로
			console.log("좌표: "+(e.pageX - left + 1)+","+ (e.pageY - top + 1));
			cntxt.stroke();  //형근: 윤곽선 그리기
		}
	});

	//형근: 캔버스 하위 메뉴들에 대한 코드
	$('#export').click(  //형근: Export 버튼 클릭시 그려진 그림 출력
			function(e) {
				e.preventDefault();
				window.open(canvas.toDataURL(), 'Canvas Export','height=400,width=400'); //형근: 새창에 그림 출력
				// console.log(canvas.toDataURL());
			});
	$('#clear').click(function(e) {  //형근: Reset버튼 클릭시 그림 초기화
		e.preventDefault();  //형근: 키 효과 유전 방지
		canvas.width = canvas.width;   //형근: 캔버스 초기화
		canvas.height = canvas.height;  //형근: 캔버스 초기화 
		canvasInit(); //형근: 캔버스 초기화
		$('#chat-image-area-colors li:first').click();  //형근: 칼라는 첫번째 li로
		$('#brush_size').change();  //형근: 브러쉬 크기는 변한다.
		undoHistory = []; //형근: 히스토리 초기화
		clearSend();
	});
	$('#brush_size').change(function(e) { //형근: 브러쉬 크기 변할때
		cntxt.lineWidth = $(this).val();  //형근: 현재 바의 길이값을 갱신
	});
	$('#chat-image-area-colors li').click(function(e) {  //형근: 색상 클릭시
		e.preventDefault();
		$('#chat-image-area-colors li').removeClass('selected'); //형근: 이전에 선택돼있던 색깔 지우고
		$(this).addClass('selected');  //형근: 클릭한 색깔 저장
		cntxt.strokeStyle = $(this).css('background-color');  //형근: 윤곽선 색깔로 지정
	});

	$('#undo').click(function(e) { //형근: undo버튼 클릭시
		e.preventDefault();
		undoDraw();  //형근: undoDraw함수 호출
		undoSend();
	});
	
	$('#disconnect').click(function(e){
		e.preventDefault();
		webSocket.close();
	});

})
