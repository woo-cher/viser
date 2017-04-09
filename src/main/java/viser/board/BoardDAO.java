package viser.board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BoardDAO {
	public Connection getConnection() {

		String url = "jdbc:mysql://localhost:3306/viser_test?useUnicode=true&characterEncoding=utf8";
		String id = "root";
		String pw = "dnwjd1528";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(url, id, pw);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public void addBoard(Board board) throws SQLException {
		String sql = "insert into board values(?,?,?,?,?,?,?)";
		
		// null 로 초기화
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, board.getNum());
			pstmt.setString(2, board.getSubject());
			pstmt.setString(3, board.getContent());
			pstmt.setString(4, board.getUserId());
			pstmt.setString(5, board.getPassword());
			pstmt.setString(6, board.getDate());
			pstmt.setInt(7, board.getReadcount());

			pstmt.executeUpdate();

		} finally {
			if (pstmt != null) {
				pstmt.close();
			}

			if (conn != null) {
				conn.close();
			}
		}
	}
}
