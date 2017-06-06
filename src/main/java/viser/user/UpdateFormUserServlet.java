package viser.user; // 우철 - 개인정보수정폼 개발

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

@WebServlet("/users/updateForm")
public class UpdateFormUserServlet extends HttpServlet{
	
	private static final Logger logger = LoggerFactory.getLogger(UpdateFormUserServlet.class);
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
			
			HttpSession session = req.getSession();
			String userId = SessionUtils.getStringValue(session, LogInServlet.SESSION_USER_ID);
			
			if(userId == null) {
				resp.sendRedirect("/");
				return;
			}
			
			logger.debug("User Id : " + userId);
			
			UserDAO userDao = new UserDAO();
	
			try {
				User user = userDao.findByUserId(userId);
				req.setAttribute("isUpdate", true);
				req.setAttribute("user", user);
				RequestDispatcher rd = req.getRequestDispatcher("/user.jsp");
				rd.forward(req, resp);
				
			} catch (SQLException e) {
			}
			
	}
	

}
