package viser.dao.cardlist;

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

import viser.dao.card.CardDAO;
import viser.domain.cardlist.CardList;

public class CardListDAO {
  public static Logger logger = LoggerFactory.getLogger(CardList.class);

  Connection conn = null;
  PreparedStatement pstmt = null;
  PreparedStatement pstmt2 = null;
  ResultSet rs = null;
  ResultSet rs2 = null;

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
      logger.debug("SourceReturn error:" + e.getMessage());
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

  public List<CardList> getLists(int boardNum) {
    String sql = "select*from lists where Board_Num=? order by List_Order";
    CardDAO cardDAO = new CardDAO();
    List<CardList> list = new ArrayList<CardList>();

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, boardNum);
      
      rs = pstmt.executeQuery();

      while (rs.next()) {
        CardList cardList = new CardList();
        cardList.setListNum(rs.getInt("List_Num"));
        cardList.setBoardNum(boardNum);
        cardList.setListName(rs.getString("List_Name"));
        cardList.setListOrder(rs.getInt("List_Order"));
        cardList.setCards(cardDAO.getCards(rs.getInt("List_Num")));
        list.add(cardList);
      }

      return list;
      
    } catch (SQLException e) {
      logger.debug("getList error:" + e.getMessage());
    } finally {
      SourceReturn();
    }
    return null;
  }

  public void addList(CardList list) {
    String sql = "insert into lists(Board_Num,List_Name,List_Order) values(?,?,?)";

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, list.getBoardNum());
      pstmt.setString(2, list.getListName());
      pstmt.setInt(3, list.getListOrder());
      
      pstmt.executeUpdate();
    } catch (SQLException e) {
      logger.debug("addList error:" + e.getMessage());
    } finally {
      SourceReturn();
    }
  }

  public void removeList(int boardNum, int listOrder) {
    String sql = "delete from lists where Board_Num=? && List_Order=?";

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, boardNum);
      pstmt.setInt(2, listOrder);
      
      pstmt.executeUpdate();

      sql = "select List_Num from lists where Board_Num=? && List_Order>?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, boardNum);
      pstmt.setInt(2, listOrder);
      
      rs = pstmt.executeQuery();

      String sql2 = "update lists set List_Order=? where List_Num=?";
      int changeOrder = listOrder;

      while (rs.next()) {
        pstmt2 = conn.prepareStatement(sql2);
        pstmt2.setInt(1, changeOrder++);
        pstmt2.setInt(2, rs.getInt("List_Num"));
        
        pstmt2.executeUpdate();
      }
    } catch (SQLException e) {
      logger.debug("removeList error:" + e.getMessage());
    } finally {
      SourceReturn();
    }
  }
  
  public void updateListName(int listNum,String listName){
    String sql="update lists set List_Name=? where List_Num=?";
    try {
      conn=getConnection();
      pstmt=conn.prepareStatement(sql);
      pstmt.setString(1, listName);
      pstmt.setInt(2, listNum);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    finally {
      SourceReturn();
    }
  }

  // listNum : 기존 list의 Num , changeOrder : 인덱스 변경 시 사용할 순서 번호
  public void updateListOrder(int boardNum, int currentListOrder, int changeListOrder) {
    int listNum=0 ,changeOrder = 0;
    String sql, sql2;
    
    CardList currentList=new CardList(boardNum,currentListOrder);
    listNum=getListNum(currentList.getBoardNum(),currentList.getListOrder());

    // 1. 변경된 위치 < 기존 리스트 순서 : 기존 리스트 순서 ~ 바뀐 리스트 전 리스트까지 sorting
    if (currentListOrder > changeListOrder) {
      sql = "select List_Num from lists where Board_Num=? && List_Order>=? && List_Order<?  order by List_Order asc";
      sql2 = "update lists set List_Order = ? where List_Num=?";
      changeOrder = changeListOrder + 1;
      
      try {
        conn = getConnection();
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, boardNum);
        pstmt.setInt(2, changeListOrder);
        pstmt.setInt(3, currentListOrder);
        
        rs = pstmt.executeQuery();
        
        while (rs.next()) {
          pstmt2 = conn.prepareStatement(sql2);
          pstmt2.setInt(1, changeOrder++);
          pstmt2.setInt(2, rs.getInt("List_Num"));
          pstmt2.executeUpdate();
        }
      } catch (SQLException e) {
        logger.debug("updateListOrder error:" + e.getMessage());
      } finally {
        SourceReturn();
      }
    }

    // 2. 기존 리스트 순서 < 바뀐 리스트 순서 : 기존 리스트 옆 ~ 바뀐 리스트까지 sorting
    else {
      sql = "select List_Num from lists where Board_Num=? && List_Order>? && List_Order<=? order by List_Order asc";
      sql2 = "update lists set List_Order = ? where List_Num=?";
      changeOrder = currentListOrder;
      
      try {
        conn = getConnection();
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, boardNum);
        pstmt.setInt(2, currentListOrder);
        pstmt.setInt(3, changeListOrder);
        
        rs = pstmt.executeQuery();
        
        while (rs.next()) {
          pstmt2 = conn.prepareStatement(sql2);
          pstmt2.setInt(1, changeOrder++);
          pstmt2.setInt(2, rs.getInt("List_Num"));
          
          pstmt2.executeUpdate();
        }
      } catch (SQLException e) {
        logger.debug("updateListOrder error:" + e.getMessage());
      } finally {
        SourceReturn();
      }
    }

    // if drag list action
    sql = "update lists set List_Order = ? where List_Num = ?";
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, changeListOrder);
      pstmt.setInt(2, listNum);
      
      pstmt.executeUpdate();
    } catch (SQLException e) {
      logger.debug("updateListOrder error:" + e.getMessage());
    } finally {
      SourceReturn();
    }
  }

  public CardList findByListNum(int listNum){
    String sql="select * from lists where List_Num=?";
    try {
      conn=getConnection();
      pstmt=conn.prepareStatement(sql);
      pstmt.setInt(1, listNum);
      rs=pstmt.executeQuery();
      while(rs.next())
        return new CardList(rs.getInt("List_Num"),rs.getInt("Board_Num"),rs.getString("List_Name"),rs.getInt("List_Order"));
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
 
  public int getListNum(int boardNum,int listOrder){
    String sql="select List_Num from lists where Board_Num=? && List_Order=?";
    try {
      conn=getConnection();
      pstmt=conn.prepareStatement(sql);
      pstmt.setInt(1, boardNum);
      pstmt.setInt(2, listOrder);
      rs=pstmt.executeQuery();
      while(rs.next())
        return rs.getInt("List_Num");  
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }
  
}
