package viser.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/board/updateBoard")
public class UpdateBoardServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(UpdateBoardServlet.class);
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		int num = Integer.parseInt( req.getParameter("num") );
		String subject = req.getParameter("subject");
		String content = req.getParameter("content");
		
		Board board = new Board();
		board.setNum(num);
		board.setSubject(subject);
		board.setContent(content);
		
		BoardDAO boardDao = new BoardDAO();
		
		try {
			logger.debug("테스트 : " + board);
			boardDao.updateBoard(board);
			resp.sendRedirect("/board/Boardlist");
		} catch (Exception e) {
			logger.debug("updateBoard Servlet error" + e);
		}
		
	}
}