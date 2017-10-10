package viser.domain.board;

public class Board {

  private int boardNum;
  private String projectName;
  private String boardName;
  private int boardProgress;

  public Board() {

  }
  
  public Board(int boardNum, String projectName, String boardName, int boardProgress) {
    this.boardNum = boardNum;
    this.projectName = projectName;
    this.boardName = boardName;
    this.boardProgress = boardProgress;
  }

  public Board(String boardName, String projectName) {
    this.boardName = boardName;
    this.projectName = projectName;
  }

  public int getBoardNum() {
    return boardNum;
  }

  public void setBoardNum(int boardNum) {
    this.boardNum = boardNum;
  }

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
  
  public int getBoardProgress() {
    return boardProgress;
  }

  public void setBoardProgress(int boardProgress) {
    this.boardProgress = boardProgress;
  }

  @Override
  public String toString() {
    return "Board [boardNum=" + boardNum + ", projectName=" + projectName + ", boardName=" + boardName + ", boardProgress=" + boardProgress + "]";
  }
}
