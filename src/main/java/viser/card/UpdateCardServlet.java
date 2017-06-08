package viser.card;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/card/updatecard")
public class UpdateCardServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(UpdateCardServlet.class);
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		int cardNum = Integer.parseInt( req.getParameter("cardNum") );
		String subject = req.getParameter("subject");
		String content = req.getParameter("content");
		
		Card card = new Card();
		card.setCardNum(cardNum);
		card.setSubject(subject);
		card.setContent(content);
		
		CardDAO cardDao = new CardDAO();
		
		try {
			logger.debug("테스트 : " + card);
			cardDao.updateCard(card);
			resp.sendRedirect("/card/cardlist");  //list조회 페이지로 이동
		} catch (Exception e) {
			logger.debug("updatecard Servlet error" + e);
		}
		
	}
}