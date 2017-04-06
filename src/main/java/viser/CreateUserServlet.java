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

@WebServlet("/users/create")
public class CreateUserServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		User user = new User(userId, password, name);
		UserDAO userDAO = new UserDAO();
		try {
			userDAO.addUser(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.sendRedirect("/");
	}
}
