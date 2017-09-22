package viser.dao.project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import viser.domain.project.Image;
import viser.domain.project.Project;
import viser.domain.project.ProjectMember;
import viser.domain.user.User;
import viser.service.support.jdbc.JdbcTemplate;
import viser.service.support.jdbc.PreparedStatementSetter;
import viser.service.support.jdbc.RowMapper;

public class ProjectDAO {
  private static final Logger logger = LoggerFactory.getLogger(ProjectDAO.class);
  JdbcTemplate jdbc = new JdbcTemplate();

  public List getProjectMemberList(String projectName) {
    List list = new ArrayList();
    String sql = "select * from project_members where Project_Name=?";
    return jdbc.list(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, projectName);
      }
    }, new RowMapper() {
      @Override
      public ProjectMember mapRow(ResultSet rs) throws SQLException {
        ProjectMember pm = new ProjectMember();
        pm.setNum(rs.getInt("PM_Num"));
        pm.setUserId(rs.getString("userId"));
        pm.setProjectName(rs.getString("project_Name"));
        pm.setPower(rs.getInt("Power"));
        return pm;
      }
    });
  }

  public List getProjectList(String userId) {
    String sql = "select * from projects where Project_Name in (select Project_Name from project_members where userId=?)";
    return jdbc.list(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, userId);
      }
    }, new RowMapper() {
      @Override
      public Project mapRow(ResultSet rs) throws SQLException {
        Project project = new Project();
        project.setProjectName(rs.getString("Project_name"));
        project.setProjectDate(rs.getDate("Project_Date"));
        return project;
      }
    });
  }

  public void addProject(Project project) {
    Timestamp date = new Timestamp(new Date().getTime());
    String sql = "insert into projects (Project_name,Project_Date) values (?,?)";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, project.getProjectName());
        pstmt.setTimestamp(2, date);
      }
    });
  }

  public void addprojectMember(Project project, User user, int Power) {
    String sql = "insert into project_members (userId, Project_Name, Power) values (?,?,?)";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, user.getUserId());
        pstmt.setString(2, project.getProjectName());
        pstmt.setInt(3, Power);
      }
    });
  }

  public void removeProject(String projectName) {
    String sql = "delete from projects where Project_name = ?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, projectName);
      }
    });
  }

  public void updateProject(String newName, String preName) {
    String sql = "update projects set Project_Name = ?, Project_Date = ? where Project_Name = ?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, newName);
        pstmt.setTimestamp(2, new Timestamp(new Date().getTime()));
        pstmt.setString(3, preName);
      }
    });
  }

  public void addImage(Image image, String projectName) {
    String sql = "insert into imagechats(Image_Path,Project_Name,Author) values(?,?,?)";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, image.getImagePath());
        pstmt.setString(2, projectName);
        pstmt.setString(3, image.getAuthor());
      }
    });
  }

  public void removeImage(String Image_Path) {
    String sql = "delete from imagechats where Image_Path=?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, Image_Path);
      }
    });
  }

  public List getImageList(String projectName) {
    String sql = "select Image_Path from imagechats where Project_Name=? order by ImageChat_Time asc";
    return jdbc.list(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, projectName);
      }
    }, new RowMapper() {
      @Override
      public String mapRow(ResultSet rs) throws SQLException {
        return rs.getString("Image_Path").toString();
      }
    });
  }

  public List getUserList(String keyword, String projectName) {
    String sql = "select * from users where not userId In (select userId from project_members where Project_Name = ?)";
    return jdbc.list(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, projectName);
      }
    }, new RowMapper() {
      @Override
      public User mapRow(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getString("userId"));
        user.setName(rs.getString("name"));
        user.setBirth(rs.getString("birth"));
        return user;
      }
    });
  }

  public void InviteUser(String userId, String projectName, int power) {
    String sql = "insert into project_members (userId, Project_Name, Power) values (?, ?, ?)";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, userId);
        pstmt.setString(2, projectName);
        pstmt.setInt(3, power);
      }
    });
  }

  public void removeProjectMember(String userId, String projectName) {
    String sql = "delete from project_members where Project_Name = ? and userId = ?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, projectName);
        pstmt.setString(2, userId);
      }
    });
  }

  public Project getByProjectName(String projectName) {
    String sql = "select * from projects where Project_name = ?";
    return jdbc.executeQuery(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, projectName);
      }
    }, new RowMapper() {
      @Override
      public Project mapRow(ResultSet rs) throws SQLException {
        while (!rs.next())
          return null;
        return new Project(rs.getString("Project_name"), rs.getDate("Project_Date"));
      }
    });
  }

  public User getProjectMember(String projectName) {
    String sql = "select * from project_members where Project_name = ?";
    return jdbc.executeQuery(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, projectName);
      }
    }, new RowMapper() {
      @Override
      public User mapRow(ResultSet rs) throws SQLException {
        while (!rs.next())
          return null;
        return new User(rs.getString("userId"));
      }
    });
  }

  public Image getByImageNum(int imageNum) {
    String sql = "select * from imagechats where Image_Num = ?";
    return jdbc.executeQuery(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, imageNum);
      }
    }, new RowMapper() {
      @Override
      public Image mapRow(ResultSet rs) throws SQLException {
        if (!rs.next()) {
          return null;
        }
        return new Image(rs.getString("Image_Path"), rs.getString("Author"));
      }
    });
  }

  public int getImageNum(String projectName, String author) {
    String sql = "select * from imagechats where Project_Name = ? and Author = ?";
    return jdbc.executeQuery(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, projectName);
        pstmt.setString(2, author);
      }
    }, new RowMapper() {
      @Override
      public Integer mapRow(ResultSet rs) throws SQLException {
        if (!rs.next()) {
          return 0;
        }
        return rs.getInt("Image_Num");
      }
    });
  }

}
