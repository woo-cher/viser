package viser.card;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import viser.user.LoginServlet;
import viser.user.SessionUtils;
import viser.user.User;
import viser.user.UserDAO;

@WebServlet("/card/viewcard")
public class ViewCardServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(ViewCardServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		String userId = SessionUtils.getStringValue(session, LoginServlet.SESSION_USER_ID);
	
		CardDAO cardDao = new CardDAO();
		Card card = new Card();
		
		int num = Integer.parseInt( req.getParameter("num") );
		String card_userId = req.getParameter("card_userId");

		if(!userId.equals(card_userId)) {
			req.setAttribute("isNotUser", true);
		}
		
		else req.setAttribute("isUser", true);
		
		try {
			card = cardDao.findBycardInfo(num);
			cardDao.updateReadcont(num);
			card = cardDao.viewCard(num);
			
			if(card == null) {
				logger.debug("card View Fail");
			}
			
			req.setAttribute("isView", true);
			req.setAttribute("card", card);
			RequestDispatcher rd = req.getRequestDispatcher("/card_form.jsp");
			rd.forward(req, resp);
			
		} catch (Exception e) {
			logger.debug("cardviewServlet error : " + e);
		} 
	}

}