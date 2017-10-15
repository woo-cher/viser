<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<%@include file="/WEB-INF/jsp/commons/T_header.jsp"%>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        간트차트 
        <small>일정관리 보드와 데이터가 연동됩니다.</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
        <li class="active">Here</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content container-fluid">
		<iframe src="/resources/gantt_resources/gantt_editor.jsp" style="
    width: -webkit-fill-available;
    height: -webkit-fill-available;
    border: 0px;
		"></iframe>
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  
  
<%@include file="/WEB-INF/jsp/commons/T_footer.jsp"%>
