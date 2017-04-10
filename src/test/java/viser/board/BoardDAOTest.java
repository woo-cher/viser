package viser.board;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;

import org.junit.Before;
import org.junit.Test;


public class BoardDAOTest {

	private BoardDAO boardDao;
	private Board board;

	@Before
	public void setUp() {
		boardDao = new BoardDAO();
		board = new Board(0, "subject", "boardContent", "TestId", "TestPd", "TestDate", 0);
	}
	
	@Test
	public void connection() throws Exception {
		boardDao = new BoardDAO();
		Connection con = boardDao.getConnection();
		assertNotNull(con);
	}
	
	
}