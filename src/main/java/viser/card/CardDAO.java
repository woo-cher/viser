package viser.card;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import viser.user.UpdateFormUserServlet;
import viser.user.User;
import viser.user.UserDAO;

public class CardDAO {
	private static final Logger logger = LoggerFactory.getLogger(CardDAO.class);

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

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

		try {
			Class.forName(driver);
			return DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return null;
		}
	}

	public Card findBycardInfo(int num) throws SQLException {
		String sql = "select * from cards where Num = ?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);

			rs = pstmt.executeQuery(); // 결과를 받아와 저장

			if (!rs.next()) {
				return null;
			}

			return new Card(rs.getString("subject"), rs.getString("content"), rs.getString("userId"));

		} finally {
			SourceReturn();
		}
	}

	public int getListCount() throws SQLException {

		String sql = "select count(*) from cards";

		int count = 0;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt(1);
			}

		} catch (Exception e) {
			logger.debug(e.getMessage());
		} finally {
			SourceReturn();
		}

		return count;
	}

	public List getCardList(int page, int limit) throws SQLException {

		List list = new ArrayList(); // 목록 리턴을 위한 변수

		// 목록를 조회하기 위한 쿼리
		String sql = "select * from cards order by re_ref desc, re_seq asc limit ?, ?";

		// 조회범위
		int startrow = (page - 1) * 10; // ex ) 0, 10, 20, 30 ...
		int endrow = limit; // ex ) limit 만큼 리스트에 나열

		try {
			conn = getConnection();
			// 실행을 위한 쿼리 및 파라미터 저장
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, endrow);
			logger.debug("Test = " + sql);
			rs = pstmt.executeQuery(); // 쿼리 실행

			while (rs.next()) {
				Card card = new Card();
				card.setNum(rs.getInt("Num"));
				card.setUserId(rs.getString("userId"));
				card.setSubject(rs.getString("Subject"));
				card.setContent(rs.getString("Content"));
				card.setReadcnt(rs.getInt("Readcnt"));
				card.setDate(rs.getDate("Date"));
				card.setRe_ref(rs.getInt("re_ref"));
				card.setRe_lev(rs.getInt("re_lev"));
				card.setRe_seq(rs.getInt("re_seq"));

				list.add(card); // 행을 하나씩 리스트에 추가
			}
			return list;

		} catch (Exception e) {
			logger.debug("getcardList Error : " + e);
		}

		finally { // DB 관련들 객체를 종료
			SourceReturn();
		}

		return null;
	}

	public List getSearchcardList(int page, int limit, String keyField, String keyWord) 
			throws SQLException {
		String keyfield = keyField;
		String keyword = keyWord;

		List list = new ArrayList(); // 목록 리턴을 위한 변수s
		
		// 목록를 조회하기 위한 쿼리
		String sql = "select * from cards where "+keyfield.trim()+" like '%"+keyword.trim()+"%' order by Num limit ?, ?";
		
		// 조회범위
		int startrow = (page - 1) * 10; // ex ) 0, 10, 20, 30 ...
		int endrow = limit; // ex ) limit 만큼 리스트에 나열
		
		try {
			conn = getConnection();
			// 실행을 위한 쿼리 및 파라미터 저장
			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, keyfield);   --> 이렇게 하면 왜 안되지??
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, endrow);
			
			logger.debug("쿼리 = " + sql + "\n" + "startrow = " + startrow + "\n" + "endrow = " + endrow);
			
			rs = pstmt.executeQuery(); // 쿼리 실행
			while (rs.next()) {
				Card card = new Card();
				card.setNum(rs.getInt("Num"));
				card.setUserId(rs.getString("userId"));
				card.setSubject(rs.getString("SubJect"));
				card.setContent(rs.getString("Content"));
				card.setReadcnt(rs.getInt("Readcnt"));
				card.setDate(rs.getDate("Date"));
				card.setRe_ref(rs.getInt("re_ref"));
				card.setRe_lev(rs.getInt("re_lev"));
				card.setRe_seq(rs.getInt("re_seq"));

				list.add(card); // 행을 하나씩 리스트에 추가
			}
			logger.debug(list.size() + "");
			return list;

		} catch (Exception e) {
			logger.debug("Search list Error : " + e);
		}

		finally { // DB 관련들 객체를 종료
			SourceReturn();
		}

		return null;
	}

	public void addCard(Card card) throws SQLException {
		String sql = "insert into cards(userId,Subject,Content,Readcnt,re_ref,re_lev,re_seq) values(?,?,?,?,?,?,?)";

		try {
			conn = getConnection();		
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, card.getUserId());
			pstmt.setString(2, card.getSubject());
			pstmt.setString(3, card.getContent());
			pstmt.setInt(4, card.getReadcnt());
			pstmt.setInt(5, card.getRe_ref());
			pstmt.setInt(6, card.getRe_lev());
			pstmt.setInt(7, card.getRe_seq());

			pstmt.executeUpdate();

		} finally {
			SourceReturn();
		}
	}

	public void removeCard(int num) throws SQLException {
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

	public Card viewCard(int num) throws SQLException {
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

	public void updateReadcont(int num) throws SQLException {
		String sql = "update cards set Readcnt = Readcnt + 1 Where Num = ?";
		conn = getConnection();

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);

			pstmt.executeUpdate();

		} catch (Exception e) {
			logger.debug("updateReadcont error : " + e);
		} finally {
			SourceReturn();
		}
	}

	public void updateCard(Card card) throws SQLException {
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
}
