package viser.cardlist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import viser.board.BoardDAOTest;
import viser.dao.board.BoardDAO;
import viser.dao.cardlist.CardListDAO;
import viser.dao.project.ProjectDAO;
import viser.domain.cardlist.CardList;
import viser.project.ProjectDAOTest;

public class CardListDAOTest {
  private static final Logger logger = LoggerFactory.getLogger(CardListDAOTest.class);
  private ProjectDAO projectDAO;
  private BoardDAO boardDAO;
  private CardListDAO cardListDAO;
  private int boardNum;
  private CardList listA;
  private CardList listB;
  private CardList listC;

  @Before
  public void setUp() throws SQLException {
    projectDAO = new ProjectDAO();
    boardDAO = new BoardDAO();
    cardListDAO = new CardListDAO();

    projectDAO.addProject(ProjectDAOTest.TEST_PROJECT);
    boardDAO.addBoard(BoardDAOTest.TEST_BOARD);
    boardNum=boardDAO.getBoardNum(BoardDAOTest.TEST_BOARD.getBoardName(), BoardDAOTest.TEST_BOARD.getProjectName());
    
    listA=new CardList(boardNum,"TEST_LIST_A",0);
    listB=new CardList(boardNum,"TEST_LIST_B",1);
    listC=new CardList(boardNum,"TEST_LIST_C",2);
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
    //create
    cardListDAO.addList(listA);
    cardListDAO.addList(listB);
    cardListDAO.addList(listC);
    int listNum=cardListDAO.getListNum(listA.getBoardNum(),listA.getListOrder());
    listA.setListNum(listNum);
    logger.debug("CREATE TEST_CARDLIST: {}",listA);
    
    //update
    listA.setListName("UPDATE_TEST");
    cardListDAO.updateListName(listNum, "UPDATE_TEST");
    logger.debug("UPDATE TEST_CARDLIST: {}",listA);
    CardList dbList=cardListDAO.findByListNum(listNum);
    assertEquals(dbList,listA);
    
    //read
    List<CardList> dbLists=cardListDAO.getLists(listA.getBoardNum());
    logger.debug("READ CARDLISTS: {}",dbLists);
    assertNotNull(dbLists);
    
    //delete
    cardListDAO.removeList(listA.getBoardNum(), listA.getListOrder());
    assertNull(cardListDAO.findByListNum(listNum));
    logger.debug("CardList : {}",cardListDAO.getLists(boardNum));
  }
  
  @Test
  public void updateListOrders() throws Exception {
    //create
    List<Integer> standardOrders=new ArrayList<Integer>();
    List<Integer> dbOrders=new ArrayList<Integer>();
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
