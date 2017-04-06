package viser;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import viser.user.User;
import viser.user.UserDAO;

@WebServlet("/users/update")
public class UpdateUserServlet extends HttpServlet{
	private static final Logger logger = LoggerFactory.getLogger(UpdateUserServlet.class);
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		String userId = req.getParameter("userId");
		String password = req.getParameter("password");
		String name = req.getParameter("name");

		User user = new User(userId, password, name);
		UserDAO userDao = new UserDAO();
		
		
		try {
			userDao.updateUser(user);
			logger.debug("개인정보 수정 : " + password);
		} catch (SQLException e) {
			logger.debug("SQL Exception error" + e);
		}
		
		resp.sendRedirect("/main.jsp");
	
	
	}
	

}
