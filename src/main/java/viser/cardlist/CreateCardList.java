package viser.cardlist;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/lists/addList")
public class CreateCardList extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int boardNum=Integer.parseInt(request.getParameter("boardNum"));
		String listName=request.getParameter("listName");
		int listOrder=Integer.parseInt(request.getParameter("listOrder"));
		
		CardListDAO cardListDao=new CardListDAO();
		cardListDao.addList(boardNum, listName, listOrder);
		
		response.sendRedirect("/lists/cardlist?boardNum="+boardNum);
	}
}
