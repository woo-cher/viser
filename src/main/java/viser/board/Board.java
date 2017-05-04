package viser.board;

public class Board {

	private String subject;
	private String content;
	private String userId;
	private String date;
	private int readcnt;
	private int num;
	private int re_ref;
	private int re_lev;
	private int re_seq;
	
	public int getRe_seq() {
		return re_seq;
	}

	public void setRe_seq(int re_seq) {
		this.re_seq = re_seq;
	}

	public int getRe_ref() {
		return re_ref;
	}

	public void setRe_ref(int re_ref) {
		this.re_ref = re_ref;
	}

	public int getRe_lev() {
		return re_lev;
	}

	public void setRe_lev(int re_lev) {
		this.re_lev = re_lev;
	}

	public Board() {
	}
	
	public Board(String userId) {
			
	}
	
	public Board(String subject, String content, String userId) {
		this.subject = subject;
		this.content = content;
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
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public int getReadcnt() {
		return readcnt;
	}
	
	public void setReadcnt(int readcnt) {
		this.readcnt = readcnt;
	}
	
	public int getNum() {
		return num;
	}
	
	public void setNum(int num) {
		this.num = num;
	}
	
	public String toString() {
		return "Board [subject=" + subject + ", content=" + content + ", userId=" + userId
				+ ", date=" + date + ", readcnt=" + readcnt + ", num=" + num + "]";
	}

}
