package viser.cardlist;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/lists/addList")
public class CreateCardList extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("utf-8");
    int boardNum = Integer.parseInt(request.getParameter("boardNum"));
    String listName = request.getParameter("listName");
    int listOrder = Integer.parseInt(request.getParameter("listOrder"));

    CardListDAO cardListDAO = new CardListDAO();
    cardListDAO.addList(boardNum, listName, listOrder);

    response.sendRedirect("/lists/cardlist?boardNum=" + boardNum);
  }
}
