package viser;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import viser.db.Database;
import viser.user.User;


@WebServlet("/users/login")
public class LoginServlet extends HttpServlet {
	public static final String SESSION_USER_ID = "userId"; 
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId=req.getParameter(SESSION_USER_ID);
		String password = req.getParameter("password");
		User user=Database.findByUserId(userId);
		
		try {
			User.login(userId, password);
			HttpSession session = req.getSession();
			session.setAttribute(SESSION_USER_ID, userId); 
			resp.sendRedirect("/main.jsp"); 

		} 
		catch (UserNotFoundException e) {
			// UserNotFoundException e
		} 
		catch (PasswordMismatchException e) {
			//PasswordMismatchException e
		}
		catch(Exception e){
				
		}
	}
	
}
