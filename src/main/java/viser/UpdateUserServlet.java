package viser;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import viser.user.User;
import viser.user.UserDAO;

@WebServlet("/users/update")
public class UpdateUserServlet extends HttpServlet{
	
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
		} catch (SQLException e) {
		}
		
		resp.sendRedirect("/");
	
	
	}
	

}
