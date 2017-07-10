package viser.card;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/cards/updatecard")
public class UpdateCardServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(UpdateCardServlet.class);

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    response.setCharacterEncoding("UTF-8");
    int cardNum = Integer.parseInt(request.getParameter("num"));
    String subject = request.getParameter("subject");
    String content = request.getParameter("content");

    Card card = new Card();
    card.setCardNum(cardNum);
    card.setSubject(subject);
    card.setContent(content);

    CardDAO cardDao = new CardDAO();
    try {
      logger.debug("테스트 : " + card);
      cardDao.updateCard(card);
    } catch (Exception e) {
      logger.debug("updatecard Servlet error" + e);
    }
    HttpSession session = request.getSession();
    response.sendRedirect("/lists/cardlist?boardNum=" + (int) session.getAttribute("boardNum"));
  }
}
