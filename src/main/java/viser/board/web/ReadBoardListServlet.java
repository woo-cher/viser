package viser.board.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import viser.board.BoardDAO;
import viser.project.Project;

@WebServlet("/board/boardlist")
public class ReadBoardListServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(ReadBoardListServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    HttpSession session = request.getSession();
    session.removeAttribute("boardNum");

    List boardlist = new ArrayList(); // 보드 목록을 가져오기 위하여 LIST 객체생성
    BoardDAO boardDao = new BoardDAO();

    String projectName = request.getParameter("projectName");
    session.setAttribute("projectName", projectName);

    try {
      boardlist = boardDao.getBoardList(projectName);
      request.setAttribute("isReadBoard", true);
      request.setAttribute("list", boardlist);

      RequestDispatcher rd = request.getRequestDispatcher("/list.jsp");
      rd.forward(request, response);

    } catch (SQLException e) {
      logger.debug("ReadProjectListServlet error:" + e.getMessage());
    }
  }
}
