package viser.board;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/board/createBoard")
public class CreateBoardServlet extends HttpServlet {
	public static Logger logger=LoggerFactory.getLogger(CreateBoardServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * review : 모든 Servlet에서 UTF-8 셋팅을 해주는 Filter를 쓰면 이 부분을 모을 수 있을 것 같음
		 * ref : http://javacan.tistory.com/entry/58
		 */
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		Board board = new Board();
		BoardDAO boardDao = new BoardDAO();
		
		HttpSession session = request.getSession();
		
		String projectName = (String)session.getAttribute("projectName");
		String boardName = request.getParameter("boardName");

		board.setProjectName(projectName);
		board.setBoardName(boardName);
	
		projectName = URLEncoder.encode(projectName, "UTF-8");
		
		try {
			boardDao.addBoard(board);
			response.sendRedirect("/board/boardlist?projectName=" + projectName);
			
		} catch (Exception e) {
			/**
			 * review : catch절에서 로그만 찍어주는 것 보다 error 응답을 client에 내려주는게 좋을 수 있음
			 * ref : https://slipp.net/questions/350
			 */
			logger.debug("Board create fail : " + e);
		}
		
	}
}
