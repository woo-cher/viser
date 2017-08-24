package viser.dao.card;

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

import viser.dao.cardlist.CardListDAO;
import viser.domain.card.Card;
import viser.domain.cardlist.CardList;

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

  public List<Card> getCards(int listNum) throws SQLException {
    String sql = "select * from cards where List_Num=? order by Card_Order asc";
    List<Card> list = new ArrayList<Card>();

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

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, num);

      rs = pstmt.executeQuery();

      if (rs.next()) {
        return new Card(rs.getInt("Card_Num"), rs.getString("userId"), rs.getString("Subject"), rs.getString("Content"), rs.getDate("Modify_Time"), rs.getInt("List_Num"), rs.getInt("Card_Order"));
      }
    } catch (Exception e) {
    } finally {
      SourceReturn();
    }
    return null;
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

  // cardNum : 기존 위치의 카드 순서 번호, listNum1 : 추가 카드의 리스트 번호, listnum2 : 삭제 카드의 리스트 번호, changeOrder : 인덱스 변화시 사용 순서 번호
  public void updateCardOrder(int boardNum, int currentListOrder, int changeListOrder, int currentCardOrder, int changeCardOrder) {
    int cardNum = 0, listNum1 = 0, listNum2 = 0, changeOrder = 0;
    String sql, sql2;
    Timestamp date = new Timestamp(new Date().getTime());
    CardListDAO cardListDAO = new CardListDAO();

    // Find listNum
    CardList addedList = new CardList(boardNum, changeListOrder);
    CardList removedList = new CardList(boardNum, currentListOrder);
    listNum1 = cardListDAO.getListNum(addedList.getBoardNum(), addedList.getListOrder());
    listNum2 = cardListDAO.getListNum(removedList.getBoardNum(), removedList.getListOrder());

    // Find Updated CardNum
    CardDAO cardDAO = new CardDAO();
    cardNum = cardDAO.getCardNum(listNum2, currentCardOrder);

    // If Card drag in same list
    if (currentListOrder == changeListOrder) {
      if (currentCardOrder > changeCardOrder) {
        sql = "select Card_Num from cards where List_Num=? && Card_Order>=? && Card_Order<?  order by Card_Order asc";
        sql2 = "update cards set Card_Order = ? where Card_Num=?";
        changeOrder = changeCardOrder + 1;

        try {
          conn = getConnection();
          pstmt = conn.prepareStatement(sql);
          pstmt.setInt(1, listNum2);
          pstmt.setInt(2, changeCardOrder);
          pstmt.setInt(3, currentCardOrder);

          rs = pstmt.executeQuery();

          while (rs.next()) {
            pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setInt(1, changeOrder++);
            pstmt2.setInt(2, rs.getInt("Card_Num"));

            pstmt2.executeUpdate();
          }
        } catch (SQLException e) {
          logger.debug("updateCardOrder error:" + e.getMessage());
        } finally {
          SourceReturn();
        }
      }

      // Sorting : If previous listOrder < changed listOrder
      else {
        sql = "select Card_Num from cards where List_Num=? && Card_Order>? && Card_Order<=? order by Card_Order asc";
        sql2 = "update cards set Card_Order = ? where Card_Num=?";
        changeOrder = currentCardOrder;
        try {
          conn = getConnection();
          pstmt = conn.prepareStatement(sql);
          pstmt.setInt(1, listNum2);
          pstmt.setInt(2, currentCardOrder);
          pstmt.setInt(3, changeCardOrder);

          rs = pstmt.executeQuery();

          while (rs.next()) {
            pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setInt(1, changeOrder++);
            pstmt2.setInt(2, rs.getInt("Card_Num"));

            pstmt2.executeUpdate();
          }
        } catch (SQLException e) {
          logger.debug("updateCardOrder error:" + e.getMessage());
        } finally {
          SourceReturn();
        }
      }
    }

    // If drag Card in differ list
    else {
      // 카드가 없어지는 리스트에 카드 번호 갱신
      changeOrder = currentCardOrder;
      sql = "select Card_Num from cards where List_Num=? && Card_Order>? order by Card_Order asc";

      try {
        conn = getConnection();
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, listNum2);
        pstmt.setInt(2, currentCardOrder);

        rs = pstmt.executeQuery();
        sql2 = "update cards set Card_Order=? where Card_Num=?";

        while (rs.next()) {
          pstmt2 = conn.prepareStatement(sql2);
          logger.debug("카드 없어지는 리스트:" + rs.getInt("Card_Num") + "    " + changeOrder);
          pstmt2.setInt(1, changeOrder++);
          pstmt2.setInt(2, rs.getInt("Card_Num"));
          pstmt2.executeUpdate();
        }
      } catch (SQLException e) {
        logger.debug("updateCardOrder error:" + e.getMessage());
      } finally {
        SourceReturn();
      }

      // 카드가 추가된 리스트에 카드 번호 갱신
      changeOrder = changeCardOrder + 1;
      sql = "select Card_Num from cards where List_Num=? && Card_Order>=? order by Card_Order asc";

      try {
        conn = getConnection();
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, listNum1);
        pstmt.setInt(2, changeCardOrder);

        rs = pstmt.executeQuery();
        logger.debug("카드 추가되는 리스트:" + listNum1 + "    " + changeCardOrder);

        sql2 = "update cards set Card_Order=? where Card_Num=?";

        while (rs.next()) {
          pstmt2 = conn.prepareStatement(sql2);
          logger.debug("카드 추가되는 리스트:" + changeOrder + "    " + rs.getInt("Card_Num"));
          pstmt2.setInt(1, changeOrder++);
          pstmt2.setInt(2, rs.getInt("Card_Num"));

          pstmt2.executeUpdate();
        }
      } catch (SQLException e) {
        logger.debug("updateCardOrder error:" + e.getMessage());
      } finally {
        SourceReturn();
      }
    }

    // 드래그하여 옮긴 카드 위치 갱신
    sql = "update cards set Card_Order = ?, Modify_Time=? ,List_Num=? where Card_Num = ?";

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, changeCardOrder);
      pstmt.setTimestamp(2, date);
      pstmt.setInt(3, listNum1);
      pstmt.setInt(4, cardNum);

      pstmt.executeUpdate();
      logger.debug("드래그 된 카드:" + changeCardOrder + "  " + listNum1 + "   " + cardNum);
    } catch (SQLException e) {
      logger.debug("updateCardOrder error:" + e.getMessage());
    } finally {
      SourceReturn();
    }
  }

  public int getCardNum(int listNum, int cardOrder) {
    String sql = "select Card_Num from cards where List_Num=? && Card_Order=?";
    int cardNum = 0;
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, listNum);
      pstmt.setInt(2, cardOrder);

      rs = pstmt.executeQuery();
      while (!rs.next()) {
        return 0;
      }
      cardNum = rs.getInt("Card_Num");

    } catch (SQLException e) {
      logger.debug("updateCardOrder error:" + e.getMessage());
    } finally {
      SourceReturn();
    }
    return cardNum;
  }
}
