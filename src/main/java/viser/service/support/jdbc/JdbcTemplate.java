package viser.service.support.jdbc;

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

import viser.dao.project.ProjectDAO;

public class JdbcTemplate {
  private static final Logger logger = LoggerFactory.getLogger(JdbcTemplate.class);
  Connection conn;
  PreparedStatement pstmt;
  PreparedStatement pstmt2;
  ResultSet rs;

  public Connection getConnection() {
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

  public void sourceReturn() {
    try {
      if (this.conn != null) {
        conn.close();
      }
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
    conn = getConnection();
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
    conn = getConnection();
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

  public void selectAndUpdate(String sql,String sql2,PreparedStatementSetter pss,SelectAndUpdateSetter snus){
    conn=getConnection();
    try {
      pstmt=conn.prepareStatement(sql);
      pstmt2=conn.prepareStatement(sql2);
      pss.setParameters(pstmt);
      rs=pstmt.executeQuery();
      while(rs.next()){
        snus.setParametersBySelect(pstmt2,rs);
        pstmt2.executeUpdate();
      }
    } catch (SQLException e) {
      logger.debug("JdbcTemplate selectAndUpdate Error: ", e.getMessage());
    }
  }
  
}
