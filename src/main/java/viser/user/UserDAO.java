package viser.user;

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

public class UserDAO {
  private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

  Connection conn = null;
  PreparedStatement pstmt = null;
  ResultSet rs = null;

  public void SourceReturn() {
    try {
      if (this.conn != null) {
        conn.close();
      }
      if (this.pstmt != null) {
        pstmt.close();
      }
      if (this.rs != null) {
        rs.close();
      }
    } catch (SQLException e) {
      logger.debug("SoueceReturn error:" + e.getMessage());
    }
  }

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
    } finally {
      SourceReturn();
    }
  }

  public void addUser(User user) throws SQLException {
    String sql = "insert into users values(?,?,?,?,?,?)";

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
      SourceReturn();
    }
  }

  public User getByUserId(String userId) throws SQLException {
    String sql = "select * from users where userId = ?";

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, userId);
      
      rs = pstmt.executeQuery();
      
      if (!rs.next()) {
        return null;
      }

      return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"), rs.getString("age"), rs.getString("email"), rs.getString("gender"));

    } finally {
      SourceReturn();
    }
  }

  public void removeUser(String userId) throws SQLException {
    String sql = "delete from users where userId = ?";

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, userId);
      
      pstmt.executeUpdate();
    } finally {
      SourceReturn();
    }
  }

  public void updateUser(User user) throws SQLException {

    String sql = "update users set password = ?, name = ?, age = ?, email = ?, gender = ? where userId = ?";

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
      SourceReturn();
    }
  }

}
