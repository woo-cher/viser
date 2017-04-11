//package viser.board;
//
//import static org.junit.Assert.assertNotNull;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class BoardDAOTest {
//	private static final Logger logger = LoggerFactory.getLogger(BoardDAOTest.class);
//
//	private BoardDAO boardDao;
//	private Board board;
//	private Board TEST_BOARD;
//
//	@Before
//	public void setUp() {
//		boardDao = new BoardDAO();
//		board = new Board("subject", "boardContent", "TestId", "TestPd", "TestDate", 0);
//		TEST_BOARD = new Board("subject", "boardContent", "TestId", "TestPd", "TestDate", 0);
//	}
//
//	@Test
//	public void connection() throws Exception {
//		boardDao = new BoardDAO();
//		Connection con = boardDao.getConnection();
//		assertNotNull(con);
//	}
//
//	@Test
//	public void testRemoveBoard() throws Exception {
//		Board dbBoard = TEST_BOARD;
//		int testNum = 50;
//		dbBoard.setNum(testNum);
//
//		boardDao.addBoard(dbBoard);
//		logger.debug("추가 성공" + dbBoard);
//
//		boardDao.removeBoard(testNum);
//		logger.debug("제거 성공");
//	}
//
//
//
//}