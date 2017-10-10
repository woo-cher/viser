<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>AdminLTE 2 | ChartJS</title>
<!-- Tell the browser to be responsive to screen width -->
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<!-- Bootstrap 3.3.7 -->
<link rel="stylesheet" href="/resources/bower_components/bootstrap/dist/css/bootstrap.min.css">
<!-- Font Awesome -->
<link rel="stylesheet" href="/resources/bower_components/font-awesome/css/font-awesome.min.css">
<!-- Ionicons -->
<link rel="stylesheet" href="/resources/bower_components/Ionicons/css/ionicons.min.css">
<!-- Theme style -->
<link rel="stylesheet" href="/resources/dist/css/AdminLTE.min.css">
<!-- Morris charts -->
<link rel="stylesheet" href="/resources/bower_components/morris.js/morris.css">

<title>Insert title here</title>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<!-- <div class="content-wrapper"></div> -->

	<!-- Content Header (Page header) -->
	<section class="content-header">
	<h1>
		<i class="glyphicon glyphicon-dashboard"></i> 대쉬보드 <small>프로젝트 관련 정보가 대쉬보드로 간략하게 표기됩니다.</small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="/main.jsp"><i class="fa fa-dashboard"></i>Home</a></li>
		<li><a href="/board/boardlist?projectName=${projectDash.projectName}">Project : ${projectDash.projectName}</a></li>
		<li class="active">Project-DashBoard</li>
	</ol>
	</section>

	<div class="alert alert-success alert-dismissible" style="margin-top: 15px;">
		<h4>
			<i class="glyphicon glyphicon-hand-right" style="margin-right: 5px;"></i> 프로젝트명 : ${projectDash.projectName}
		</h4>
	</div>

	<!-- Project Progress -->
	<div class="box" style="margin-top: 20px;">
		<div class="box-header with-border">
			<i class="fa fa-line-chart"></i>
			<h3 class="box-title">전체 프로젝트 진행도</h3>
		</div>
		<div class="box-body chart-responsive">
			<div class="progress progress active">
				<div class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" style="width: 20%">
					<p>${projectDash.projectProgress}%Complete</p>
				</div>
			</div>
		</div>
		<!-- /.box-body -->
	</div>

	<div class="col-md-6">
		<!-- Project Member Box -->
		<div class="box">
			<div class="box-header with-border">
				<i class="fa fa-users"></i>
				<h3 class="box-title">Project Member</h3>
			</div>
			<div class="box-body chart-responsive">
				<div class="chart" id="bar-chart" style="height: auto;">
					<div id="uesrList" style="display: -webkit-box;">
						<c:forEach var="memberList" items="${projectDash.projectMembers}" varStatus="status">
							<div>
								<image src="${memberList.imagePath}" style="width: 150px; height: 150px;"></image>
								<figcaption style="margin-left: 56px;">
								<h3 class="box-title">${memberList.userId}</h3>
								</figcaption>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
			<!-- /.box-body -->
		</div>

		<div class="box">
			<div class="box-header with-border">
				<i class="fa fa-list"></i>
				<h3 class="box-title">Board List</h3>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<table class="table table-bordered">
					<tr>
						<th style="width: 10px">#</th>
						<th>Board Name</th>
						<th>Progress</th>
						<th style="width: 40px">Percent</th>
					</tr>
					<c:forEach var="boards" items="${projectDash.boards}" varStatus="status">
						<tr>
							<td>${status.count}.</td>
							<td>${boards.boardName}</td>
							<td>
								<div class="progress progress-xs progress-striped active">
									<div class="progress-bar progress-bar-success" style="width:${boards.boardProgress}%"></div>
								</div>
							</td>
							<td>
								<span class="badge bg-green">${boards.boardProgress}%</span>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<!-- /.box-body -->
			<div class="box-footer clearfix">
				<ul class="pagination pagination-sm no-margin pull-right">
					<li><a href="#">&laquo;</a></li>
					<li><a href="#">1</a></li>
					<li><a href="#">2</a></li>
					<li><a href="#">3</a></li>
					<li><a href="#">&raquo;</a></li>
				</ul>
			</div>
		</div>
	</div>

	<div class="col-md-6">
		<div class="box">
			<div class="box-header">
				<i class="fa fa-credit-card"></i>
				<h3 class="box-title">Last Card List</h3>
			</div>
			<!-- /.box-header -->
			<div class="box-body no-padding">
				<table class="table table-striped">
					<tr>
						<th style="width: 10px">#</th>
						<th style="width: 300px">Subject</th>
						<th>Board</th>
						<th>User</th>
						<th style="width: 40px">Progress</th>
					</tr>
					<c:forEach var="cardList" items="${projectDash.lastCards}" varStatus="status">
						<tr>
							<td>${status.count}.</td>
							<td>${cardList.subject}</td>
							<td>${cardList.boardName}</td>
							<td>${cardList.userId}</td>
							<td>
								<span class="badge bg-red">${cardList.progress}%</span>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<!-- /.box-body -->
		</div>

		<div class="box">
			<div class="box-header with-border">
				<i class="fa fa-list"></i>
				<h3 class="box-title">Gantt Chart List</h3>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<table class="table table-bordered">
					<tr>
						<th style="width: 10px">#</th>
						<th>Attribute1</th>
						<th>Attribute2</th>
						<th style="width: 40px">Attribute3</th>
					</tr>
				</table>
			</div>
			<!-- /.box-body -->
			<div class="box-footer clearfix">
				<ul class="pagination pagination-sm no-margin pull-right">
					<li><a href="#">&laquo;</a></li>
					<li><a href="#">1</a></li>
					<li><a href="#">2</a></li>
					<li><a href="#">3</a></li>
					<li><a href="#">&raquo;</a></li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>