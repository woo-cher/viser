package viser.web.board;

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

import viser.dao.board.BoardDAO;
import viser.domain.project.Project;

@WebServlet("/board/boardlist")
public class ReadBoardListServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(ReadBoardListServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    HttpSession session = request.getSession();
    session.removeAttribute("boardNum");

    List boardlist = new ArrayList();
    BoardDAO boardDAO = new BoardDAO();

    String projectName = request.getParameter("projectName");
    session.setAttribute("projectName", projectName);

    try {
      request.setAttribute("isReadBoard", true);
      request.setAttribute("list", boardlist = boardDAO.getBoardList(projectName));

      RequestDispatcher rd = request.getRequestDispatcher("/list.jsp");
      rd.forward(request, response);

    } catch (SQLException e) {
      logger.debug("ReadProjectListServlet error:" + e.getMessage());
    }
  }
}
