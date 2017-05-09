package viser.project;
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

import viser.user.UserDAO;
public class ProjectDAO {
	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs = null;
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectDAO.class);

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
	
		Connection getConnection() throws SQLException{
		Properties props = new Properties();
		InputStream in = UserDAO.class.getResourceAsStream("/db.properties");
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
		try{
			Class.forName(driver);
			return DriverManager.getConnection(url, username, password);
		}catch(Exception e){
			logger.debug(e.getMessage());
			return null;
		}
	}
	List getChatMemberList(String project_name) throws SQLException{
		List list = new ArrayList(); // 유저목록 리턴을 위한 변수
		String sql="select userId from ProjectMember where Project_Name=?";
		conn=getConnection();
		try{
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, project_name);
		rs=pstmt.executeQuery();
		ProjectMember pm=new ProjectMember();
		while(rs.next()){
			pm.setNum(rs.getInt("num"));
			pm.setUserId(rs.getString("userId"));
			pm.setProjectName(rs.getString("projectName"));
			pm.setPower(rs.getString("power"));
			list.add(pm);
		}
		return list;
		}catch(Exception e){
			logger.debug("getChatMemberList error :"+e);
		}
		finally{
			SourceReturn();  //db관련 객체 종료
		}
		return null;
	}
}
