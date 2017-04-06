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
		}catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		}
	}

	public void addUser(User user) throws SQLException {
		String sql="insert into users values(?,?,?)";
		PreparedStatement psmt=getConnection().prepareStatement(sql);
		psmt.setString(1, user.getUserId());
		psmt.setString(2, user.getPassword());
		psmt.setString(3, user.getName());
		psmt.executeUpdate();
	}

	public User findByUserId(String userId) throws SQLException {
		String sql="select * from users where userId=?";
		PreparedStatement psmt=getConnection().prepareStatement(sql);
		psmt.setString(1, userId);
		ResultSet rs=psmt.executeQuery();  //결과를 받아와 저장
		while(rs.next()){  //저장한 결과가 끝날때까지 user객체 반환
			User user=new User(
					rs.getString("userId"),
					rs.getString("password"),
					rs.getString("name")
					);
			return user;
		}
		return null;
	}

	public void removeUser(String userId) throws SQLException {
		String sql="delete from users where userId=?";
		PreparedStatement pstmt=getConnection().prepareStatement(sql);
		pstmt.setString(1, userId);
		pstmt.executeUpdate();
	}

	public void updateUser(User user) throws SQLException {
		
		String sql = "update users set password = ?, name = ? where userId = ?";
		
		PreparedStatement pstmt = getConnection().prepareStatement(sql);
		
		pstmt.setString(1,user.getPassword());
		pstmt.setString(2,user.getName());
		pstmt.setString(3,user.getUserId());
		
		pstmt.executeUpdate();
	
	}
}
