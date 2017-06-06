package viser.board;

public class Board {
	private String projectName;
	private String boardName;
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getBoardName() {
		return boardName;
	}
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	
	@Override
	public String toString() {
		return "Board [projectName=" + projectName + ", boardName=" + boardName + "]";
	}

	
	
	}
