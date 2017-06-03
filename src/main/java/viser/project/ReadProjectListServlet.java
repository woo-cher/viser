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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/project/projectlist")
public class ReadProjectListServlet extends HttpServlet{
	public static Logger logger=LoggerFactory.getLogger(ReadProjectListServlet.class);
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session=request.getSession();
		session.removeAttribute("projectname"); //형근: 프로젝트 목록으로돌와왔을때 이전에 세션에 저장했던 프로젝트 이름 삭제하기위해
		logger.debug("ReadProjectListServlet에서 세션에서 불러온  projectname:"+(String)session.getAttribute("projectname")); //형근: 프로젝트 목록으로돌와왔을때 이전에 세션에 저장했던 프로젝트 이름이 잘 삭제되었는지 확인
		List<Project> projectlist = new ArrayList<Project>(); 		// 게시물 목록을 가져오기 위하여 LIST 객체생성
		ProjectDAO projectDao = new ProjectDAO();
		try {
			projectlist=projectDao.getProjectList((String)session.getAttribute("userId")); //형근: 세션에 저장된 유저 id로 projectlit조회
			
			request.setAttribute("projectlist", projectlist);
			
			for (int i=0; i<projectlist.size();i++) {
				logger.debug("프로젝트 리스트 = " + projectlist.get(i).getProjectDate().toString());
				logger.debug("프로젝트 리스트 = " + projectlist.get(i).getProjectName());
			}
			
			RequestDispatcher rd = request.getRequestDispatcher("/list.jsp");
			rd.forward(request, response);
		} catch (SQLException e) {
			logger.debug("ReadProjectListServlet error:"+e.getMessage());
		}
	}
}
