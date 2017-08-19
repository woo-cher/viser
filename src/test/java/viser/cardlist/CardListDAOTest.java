package viser.cardlist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import viser.board.BoardDAOTest;
import viser.dao.board.BoardDAO;
import viser.dao.cardlist.CardListDAO;
import viser.dao.project.ProjectDAO;
import viser.domain.board.Board;
import viser.domain.cardlist.CardList;
import viser.domain.project.Project;
import viser.project.ProjectDAOTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext.xml")
public class CardListDAOTest {
  private static final Logger logger = LoggerFactory.getLogger(CardListDAOTest.class);

  @Autowired private Project project;
  @Autowired private ProjectDAO projectDAO;
  @Autowired private BoardDAO boardDAO;
  @Autowired private CardListDAO cardListDAO;
  @Autowired private Board board;
  @Autowired private CardList listA;
  @Autowired private CardList listB;
  @Autowired private CardList listC;
  
  @Before
  public void setUp() throws SQLException {
    project = ProjectDAOTest.TEST_PROJECT;
    projectDAO.addProject(project);
    boardDAO.addBoard(BoardDAOTest.TEST_BOARD);
    board.setBoardNum(boardDAO.getBoardNum(BoardDAOTest.TEST_BOARD.getBoardName(), BoardDAOTest.TEST_BOARD.getProjectName()));

    listA = new CardList(board.getBoardNum(), "TEST_LIST_A", 0);
    listB = new CardList(board.getBoardNum(), "TEST_LIST_B", 1);
    listC = new CardList(board.getBoardNum(), "TEST_LIST_C", 2);
  }

  @After
  public void returns() throws SQLException {
    projectDAO.removeProject(project.getProjectName());
  }

  @Test
  public void crud() throws Exception {
    // create
    cardListDAO.addList(listA);
    cardListDAO.addList(listB);
    cardListDAO.addList(listC);
    int listNumA = cardListDAO.getListNum(listA.getBoardNum(), listA.getListOrder());
    int listNumB = cardListDAO.getListNum(listB.getBoardNum(), listB.getListOrder());
    int listNumC = cardListDAO.getListNum(listC.getBoardNum(), listC.getListOrder());
    listA.setListNum(listNumA);
    logger.debug("CREATE TEST_CARDLIST: {}", listA);

    // update
    listA.setListName("UPDATE_TEST");
    cardListDAO.updateListName(listNumA, "UPDATE_TEST");
    logger.debug("UPDATE TEST_CARDLIST: {}", listA);
    CardList dbList = cardListDAO.findByListNum(listNumA);
    assertEquals(dbList, listA);

    // read
    List<CardList> dbLists = cardListDAO.getLists(listA.getBoardNum());
    logger.debug("READ CARDLISTS: {}", dbLists);
    assertNotNull(dbLists);

    // delete
    cardListDAO.removeList(listA.getBoardNum(), listA.getListOrder());
    logger.debug("AFTER DELETE CARDLIST : {}", cardListDAO.getLists(board.getBoardNum()));
    assertNull(cardListDAO.findByListNum(listNumA));
    assertEquals(cardListDAO.getListNum(listB.getBoardNum(), 0), listNumB);
    assertEquals(cardListDAO.getListNum(listC.getBoardNum(), 1), listNumC);
  }

  @Test
  public void updateListOrders() throws Exception {
    // create
    List<Integer> standardOrders = new ArrayList<Integer>();
    List<Integer> dbOrders = new ArrayList<Integer>();
    cardListDAO.addList(listA);
    cardListDAO.addList(listB);
    cardListDAO.addList(listC);
    int listNumA = cardListDAO.getListNum(listA.getBoardNum(), listA.getListOrder());
    int listNumB = cardListDAO.getListNum(listB.getBoardNum(), listB.getListOrder());
    int listNumC = cardListDAO.getListNum(listC.getBoardNum(), listC.getListOrder());

    // currentListOrder>changeListOrder
    cardListDAO.updateListOrder(board.getBoardNum(), 2, 0);
    standardOrders.add(1);
    standardOrders.add(2);
    standardOrders.add(0);
    logger.debug("current>change standard: {}", standardOrders);
    dbOrders.add(cardListDAO.findByListNum(listNumA).getListOrder());
    dbOrders.add(cardListDAO.findByListNum(listNumB).getListOrder());
    dbOrders.add(cardListDAO.findByListNum(listNumC).getListOrder());
    logger.debug("current>change db: {}", dbOrders);
    assertEquals(standardOrders, dbOrders);
    standardOrders.clear();
    dbOrders.clear();

    // currentListOrder<changeListOrder
    cardListDAO.updateListOrder(board.getBoardNum(), 0, 2);
    standardOrders.add(0);
    standardOrders.add(1);
    standardOrders.add(2);
    logger.debug("current<change standard: {}", standardOrders);
    dbOrders.add(cardListDAO.findByListNum(listNumA).getListOrder());
    dbOrders.add(cardListDAO.findByListNum(listNumB).getListOrder());
    dbOrders.add(cardListDAO.findByListNum(listNumC).getListOrder());
    logger.debug("current<change db: {}", dbOrders);
    assertEquals(standardOrders, dbOrders);
  }
}
