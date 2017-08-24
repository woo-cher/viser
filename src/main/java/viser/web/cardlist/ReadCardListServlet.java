package viser.web.cardlist;

import java.io.IOException;
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

import viser.dao.cardlist.CardListDAO;

@WebServlet("/lists/cardlist")
public class ReadCardListServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(ReadCardListServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    CardListDAO cardListDAO = new CardListDAO();
    HttpSession session = request.getSession();
    List list = new ArrayList();

    int boardNum = Integer.parseInt(request.getParameter("boardNum"));
    session.getAttribute("projectName");

    session.setAttribute("boardNum", boardNum);

    list = cardListDAO.getLists(boardNum);

    request.setAttribute("lists", list);

    logger.debug("ReadCardListServlet db에서 가져온 lists:" + list);
    RequestDispatcher rd = request.getRequestDispatcher("/card_list.jsp");
    rd.forward(request, response);
  }
}
