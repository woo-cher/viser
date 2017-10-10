<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- Main Footer -->
  <footer class="main-footer">
    <!-- Default to the left -->
    <strong>Copyright &copy; 2017 <a href="#">Team Runtime</a>.</strong> All rights reserved.
  </footer>

  <!-- Right Sidebar -->
  <aside class="control-sidebar control-sidebar-dark">
    <!-- Create the tabs -->
    <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
      <li class="active"><a href="#control-sidebar-chat-tab" data-toggle="tab"><i class="fa fa-exchange"></i></a></li>
      <li><a href="#control-sidebar-album-tab" onclick="getImageList()" data-toggle="tab"><i class="fa fa-picture-o"></i></a></li>
    </ul>
    <!-- Tab panes -->
    <div class="tab-content">
      <!-- Chat tab content -->
      <div class="tab-pane active" id="control-sidebar-chat-tab">
        <ul class="control-sidebar-menu">
          <li>
          	<script>
			   var now_userId='${user.userId}';
			   var now_userProfile='${user.image}';
			</script>
			     <div class="box box-primary">
		            <div class="box-header with-border">
		              <h3 class="box-title">그림판</h3>
		
		              <div class="box-tools pull-right">
		                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
		                </button>
		              </div>
		              <!-- /.box-tools -->
		            </div>
		            <!-- /.box-header -->
		            <div class="box-body" style="padding:0px;">
		              <div id="chat-image">
					      <div id="chat-image-area" >
					         <canvas id="chat-image-area-canvas" width="230px" height="200px"></canvas>
					         <!-- Color Picker -->
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
					      </div>
		   			 </div>
		            </div>
		            <!-- /.box-body -->
		          </div>
             
          </li>
        </ul>
        <!-- /.control-sidebar-menu -->
		<ul class="control-sidebar-menu">
          <li>
			<div class="box box-primary direct-chat direct-chat-primary">
	            <div class="box-header with-border">
	              <h3 class="box-title">채팅창</h3>
	
	              <div class="box-tools pull-right">
	                <span data-toggle="tooltip" title="3 New Messages" class="badge bg-light-blue">3</span>
	                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
	                </button>
	                <button type="button" class="btn btn-box-tool" onclick="getProjectMember()" data-toggle="tooltip" title="Contacts" data-widget="chat-pane-toggle">
	                  <i class="fa fa-user"></i></button>
	              </div>
	            </div>
	            <!-- /.box-header -->
	            <div class="box-body">
	              <!-- Conversations are loaded here -->
	              <div class="direct-chat-messages">
	              </div>
	              <!--/.direct-chat-messages-->
	
	              <!-- Contacts are loaded here -->
	              <div class="direct-chat-contacts">
	              	<ul class="contacts-list"></ul>
	              </div>
	              <!-- /.direct-chat-pane -->
	            </div>
	            <!-- /.box-body -->
	            <div class="box-footer">
	                <div class="input-group">
	                  <input type="text" id="inputMessage" name="message" placeholder="Type Message ..." class="form-control" onKeyPress="javascript:if(event.keyCode == 13) { textSend() }">
	                      <span class="input-group-btn">
	                        <button type="button" id="send" class="btn btn-primary btn-flat" onclick="textSend()">Send</button>
	                      </span>
	                </div>
	            </div>
	            <!-- /.box-footer-->
	          </div>
	          <!--/.direct-chat -->
			</li>
		</ul>
        
		<script src="/scripts/paintCanvas.js"></script>
		<script src="/scripts/webSocketChat.js"></script>
		
        <!-- /.control-sidebar-menu -->
			
      </div>
      <!-- /.tab-pane -->
      	
      
      <!-- Album tab content -->
      
      <!-- upload modal -->
	    <div class="modal fade" id="upload-modal" tabindex="-1" role="dialog" aria-labelledby="upload-modal" aria-hidden="true">
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
      
      <div class="tab-pane" id="control-sidebar-album-tab">
        <h3 class="control-sidebar-heading">이미지 목록</h3>
        	<div id="chat-image-list">
	            <div id="chat-image-list-control">
	               <button id="image_add" class="btn-info" data-toggle="modal" onclick="addImage()">이미지 추가</button>
	               <button id="image_delete" class="btn-info" onclick="checkImageClick()">이미지 삭제</button>
	            </div>
	            <div id="chat-image-list-display">
	            </div>
	         </div>
      </div>
      
     <script>
     function getProjectMember(){
    	 $.ajax({
    		 type:'get',
    		 dataType:'json',
    		 url:'/project/memberlist',
    		 success:function(data){
    			 $('.contacts-list').empty();
    			 var userPower=JSON.parse(data.isMaster);
    			 JSON.parse(data.list).forEach(function(item){
    				 var temp='';
    				 temp+="<li class='dropdown'>";
                     temp+="<a class='dropdown-toggle' data-toggle='dropdown' href='#'>";
                     temp+="<img class='contacts-list-img' src='"+item.userImage+"' alt='User Image'>";
                     temp+="<div class='contacts-list-info'>";
                     temp+="<span class='contacts-list-name'>";
                     temp+=item.userId;
                     temp+="<small class='contacts-list-date pull-right'>"+powerToString(item.power)+"</small>";
                     temp+="</span>";
                     temp+="<span class='contacts-list-msg'>"+item.userName+"</span>";
                     temp+="</div>";
                   	 temp+="</a>";
                     temp+="<ul class='dropdown-menu'>";
                     temp+="<li role='presentation'><a role='menuitem' tabindex='-1' href='#'>정보보기</a></li>";
                     if(userPower==true && item.userId!=='${user.userId}'){
	                     temp+="<li role='presentation' class='divider'></li>";
	                     temp+="<li role='presentation'><a role='menuitem' tabindex='-1' href='#'>방장위임</a></li>";
	                     temp+="<li role='presentation'><a role='menuitem' tabindex='-1' value='"+item.userId+"' onclick='deleteProjectMember("+"$(this)"+")' href='#'>강퇴하기</a></li>";
                     }
                     temp+="</ul>";
               		 temp+="</li>";
               		$('.contacts-list').append(temp);
    			 });
    		 },
    		 error:ajaxError
    	 });
     }
     
     function deleteProjectMember(val){
    	$.ajax({
    		url:'/projects/kickProjectUser',
    		type:'get',
    		data:{
    			userId:val[0].attributes.value.value
    		},
    		dataType:'json',
    		success:function(data){
    			if(data==true)
    				getProjectMember();
    			else
    				ajaxError();
    		} 
    	});
     }
     
     function getImageList(){
	     $.ajax({
	    	 type:'get',
	    	 url:"/project/imagelist",
	    	 dataType:"json",
	    	 success:function(data){
	    		 $('#chat-image-list-display').empty();
	    		 data.forEach(function(item){
					 var tempStr='';
					 tempStr="<button type="+"'button'"+ "id='"+item+"' onclick='clickImage("+"$(this)"+")'><input type='checkbox' class='imageCheck'><img src='"+item+"'></button>";
		    		 $('#chat-image-list-display').append(tempStr);
	    		 });
	    	 },
	    	 error:ajaxError
	     });
     }
     
     function ajaxError(){
    	   alert("데이터 로딩 오류");
     }
     
     function clickImage(val){
    	 console.log(val);
    	 
    	 console.log('버튼클릭');
    	 $('#chat-image-list-display button').removeClass('selected');
    	 val.addClass('selected');
    	 imageId=val.attr('id');
    	 var clickImg = new Image();
    	 clickImg.src =imageId;
    	 var context = document.getElementById("chat-image-area-canvas").getContext("2d");
    	 context.drawImage(clickImg, 0, 0);

    	 imageSend();
    	 clickSync();
     }
     
     function powerToString(val){
    	 if(val==1) return "방장";
    	 return "팀원";
     }
     </script>
      <!-- /.tab-pane -->
    </div>
  </aside>
  <!-- /.control-sidebar -->
  <!-- Add the sidebar's background. This div must be placed
  immediately after the control sidebar -->
  <div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->

<!-- REQUIRED JS SCRIPTS -->


<!-- Bootstrap 3.3.7 -->
<script src="/resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- AdminLTE App -->
<script src="/resources/dist/js/adminlte.min.js"></script>

<!-- Optionally, you can add Slimscroll and FastClick plugins.
     Both of these plugins are recommended to enhance the
     user experience. -->
</body>
</html>