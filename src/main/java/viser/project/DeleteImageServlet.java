package viser.project;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/project/imagedelete")
public class DeleteImageServlet extends HttpServlet {
	public static Logger logger = LoggerFactory.getLogger(DeleteImageServlet.class); 
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProjectDAO projectDao=new ProjectDAO();
		try {
			logger.debug("deleteimage 시도"+request.getParameter("Image_Path"));
			projectDao.removeImage(request.getParameter("Image_Path"));
		} catch (SQLException e) {
			logger.debug("DeleteImageServlet error:"+e.getMessage());
		}
		finally{
			response.sendRedirect("/project/imagelist");
		}
	}
}
