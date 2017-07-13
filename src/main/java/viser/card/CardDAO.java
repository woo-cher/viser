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

public class CardDAO {
  private static final Logger logger = LoggerFactory.getLogger(CardDAO.class);

  Connection conn = null;
  PreparedStatement pstmt = null;
  PreparedStatement pstmt2 = null;
  ResultSet rs = null;

  public void SourceReturn() {
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
      logger.debug("SoueceReturn error:" + e.getMessage());
    }
  }

  public Connection getConnection() {
    Properties props = new Properties();
    InputStream in = CardDAO.class.getResourceAsStream("/db.properties");
    try {
      props.load(in);
      in.close();
    } catch (IOException e) {
      logger.debug("InputStream error:" + e.getMessage());
    }
    String driver = props.getProperty("jdbc.driver");
    String url = props.getProperty("jdbc.url");
    String username = props.getProperty("jdbc.username");
    String password = props.getProperty("jdbc.password");

    try {
      Class.forName(driver);
      return DriverManager.getConnection(url, username, password);
    } catch (Exception e) {
      logger.debug("DriverManager error:" + e.getMessage());
    }
    return null;
  }

  public List getCardList(int listNum) throws SQLException {
    String sql = "select * from cards where List_Num=? order by Card_Order asc";
    List list = new ArrayList();

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, listNum);
      
      rs = pstmt.executeQuery();

      while (rs.next()) {
        Card card = new Card();
        card.setCardNum(rs.getInt("Card_Num"));
        card.setUserId(rs.getString("userId"));
        card.setSubject(rs.getString("Subject"));
        card.setContent(rs.getString("Content"));
        card.setModifyTime(rs.getDate("Modify_Time"));
        card.setListNum(rs.getInt("List_Num"));

        list.add(card);
      }
      
      return list;

    } catch (Exception e) {
      logger.debug("getcardList error : " + e);
    } finally {
      SourceReturn();
    }
    return null;
  }

  // 형근: card검색기능 현재 사용하지 않음
  // public List getSearchcardList(int page, int limit, String keyField,
  // String keyWord)
  // throws SQLException {
  // String keyfield = keyField;
  // String keyword = keyWord;
  //
  // List list = new ArrayList(); // 목록 리턴을 위한 변수s
  //
  // // 목록를 조회하기 위한 쿼리
  // String sql = "select * from cards where "+keyfield.trim()+" like
  // '%"+keyword.trim()+"%' order by Num limit ?, ?";
  //
  // // 조회범위
  // int startrow = (page - 1) * 10; // ex ) 0, 10, 20, 30 ...
  // int endrow = limit; // ex ) limit 만큼 리스트에 나열
  //
  // try {
  // conn = getConnection();
  // // 실행을 위한 쿼리 및 파라미터 저장
  // pstmt = conn.prepareStatement(sql);
  //// pstmt.setString(1, keyfield); --> 이렇게 하면 왜 안되지??
  // pstmt.setInt(1, startrow);
  // pstmt.setInt(2, endrow);
  //
  // logger.debug("쿼리 = " + sql + "\n" + "startrow = " + startrow + "\n" +
  // "endrow = " + endrow);
  //
  // rs = pstmt.executeQuery(); // 쿼리 실행
  // while (rs.next()) {
  // Card card = new Card();
  // card.setNum(rs.getInt("Num"));
  // card.setUserId(rs.getString("userId"));
  // card.setSubject(rs.getString("SubJect"));
  // card.setContent(rs.getString("Content"));
  // card.setReadcnt(rs.getInt("Readcnt"));
  // card.setDate(rs.getDate("Date"));
  // card.setRe_ref(rs.getInt("re_ref"));
  // card.setRe_lev(rs.getInt("re_lev"));
  // card.setRe_seq(rs.getInt("re_seq"));
  //
  // list.add(card); // 행을 하나씩 리스트에 추가
  // }
  // logger.debug(list.size() + "");
  // return list;
  //
  // } catch (Exception e) {
  // logger.debug("Search list Error : " + e);
  // }
  //
  // finally { // DB 관련들 객체를 종료
  // SourceReturn();
  // }
  //
  // return null;
  // }

  public void addCard(Card card) {
    String sql = "insert into cards(userId,Subject,Content, List_Num, Card_Order) values(?,?,?,?,?)";

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, card.getUserId());
      pstmt.setString(2, card.getSubject());
      pstmt.setString(3, card.getContent());
      pstmt.setInt(4, card.getListNum());
      pstmt.setInt(5, card.getCardOrder());
      
      pstmt.executeUpdate();

    } catch (SQLException e) {
      logger.debug("addCard error:" + e.getMessage());
    } finally {
      SourceReturn();
    }
  }

  public void removeCard(int num, int listNum, int cardOrder) {
    String sql = "delete from cards where Card_Num = ?";

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, num);
      
      pstmt.executeUpdate();
    } catch (SQLException e) {
    }

    sql = "select Card_Num from cards where List_Num=?&& Card_Order>?";
    
    try {
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, listNum);
      pstmt.setInt(2, cardOrder);
      
      rs = pstmt.executeQuery();

      String sql2 = "update cards set Card_Order=? where Card_Num=?";
      int changeOrder = cardOrder; // 삭제되는 위치부터 그 뒤까지 순서 갱신을 위해 사용하는 변수

      while (rs.next()) {
        pstmt2 = conn.prepareStatement(sql2);
        pstmt2.setInt(1, changeOrder++);
        pstmt2.setInt(2, rs.getInt("Card_Num"));
        pstmt2.executeUpdate();
      }
    } catch (SQLException e) {
    } finally {
      SourceReturn();
    }
  }

  public Card viewCard(int num) throws SQLException {
    String sql = "select * from cards where Card_Num = ?";
    Card card = new Card();
    
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, num);
      
      rs = pstmt.executeQuery();
      
      if (rs.next()) {
        card.setCardNum(rs.getInt("Card_Num"));
        card.setUserId(rs.getString("userId"));
        card.setSubject(rs.getString("Subject"));
        card.setContent(rs.getString("Content"));
        card.setModifyTime(rs.getDate("Modify_Time"));
        card.setListNum(rs.getInt("List_Num"));
      }
      
    } catch (Exception e) {
    } finally {
      SourceReturn();
    }
    return card;
  }

  public void updateCard(Card card) throws SQLException {
    String sql = "update cards set SubJect = ?, Content = ?, Modify_Time = ? where Card_Num = ?";
    Timestamp date = new Timestamp(new Date().getTime());
    
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, card.getSubject());
      pstmt.setString(2, card.getContent());
      pstmt.setTimestamp(3, date);
      pstmt.setInt(4, card.getCardNum());
      pstmt.execute();
      
      logger.debug("Updatecard : " + card);
    } catch (Exception e) {
      logger.debug("Updatecard error : " + e);
    } finally {
      SourceReturn();
    }
  }
}
