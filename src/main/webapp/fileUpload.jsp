<%@page import="java.io.File"%>

<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>

<%@page import="com.oreilly.servlet.MultipartRequest"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"

    pageEncoding="UTF-8"%>

<%

	// 첨부된 파일을 받아서 올리는데 목적이 있다.

	// 위치는 현재 프로젝트 /upload

	String path = application.getRealPath("/upload");



	// 파일이 첨부된 경우에는 일반적으로 request로 받지 못한다.

	// 이유는 폼 태그에서 enctype이 지정되기 때문이다.

	// 그리고 받는 JSP에서는 Multi를 지원하는 객체로 받아야 한다.

	MultipartRequest mr = new MultipartRequest(

			request, path, 1024*1024*5, "utf-8", 

			new DefaultFileRenamePolicy());

	// 인자 설명

	// 요청객체, 저장될 위치, 용량, 인코딩, 파일명변경객체

	// 고로 파라미터를 받기 전에 이미 파일이 저장될 곳을 정하여

	// MultipartRequest가 생성되어야 한다.

	

	File s_file = mr.getFile("s_file"); // 업로드 후에 파일객체 반환!

	

	// 만약! 동일한 파일이 있었다면 현재 파일의 이름이 변경된다.

	// 그래서 다음과 같이 원래의 이름을 가려낼 수 있다.

	String o_name = mr.getOriginalFileName("s_file");

%>    

상태: <%= s_file.getName() %>(<%= o_name %>) 저장 완료!
