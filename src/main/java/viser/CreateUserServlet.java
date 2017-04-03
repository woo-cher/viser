package viser;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import viser.db.Database;
import viser.user.User;


@WebServlet("/users/create")
public class CreateUserServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId=req.getParameter("userId");
		String password = req.getParameter("password");
		String name = req.getParameter("name");
		User user=new User(userId,password,name);
		Database.addUser(user);
		resp.sendRedirect("/");
	}
}
