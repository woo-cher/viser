package viser.service.support.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class JdbcTemplate extends JdbcDaoSupport {
  private static final Logger logger = LoggerFactory.getLogger(JdbcTemplate.class);
  
  public static Connection conn;
  public DataSource ds;
  public PreparedStatement pstmt;
  public PreparedStatement pstmt2;
  public ResultSet rs;
  
  @PostConstruct
  public void initialize() {
    ds = getDataSource();
    logger.info("get Data Source success!");
    conn = DataSourceUtils.getConnection(ds);
    logger.info("Conn = " + conn);
    logger.info("database initialized success!");
  }
  
  public void sourceReturn() {
    try {
      if (this.pstmt != null) {
        pstmt.close();
      }
      if (this.pstmt2 != null) {
        pstmt2.close();
      }
      if (this.rs != null) {
        rs.close();
      }
    } catch (SQLException e) {
      logger.debug("sourceReturn Error:" + e.getMessage());
    }
  }

  public void executeUpdate(String sql, PreparedStatementSetter pss) {
    try {
      pstmt = conn.prepareStatement(sql);
      pss.setParameters(pstmt);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      logger.debug("JdbcTemplate executeUpdate Error: ", e);
    } finally {
      sourceReturn();
    }
  }

  public <T> T executeQuery(String sql, PreparedStatementSetter pss, RowMapper rm) {
    T result = null;
    try {
      pstmt = conn.prepareStatement(sql);
      pss.setParameters(pstmt);
      rs = pstmt.executeQuery();
      result = rm.mapRow(rs);
    } catch (SQLException e) {
      logger.debug("JdbcTemplate executeQuery Error: ", e);
    } finally {
      sourceReturn();
      return result;
    }
  }

  public <T> List<T> list(String sql, PreparedStatementSetter pss, RowMapper rm) {
    List<T> list = new ArrayList<T>();
    conn = getConnection();
    try {
      pstmt = conn.prepareStatement(sql);
      pss.setParameters(pstmt);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        list.add(rm.mapRow(rs));
      }
    } catch (SQLException e) {
      logger.debug("JdbcTemplate executeQuery Error: ", e.getMessage());
    } finally {
      sourceReturn();
      return list;
    }
  }

  public void selectAndUpdate(String sql, String sql2, PreparedStatementSetter pss, SelectAndUpdateSetter snus) {
    try {
      pstmt = conn.prepareStatement(sql);
      pstmt2 = conn.prepareStatement(sql2);
      pss.setParameters(pstmt);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        snus.setParametersBySelect(pstmt2, rs);
        pstmt2.executeUpdate();
      }
    } catch (SQLException e) {
      logger.debug("JdbcTemplate selectAndUpdate Error: ", e.getMessage());
    }
  }
}
