package viser.card;

import java.sql.Date;

public class Card {

	private int cardNum;
	private String userId;
	private String subject;
	private String content;
	private Date modifyTime;
	private int listNum;
	private int cardOrder;
	
	public Card(String subject, String content, String userId, int listNum, int cardOrder) {
		this.subject=subject;
		this.content=content;
		this.userId=userId;
		this.listNum=listNum;
		this.cardOrder=cardOrder;
	}
	
	
	public Card() {
		
	}


	public Card(int listNum, int cardOrder) {
		this.listNum=listNum;
		this.cardOrder=cardOrder;
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

	/**
	 * review : 포매터를 돌리면 불필요한 2줄 개행 같은 것을 방지 할 수 있음
	 * 팀 내에서 포매터를 사용하지 않으면 불필요한 file diff가 발생하여 리뷰하기 어려울 수 있음
     */
	@Override
	public String toString() {
		return "Card [cardNum=" + cardNum + ", userId=" + userId + ", subject=" + subject + ", content=" + content
				+ ", modifyTime=" + modifyTime + ", listNum=" + listNum + ", cardOrder=" + cardOrder + "]";
	}

}