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

@WebServlet("/cards/createcard")
public class CreateCardServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(CreateCardServlet.class);

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    CardDAO cardDao = new CardDAO();
    req.setCharacterEncoding("UTF-8");

    String subject = req.getParameter("subject");
    String content = req.getParameter("content");
    String userId = req.getParameter("userId");
    int listNum = Integer.parseInt(req.getParameter("listNum"));
    int cardOrder = Integer.parseInt(req.getParameter("cardOrder"));

    Card card = new Card(subject, content, userId, listNum, cardOrder);
    logger.debug("CreateCardServlet 에서 받은 card객체:" + card.toString());
    try {
      cardDao.addCard(card);
    } catch (Exception e) {
      logger.debug("CreateCardServlet error:" + e.getMessage());
    }
    HttpSession session = req.getSession();
    resp.sendRedirect("/lists/cardlist?boardNum=" + (int) session.getAttribute("boardNum"));
  }
}
