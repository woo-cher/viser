package viser.board;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.imageio.IIOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/board/createBoard")
public class CreateBoardServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		BoardDAO boardDao = new BoardDAO();
		req.setCharacterEncoding("UTF-8");
		
		String subject = req.getParameter("subject");
		String content = req.getParameter("content");
		String userId = req.getParameter("userId");
		String boardDate = sdf.format(date);
		
		Board board = new Board(subject, content, userId);
		board.setDate(boardDate);
		
		try {
		
		boardDao.addBoard(board);
		} catch (SQLException e) {
			System.out.println(e);
		}

		resp.sendRedirect("/board/Boardlist");
	
	}
}
