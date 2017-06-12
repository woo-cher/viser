<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<link href="/stylesheets/index.css?" rel="stylesheet" type="text/css">
<!-- jquery -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<!-- jquery ui -->
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<!-- bootstrap -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous"> -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

<style>
#header {
	position: absolute;
}
</style>
<%@ include file="./commons/top.jspf"%>
<div style="position: relative;">
	<div style="position: absolute; height: 100%; width: 100%; left: 0; top: 0; z-index: -1; overflow: hidden;">

		<video style="width: 100%;" autoplay="" loop=""
			poster="https://d2v80xjmx68n4w.cloudfront.net/intro/c1a31243becb02fba269c3e290a7e652.jpg">
			<source type="video/mp4" src="take5.mp4">
			<source type="video/webm" src="/acc.webm">
			<source type="video/ogg" src="/acc.ogv">
			<object>
				<embed src="/acc.mp4" type="application/x-shockwave-flash" allowfullscreen="false" allowscriptaccess="always">
			</object>
		</video>
	</div>
	
	<div id ="pop" style="display: none;">
	<div>
		<div class='alert alert-warning' style = "width:380px; display: inline-block;">
			<button type="button" class="close" data-dismiss="alert" aria-hidden="false">&times;
			</button>${errorMessage}  
		</div>
	</div>
	</div>
	
	<div id="container" style="border: 0;">
		<div id="title"> 
			Beyond yourself with <p>"runtime"</p>
		</div>
		<div id="content">
				 <c:if test="${not empty errorMessage }">
					<label class='error alert alert-warning' style = "width:380px; display: inline-block;">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;
						</button>${errorMessage}  
					</label>
				</c:if>
				<div style="width: 300px; margin: 0 auto; text-align: right; margin-bottom: 20px;">
				<form id="form-sign" action="/users/login" method="post" class="form-inline">	
				<fieldset>
					<div class="form-group" style="float: left; padding-right: 26px;">
						<div class = "input-group" style="">
							<span class="input-group-addon" id="basic-addon1"> ID </span>
						    <input type="text" class="form-control" aria-describedby="basic-addon1" name="userId" value="" placeholder="Enter Id" />
						</div>
						<div class = "input-group" style="">
							<span class="input-group-addon">password</span> 
							<input type="password" class="form-control" name="password" value="" placeholder="Enter Password" />
						</div> 
					</div>
					<div class = "Signin" style="display: flow-root;" >
							<button type="submit" class="btn btn-success" style="height: 68px;">Sign in</button>
					</div>
				</fieldset>
				</form>
				</div>
				<div id="button">
					<div class="controls">
						<button type="button" class="btn btn-danger" data-toggle="modal" data-target="#Create">Sign up</button>
					</div>
				</div>
<!-- 				<div>
				<a href="#"> forget your ID/password ? </a>
				</div> -->
		</div>
	</div>
</div>
<%@ include file="./commons/bottom.jspf"%>
