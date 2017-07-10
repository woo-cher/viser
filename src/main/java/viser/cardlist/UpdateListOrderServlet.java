package viser.cardlist;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/lists/updateListOrder")
public class UpdateListOrderServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(UpdateListOrderServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    CardListDAO cardListDao = new CardListDAO();
    int boardNum = Integer.parseInt(request.getParameter("num"));
    int currentListOrder = Integer.parseInt(request.getParameter("current"));
    int updateListOrder = Integer.parseInt(request.getParameter("update"));
    logger.debug("UpdateListOrderServlet : currentListOrder:" + currentListOrder + " updateListOrder:" + updateListOrder);
    cardListDao.updateListOrder(boardNum, currentListOrder, updateListOrder);
  }
}
