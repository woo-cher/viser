package viser.web.card;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import viser.dao.assignee.AssigneeDAO;
import viser.dao.card.CardDAO;
import viser.domain.card.Card;
import viser.service.support.SessionUtils;

@WebServlet("/cards/createcard")
public class CreateCardServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(CreateCardServlet.class);

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    CardDAO cardDAO = new CardDAO();
    AssigneeDAO assigneeDAO = new AssigneeDAO();
    HttpSession session = request.getSession();
    // Card
    String subject = request.getParameter("subject");
    String content = request.getParameter("content");
    String userId = request.getParameter("userId");
    int listNum = Integer.parseInt(request.getParameter("listNum"));
    int cardOrder = Integer.parseInt(request.getParameter("cardOrder"));
    String duedate = request.getParameter("dueDate");
    // Assignee
    String assigneeMember = request.getParameter("assigneeMember");
    String roleName = request.getParameter("roleName");
    int boardNum = SessionUtils.getIntegerValue(session, "boardNum");
    int cardNum;
    logger.debug("카드 생성 위한 dueDate" + duedate);
    
    Card card = new Card(userId, subject, content, listNum, cardOrder, duedate);
    logger.debug("CreateCardServlet 에서 받은 card객체 : " + card.toString());
    try {
      cardNum = cardDAO.addCard(card);
      assigneeDAO.addAssignee(assigneeMember, roleName, boardNum, cardNum);
    } catch (Exception e) {
      logger.debug("CreateCardServlet error:" + e.getMessage());
    }
    response.sendRedirect("/lists/cardlist?boardNum=" + (int) session.getAttribute("boardNum"));
  }
}