package viser.cardlist;

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

import viser.card.Card;
import viser.card.CardDAO;

public class CardListDAO {
	public static Logger logger=LoggerFactory.getLogger(CardList.class);

	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs=null;

	public void SourceReturn(){
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
			logger.debug("SourceReturn error:"+e.getMessage());
		}
	}
	
	public Connection getConnection(){
		Properties props=new Properties();
		InputStream in = CardDAO.class.getResourceAsStream("/db.properties");
		try {
			props.load(in);
			in.close();
		} catch (IOException e) {
			logger.debug("InputStream error:"+e.getMessage());
		}
		String driver = props.getProperty("jdbc.driver");
		String url = props.getProperty("jdbc.url");
		String username = props.getProperty("jdbc.username");
		String password = props.getProperty("jdbc.password");

		try {
			Class.forName(driver);
			return DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			logger.debug("DriverManager error:"+e.getMessage());
		}
		return null;
	}
	
	public List getList(int boardNum){
		String sql="select*from lists where Board_Num=?";
		CardDAO cardDao=new CardDAO();
		List<CardList> list=new ArrayList<CardList>();
		try{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			rs=pstmt.executeQuery();
			while(rs.next()){
				CardList cardList=new CardList();
				cardList.setListNum(rs.getInt("List_Num"));
				cardList.setBoardNum(boardNum);
				cardList.setListName(rs.getString("List_Name"));
				cardList.setListOrder(rs.getInt("List_Order"));
				cardList.setCards(cardDao.getCardList(rs.getInt("List_Num")));
				list.add(cardList);
			}
			logger.debug("getList 내용 확인:"+list.get(0).toString());
			return list;
		}catch(SQLException e){
			logger.debug("getList error:"+e.getMessage());
			return null;
		}
		finally{
			SourceReturn();
		}
	}
	
	public void addList(int boardNum,String listName,int listOrder){
		String sql="insert into lists(Board_Num,List_Name,List_Order) values(?,?,?)";
		try{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			pstmt.setString(2, listName);
			pstmt.setInt(3, listOrder);
			pstmt.executeUpdate();
		}catch(SQLException e){
			logger.debug("addList error:"+e.getMessage());
		}
		finally{
			SourceReturn();
		}
	}
}