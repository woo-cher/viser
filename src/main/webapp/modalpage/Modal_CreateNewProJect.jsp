<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="modal fade" id="CreateNewProJect" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
      <div class="modal-body">
        <h2 class="modal-title" id="MyModalLabel">프로젝트 만들기</h2>
     
     	</div>  
		 <form class="form-inline" action="/project/createProject" method="get">
		 	 <div class="form-group">
		 	   <label for="exampleInputName2" style="padding-right: 10px">Name</label>
			    <input type="text" class="form-control" name="Project_name" placeholder="프로젝트 이름을 입력하세요." style="width:300px">
			  </div>
    	 </div>
		  
     	 	<div class="modal-footer">
    		    <button type="button" class="btn btn-default" data-dismiss="modal">나가기</button>
     		   <button type="submit" class="btn btn-primary">생성하기</button>
        </form>
    </div>
   </div>
  </div>
</div>