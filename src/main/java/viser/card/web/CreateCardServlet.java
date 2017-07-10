package viser.card.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import viser.card.Card;
import viser.card.CardDAO;

@WebServlet("/cards/createcard")
public class CreateCardServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(CreateCardServlet.class);

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    CardDAO cardDao = new CardDAO();
    request.setCharacterEncoding("UTF-8");

    String subject = request.getParameter("subject");
    String content = request.getParameter("content");
    String userId = request.getParameter("userId");
    int listNum = Integer.parseInt(request.getParameter("listNum"));
    int cardOrder = Integer.parseInt(request.getParameter("cardOrder"));

    Card card = new Card(subject, content, userId, listNum, cardOrder);
    logger.debug("CreateCardServlet 에서 받은 card객체:" + card.toString());
    try {
      cardDao.addCard(card);
    } catch (Exception e) {
      logger.debug("CreateCardServlet error:" + e.getMessage());
    }
    HttpSession session = request.getSession();
    response.sendRedirect("/lists/cardlist?boardNum=" + (int) session.getAttribute("boardNum"));
  }
}
