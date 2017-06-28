package viser.project;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/projects/kickProjectUser")
public class KickUserServlet extends HttpServlet{
	public static Logger logger=LoggerFactory.getLogger(KickUserServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		ProjectDAO prjDao = new ProjectDAO();
		HttpSession session = request.getSession();
		
		String userId = request.getParameter("userId");
		String projectName = (String)session.getAttribute("projectName");
		
		logger.debug("추방할 userId " + userId + "\n현재 프로젝트 =" + projectName);
		try {
			prjDao.KickProjectUser(userId, projectName);
			
			response.sendRedirect("/project/memberlist");
		} catch (Exception e) {
			logger.debug("kickUser fail : " + e); 
		}
	}
}