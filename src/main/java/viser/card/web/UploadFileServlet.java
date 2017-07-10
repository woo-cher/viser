package viser.card.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/fileUpload")
public class UploadFileServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // 첨부된 파일을 받아서 올리는데 목적이 있다.

    // 위치는 현재 프로젝트 /upload_file

    String path = request.getRealPath("/upload_file");

    // 파일이 첨부된 경우에는 일반적으로 request로 받지 못한다.

    // 이유는 폼 태그에서 enctype이 지정되기 때문이다.

    // 그리고 받는 JSP에서는 Multi를 지원하는 객체로 받아야 한다.

    MultipartRequest mr = new MultipartRequest(

        request, path, 1024 * 1024 * 5, "utf-8",

        new DefaultFileRenamePolicy());

    response.sendRedirect("/index.jsp"); // 환용 : 모달 종료후 돌아우게
    // 인자 설명

    // 요청객체, 저장될 위치, 용량, 인코딩, 파일명변경객체

    // 고로 파라미터를 받기 전에 이미 파일이 저장될 곳을 정하여

    // MultipartRequest가 생성되어야 한다.

    // 형근: 아래 주석 업로드한 파일명이 겹칠경우 원래 파일명과 함께 맞는지 확인하기 위한 코드
    // File s_file = mr.getFile("s_file"); // 업로드 후에 파일객체 반환!
    //
    //
    //
    // // 만약! 동일한 파일이 있었다면 현재 파일의 이름이 변경된다.
    //
    // // 그래서 다음과 같이 원래의 이름을 가려낼 수 있다.
    //
    // String o_name = mr.getOriginalFileName("s_file");
    // response.setCharacterEncoding("utf-8");
    // PrintWriter out=response.getWriter();
    // out.print("상태: "+ s_file.getName()+"="+ o_name+ "저장 완료!");
  }

}
