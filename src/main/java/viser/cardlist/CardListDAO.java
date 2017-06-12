package viser.cardlist;

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

import viser.card.CardDAO;

public class CardListDAO {
	public static Logger logger=LoggerFactory.getLogger(CardList.class);

	Connection conn=null;
	PreparedStatement pstmt=null;
	PreparedStatement pstmt2=null;
	ResultSet rs=null;
	ResultSet rs2=null;

	public void SourceReturn(){
		try{
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
		String sql="select*from lists where Board_Num=? order by List_Order";
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
	public void removeList(int boardNum,int listOrder){
		String sql="delete from lists where Board_Num=? && List_Order=?";
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			pstmt.setInt(2, listOrder);
			pstmt.executeUpdate();
			
			sql="select List_Num from lists where Board_Num=? && List_Order>?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			pstmt.setInt(2, listOrder);
			rs=pstmt.executeQuery();
			
			String sql2="update lists set List_Order=? where List_Num=?";
			int changeOrder=listOrder;
			while(rs.next()){
				pstmt2=conn.prepareStatement(sql2);
				pstmt2.setInt(1,changeOrder++);
				pstmt2.setInt(2, rs.getInt("List_Num"));
				pstmt2.executeUpdate();
			}
			
		} catch (SQLException e) {
			logger.debug("removeList error:"+e.getMessage());
		}
		
	}
	public void updateListOrder(int boardNum,int currentListOrder,int updateListOrder){
		int listNum=0;  //원래 위치에 있던 리스트의 번호
		int changeOrder=0; //인덱스를  변화시킬때 사용할 순서번호
		Timestamp date=new Timestamp(new Date().getTime());  //업데이트 시간
		String sql;
		String sql2;
		
		//형근: 먼저 업데이트 시키는 ListNum을 알아냄 
		sql="select List_Num from lists where Board_Num=? && List_Order=?";
		conn=getConnection();
		try {
			pstmt=conn.prepareStatement(sql);
			//형근: 드래그한 카드의 정보
			pstmt.setInt(1, boardNum);
			pstmt.setInt(2, currentListOrder);
			rs=pstmt.executeQuery();
			while(rs.next()){
				listNum=rs.getInt("List_Num");
			}
		} catch (SQLException e) {
			logger.debug("updateListOrder error:"+e.getMessage());
		}
		
		//형근: 바뀐 위치보다 기존 리스트 순서가 더 클 경우 기존 리스트 순서 부터 바뀐 리스트 전 리스트까지 순서 갱신
		if(currentListOrder>updateListOrder){
			sql="select List_Num from lists where Board_Num=? && List_Order>=? && List_Order<?  order by List_Order asc";
			sql2="update lists set List_Order = ? where List_Num=?";
			changeOrder=updateListOrder+1;
			try {
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, boardNum);
				pstmt.setInt(2, updateListOrder);
				pstmt.setInt(3, currentListOrder);
				rs=pstmt.executeQuery();
				while(rs.next()){
					pstmt2=conn.prepareStatement(sql2);
					pstmt2.setInt(1, changeOrder++);
					pstmt2.setInt(2, rs.getInt("List_Num"));
					pstmt2.executeUpdate();
				}
			} catch (SQLException e) {
				logger.debug("updateListOrder error:"+e.getMessage());
			}
		}
		
		//형근: 기존 리스트 순서보다 바뀐 리스트순서가 크면 기존 리스트 옆 부터 바뀐 리스트 위치 까지 순서 갱신
		else{
			sql="select List_Num from lists where Board_Num=? && List_Order>? && List_Order<=? order by List_Order asc";
			sql2="update lists set List_Order = ? where List_Num=?";
			changeOrder=currentListOrder;
			try {
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, boardNum);
				pstmt.setInt(2, currentListOrder);
				pstmt.setInt(3, updateListOrder);
				rs=pstmt.executeQuery();
				while(rs.next()){
					pstmt2=conn.prepareStatement(sql2);
					pstmt2.setInt(1, changeOrder++);
					pstmt2.setInt(2, rs.getInt("List_Num"));
					pstmt2.executeUpdate();
					}
				} catch (SQLException e) {
					logger.debug("updateListOrder error:"+e.getMessage());
			}
		}
		
		//형근: 드래그하여 옮긴 리스트 위치 갱신
		sql="update lists set List_Order = ? where List_Num = ?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, updateListOrder);
			pstmt.setInt(2, listNum);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.debug("updateListOrder error:"+e.getMessage());
		}
		
	}
	
	public void updateCardOrder(int boardNum,int currentListOrder,int updateListOrder){
		int listNum=0;  //원래 위치에 있던 리스트의 번호
		int changeOrder=0; //인덱스를  변화시킬때 사용할 순서번호
		Timestamp date=new Timestamp(new Date().getTime());  //업데이트 시간
		
		//형근: 먼저 업데이트 시키는 ListNum을 알아냄 
		String sql="select List_Num from lists where Board_Num=? && List_Order=?";
		conn=getConnection();
		try {
			pstmt=conn.prepareStatement(sql);
			//형근: 드래그한 카드의 정보
			pstmt.setInt(1, boardNum);
			pstmt.setInt(2, currentListOrder);
			rs=pstmt.executeQuery();
			while(rs.next()){
				listNum=rs.getInt("List_Num");
			}
		} catch (SQLException e) {
			logger.debug("updateListOrder error:"+e.getMessage());
		}
		
		
		//형근: 바뀔 위치의 원래 리스트 부터 그 위로 마지막 리스트까지 순서 갱신
		sql="select List_Num from lists where Board_Num=? && List_Order>=? order by List_Order asc";
		String sql2="update lists set List_Order = ? where List_Num=?";
		changeOrder=updateListOrder;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			pstmt.setInt(2, updateListOrder);
			rs=pstmt.executeQuery();
			while(rs.next()){
				pstmt2=conn.prepareStatement(sql2);
				pstmt2.setInt(1, changeOrder++);
				pstmt2.setInt(2, rs.getInt("List_Num"));
				pstmt2.executeUpdate();
			}
		} catch (SQLException e) {
			logger.debug("updateListOrder error:"+e.getMessage());
		}
		
		//형근: 드래그하여 옮긴 리스트 위치 갱신
		sql="update lists set List_Order = ? where List_Num = ?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, updateListOrder);
			pstmt.setInt(2, listNum);
			pstmt.executeUpdate();
			} catch (SQLException e) {
				logger.debug("updateListOrder error:"+e.getMessage());
			}
		
		//형근: 바뀐 리스트 기존 위치부터 바뀐 리스트 위치 아래 까지 순서 갱신
		sql="select List_Num from lists where Board_Num=? && List_Order>? && List_Order<? order by List_Order asc";
		sql2="update lists set List_Order = ? where List_Num=?";
		changeOrder=currentListOrder;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			pstmt.setInt(2, currentListOrder);
			pstmt.setInt(3, updateListOrder);
			rs=pstmt.executeQuery();
			while(rs.next()){
				pstmt2=conn.prepareStatement(sql2);
				pstmt2.setInt(1, changeOrder++);
				pstmt2.setInt(2, rs.getInt("List_Num"));
				}
			} catch (SQLException e) {
				logger.debug("updateListOrder error:"+e.getMessage());
			}
	}
	
	
}