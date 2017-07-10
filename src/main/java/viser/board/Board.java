package viser.board;

public class Board {

  private int boardNum;
  private String projectName;
  private String boardName;

  public Board() {

  }

  public Board(int boardNum, String boardName, String projectName) {
    this.boardNum = boardNum;
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

  @Override
  public String toString() {
    return "Board [boardNum=" + boardNum + ", projectName=" + projectName + ", boardName=" + boardName + "]";
  }

}
