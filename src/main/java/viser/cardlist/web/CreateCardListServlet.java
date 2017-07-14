package viser.cardlist.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import viser.cardlist.CardListDAO;

@WebServlet("/lists/addList")
public class CreateCardListServlet extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    int boardNum = Integer.parseInt(request.getParameter("boardNum"));
    String listName = request.getParameter("listName");
    int listOrder = Integer.parseInt(request.getParameter("listOrder"));

    CardListDAO cardListDAO = new CardListDAO();
    cardListDAO.addList(boardNum, listName, listOrder);

    response.sendRedirect("/lists/cardlist?boardNum=" + boardNum);
  }
}
