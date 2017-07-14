package viser.board;

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

import viser.user.User;

public class BoardDAO {
  private static final Logger logger = LoggerFactory.getLogger(BoardDAO.class);

  Connection conn = null;
  PreparedStatement pstmt = null;
  ResultSet rs = null;

  PreparedStatement pstmt2 = null;
  ResultSet rs2 = null;

  public void SourceReturn() throws SQLException {
    if (this.conn != null) {
      conn.close();
    }
    if (this.pstmt != null) {
      pstmt.close();
    }
    if (this.rs != null) {
      rs.close();
    }

  }

  public Connection getConnection() throws SQLException {
    Properties props = new Properties();
    InputStream in = BoardDAO.class.getResourceAsStream("/db.properties");
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

  public List getBoardList(String projectName) throws SQLException {
    String sql = "select * from boards where Project_Name = ?";
    List boardlist = new ArrayList();

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, projectName);
      
      rs = pstmt.executeQuery();
      
      while (rs.next()) {
        Board board = new Board();
        board.setBoardNum(rs.getInt("Board_Num"));
        board.setBoardName(rs.getString("Board_Name"));
        boardlist.add(board);
      }
      
      return boardlist;
      
    } catch (Exception e) {
      logger.debug("getBoardList Error : " + e);
    } finally {
      SourceReturn();
    }
    
    return null;
  }

  public void addBoard(Board board) throws SQLException {
    String sql = "insert into boards (Project_Name , Board_Name) values (? , ?)";

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, board.getProjectName());
      pstmt.setString(2, board.getBoardName());
      
      pstmt.executeUpdate();
    } finally {
      SourceReturn();
    }
  }

  public void removeBoard(String boardName) throws SQLException {
    String sql = "delete from boards where Board_Name = ?";

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, boardName);
      
      pstmt.executeUpdate();
    } finally {
      SourceReturn();
    }
  }

  public void updateBoard(String newName, String preName) throws SQLException {
    String sql = "update boards set Board_Name = ? where Board_Name = ?";
  
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, newName);
      pstmt.setString(2, preName);
      
      pstmt.execute();
    } catch (Exception e) {
      logger.debug("Updateproject error : " + e);
    } finally {
      SourceReturn();
    }
  }

  public Board getByBoardNum(int boardNum) throws SQLException {
    String sql = "select * from boards where Board_Num = ?";
    
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, boardNum);
      
      rs = pstmt.executeQuery();

      if (!rs.next()) {
        return null;
      }

      return new Board(rs.getString("Board_Name"), rs.getString("Project_Name"));

    } finally {
      SourceReturn();
    }
  }
  
  public int getBoardNum(String boardName, String projectName) throws SQLException {
    String sql = "select * from boards where Board_Name = ? and Project_Name = ?";
    int getter = 0;
    
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, boardName);
      pstmt.setString(2, projectName);
      
      rs = pstmt.executeQuery();
      
      if (!rs.next()) {
       return 0;
      }
      getter = rs.getInt("Board_Num");
      
    } catch (Exception e) {
      logger.debug("getBoardNum error : " + e);
    } finally {
      SourceReturn();
    }
    return getter;
  }
}
