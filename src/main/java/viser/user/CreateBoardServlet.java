package viser.user;

import java.io.IOException;
import java.sql.SQLException;

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
		
		String subject = req.getParameter("subject");
		String content = req.getParameter("content");
		String userId = req.getParameter("userId");
		String password = req.getParameter("password");
		String date = "This is date";
		int readcount = 1;
		int num = 1;
		
		Board board = new Board(num, subject, content, userId, password, date, readcount);
		System.out.println(board);
		
		BoardDAO boardDao = new BoardDAO();
		
		try {
			boardDao.addBoard(board);
		} catch (SQLException e) {
			System.out.println(e);
		}

		resp.sendRedirect("/board.jsp");
	
	}
}
