package viser.dao.role;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import viser.domain.role.Role;
import viser.service.support.jdbc.JdbcTemplate;
import viser.service.support.jdbc.PreparedStatementSetter;
import viser.service.support.jdbc.RowMapper;

public class RoleDAO {
  private static final Logger logger = LoggerFactory.getLogger(RoleDAO.class);
  JdbcTemplate jdbc = new JdbcTemplate();

  public Role addRole(String roleName, String projectName) {
    String sql = "insert into roles (roleName, Project_Name) values(?,?)";
    return jdbc.generatedExecuteUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, roleName);
        pstmt.setString(2, projectName);
      } 
    }, new RowMapper() {
      @Override
      public Role mapRow(ResultSet rs) throws SQLException {
        if(rs.next()) {
          return new Role(rs.getInt(1), roleName, projectName);
        }
        return null;
      }
    });
  }
  
  public void deleteRole(int roleNum) {
    String sql = "delete from roles where roleNum = ?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, roleNum);
      }
    });
  }
  
  public void updateRole(int roleNum, String newRoleName) {
    String sql = "update roles set roleName = ? where roleNum = ?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, newRoleName);
        pstmt.setInt(2, roleNum);
      }
    });
  }
  
  public List getRoleList(String projectName) {
    String sql = "select * from roles where Project_Name = ?";
    return jdbc.list(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, projectName);
      }
    }, new RowMapper() {
      @Override
      public Role mapRow(ResultSet rs) throws SQLException {
        Role role = new Role();
        role.setRoleNum(rs.getInt("roleNum"));
        role.setRoleName(rs.getString("roleName"));
        role.setProjectName(rs.getString("Project_Name"));
        return role;
      }
    }); 
  }
}
