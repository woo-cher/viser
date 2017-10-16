package viser.web.gantt;

import java.io.IOException;

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
import viser.dao.card.CardDAO;
import viser.dao.cardlist.CardListDAO;
import viser.dao.gantt.GanttDAO;
import viser.domain.gantt.Gantt;
import viser.domain.user.User;
import viser.service.support.SessionUtils;

@WebServlet("/gantts/createGantt")
public class CreateGanttServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(CreateGanttServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    logger.debug("CreateGanttServlet Servlet response받음");
    GanttDAO ganttDAO = new GanttDAO();
    BoardDAO boardDAO = new BoardDAO();
    CardListDAO cardListDAO = new CardListDAO();
    CardDAO cardDAO = new CardDAO();
    HttpSession session = request.getSession();

    int listNum = Integer.parseInt(request.getParameter("listNum"));
    int boardNum = SessionUtils.getIntegerValue(session, "boardNum");
    String projectName = SessionUtils.getStringValue(session, "projectName");
    int ganttNum = ganttDAO.addGantt(new Gantt(true, true, true, "w3", listNum, boardNum, projectName));
    int firstListNum = cardListDAO.getListNum(boardNum, 0);
    User user = (User) SessionUtils.getObjectValue(session, "user");
    logger.debug("ganttNum: {}", ganttNum);

    cardDAO.addBoardCardForGantt(user.getUserId(), boardDAO.getByBoardNum(boardNum).getBoardName(), firstListNum);
    session.setAttribute("ganttNum", ganttNum);

    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/gantt.jsp");
    rd.forward(request, response);
  }
}
