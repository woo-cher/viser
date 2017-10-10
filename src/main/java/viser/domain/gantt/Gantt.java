package viser.domain.gantt;

import java.util.List;

import viser.domain.card.Card;
import viser.domain.role.Role;
import viser.domain.user.User;

public class Gantt {
  private List<User> projectMembers;
  private List<Role> roles;
  private List<Card> cards;
  private List<String> deleteTaskId;
  private List<String> deleteAssigId;
  private boolean canWrite;
  private boolean canWriteOnParent;
  private boolean canDelete;
  private boolean cannotCloseTaskIfIssueOpen;
  private boolean canAddIssue;
  
  public List<User> getProjectMembers() {
    return projectMembers;
  }

  public void setProjectMembers(List<User> projectMembers) {
    this.projectMembers = projectMembers;
  }

  public List<Card> getCards() {
    return cards;
  }

  public void setCards(List<Card> cards) {
    this.cards = cards;
  }

  private int selectedRow;
  private String zoom;
  private int listNum;
  private int boardNum;
  private String userId;
  
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public List<String> getDeleteTaskId() {
    return deleteTaskId;
  }

  public void setDeleteTaskId(List<String> deleteTaskId) {
    this.deleteTaskId = deleteTaskId;
  }

  public List<String> getDeleteAssigId() {
    return deleteAssigId;
  }

  public void setDeleteAssigId(List<String> deleteAssigId) {
    this.deleteAssigId = deleteAssigId;
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

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  public boolean isCanWrite() {
    return canWrite;
  }

  public void setCanWrite(boolean canWrite) {
    this.canWrite = canWrite;
  }

  public boolean isCanWriteOnParent() {
    return canWriteOnParent;
  }

  public void setCanWriteOnParent(boolean canWriteOnParent) {
    this.canWriteOnParent = canWriteOnParent;
  }

  public boolean isCannotCloseTaskIfIssueOpen() {
    return cannotCloseTaskIfIssueOpen;
  }

  public void setCannotCloseTaskIfIssueOpen(boolean cannotCloseTaskIfIssueOpen) {
    this.cannotCloseTaskIfIssueOpen = cannotCloseTaskIfIssueOpen;
  }

  public boolean isCanAddIssue() {
    return canAddIssue;
  }

  public void setCanAddIssue(boolean canAddIssue) {
    this.canAddIssue = canAddIssue;
  }

  public boolean isCanDelete() {
    return canDelete;
  }

  public void setCanDelete(boolean canDelete) {
    this.canDelete = canDelete;
  }

  public int getSelectedRow() {
    return selectedRow;
  }

  public void setSelectedRow(int selectedRow) {
    this.selectedRow = selectedRow;
  }

  public String getZoom() {
    return zoom;
  }

  public void setZoom(String zoom) {
    this.zoom = zoom;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + boardNum;
    result = prime * result + (canAddIssue ? 1231 : 1237);
    result = prime * result + (canDelete ? 1231 : 1237);
    result = prime * result + (canWrite ? 1231 : 1237);
    result = prime * result + (canWriteOnParent ? 1231 : 1237);
    result = prime * result + (cannotCloseTaskIfIssueOpen ? 1231 : 1237);
    result = prime * result + ((cards == null) ? 0 : cards.hashCode());
    result = prime * result + ((deleteAssigId == null) ? 0 : deleteAssigId.hashCode());
    result = prime * result + ((deleteTaskId == null) ? 0 : deleteTaskId.hashCode());
    result = prime * result + listNum;
    result = prime * result + ((projectMembers == null) ? 0 : projectMembers.hashCode());
    result = prime * result + ((roles == null) ? 0 : roles.hashCode());
    result = prime * result + selectedRow;
    result = prime * result + ((userId == null) ? 0 : userId.hashCode());
    result = prime * result + ((zoom == null) ? 0 : zoom.hashCode());
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
    Gantt other = (Gantt) obj;
    if (boardNum != other.boardNum)
      return false;
    if (canAddIssue != other.canAddIssue)
      return false;
    if (canDelete != other.canDelete)
      return false;
    if (canWrite != other.canWrite)
      return false;
    if (canWriteOnParent != other.canWriteOnParent)
      return false;
    if (cannotCloseTaskIfIssueOpen != other.cannotCloseTaskIfIssueOpen)
      return false;
    if (cards == null) {
      if (other.cards != null)
        return false;
    } else if (!cards.equals(other.cards))
      return false;
    if (deleteAssigId == null) {
      if (other.deleteAssigId != null)
        return false;
    } else if (!deleteAssigId.equals(other.deleteAssigId))
      return false;
    if (deleteTaskId == null) {
      if (other.deleteTaskId != null)
        return false;
    } else if (!deleteTaskId.equals(other.deleteTaskId))
      return false;
    if (listNum != other.listNum)
      return false;
    if (projectMembers == null) {
      if (other.projectMembers != null)
        return false;
    } else if (!projectMembers.equals(other.projectMembers))
      return false;
    if (roles == null) {
      if (other.roles != null)
        return false;
    } else if (!roles.equals(other.roles))
      return false;
    if (selectedRow != other.selectedRow)
      return false;
    if (userId == null) {
      if (other.userId != null)
        return false;
    } else if (!userId.equals(other.userId))
      return false;
    if (zoom == null) {
      if (other.zoom != null)
        return false;
    } else if (!zoom.equals(other.zoom))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Gantt [projectMembers=" + projectMembers + ", roles=" + roles + ", cards=" + cards + ", deleteTaskId=" + deleteTaskId + ", deleteAssigId=" + deleteAssigId + ", canWrite=" + canWrite + ", canWriteOnParent=" + canWriteOnParent + ", canDelete=" + canDelete + ", cannotCloseTaskIfIssueOpen=" + cannotCloseTaskIfIssueOpen + ", canAddIssue=" + canAddIssue + ", selectedRow=" + selectedRow + ", zoom=" + zoom + ", listNum=" + listNum + ", boardNum=" + boardNum + ", userId=" + userId + "]";
  }
}
