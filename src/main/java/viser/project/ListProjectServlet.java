package viser.project;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/project/projectlist")
public class ListProjectServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session=request.getSession();
		session.removeAttribute("projectname");
		System.out.println((String)session.getAttribute("projectlist"));
		List projectlist = new ArrayList(); 		// 게시물 목록을 가져오기 위하여 LIST 객체생성
		ProjectDAO projectDao = new ProjectDAO();
		try {
			projectlist=projectDao.getProjectList((String)session.getAttribute("userId"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("projectlist", projectlist);
		
		RequestDispatcher rd = request.getRequestDispatcher("/project.jsp");
		rd.forward(request, response);
	
	}
}
