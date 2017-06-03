package viser.board;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import viser.project.Project;

public class BoardDAO {
	private static final Logger logger = LoggerFactory.getLogger(BoardDAO.class);

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	PreparedStatement pstmt2 = null;
	ResultSet rs2 = null;
	
			public void SourceReturn() throws SQLException {

		if (this.conn != null) {
			conn.close();
		}
		if (this.pstmt != null) {
			pstmt.close();
		}
		if (this.rs != null) {
			rs.close();
		}

	}

	public Connection getConnection() throws SQLException {
		Properties props = new Properties();
		InputStream in = BoardDAO.class.getResourceAsStream("/db.properties");
		try {
			props.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String driver = props.getProperty("jdbc.driver");
		String url = props.getProperty("jdbc.url");
		String username = props.getProperty("jdbc.username");
		String password = props.getProperty("jdbc.password");

		try {
			Class.forName(driver);
			return DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return null;
		}
	}

	public List getBoardList(Project project) throws SQLException {

		List boardlist = new ArrayList();
		Board board =new Board();
		
		// 목록를 조회하기 위한 쿼리
		
		String sql = "select * from boards where Project_Name = ?";
		String sql2= "select * from projects where Board_Name = ?";
		
		try {
			conn = getConnection();
			
			// 실행을 위한 쿼리 및 파라미터 저장
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, project.getProjectName());
			
			pstmt2 = conn.prepareStatement(sql2);
			rs = pstmt.executeQuery(); // 쿼리 실행
			
			while (rs.next()) {
				
				pstmt2.setString(1, rs.getString("Board_Name"));
				rs2 = pstmt2.executeQuery();
				while(rs2.next()){
					
					board.setBoardName(rs2.getString("Board_Name"));
					boardlist.add(project);
				}
			}
			return boardlist;

		} catch (Exception e) {
			logger.debug("getBoardList Error : " + e);
		}

		finally { // DB 관련들 객체를 종료
			SourceReturn();
		}

		return null;
	}

}
