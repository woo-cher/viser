package viser.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoardDAOTest {

	private static final Logger logger = LoggerFactory.getLogger(BoardDAOTest.class);
	private static Board TEST_BOARD = new Board(0, "TestBoard", "TEST");
	private BoardDAO boardDAO;

	@Before
	public void setup() {
		boardDAO = new BoardDAO();
	}

	@Test
	public void Connection() throws SQLException {
		Connection con = boardDAO.getConnection();
		assertNotNull(con);
	}

	@Test
	public void crud() throws SQLException {
		Board board = TEST_BOARD;
		boardDAO.removeBoard(board.getBoardName());
		boardDAO.addBoard(board);
		Board dbBoard = boardDAO.findByBoardName(board.getBoardName());
		assertEquals(board.getBoardName(), dbBoard.getBoardName());
		logger.debug("dbBoard : {}", dbBoard);

		Board UpdateBoard = new Board();
		UpdateBoard.setBoardName("UpdateBoard");
		boardDAO.updateBoard(UpdateBoard.getBoardName(), dbBoard.getBoardName());
		dbBoard = boardDAO.findByBoardName(UpdateBoard.getBoardName());
		assertEquals(dbBoard.getBoardName(), UpdateBoard.getBoardName());

		boardDAO.removeBoard(UpdateBoard.getBoardName());
	}

	@Test
	public void getList() throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<Board> list = new ArrayList<Board>();
		String sql = "select * from boards where Project_Name = ?";

		conn = boardDAO.getConnection();

		// 실행을 위한 쿼리 및 파라미터 저장
		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, "TEST");

		rs = pstmt.executeQuery(); // 쿼리 실행

		while (rs.next()) {
			Board board = new Board();
			board.setBoardNum(rs.getInt("Board_Num"));
			board.setBoardName(rs.getString("Board_Name"));
			list.add(board);
		}
		assertNotNull(list);
		logger.debug("list : {}", list);
	}
}
