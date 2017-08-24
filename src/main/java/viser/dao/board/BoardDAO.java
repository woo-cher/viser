package viser.dao.board;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import viser.domain.board.Board;
import viser.service.support.jdbc.JdbcTemplate;
import viser.service.support.jdbc.PreparedStatementSetter;
import viser.service.support.jdbc.RowMapper;

public class BoardDAO {
  private static final Logger logger = LoggerFactory.getLogger(BoardDAO.class);
  JdbcTemplate jdbc = new JdbcTemplate();

  public List getBoardList(String projectName) throws SQLException {
    String sql = "select * from boards where Project_Name = ?";
    return jdbc.list(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, projectName);
      }
    }, new RowMapper() {
      @Override
      public Board mapRow(ResultSet rs) throws SQLException {
        Board board = new Board();
        board.setBoardNum(rs.getInt("Board_Num"));
        board.setBoardName(rs.getString("Board_Name"));
        return board;
      }
    });
  }

  public void addBoard(Board board) throws SQLException {
    String sql = "insert into boards (Project_Name , Board_Name) values (? , ?)";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, board.getProjectName());
        pstmt.setString(2, board.getBoardName());

      }
    });
  }

  public void removeBoard(String boardName) throws SQLException {
    String sql = "delete from boards where Board_Name = ?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, boardName);
      }
    });
  }

  public void updateBoard(String newName, String preName) throws SQLException {
    String sql = "update boards set Board_Name = ? where Board_Name = ?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, newName);
        pstmt.setString(2, preName);
      }
    });
  }

  public Board getByBoardNum(int boardNum) throws SQLException {
    String sql = "select * from boards where Board_Num = ?";
    return jdbc.executeQuery(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, boardNum);
      }
    }, new RowMapper() {
      @Override
      public Board mapRow(ResultSet rs) throws SQLException {
        while (!rs.next())
          return null;
        return new Board(rs.getString("Board_Name"), rs.getString("Project_Name"));
      }
    });
  }

  public int getBoardNum(String boardName, String projectName) throws SQLException {
    String sql = "select * from boards where Board_Name = ? and Project_Name = ?";
    return jdbc.executeQuery(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, boardName);
        pstmt.setString(2, projectName);
      }
    }, new RowMapper() {
      @Override
      public Integer mapRow(ResultSet rs) throws SQLException {
        if (!rs.next()) {
          return 0;
        }
        return rs.getInt("Board_Num");
      }
    });
  }
}
