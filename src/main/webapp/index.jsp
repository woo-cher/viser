<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="./commons/top.jspf"%>

<style>
#header {
	position: absolute;
}
</style>

<div style="position: relative;">
	<div
		style="position: absolute; height: 100%; width: 100%; left: 0; top: 0; z-index: -1; overflow: hidden;">

		<video style="width: 100%;" autoplay="" loop=""
			poster="https://d2v80xjmx68n4w.cloudfront.net/intro/c1a31243becb02fba269c3e290a7e652.jpg">
			<source type="video/mp4" src="/acc.mp4">
			<source type="video/webm" src="/acc.webm">
			<source type="video/ogg" src="/acc.ogv">
			<object>
				<embed src="/acc.mp4" type="application/x-shockwave-flash"
					allowfullscreen="false" allowscriptaccess="always">
			</object>
		</video>
	</div>
	<div id="container" style="border: 0;">
		<div id="title">
			Beyond yourself with
			<p>"runtime"</p>
		</div>
		<div id="content">
			<form id="form-sign" action="/users/login" method="post"
				class="form-inline">
				<c:if test="${not empty errorMessage }">
					<div class="control-group">
						<label class="error">${errorMessage}</label>
					</div>
				</c:if>
				<!-- <div style="width:300px;margin:0 auto; text-align:right;margin-bottom:20px;">
		 			<div class="control-group" style="margin-bottom:10px;">
						<label class="control-label" for="userId">사용자 아이디</label> 
	 					<input type="text" class="form-control" name="userId" value="" placeholder="Enter Id"/>
					</div>
					<div class="control-group">
						<label class="control-label" for="password" >비밀번호</label> 
						<input type="password" class="form-control" name="password" value="" placeholder="Enter Password"/>
					</div>
				</div> -->
				<div
					style="width: 300px; margin: 0 auto; text-align: right; margin-bottom: 20px;">
					<div class="input-group">
						<span class="input-group-addon" id="basic-addon1"> ID </span> <input
							type="text" class="form-control" aria-describedby="basic-addon1"
							name="userId" value="" placeholder="Enter Id" />
					</div>
					<div class="input-group">
						<span class="input-group-addon">password</span> <input
							type="password" class="form-control" name="password" value=""
							placeholder="Enter Password" />
					</div>
				</div>

				<div id="button">
					<div class="controls">
						<!-- 					<button type="button" onclick="location.href='/users/createForm'" class="btn btn-primary">Sign up</button> -->
						<!-- <button type="button" onclick="popupOpen();" class="btn btn-danger">Sign up</button> -->
						<button type="button" class="btn btn-danger" data-toggle="modal"
							data-target="#myLoginModal">Sign up</button>
						<button type="submit" class="btn btn-success">Sign in</button>
						<!-- 환용 : 가입버튼에 모달 적용 -->
					</div>
				</div>
				<a href="#"> forget your ID/password ? </a>
			</form>
		</div>

	</div>
</div>



<a href="javascript:popupOpen2();"> 업로드할래?! </a>

<div id="wrap-sortable">
	<!--카드박스의 이름(수정)  -->
	<div id="card_wrap">
		<li class="ui-state-default" id="title-sortable">카드이름</li>
		<li class="ui-state-default" id="title-sortable">추가</li>
		<ul id="sortable1" class="connectedSortable">
			<!-- <li id="title-sortable" class="ui-state-default">이름</li> -->

			<li class="ui-state-default">Item 2</li>
			<li class="ui-state-default">Item 3</li>
			<li class="ui-state-default">Item 4</li>
			<li class="ui-state-default">Item 5</li>
		</ul>
	</div>
	<ul id="sortable1" class="connectedSortable">
		<li class="ui-state-highlight">Item 1</li>
		<li class="ui-state-highlight">Item 2</li>
		<li class="ui-state-highlight">Item 3</li>
		<li class="ui-state-highlight">Item 4</li>
		<li class="ui-state-highlight">Item 5</li>
	</ul>
	<ul id="sortable1" class="connectedSortable">
		<li class="ui-state-highlight">Item 5</li>
	</ul>
</div>




<!-- <script type="text/javascript">


function popupOpen2(){

	var popUrl = "/upload.jsp?perposeURL=/fileUpload";	//팝업창에 출력될 페이지 URL

	var popOption = "width=500, height=250, resizable=no, scrollbars=no, status=no;";    //팝업창 옵션(optoin)

		window.open(popUrl,"",popOption);

	}
	
function popupOpen(){

	var popUrl = "/form.jsp";	//팝업창에 출력될 페이지 URL

	var popOption = "width=370, height=360, resizable=no, scrollbars=no, status=no;";    //팝업창 옵션(optoin)

		window.open(popUrl,"",popOption);

	}
	

</script> -->



<a href="javascript:popupOpen();"> 가입할래?! </a>



<%@ include file="./commons/bottom.jspf"%>
