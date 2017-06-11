<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<div class="modal fade" id="uploadmodal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="color:black;">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
      <div class="modal-body">
	<c:set var="pageName" value="이미지첨부"/>
	<c:if test="${param.perposeURL =='/fileUpload'}">
	<c:set var="pageName" value="파일첨부"/>
	</c:if>
	
	<h2 class="modal-title" id="MyModalLabel">${pageName}</h2>
	<form action="${param.perposeURL }" method="post" enctype="multipart/form-data">

		첨부파일:<input type="file" class="form-control" name="s_file" value="보내기" style="margin-left: 17px" ><br/>
		<input type="submit" class="form-control" value="보내기 " style= "margin-top: 68px;"/>

	</form>
</div>
</div>
</div>
</div>
</div>
