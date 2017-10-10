package viser.dao.board;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import viser.domain.board.Board;
import viser.service.gantt.GanttService;
import viser.service.support.jdbc.JdbcTemplate;
import viser.service.support.jdbc.PreparedStatementSetter;
import viser.service.support.jdbc.RowMapper;

public class BoardDAO {
  private static final Logger logger = LoggerFactory.getLogger(BoardDAO.class);
  JdbcTemplate jdbc = new JdbcTemplate();

  public List<Board> getBoardList(String projectName) throws SQLException {
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
        return new Board(rs.getInt("Board_Num"), rs.getString("Project_Name"), rs.getString("Board_Name"), rs.getInt("progress"));
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
  
  public void initGantt(int addListNum,int boardNum,boolean canDelete,boolean canWrite,boolean canWriteOnParent, String zoom){
    String sql="update boards set addListNum=?, canDelete=?, canWrite=?, canWriteOnParent=?, zoom=? where boardNum=?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, addListNum);
        pstmt.setInt(2, GanttService.getBoolaenValue(canDelete));
        pstmt.setInt(3, GanttService.getBoolaenValue(canWrite));
        pstmt.setInt(4, GanttService.getBoolaenValue(canWriteOnParent));
        pstmt.setString(5, zoom);
        pstmt.setInt(6, boardNum);
      }
    });
  }
  
  public void updateBoardProgress(int listNum) {
    String sql = "update boards set progress = (select round(avg(progress),0) from cards where List_Num in (select List_Num from lists where Board_Num in (select Board_Num from lists Where List_Num = ?) ) and not progress = -1) where Board_Num in (select Board_Num from lists where List_Num = ?);";    
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, listNum);
        pstmt.setInt(2, listNum);
      }
    });
  }
}
