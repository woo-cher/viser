package viser.dao.gantt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import viser.domain.gantt.Gantt;
import viser.service.support.jdbc.JdbcTemplate;
import viser.service.support.jdbc.PreparedStatementSetter;
import viser.service.support.jdbc.RowMapper;

public class GanttDAO {
  JdbcTemplate jdbc = new JdbcTemplate();

  public int addGantt(Gantt gantt) {
    String sql = "insert into gantts(addListNum, canDelete, canWrite, canWriteOnParent, zoom,Board_Num,Project_Name) values(?,?,?,?,?,?,?)";
    return jdbc.generatedExecuteUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, gantt.getListNum());
        pstmt.setBoolean(2, gantt.isCanDelete());
        pstmt.setBoolean(3, gantt.isCanWrite());
        pstmt.setBoolean(4, gantt.isCanWriteOnParent());
        pstmt.setString(5, gantt.getZoom());
        pstmt.setInt(6, gantt.getBoardNum());
        pstmt.setString(7, gantt.getProjectName());
      }
    }, new RowMapper() {
      @Override
      public Integer mapRow(ResultSet rs) throws SQLException {
        if (rs.next()) {
          return rs.getInt(1);
        }
        return null;
      }
    });
  }

  public void removeGantt() {

  }

  public void updateGantt(String zoom, int ganttNum) {
    String sql = "update gantts set zoom=? where ganttNum=?";
    jdbc.executeUpdate(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, zoom);
        pstmt.setInt(2, ganttNum);
      }
    });
  }

  public Gantt getGantt(int ganttNum) {
    String sql = "select * from gantts where ganttNum=?";
    return jdbc.executeQuery(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, ganttNum);
      }
    }, new RowMapper() {
      @Override
      public Gantt mapRow(ResultSet rs) throws SQLException {
        while (rs.next()) {
          return new Gantt(rs.getBoolean("canWrite"), rs.getBoolean("canWriteOnParent"), rs.getBoolean("canDelete"), rs.getString("zoom"), rs.getInt("addListNum"), rs.getInt("Board_Num"), rs.getString("Project_Name"), rs.getInt("ganttNum"),true);
        }
        return null;
      }
    });
  }

  public List<Gantt> getGantts(String projectName) {
    String sql = "select ganttNum, Board_Name, gantts.Board_Num from gantts, boards where gantts.Project_Name=? && gantts.Board_Num = boards.Board_Num;";
    return jdbc.list(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, projectName);
      }
    }, new RowMapper() {
      @Override
      public Gantt mapRow(ResultSet rs) throws SQLException {
        return new Gantt(rs.getInt("ganttNum"), rs.getString("Board_Name"), rs.getInt("Board_Num"));
      }
    });
  }

  public int isExistGantt(int boardNum) {
    String sql = "select Board_Num from gantts where Board_Num=?";
    return jdbc.executeQuery(sql, new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, boardNum);
      }
    }, new RowMapper() {
      @Override
      public Integer mapRow(ResultSet rs) throws SQLException {
        while (rs.next()) {
          return rs.getInt("Board_Num");
        }
        return 0;
      }
    });
  }
}
