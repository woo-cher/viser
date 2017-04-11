package viser.user;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import javax.imageio.IIOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import viser.board.Board;
import viser.board.BoardDAO;
@WebServlet("/board/createBoard")

public class CreateBoardServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		BoardDAO boardDao = new BoardDAO();

		String subject = req.getParameter("subject");
		String content = req.getParameter("content");
		String userId = req.getParameter("userId");
		String password = req.getParameter("password");
		
		Board board = new Board(subject, content, userId, password);
	
		try {
		boardDao.addBoard(board);
		} catch (SQLException e) {
			System.out.println(e);
		}

		resp.sendRedirect("/board/Boardlist");
	
	}
}
