package viser.board;

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

@WebServlet("/board/createBoard")
public class CreateBoardServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(CreateBoardServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    Board board = new Board();
    BoardDAO boardDao = new BoardDAO();

    HttpSession session = request.getSession();

    String projectName = (String) session.getAttribute("projectName");
    String boardName = request.getParameter("boardName");

    board.setProjectName(projectName);
    board.setBoardName(boardName);

    projectName = URLEncoder.encode(projectName, "UTF-8");

    try {
      boardDao.addBoard(board);
      response.sendRedirect("/board/boardlist?projectName=" + projectName);

    } catch (Exception e) {
      logger.debug("Board create fail : " + e);
    }

  }
}
