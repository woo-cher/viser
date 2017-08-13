package viser.dao.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import viser.domain.user.User;
import viser.service.support.jdbc.JdbcTemplate;
import viser.service.support.jdbc.PreparedStatementSetter;
import viser.service.support.jdbc.RowMapper;

public class UserDAO extends JdbcDaoSupport {
  private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);
  
  JdbcTemplate jdbc = new JdbcTemplate();
  
  public void addUser(User user) {
    logger.debug("Connection : {}", jdbc.conn);
    
    String sql = "insert into users values(?,?,?,?,?,?)";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, user.getUserId());
        pstmt.setString(2, user.getPassword());
        pstmt.setString(3, user.getName());
        pstmt.setString(4, user.getAge());
        pstmt.setString(5, user.getEmail());
        pstmt.setString(6, user.getGender());
      }
    });
  }

  public User getByUserId(String userId) {
    String sql = "select * from users where userId = ?";
    return jdbc.executeQuery(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, userId);
      }
    }, new RowMapper() {
      @Override
      public User mapRow(ResultSet rs) throws SQLException {
        while (!rs.next())
          return null;
        return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"), rs.getString("age"), rs.getString("email"), rs.getString("gender"));
      }
    });
  }

  public void removeUser(String userId) throws SQLException {
    String sql = "delete from users where userId = ?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, userId);
      }
    });
  }

  public void updateUser(User user) throws SQLException {
    String sql = "update users set password = ?, name = ?, age = ?, email = ?, gender = ? where userId = ?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, user.getPassword());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getAge());
        pstmt.setString(4, user.getEmail());
        pstmt.setString(5, user.getGender());
        pstmt.setString(6, user.getUserId());
      }
    });
  }

}
