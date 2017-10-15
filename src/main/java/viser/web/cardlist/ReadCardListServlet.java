package viser.web.cardlist;

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
import viser.dao.cardlist.CardListDAO;
import viser.domain.board.Board;
import viser.dao.gantt.GanttDAO;

@WebServlet("/lists/cardlist")
public class ReadCardListServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(ReadCardListServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
   
    BoardDAO boardDAO = new BoardDAO();
    GanttDAO ganttDAO=new GanttDAO();
    CardListDAO cardListDAO = new CardListDAO();
    
    Board board = new Board();
    List list = new ArrayList();

    int boardNum = Integer.parseInt(request.getParameter("boardNum"));
    session.getAttribute("projectName");
    session.setAttribute("boardNum", boardNum);

    try {
      board = boardDAO.getByBoardNum(boardNum);
      list = cardListDAO.getLists(boardNum);
      
      request.setAttribute("board", board);
      request.setAttribute("lists", list);
      request.setAttribute("isReadBoard", true);
      logger.debug("ReadCardListServlet db에서 가져온 lists:" + list);
      
      if(ganttDAO.isExistGantt(boardNum)>0)
        request.setAttribute("isExistGantt", true);
      else
        request.setAttribute("isExistGantt", false);
      
      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/card_list.jsp");
      rd.forward(request, response);
    } catch (SQLException e) {
      logger.debug("ReadCardList Error : " + e.getMessage());
    }
  }
}
