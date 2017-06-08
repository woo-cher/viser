package viser.card;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import viser.support.SessionUtils;
import viser.user.LogInServlet;
import viser.user.User;
import viser.user.UserDAO;

@WebServlet("/card/createcardForm")
public class CreateFormCardServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String userId = SessionUtils.getStringValue(session, LogInServlet.SESSION_USER_ID);
		
		int listNum=Integer.parseInt(request.getParameter("listNum"));
		int cardOrder=Integer.parseInt(request.getParameter("cardOrder"));
	
		UserDAO userDao = new UserDAO();
		try {
			request.setAttribute("isCreate", true);
			User user = userDao.findByUserId(userId);
			request.setAttribute("user", user);
			request.setAttribute("card",new Card(listNum,cardOrder));
			RequestDispatcher rd = request.getRequestDispatcher("/card.jsp");
			rd.forward(request, response);
		} catch (SQLException e) {
		}

	}
}
