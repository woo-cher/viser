package viser.card;

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

import viser.board.BoardDAOTest;
import viser.dao.board.BoardDAO;
import viser.dao.card.CardDAO;
import viser.dao.cardlist.CardListDAO;
import viser.dao.project.ProjectDAO;
import viser.dao.user.UserDAO;
import viser.domain.card.Card;
import viser.domain.cardlist.CardList;
import viser.project.ProjectDAOTest;
import viser.user.UserTest;

public class CardDAOTest {
  private static final Logger logger = LoggerFactory.getLogger(CardDAOTest.class);
  private ProjectDAO projectDAO;
  private BoardDAO boardDAO;
  private CardListDAO cardListDAO;
  private CardDAO cardDAO;
  private Card card;
  private UserDAO userDAO;
  private int boardNum;

  @Before
  public void setUp() throws SQLException {
    projectDAO = new ProjectDAO();
    boardDAO = new BoardDAO();
    cardListDAO = new CardListDAO();
    cardDAO = new CardDAO();
    userDAO = new UserDAO();

    projectDAO.addProject(ProjectDAOTest.TEST_PROJECT);
    boardDAO.addBoard(BoardDAOTest.TEST_BOARD);
    userDAO.addUser(UserTest.TEST_USER);
    boardNum = boardDAO.getBoardNum(BoardDAOTest.TEST_BOARD.getBoardName(), BoardDAOTest.TEST_BOARD.getProjectName());
  }

  @After
  public void reterns() throws SQLException {
    projectDAO.removeProject(ProjectDAOTest.TEST_PROJECT.getProjectName());
    userDAO.removeUser(UserTest.TEST_USER.getUserId());
  }

  @Test
  public void connection() {
    Connection conn = cardDAO.getConnection();
    assertNotNull(conn);
  }

  @Test
  public void crud() throws Exception {
    CardList list = new CardList(boardNum, "TEST_LIST1", 0);
    cardListDAO.addList(list);
    int listNum = cardListDAO.getListNum(boardNum, 0);
    Card testCard = new Card("TEST_CARD", "123", UserTest.TEST_USER.getUserId(), listNum, 0);

    // create
    cardDAO.addCard(testCard);
    int cardNum = cardDAO.getCardNum(listNum, 0);
    testCard.setCardNum(cardNum);
    logger.debug("CREATE CARD: {}", testCard);

    // update
    testCard.setSubject("UPDATE_CARD");
    cardDAO.updateCard(testCard);
    Card dbCard = cardDAO.viewCard(cardNum);
    logger.debug("UPDATE CARD: {}", dbCard);
    assertEquals(testCard.getSubject(), dbCard.getSubject());

    // read
    List<Card> cards = cardDAO.getCards(listNum);
    logger.debug("READ CARD: {}", cards);
    assertNotNull(cards);

    // delete
    cardDAO.removeCard(cardNum, listNum, 0);
    dbCard = null;
    dbCard = cardDAO.viewCard(cardNum);
    assertNull(dbCard);
  }

  @Test
  public void updateCardOrders() throws Exception {
    //list create
    List<Integer> standardOrders=new ArrayList<Integer>();
    List<Integer> dbOrders=new ArrayList<Integer>();
    CardList listA = new CardList(boardNum, "TEST_LIST_A", 0);
    CardList listB = new CardList(boardNum, "TEST_LIST_B", 1);
    cardListDAO.addList(listA);
    cardListDAO.addList(listB);
    int listNumA=cardListDAO.getListNum(boardNum, 0);
    int listNumB=cardListDAO.getListNum(boardNum, 1);
    
    //card create
    Card cardA1=new Card("TEST_CARD_A1","",UserTest.TEST_USER.getUserId(),listNumA,0);
    Card cardA2=new Card("TEST_CARD_A2","",UserTest.TEST_USER.getUserId(),listNumA,1);
    Card cardA3=new Card("TEST_CARD_A3","",UserTest.TEST_USER.getUserId(),listNumA,2);
    Card cardB1=new Card("TEST_CARD_B1","",UserTest.TEST_USER.getUserId(),listNumB,0);
    Card cardB2=new Card("TEST_CARD_B2","",UserTest.TEST_USER.getUserId(),listNumB,1);
    Card cardB3=new Card("TEST_CARD_B3","",UserTest.TEST_USER.getUserId(),listNumB,2);
    cardDAO.addCard(cardA1);
    cardDAO.addCard(cardA2);
    cardDAO.addCard(cardA3);
    cardDAO.addCard(cardB1);
    cardDAO.addCard(cardB2);
    cardDAO.addCard(cardB3);
    int cardNumA1=cardDAO.getCardNum(listNumA, 0);
    int cardNumA2=cardDAO.getCardNum(listNumA, 1);
    int cardNumA3=cardDAO.getCardNum(listNumA, 2);
    int cardNumB1=cardDAO.getCardNum(listNumB, 0);
    int cardNumB2=cardDAO.getCardNum(listNumB, 1);
    int cardNumB3=cardDAO.getCardNum(listNumB, 2);
    
    //currentListOrder == changeListOrder && currentCardOrder > changeCardOrder
    cardDAO.updateCardOrder(boardNum, 0, 0, 2, 0);
    standardOrders.add(1);
    standardOrders.add(2);
    standardOrders.add(0);
    dbOrders.add(cardDAO.viewCard(cardNumA1).getCardOrder());
    dbOrders.add(cardDAO.viewCard(cardNumA2).getCardOrder());
    dbOrders.add(cardDAO.viewCard(cardNumA3).getCardOrder());
    assertEquals(standardOrders,dbOrders);
    
    //currentListOrder == changeListOrder && currentCardOrder < changeCardOrder
    cardDAO.updateCardOrder(boardNum, 0, 0, 0, 2);
    standardOrders.add(0);
    standardOrders.add(1);
    standardOrders.add(2);
    dbOrders.add(cardDAO.viewCard(cardNumA1).getCardOrder());
    dbOrders.add(cardDAO.viewCard(cardNumA2).getCardOrder());
    dbOrders.add(cardDAO.viewCard(cardNumA3).getCardOrder());
    assertEquals(standardOrders,dbOrders);
    standardOrders.clear();
    dbOrders.clear();
    
    //currentListOrder != changeListOrder A -> B
    cardDAO.updateCardOrder(boardNum, 0, 1, 1, 1);
    //listA
    standardOrders.add(0);
    standardOrders.add(listNumA);
    standardOrders.add(1);
    standardOrders.add(listNumA);
    dbOrders.add(cardDAO.viewCard(cardNumA1).getCardOrder());
    dbOrders.add(cardDAO.viewCard(cardNumA1).getListNum());
    dbOrders.add(cardDAO.viewCard(cardNumA3).getCardOrder());
    dbOrders.add(cardDAO.viewCard(cardNumA3).getListNum());
    assertEquals(standardOrders,dbOrders);
    standardOrders.clear();
    dbOrders.clear();
    //listB
    standardOrders.add(0);
    standardOrders.add(listNumB);
    standardOrders.add(1);
    standardOrders.add(listNumB);
    standardOrders.add(2);
    standardOrders.add(listNumB);
    standardOrders.add(3);
    standardOrders.add(listNumB);
    dbOrders.add(cardDAO.viewCard(cardNumB1).getCardOrder());
    dbOrders.add(cardDAO.viewCard(cardNumB1).getListNum());
    dbOrders.add(cardDAO.viewCard(cardNumA2).getCardOrder());
    dbOrders.add(cardDAO.viewCard(cardNumA2).getListNum());
    dbOrders.add(cardDAO.viewCard(cardNumB2).getCardOrder());
    dbOrders.add(cardDAO.viewCard(cardNumB2).getListNum());
    dbOrders.add(cardDAO.viewCard(cardNumB3).getCardOrder());
    dbOrders.add(cardDAO.viewCard(cardNumB3).getListNum());
    assertEquals(standardOrders,dbOrders);
    standardOrders.clear();
    dbOrders.clear();
    
    //currentListOrder != changeListOrder B -> A
    cardDAO.updateCardOrder(boardNum, 1, 0, 3, 2);
    //listA
    standardOrders.add(0);
    standardOrders.add(listNumA);
    standardOrders.add(1);
    standardOrders.add(listNumA);
    standardOrders.add(2);
    standardOrders.add(listNumA);
    dbOrders.add(cardDAO.viewCard(cardNumA1).getCardOrder());
    dbOrders.add(cardDAO.viewCard(cardNumA1).getListNum());
    dbOrders.add(cardDAO.viewCard(cardNumA3).getCardOrder());
    dbOrders.add(cardDAO.viewCard(cardNumA3).getListNum());
    dbOrders.add(cardDAO.viewCard(cardNumB3).getCardOrder());
    dbOrders.add(cardDAO.viewCard(cardNumB3).getListNum());
    assertEquals(standardOrders,dbOrders);
    standardOrders.clear();
    dbOrders.clear();
    //listB
    standardOrders.add(0);
    standardOrders.add(listNumB);
    standardOrders.add(1);
    standardOrders.add(listNumB);
    standardOrders.add(2);
    standardOrders.add(listNumB);
    dbOrders.add(cardDAO.viewCard(cardNumB1).getCardOrder());
    dbOrders.add(cardDAO.viewCard(cardNumB1).getListNum());
    dbOrders.add(cardDAO.viewCard(cardNumA2).getCardOrder());
    dbOrders.add(cardDAO.viewCard(cardNumA2).getListNum());
    dbOrders.add(cardDAO.viewCard(cardNumB2).getCardOrder());
    dbOrders.add(cardDAO.viewCard(cardNumB2).getListNum());
    assertEquals(standardOrders,dbOrders);
    standardOrders.clear();
    dbOrders.clear();
  }
}
