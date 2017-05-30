<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>jQuery UI Selectable - Default functionality</title>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/stylesheets/user.css" type="text/css">

<style>
#feedback {
	font-size: 1.4em;
}

#selectable .ui-selecting {
	background: #FECA40;
}

#selectable .ui-selected {
	background: #F39814;
	color: white;
}

#selectable {
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 100%;
}

#selectable li {
	margin: 3px;
	padding: 0.4em;
	font-size: 1.4em;
	height: 18px;
}
</style>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#selectable").selectable({
			stop : function() {
				var result = $("#select-result").empty();
				$(".ui-selected", this).each(function() {
					var index = $("#selectable li").index(this);
					result.append(" #" + (index + 1));
				});
			}
		});
	});
</script>
</head>

<body>
	<div id="chat-container">
		<div id="chat-user">
			<h2>유저목록</h2>
			<div id="chat-user-list">
				<p id="feedback">
					<span>You've selected:</span> <span id="select-result">none</span>.
				</p>
				<ol id="selectable">
					<li class="ui-widget-content">Item 1</li>
					<li class="ui-widget-content">Item 2</li>
					<li class="ui-widget-content">Item 3</li>
					<li class="ui-widget-content">Item 4</li>
					<li class="ui-widget-content">Item 5</li>
					<li class="ui-widget-content">Item 6</li>
					<li class="ui-widget-content">Item 7</li>
				</ol>
			</div>
		</div>
	</div>
</body>
</html>