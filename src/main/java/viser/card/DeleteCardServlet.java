package viser.card;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/card/removecard")
public class DeleteCardServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(DeleteCardServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		int num = Integer.parseInt( req.getParameter("num") );
		
		CardDAO cardDao = new CardDAO();
		
		try {
			cardDao.removeCard(num);
			resp.sendRedirect("/card/cardlist");
		} catch (Exception e) {
			logger.debug("RemovecardServlet error" + e);
		}
		
	}
}