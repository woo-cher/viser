package viser.project;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import viser.user.User;

public class ProjectDAO {
  Connection conn = null;
  PreparedStatement pstmt = null;
  ResultSet rs = null;
  PreparedStatement pstmt2 = null;
  ResultSet rs2 = null;

  private static final Logger logger = LoggerFactory.getLogger(ProjectDAO.class);

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
      logger.debug("sourceReturn error:" + e.getMessage());
    }

  }

  public Connection getConnection() throws SQLException {
    Properties props = new Properties();
    InputStream in = ProjectDAO.class.getResourceAsStream("/db.properties");
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

  public List getProjectMemberList(String projectName) throws SQLException {
    List list = new ArrayList();
    String sql = "select * from project_members where Project_Name=?";

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, projectName);

      rs = pstmt.executeQuery();

      while (rs.next()) {
        ProjectMember pm = new ProjectMember();
        pm.setNum(rs.getInt("PM_Num"));
        pm.setUserId(rs.getString("userId"));
        pm.setProjectName(rs.getString("project_Name"));
        pm.setPower(rs.getInt("Power"));
        list.add(pm);
      }

      return list;

    } catch (Exception e) {
      logger.debug("getProjectMemberList error :" + e);
    } finally {
      SourceReturn();
    }
    return null;
  }

  public List getProjectList(String userId) throws SQLException {
    List projects = new ArrayList();
    String sql = "select * from project_members where userId=?";
    String sql2 = "select * from projects where Project_Name=?";

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt2 = conn.prepareStatement(sql2);
      pstmt.setString(1, userId);

      rs = pstmt.executeQuery();

      while (rs.next()) {
        pstmt2.setString(1, rs.getString("Project_name"));
        rs2 = pstmt2.executeQuery();

        while (rs2.next()) {
          Project project = new Project();
          project.setProjectName(rs2.getString("Project_name"));
          project.setProjectDate(rs2.getDate("Project_Date"));
          projects.add(project);
        }
      }

      return projects;

    } catch (Exception e) {
      logger.debug("getProjectList Error : " + e);
    } finally {
      SourceReturn();
    }
    return null;
  }

  public void addProject(Project project) throws SQLException {
    Timestamp date = new Timestamp(new Date().getTime());
    String sql = "insert into projects (Project_name,Project_Date) values (?,?)";

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, project.getProjectName());
      pstmt.setTimestamp(2, date);

      pstmt.executeUpdate();
    } finally {
      SourceReturn();
    }
  }

  public void addprojectMember(Project project, User user, int Power) throws SQLException {
    String sql = "insert into project_members (userId, Project_Name, Power) values (?,?,?)";

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, user.getUserId());
      pstmt.setString(2, project.getProjectName());
      pstmt.setInt(3, Power);

      pstmt.executeUpdate();
    } finally {
      SourceReturn();
    }
  }

  public void removeProject(String projectName) throws SQLException {
    String sql = "delete from projects where Project_name = ?";

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, projectName);

      pstmt.executeUpdate();
    } finally {
      SourceReturn();
    }
  }

  public void updateProject(String newName, String preName) throws SQLException {
    String sql = "update projects set Project_Name = ?, Project_Date = ? where Project_Name = ?";
    Timestamp date = new Timestamp(new Date().getTime());

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, newName);
      pstmt.setTimestamp(2, date);
      pstmt.setString(3, preName);

      pstmt.execute();
    } catch (Exception e) {
      logger.debug("Updateproject error : " + e);
    } finally {
      SourceReturn();
    }
  }

  public void addImage(Image image, String projectName) throws SQLException {
    String sql = "insert into imagechats(Image_Path,Project_Name,Author) values(?,?,?)";

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, image.getImagePath());
      pstmt.setString(2, projectName);
      pstmt.setString(3, image.getAuthor());

      pstmt.executeUpdate();
    } catch (SQLException e) {
      logger.debug("addImage error:" + e.getMessage());
    } finally {
      SourceReturn();
    }
  }

  public void removeImage(String Image_Path) throws SQLException {
    String sql = "delete from imagechats where Image_Path=?";

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, Image_Path);

      pstmt.executeUpdate();
      logger.debug("deleteimage 성공" + Image_Path);
    } catch (SQLException e) {
      logger.debug("removeImage error:" + e.getMessage());
    } finally {
      SourceReturn();
    }
  }

  public List getImageList(String projectName) throws SQLException {
    String sql = "select Image_Path from imagechats where Project_Name=? order by ImageChat_Time asc";
    List<String> imagelists = new ArrayList<String>();

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, projectName);

      rs = pstmt.executeQuery();

      while (rs.next()) {
        imagelists.add(rs.getString("Image_Path").toString());
      }

      return imagelists;

    } catch (SQLException e) {
      logger.debug("getImageList Error:" + e.getMessage());
    } finally {
      SourceReturn();
    }
    return null;
  }

  // issue #105
  public List getUserList(String keyword, String loginUser) throws SQLException {
    String sql = "select * from users where not userId = ? and userId " + " like '%" + keyword.trim() + "%' order by age";
    List list = new ArrayList();

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, loginUser);

      rs = pstmt.executeQuery();

      while (rs.next()) {
        User user = new User();
        user.setUserId(rs.getString("userId"));
        user.setName(rs.getString("name"));
        user.setAge(rs.getString("age"));
        user.setGender(rs.getString("gender"));
        list.add(user);
      }

      return list;

    } catch (Exception e) {
      logger.debug("getProjectMemberList error :" + e);
    } finally {
      SourceReturn();
    }
    return null;
  }

  public void InviteUser(String userId, String projectName, int power) throws SQLException {
    String sql = "insert into project_members (userId, Project_Name, Power) values (?, ?, ?)";

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, userId);
      pstmt.setString(2, projectName);
      pstmt.setInt(3, power);

      pstmt.executeUpdate();
    } catch (Exception e) {
      logger.debug("Invite Action Fail" + e);
    } finally {
      SourceReturn();
    }
  }

  public void KickProjectUser(String userId, String projectName) throws SQLException {
    String sql = "delete from project_members where Project_Name = ? and userId = ?";

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, projectName);
      pstmt.setString(2, userId);

      pstmt.executeUpdate();
    } catch (Exception e) {
      logger.debug("Kick Action Fail" + e);
    } finally {
      SourceReturn();
    }
  }

  public Project findByProjectName(String projectName) throws SQLException {
    String sql = "select * from projects where Project_name = ?";

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, projectName);

      rs = pstmt.executeQuery();

      if (!rs.next()) {
        return null;
      }

      return new Project(rs.getString("Project_name"));

    } finally {
      SourceReturn();
    }
  }

  public User findProjectMember(String projectName) throws SQLException {
    User user = new User();

    String sql = "select * from project_members where Project_name = ?";

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, projectName);

      rs = pstmt.executeQuery();

      if (!rs.next()) {
        return null;
      }

      user.setUserId(rs.getString("userId"));
      return user;

    } finally {
      SourceReturn();
    }
  }

  public Image findByImageNum(int imageNum) throws SQLException {
    String sql = "select * from imagechats where Image_Num = ?";

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, imageNum);

      rs = pstmt.executeQuery();

      if (!rs.next()) {
        return null;
      }

      return new Image(rs.getString("Image_Path"), rs.getString("Author"));
    } finally {
      SourceReturn();
    }
  }
  
  public int getImageNum(String projectName, String author) {
    String sql = "select * from imagechats where Project_Name = ? and Author = ?";
    int getter = 0;
    
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, projectName);
      pstmt.setString(2, author);
      
      rs = pstmt.executeQuery();
      
      if (!rs.next()) {
       return 0;
      }
      
      getter = rs.getInt("Image_Num");
      
    } catch (Exception e) {
      logger.debug("getBoardNum error : " + e);
    } finally {
      SourceReturn();
    }
    return getter;
  }
  
}
