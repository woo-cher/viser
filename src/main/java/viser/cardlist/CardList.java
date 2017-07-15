package viser.cardlist;

import java.util.ArrayList;
import java.util.List;

import viser.card.Card;

public class CardList {

  private int listNum;
  private int boardNum;
  private String listName;
  private int listOrder;
  private List<Card> cards = new ArrayList<Card>();

  public CardList() {
  }

  public CardList(int boardNum,String listName,int listOrder){
    this.boardNum=boardNum;
    this.listName=listName;
    this.listOrder=listOrder;
  }

  public CardList(String listName, int listOrder) {
    this.listName=listName;
    this.listOrder=listOrder;
  }

  public CardList(int listNum, int boardNum, String listName, int listOrder) {
    this.listNum=listNum;
    this.boardNum=boardNum;
    this.listName=listName;
    this.listOrder=listOrder;
  }

  public int getListNum() {
    return listNum;
  }

  public void setListNum(int listNum) {
    this.listNum = listNum;
  }

  public int getBoardNum() {
    return boardNum;
  }

  public void setBoardNum(int boardNum) {
    this.boardNum = boardNum;
  }

  public String getListName() {
    return listName;
  }

  public void setListName(String listName) {
    this.listName = listName;
  }

  public int getListOrder() {
    return listOrder;
  }

  public void setListOrder(int listOrder) {
    this.listOrder = listOrder;
  }

  public List<Card> getCards() {
    return cards;
  }

  public void setCards(List<Card> cards) {
    this.cards = cards;
  }

  @Override
  public String toString() {
    return "CardList [listNum=" + listNum + ", boardNum=" + boardNum + ", listName=" + listName + ", listOrder=" + listOrder + ", cards=" + cards + "]";
  }

}
