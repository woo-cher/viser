<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="stylesheets/card.css" rel="stylesheet" type="text/css">
<title>card</title>
</head>
<body>
	<div id="container">
		<div id="header">card name</div>
		<div id="content">
			<div id="card-contents">card contents</div>

			<div id="bottom">
				<form id="form-comment" action="#" method="get">
					<div id="comment-top">
						<label class="control-label" for="comment-window">add comment</label>
					</div>
					<div id="comment-middle">
						<input type="text" id="comment-window" value="" />
					</div>
					<div id="comment-button">
						<button type="submit" class="btn btn-primary">send</button>
					</div>
				</form>
			</div>
		</div>
		<div id="right-sidemenu">
		<p>add
		<ul>
		<li>label</li>
		<li>member</li>
		<li>checklist</li>
		<li>due date</li>
		</ul>
		</div>
	</div>
</body>
</html>