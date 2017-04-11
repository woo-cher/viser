package viser.board;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import viser.user.UserDAO;

public class BoardDAO {
	public Connection getConnection() {
		Properties props = new Properties();
		InputStream in = UserDAO.class.getResourceAsStream("/db.properties");
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
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public int getListCount() throws SQLException{

		String sql = "select count(*) from board";
		
		int count = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
			
		} finally {
				if(conn != null) {
					conn.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(rs != null) {
					rs.close();
				}
		}
		
		return count;
	}
	
	public List getBoardList(int page, int limit){
		
		List list = new ArrayList(); // 목록 리턴을 위한 변수
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// 목록를 조회하기 위한 쿼리
		String sql = "select * from board order by re_ref desc, re_seq asc limit ?, ?"; 
		
		// 조회범위
		int startrow = (page-1) * 10; // ex )  0, 10, 20, 30 ...
		int endrow = limit;  			 // ex ) limit 만큼 리스트에 나열
		
		try{
			conn = getConnection();
			// 실행을 위한 쿼리 및 파라미터 저장
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, endrow);
			
			rs = pstmt.executeQuery(); // 쿼리 실행 
			
			while(rs.next()){
				Board board = new Board();
				board.setNum(rs.getInt("Num"));
				board.setUserId(rs.getString("UserId"));
				board.setSubject(rs.getString("Subject"));
				board.setContent(rs.getString("Content"));
				board.setReadcount(rs.getInt("Readcnt"));
				board.setDate(rs.getString("Date"));
				board.setRe_ref(rs.getInt("re_ref"));
				board.setRe_lev(rs.getInt("re_lev"));
				board.setRe_seq(rs.getInt("re_seq"));
				
				list.add(board); // 행을 하나씩 리스트에 추가
			}
			return list;
			
		}catch(Exception e){
			System.out.println("getBoardList Error : "+e);
		}finally{ // DB 관련들 객체를 종료
			if(rs != null){ try{ rs.close(); }catch(SQLException se){ } }
			if(pstmt != null){ try{ pstmt.close(); }catch(SQLException se){ } }
		}
		
		return null;
	}
	
	public void addBoard(Board board) throws SQLException {
		String sql = "insert into board values(?,?,?,?,?,?,?,?,?,?)";
		int num = 0;
		// null 로 초기화
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("select max(Num) from board");
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next())
				num = rs.getInt(1)+1;
			else
				num = 1;
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			pstmt.setString(2, board.getSubject());
			pstmt.setString(3, board.getContent());
			pstmt.setString(4, board.getUserId());
			pstmt.setString(5, board.getPassword());
			pstmt.setInt(6, board.getReadcount());
			pstmt.setString(7, board.getDate());
			pstmt.setInt(8, board.getRe_ref());
			pstmt.setInt(9, board.getRe_lev());
			pstmt.setInt(10, board.getRe_seq());

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
	
	public void removeBoard(int num) throws SQLException {
		String sql = "delete from board where Num = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			pstmt.executeUpdate();
			
		} finally {
			if (conn != null) {
				conn.close();
			}

			if (pstmt != null) {
				pstmt.close();
			}
		}
	}
}
