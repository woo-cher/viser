<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<head>
<link href="/stylesheets/style.css?" rel="stylesheet" type="text/css">

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
               <button id="undo" class="btn-info" href="#" disabled="disabled">Undo</button>
               <button id="clear" class="btn-info" href="#">Reset</button>
               <button id="export" class="btn-info" href="#">Export as Image</button>
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
               <button id="image_add" class="btn-info" data-toggle="modal" onclick="addImage()">이미지 추가</button>
               <button id="image_delete" class="btn-info" onclick="deleteImage()">이미지 삭제</button>
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
            <input id="send" class="btn-info" type="submit" value="send" onclick="textSend()" />
         </div>
      </div>
   </div>
   
   <div class="modal fade" id="upload-modal" tabindex="-1" role="dialog" aria-labelledby="myUpload" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		      <div class="modal-body">
				<h2 class="modal-title" id="MyModalLabel"></h2>
				<form action="/imageUpload" method="post" enctype="multipart/form-data">
			
					첨부파일:<input type="file" class="form-control" name="uploadFile" value="보내기" style="margin-left: 17px" ><br/>
					<input type="submit" class="form-control" value="보내기 " style= "margin-top: 68px;"/>
				</form>
			  </div>
		  </div>
		</div>
	  </div>
   </div>
</body>

<script src="http://reali.kr/js/jquery.min.js"></script>  
<script src="/scripts/paintCanvas.js"></script>
<script src="/scripts/webSocketChat.js"></script>


</html>