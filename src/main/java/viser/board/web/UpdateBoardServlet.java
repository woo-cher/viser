package viser.board.web;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import viser.board.BoardDAO;

@WebServlet("/board/updateBoard")
public class UpdateBoardServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(UpdateBoardServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    HttpSession session = request.getSession();
    BoardDAO boardDao = new BoardDAO();

    String projectName = (String) session.getAttribute("projectName");
    String preBoardName = request.getParameter("preBoardName");
    String newBoardName = request.getParameter("newBoardName");

    projectName = URLEncoder.encode(projectName, "UTF-8");
    try {
      boardDao.updateBoard(newBoardName, preBoardName);
      response.sendRedirect("/board/boardlist?projectName=" + projectName);

    } catch (Exception e) {
      logger.debug("Update Project fail = " + e);
    }

  }
}
