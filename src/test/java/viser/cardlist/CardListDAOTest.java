package viser.cardlist;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import viser.board.BoardDAO;
import viser.board.BoardDAOTest;
import viser.card.Card;
import viser.card.CardDAO;
import viser.project.ProjectDAO;
import viser.project.ProjectDAOTest;

public class CardListDAOTest {
  private static final Logger logger = LoggerFactory.getLogger(CardListDAOTest.class);
  private ProjectDAO projectDAO;
  private BoardDAO boardDAO;
  private CardListDAO cardListDAO;
  private int boardNum;

  @Before
  public void setUp() throws SQLException {
    projectDAO = new ProjectDAO();
    boardDAO = new BoardDAO();
    cardListDAO = new CardListDAO();

    projectDAO.addProject(ProjectDAOTest.TEST_PROJECT);
    boardDAO.addBoard(BoardDAOTest.TEST_BOARD);
    boardNum=boardDAO.getBoardNum(BoardDAOTest.TEST_BOARD.getBoardName(), BoardDAOTest.TEST_BOARD.getProjectName());
  }

  @After
  public void returns() throws SQLException{
    projectDAO.removeProject(ProjectDAOTest.TEST_PROJECT.getProjectName());
  }
  
  @Test
  public void connection() {
    Connection conn = cardListDAO.getConnection();
    assertNotNull(conn);
  }

  @Test
  public void crud() throws Exception {
    CardList testCardList = new CardList(boardNum,"TEST_LIST", 0);
    
    //create
    cardListDAO.addList(testCardList);
    int listNum=cardListDAO.getListNum(testCardList.getBoardNum(),testCardList.getListOrder());
    testCardList.setListNum(listNum);
    logger.debug("CREATE TEST_CARDLIST: {}",testCardList);
    
    //update
    testCardList.setListName("UPDATE_TEST");
    cardListDAO.updateListName(listNum, "UPDATE_TEST");
    logger.debug("UPDATE TEST_CARDLIST: {}",testCardList);
    CardList dbList=cardListDAO.findByListNum(listNum);
    assertEquals(dbList,testCardList);
    
    //read
    List<CardList> dbLists=cardListDAO.getLists(testCardList.getBoardNum());
    logger.debug("READ CARDLISTS: {}",dbLists);
    assertNotNull(dbLists);
    
    //delete
    cardListDAO.removeList(testCardList.getBoardNum(), testCardList.getListOrder());
    assertNull(cardListDAO.findByListNum(listNum));
  }
  
  @Test
  public void updateListOrders() throws Exception {
    //create
    List<Integer> standardOrders=new ArrayList<Integer>();
    List<Integer> dbOrders=new ArrayList<Integer>();
    CardList listA=new CardList(boardNum,"TEST_LIST_A",0);
    CardList listB=new CardList(boardNum,"TEST_LIST_B",1);
    CardList listC=new CardList(boardNum,"TEST_LIST_C",2);
    cardListDAO.addList(listA);
    cardListDAO.addList(listB);
    cardListDAO.addList(listC);
    int listNumA=cardListDAO.getListNum(listA.getBoardNum(), listA.getListOrder());
    int listNumB=cardListDAO.getListNum(listB.getBoardNum(), listB.getListOrder());
    int listNumC=cardListDAO.getListNum(listC.getBoardNum(), listC.getListOrder());
    
    //currentListOrder>changeListOrder
    cardListDAO.updateListOrder(boardNum, 2, 0);
    standardOrders.add(1);
    standardOrders.add(2);
    standardOrders.add(0);
    logger.debug("current>change standard: {}",standardOrders);
    dbOrders.add(cardListDAO.findByListNum(listNumA).getListOrder());
    dbOrders.add(cardListDAO.findByListNum(listNumB).getListOrder());
    dbOrders.add(cardListDAO.findByListNum(listNumC).getListOrder());
    logger.debug("current>change db: {}",dbOrders);
    assertEquals(standardOrders,dbOrders);
    standardOrders.clear();
    dbOrders.clear();
    
    //currentListOrder<changeListOrder
    cardListDAO.updateListOrder(boardNum, 0, 2);
    standardOrders.add(0);
    standardOrders.add(1);
    standardOrders.add(2);
    logger.debug("current<change standard: {}",standardOrders);
    dbOrders.add(cardListDAO.findByListNum(listNumA).getListOrder());
    dbOrders.add(cardListDAO.findByListNum(listNumB).getListOrder());
    dbOrders.add(cardListDAO.findByListNum(listNumC).getListOrder());
    logger.debug("current<change db: {}",dbOrders);
    assertEquals(standardOrders,dbOrders);
  }
}
