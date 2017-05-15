package viser.card;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.imageio.IIOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/card/createcard")
public class CreateCardServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		CardDAO cardDao = new CardDAO();
		req.setCharacterEncoding("UTF-8");
		
		String subject = req.getParameter("subject");
		String content = req.getParameter("content");
		String userId = req.getParameter("userId");
		
		Card card = new Card(subject, content, userId);
		
		try {
		
		cardDao.addcard(card);
		} catch (SQLException e) {
			System.out.println(e);
		}

		resp.sendRedirect("/card/cardlist");
	
	}
}
