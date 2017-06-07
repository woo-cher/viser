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

@WebServlet("/project/updateProject")
public class UpdateProjectServlet extends HttpServlet{
	public static Logger logger =LoggerFactory.getLogger(UpdateProjectServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		ProjectDAO prjDao = new ProjectDAO();
		String preProjectName = request.getParameter("preProjectName");
		String newProjectName = request.getParameter("newProjectName");
		
		try {
			prjDao.updateProject(newProjectName, preProjectName);
			response.sendRedirect("/project/projectlist");
			
		} catch (Exception e) {
			logger.debug("Update Project fail = " + e);
		}
		
		
	
	}
}
