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

import viser.card.Card;
import viser.user.User;

public class ProjectDAO {
	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs = null;
	PreparedStatement pstmt2=null;  // 형근: 이중 sql문을 위해 생성
	ResultSet rs2 = null;  // 형근: 이중 sql문을 위해 생성
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectDAO.class);

	public void SourceReturn() {
		try{
		if (this.conn != null) {
			conn.close();
		}
		if (this.pstmt != null) {
			pstmt.close();
		}
		if (this.rs != null) {
			rs.close();
		}
		}catch(SQLException e){
			logger.debug("sourceReturn error:"+e.getMessage());
		}

	}
	
		Connection getConnection() throws SQLException{
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
		try{
			Class.forName(driver);
			return DriverManager.getConnection(url, username, password);
		}catch(Exception e){
			logger.debug(e.getMessage());
			return null;
		}
	}
		
	List getProjectMemberList(String project_name) throws SQLException{
		List list = new ArrayList(); // 유저목록 리턴을 위한 변수
		String sql="select * from project_members where Project_Name=?";
		conn=getConnection();
		try{
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, project_name);
		rs=pstmt.executeQuery();
		while(rs.next()){
			ProjectMember pm=new ProjectMember();
			pm.setNum(rs.getInt("PM_Num"));
			pm.setUserId(rs.getString("userId"));
			pm.setProjectName(rs.getString("Project_Name"));
			pm.setPower(rs.getInt("Power"));
			list.add(pm);
		}
		return list;
		}catch(Exception e){
			logger.debug("getProjectMemberList error :"+e);
		}
		finally{
			SourceReturn();  //db관련 객체 종료
		}
		return null;
	}
	
	public List getProjectList(String userId) throws SQLException {

		List projects = new ArrayList(); // 형근: 프로젝트목록 리턴을 위한 변수
		// 목록를 조회하기 위한 쿼리
		String sql = "select * from project_members where userId=?";
		String sql2= "select * from projects where Project_Name=?";
				try {
			conn = getConnection();
			// 실행을 위한 쿼리 및 파라미터 저장
			pstmt = conn.prepareStatement(sql);
			pstmt2=conn.prepareStatement(sql2);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery(); // 쿼리 실행
			while (rs.next()) {
				logger.debug("project_getlist test1= 유저가 참여중인 프로젝트 이름:"+rs.getString("Project_Name") );
				pstmt2.setString(1, rs.getString("Project_Name"));
				rs2=pstmt2.executeQuery();
				while(rs2.next()){
					logger.debug("project_getlist test2=조회한 프로젝트이름:"+rs2.getString("Project_Name") );
					logger.debug("project_getlist test3=조회한 프로젝트 생성날짜:"+rs2.getDate("Project_Date") );
					Project project =new Project(); //형근: 각 프로젝트 정보를 저장할 객체
					project.setProjectName(rs2.getString("Project_Name"));
					project.setProjectDate(rs2.getDate("Project_Date"));
					projects.add(project);
				}
			}
			return projects;

		} catch (Exception e) {
			logger.debug("getProjectList Error : " + e);
		}

		finally { // DB 관련들 객체를 종료
			SourceReturn();
		}

		return null;
	}
	
	public void addProject(Project project) throws SQLException {
		Timestamp date=new Timestamp(new Date().getTime());  // Datetype Obj
		String sql = "insert into projects (Project_name,Project_Date) values (?,?)";
		
		try {
			conn = getConnection();		
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, project.getProjectName());
			pstmt.setTimestamp(2 , date);

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

	public void removeProject(int num) throws SQLException {
		String sql = "delete from cards where Num = ?";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, num);

			pstmt.executeUpdate();

		} finally {
			SourceReturn();
		}
	}

	public Card viewProject(int num) throws SQLException {
		String sql = "select * from cards where Num = ?";
		Card card = new Card();
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				card.setNum(rs.getInt("Num"));
				card.setUserId(rs.getString("userId"));
				card.setSubject(rs.getString("SubJect"));
				card.setContent(rs.getString("Content"));
				card.setReadcnt(rs.getInt("Readcnt"));
				card.setDate(rs.getDate("Date"));
				card.setRe_lev(rs.getInt("re_ref"));
				card.setRe_ref(rs.getInt("re_ref"));
				card.setRe_seq(rs.getInt("re_seq"));
			}
			logger.debug(card + "");
		} catch (Exception e) {
		} finally {
			SourceReturn();
		}
		return card;
	}

	public void updateProject(Card card) throws SQLException {
		String sql = "update cards set SubJect = ?, Content = ?, Date = ? where Num = ?";
		conn = getConnection();
		Timestamp date=new Timestamp(new Date().getTime());  //형근: datetime 타입에 맞는 현재 시간을 입력하기 위한 객체
		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, card.getSubject());
			pstmt.setString(2, card.getContent());
			pstmt.setTimestamp(3,date);
			pstmt.setInt(4, card.getNum());

			pstmt.execute();
			logger.debug("Updatecard : " + card);
		} catch (Exception e) {
			logger.debug("Updatecard error : " + e);
			logger.debug(card + "");
		} finally {
			SourceReturn();
		}
	}
	
	public void addImage(String Image_Path,String Project_Name,String Author)throws SQLException{
		String sql="insert into imagechats(Image_Path,Project_Name,Author) values(?,?,?)";
		try{
		conn=getConnection();
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, Image_Path);
		pstmt.setString(2, Project_Name);
		pstmt.setString(3, Author);
		pstmt.executeUpdate();
		}catch(SQLException e){
			logger.debug("addImage error:"+e.getMessage());
		}
		finally{
			SourceReturn();
		}
	}
	public void removeImage(String Image_Path) throws SQLException{
		String sql="delete from imagechats where Image_Path=?";
		try{
		conn=getConnection();
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1,Image_Path);
		pstmt.executeUpdate();
		logger.debug("deleteimage 성공"+Image_Path);
		}catch(SQLException e){
			logger.debug("removeImage error:"+e.getMessage());
		}
		finally{
			SourceReturn();
		}
	}
	public List getImageList(String projectName) throws SQLException{  //형근: 프로젝트의 이미지 목록을 가져오는 메소드
		String sql="select Image_Path from imagechats where Project_Name=? order by ImageChat_Time asc";
		List<String> imagelists=new ArrayList<String>();
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, projectName);
			rs=pstmt.executeQuery();
			while(rs.next()){
				imagelists.add(rs.getString("Image_Path").toString());
			}
			return imagelists;
		} catch (SQLException e) {
			logger.debug("getImageList Error:"+e.getMessage());
		}
		finally{
			SourceReturn();
		}
		return null;
	}
}
