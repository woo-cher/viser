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

import viser.support.SessionUtils;
import viser.user.LogInServlet;
import viser.user.User;
import viser.user.UserDAO;

@WebServlet("/card/viewcard")
public class ReadCardServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(ReadCardServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		String userId = SessionUtils.getStringValue(session, LogInServlet.SESSION_USER_ID);
	
		CardDAO cardDao = new CardDAO();
		Card card = new Card();
		
		int cardNum = Integer.parseInt( req.getParameter("cardNum") );

		try {
			card = cardDao.viewCard(cardNum);
			
			if(card == null) {
				logger.debug("card View null");
			}
			
			/*req.setAttribute("isView", true);
			req.setAttribute("card", card);
			RequestDispatcher rd = req.getRequestDispatcher("/card.jsp");
			rd.forward(req, resp);
			*/  //json으로 변환해서 보내주기
			
			
		} catch (Exception e) {
			logger.debug("cardviewServlet error : " + e);
		} 
	}

}