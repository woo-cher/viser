package viser.board;

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

@WebServlet("/board/viewBoard")
public class BoardviewServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(BoardviewServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		UserDAO userDao = new UserDAO();
		User user = new User();
		BoardDAO boardDao = new BoardDAO();
		Board board = new Board();
		
		HttpSession session = req.getSession();
		
		String userId = SessionUtils.getStringValue(session, LoginServlet.SESSION_USER_ID);
		
		int num = Integer.parseInt( req.getParameter("num") );
		
		try {
			user = userDao.findByUserId(userId);
			boardDao.updateReadcont(num);
			board = boardDao.viewBoard(num);
			
			if(board == null) {
				logger.debug("Board View Fail");
			}
			
			req.setAttribute("isView", true);
			req.setAttribute("board", board);
			req.setAttribute("user", user);
			RequestDispatcher rd = req.getRequestDispatcher("/board_form.jsp");
			rd.forward(req, resp);
			
		} catch (Exception e) {
			logger.debug("BoardviewServlet error : " + e);
		} 
	}
}
