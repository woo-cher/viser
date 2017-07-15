package viser.cardlist.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import viser.cardlist.CardList;
import viser.cardlist.CardListDAO;

@WebServlet("/lists/addList")
public class CreateCardListServlet extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    CardList cardList=new CardList(Integer.parseInt(request.getParameter("boardNum")),request.getParameter("listName"),Integer.parseInt(request.getParameter("listOrder")));
    CardListDAO cardListDAO = new CardListDAO();
    cardListDAO.addList(cardList);

    response.sendRedirect("/lists/cardlist?boardNum=" + cardList.getBoardNum());
  }
}
