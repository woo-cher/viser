package viser;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	
		HttpSession session = req.getSession();
		String sessionUseId = SessionUtils.getStringValue(session, LoginServlet.SESSION_USER_ID);

//		★ 로그인 여부 판단
		if (sessionUseId == null) {
			resp.sendRedirect("/");
			logger.debug("UpdateUserServelt");
			return;
		}
		
//		★ 로그인의 상태일 때, 세션에 저장된 유저와 로그인 유저 ID 비교
		String userId = req.getParameter("userId");
		
		if(!sessionUseId.equals(userId)) {
			resp.sendRedirect("/");
			return;
		}
		
		String password = req.getParameter("password");
		String name = req.getParameter("name");
		String age = req.getParameter("age");
		String email = req.getParameter("email");
		String gender = req.getParameter("gender");
		
		User user = new User(userId, password, name, age, email, gender);
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
