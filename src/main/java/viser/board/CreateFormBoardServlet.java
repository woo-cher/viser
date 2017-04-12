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

import viser.user.LoginServlet;
import viser.user.SessionUtils;
import viser.user.User;
import viser.user.UserDAO;

@WebServlet("/board/createBoardForm")
public class CreateFormBoardServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		String userId = SessionUtils.getStringValue(session, LoginServlet.SESSION_USER_ID);
		
	
		UserDAO userDao = new UserDAO();
		
		try {
			req.setAttribute("isCreate", true);
			User user = userDao.findByUserId(userId);
			req.setAttribute("user", user);
			RequestDispatcher rd = req.getRequestDispatcher("/board_form.jsp");
			rd.forward(req, resp);
		} catch (SQLException e) {
		}

	}
}
