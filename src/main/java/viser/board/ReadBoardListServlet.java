package viser.board;

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

import viser.project.Project;

@WebServlet("/board/boardlist")
public class ReadBoardListServlet extends HttpServlet{
	public static Logger logger=LoggerFactory.getLogger(ReadBoardListServlet.class);
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		HttpSession session=req.getSession();
		session.removeAttribute("boardName"); 
		
		List boardlist = new ArrayList(); 		// 보드 목록을 가져오기 위하여 LIST 객체생성
		BoardDAO boardDao = new BoardDAO();
		
		String projectName = req.getParameter("projectName");
		session.setAttribute("projectName", projectName);
		
		try {
			boardlist = boardDao.getBoardList(projectName);
			req.setAttribute("isReadBoard", true);
			req.setAttribute("list", boardlist);
			
			RequestDispatcher rd = req.getRequestDispatcher("/list.jsp");
			rd.forward(req, resp);
			
		} catch (SQLException e) {
			logger.debug("ReadProjectListServlet error:"+e.getMessage());
		}
	}
}
