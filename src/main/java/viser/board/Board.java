package viser.board;

public class Board {

	private String subject;
	private String content;
	private String userId;
	private String password;
	private String date;
	private int readcount;
	private int num;
	
	public Board() {
	}
	
	public Board(int num, String subject, String content, String userId, String password, String date, int readcount) {
		this.num = num;
		this.subject = subject;
		this.content = content;
		this.userId = userId;
		this.password = password;
		this.date = date;
		this.readcount = readcount;
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
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public int getReadcount() {
		return readcount;
	}
	
	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}
	
	public int getNum() {
		return num;
	}
	
	public void setNum(int num) {
		this.num = num;
	}
	
	public String toString() {
		return "Board [subject=" + subject + ", content=" + content + ", userId=" + userId + ", password=" + password
				+ ", date=" + date + ", readcount=" + readcount + ", num=" + num + "]";
	}

}
