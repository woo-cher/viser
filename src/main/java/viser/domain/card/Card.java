package viser.domain.card;

import java.sql.Date;

public class Card {

  private int cardNum;
  private String userId;
  private String subject;
  private String content;
  private Date modifyTime;
  private int listNum;
  private int cardOrder;

  public Card() {}

  public Card(int cardNum, String userId, String subject, String content, Date modifyTime, int listNum, int cardOrder) {
    this.cardNum = cardNum;
    this.userId = userId;
    this.subject = subject;
    this.content = content;
    this.modifyTime = modifyTime;
    this.listNum = listNum;
    this.cardOrder = cardOrder;
  }

  public Card(String subject, String content, String userId, int listNum, int cardOrder) {
    this.subject = subject;
    this.content = content;
    this.userId = userId;
    this.listNum = listNum;
    this.cardOrder = cardOrder;
  }


  public Card(int listNum, int cardOrder) {
    this.listNum = listNum;
    this.cardOrder = cardOrder;
  }

  public int getCardNum() {
    return cardNum;
  }

  public void setCardNum(int cardNum) {
    this.cardNum = cardNum;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(Date modifyTime) {
    this.modifyTime = modifyTime;
  }

  public int getListNum() {
    return listNum;
  }

  public void setListNum(int listNum) {
    this.listNum = listNum;
  }

  public int getCardOrder() {
    return cardOrder;
  }

  public void setCardOrder(int cardOrder) {
    this.cardOrder = cardOrder;
  }

  @Override
  public String toString() {
    return "Card [cardNum=" + cardNum + ", userId=" + userId + ", subject=" + subject + ", content=" + content + ", modifyTime=" + modifyTime + ", listNum=" + listNum + ", cardOrder=" + cardOrder + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + cardNum;
    result = prime * result + cardOrder;
    result = prime * result + ((content == null) ? 0 : content.hashCode());
    result = prime * result + listNum;
    result = prime * result + ((modifyTime == null) ? 0 : modifyTime.hashCode());
    result = prime * result + ((subject == null) ? 0 : subject.hashCode());
    result = prime * result + ((userId == null) ? 0 : userId.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Card other = (Card) obj;
    if (cardNum != other.cardNum)
      return false;
    if (cardOrder != other.cardOrder)
      return false;
    if (content == null) {
      if (other.content != null)
        return false;
    } else if (!content.equals(other.content))
      return false;
    if (listNum != other.listNum)
      return false;
    if (modifyTime == null) {
      if (other.modifyTime != null)
        return false;
    } else if (!modifyTime.equals(other.modifyTime))
      return false;
    if (subject == null) {
      if (other.subject != null)
        return false;
    } else if (!subject.equals(other.subject))
      return false;
    if (userId == null) {
      if (other.userId != null)
        return false;
    } else if (!userId.equals(other.userId))
      return false;
    return true;
  }

}
