package viser.board;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/board/removeBoard")
public class RemoveBoardServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(RemoveBoardServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		int num = Integer.parseInt( req.getParameter("num") );
		
		BoardDAO boardDao = new BoardDAO();
		
		try {
			boardDao.removeBoard(num);
			resp.sendRedirect("/board/Boardlist");
		} catch (Exception e) {
			logger.debug("RemoveBoardServlet error" + e);
		}
		
	}
}
