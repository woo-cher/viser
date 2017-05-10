<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="./commons/top.jspf"%>
<title>Main.jsp</title>
</head>
<body>
	<%@ include file="./commons/left_sidemenu.jspf"%>


<!-- Button trigger modal -->
<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
  Launch demo modal
</button>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document" style="width: 300px;">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">-</h4>
      </div>
      <div class="modal-body" style="font-size:20px;text-align:center;">
      로그인성공!
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close!</button>
      </div>
    </div>
  </div>
</div>

<script>
$('#myModal').modal()
</script>
	<div id=main_content>
		<div>ProJect 목록 JSP</div>
		<a href="/card/cardlist"> 게시판 가기</p>

	</div>
<%@ include file="./commons/bottom.jspf"%>