package viser.service.support.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface PreparedStatementSetter {
  PreparedStatement pstmt=null;
  
  void setParameters(PreparedStatement pstmt) throws SQLException;
  
}
