package viser.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDAO {


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

	public void addUser(User user) throws SQLException {
		String sql = "insert into users values(?,?,?,?,?,?)";

		// null 로 초기화
		Connection conn = null;
		PreparedStatement pstmt = null;

		// JDBC 리소스 반환

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getName());
			pstmt.setString(4, user.getAge());
			pstmt.setString(5, user.getEmail());
			pstmt.setString(6, user.getGender());

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

	public User findByUserId(String userId) throws SQLException {
		String sql = "select * from users where userId = ?";
		// 리소스 반환

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);

			rs = pstmt.executeQuery(); // 결과를 받아와 저장

			if (!rs.next()) {
				return null;
			}

			return new User(
					rs.getString("userId"), 
					rs.getString("password"), 
					rs.getString("name"), 
					rs.getString("age"),
					rs.getString("email"), 
					rs.getString("gender"));

		} finally {
			
			if (rs != null) {
				rs.close();
			}
			
			if (pstmt != null) {
				pstmt.close();
			}

			if (conn != null) {
				conn.close();
			}
		}
	}

	public void removeUser(String userId) throws SQLException {
		String sql = "delete from users where userId = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);

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

	public void updateUser(User user) throws SQLException {

		String sql = "update users set password = ?, name = ?, age = ?, email = ?, gender = ? where userId = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, user.getPassword());
			pstmt.setString(2, user.getName());
			pstmt.setString(3, user.getAge());
			pstmt.setString(4, user.getEmail());
			pstmt.setString(5, user.getGender());
			pstmt.setString(6, user.getUserId());
			
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
