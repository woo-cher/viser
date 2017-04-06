package viser.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
	public Connection getConnection(){
		
		String url="jdbc:mysql://localhost:3306/viser";
		String id="root";
		String pw="Runtime123!";

		try{
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(url, id, pw);
		} catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		}
	}

	public void addUser(User user) throws SQLException {
		String sql="insert into users values(?,?,?)";
		
		// null 로 초기화
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		//JDBC 리소스 반환
		
		conn = getConnection();
		pstmt = conn.prepareStatement(sql);
		
		try {
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getName());
			
			pstmt.executeUpdate();
			
		} finally {
			if ( pstmt != null) {
				pstmt.close();
			}
			
			if ( conn != null) {
				conn.close();
			}
		}
	}

	public User findByUserId(String userId) throws SQLException {
		String sql="select * from users where userId=?";
		// 리소스 반환
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();  //결과를 받아와 저장
			
			if ( !rs.next() ) {
				return null;
			}
			
			return new User(
					rs.getString("userId"),
					rs.getString("password"),			
					rs.getString("name")
					// rs.getString("email") 필요
					);
			
		} finally {
	
			if ( conn != null ) {
				conn.close();
			}
			
			if ( pstmt != null ) {
				pstmt.close();		
			}
			
			if ( rs != null ) {
				rs.close();
			}
		}
	}

	public void removeUser(String userId) throws SQLException {
		String sql="delete from users where userId=?"; 
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			pstmt.executeUpdate();
		} finally {
			
			if ( conn != null ) {
				conn.close();
			}
			
			if ( pstmt != null ) {
				pstmt.close();		
			}
		}
	}

	public void updateUser(User user) throws SQLException {
		
		String sql = "update users set password = ?, name = ? where userId = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
						
			pstmt.setString(1,user.getPassword());
			pstmt.setString(2,user.getName());
			pstmt.setString(3,user.getUserId());
			
			pstmt.executeUpdate();
			
		} finally {
			
			if ( conn != null ) {
				conn.close();
			}
			
			if ( pstmt != null ) {
				pstmt.close();		
			}
		}
	}
}
