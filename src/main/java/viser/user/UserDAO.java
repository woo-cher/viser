package viser.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAO {
	public Connection getConnection(){
		String url="jdbc:mysql://localhost:3306/viser";
		String id="root";
		String pw="";
		try{
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(url, id, pw);
		}catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		}
	}

	public void insert(User user) throws SQLException {
		String sql="insert into users values(?,?,?)";
		PreparedStatement psmt=getConnection().prepareStatement(sql);
		psmt.setString(1, user.getUserId());
		psmt.setString(2, user.getPassword());
		psmt.setString(3, user.getName());
		psmt.executeUpdate();
	}
}
