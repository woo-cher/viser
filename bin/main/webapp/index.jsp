<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="./commons/top.jspf"%>

<style>
	#header {
		position:absolute;
	}
</style>

<div style="position:relative;">
	<div style="position:absolute;height:100%;width:100%;left:0;top:0;z-index:-1;overflow:hidden;">
	
		<video style="width:100%;" autoplay="" loop="" poster="https://d2v80xjmx68n4w.cloudfront.net/intro/c1a31243becb02fba269c3e290a7e652.jpg">
			<source type="video/mp4" src="/acc.mp4">
               <source type="video/webm" src="/acc.webm">
               <source type="video/ogg" src="/acc.ogv">
               <object>
                   <embed src="/acc.mp4" type="application/x-shockwave-flash" allowfullscreen="false" allowscriptaccess="always">
               </object>
           </video>
	</div>
	<div id="container" style="border:0;">
		<div id="title">
		<%-- <%=getServletContext().getRealPath("/").replace('\\', '/') %> --%>Beyond yourself with
			<p>"runtime"</p>
		</div>
		<div id="content">
			<form id="form-sign" action="/users/login" method="post">
				<c:if test="${not empty errorMessage }">
					<div class="control-group">
						<label class="error">${errorMessage}</label>
					</div>
				</c:if>
				<div class="control-group">
					<label class="control-label" for="userId">사용자 아이디</label> <input
						type="text" name="userId" value="" />
				</div>
				<div class="control-group">
					<label class="control-label" for="password">비밀번호</label> <input
						type="password" name="password" value="" />
				</div>
				<div id="button">
					<div class="controls">
						<button type="button" onclick="location.href='/users/createForm' "
							class="btn btn-primary">Sign up</button>
						<button type="submit" class="btn btn-primary">Sign in</button>
					</div>
				</div>
				<a href="#"> forget your ID/password ? </a>
			</form>
		</div>

	</div>
</div>


<a href="javascript:popupOpen2();" > 업로드할래?! </a>

<div class="wrapper">
	<ul id="sortable1" class="connectedSortable">
		<li class="ui-state-default">Item 1</li>
		<li class="ui-state-default">Item 2</li>
		<li class="ui-state-default">Item 3</li>
		<li class="ui-state-default">Item 4</li>
		<li class="ui-state-default">Item 5</li>
	</ul>
	
	<ul id="sortable2" class="connectedSortable">
		<li class="ui-state-highlight">Item 1</li>
		<li class="ui-state-highlight">Item 2</li>
		<li class="ui-state-highlight">Item 3</li>
		<li class="ui-state-highlight">Item 4</li>
		<li class="ui-state-highlight">Item 5</li>
	</ul>
	<video src="/abcd.avi" controls></video>
</div>

<div class="wrapper">
	<!-- <ul id="sortable1" class="connectedSortable">
		<li class="ui-state-default">Item 1</li>
		<li class="ui-state-default">Item 2</li>
		<li class="ui-state-default">Item 3</li>
		<li class="ui-state-default">Item 4</li>
		<li class="ui-state-default">Item 5</li>
	</ul>
	
	<ul id="sortable2" class="connectedSortable">
		<li class="ui-state-highlight">Item 1</li>
		<li class="ui-state-highlight">Item 2</li>
		<li class="ui-state-highlight">Item 3</li>
		<li class="ui-state-highlight">Item 4</li>
		<li class="ui-state-highlight">Item 5</li>
	</ul> -->
	<!-- <video src="/abcd.avi" controls></video> -->
</div>


<script type="text/javascript">


function popupOpen2(){

	var popUrl = "fileUpload.html";	//팝업창에 출력될 페이지 URL

	var popOption = "width=500, height=250, resizable=no, scrollbars=no, status=no;";    //팝업창 옵션(optoin)

		window.open(popUrl,"",popOption);

	}
	
function popupOpen(){

	var popUrl = "form.jsp";	//팝업창에 출력될 페이지 URL

	var popOption = "width=370, height=360, resizable=no, scrollbars=no, status=no;";    //팝업창 옵션(optoin)

		window.open(popUrl,"",popOption);

	}
	



</script>



<a href="javascript:popupOpen();" > 가입할래?! </a>


 
<%@ include file="./commons/bottom.jspf"%>
