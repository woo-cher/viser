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

@WebServlet("/project/deleteProject")
public class DeleteProjectServlet extends HttpServlet {
	public static Logger logger = LoggerFactory.getLogger(DeleteProjectServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		ProjectDAO prjDao = new ProjectDAO();		
		
		String projectName = request.getParameter("projectName");

		logger.debug("delete project name = " + projectName);
		try {
			prjDao.removeProject(projectName);
			response.sendRedirect("/project/projectlist");
		} catch (Exception e) {
			logger.debug("Delete fail : " + e);
		}

	}

}
