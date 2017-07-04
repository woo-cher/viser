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
		session.removeAttribute("boardNum");

		BoardDAO boardDao = new BoardDAO();
		
		String projectName = req.getParameter("projectName");
		session.setAttribute("projectName", projectName);
		
		try {
			req.setAttribute("isReadBoard", true);
			/**
			 * review : 위에서 선언 해 줄 필요 없이 여기서 사용하면 됨
			 */
			req.setAttribute("list", boardDao.getBoardList(projectName));
			
			RequestDispatcher rd = req.getRequestDispatcher("/list.jsp");
			rd.forward(req, resp);
			
		} catch (SQLException e) {
			logger.debug("ReadProjectListServlet error:"+e.getMessage());
		}
	}
}
