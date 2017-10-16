package viser.domain.gantt;

import java.util.List;

import viser.domain.card.Card;
import viser.domain.role.Role;
import viser.domain.user.User;

public class Gantt {
  private List<User> resources;
  private List<Role> roles;
  private List<Card> tasks;
  private boolean canWrite;
  private boolean canWriteOnParent;
  private boolean canDelete;
  private String zoom;
  private int addListNum;
  private int ganttNum;
  private int boardNum;
  private String projectName;
  private String boardName;
  private boolean roleUnchanged;

  public Gantt() {}

  public Gantt(int ganttNum, String boardName, int boardNum) {
    this.ganttNum = ganttNum;
    this.boardName = boardName;
    this.boardNum = boardNum;
  }

  public Gantt(boolean canWrite, boolean canWriteOnParent, boolean canDelete, String zoom, int addListNum, int boardNum, String projectName, int ganttNum, boolean roleUnchanged) {
    this.canWrite = canWrite;
    this.canWriteOnParent = canWriteOnParent;
    this.canDelete = canDelete;
    this.zoom = zoom;
    this.addListNum = addListNum;
    this.boardNum = boardNum;
    this.projectName = projectName;
    this.ganttNum = ganttNum;
    this.roleUnchanged = roleUnchanged;
  }

  public Gantt(boolean canWrite, boolean canWriteOnParent, boolean canDelete, String zoom, int addListNum, int boardNum, String projectName) {
    this.canWrite = canWrite;
    this.canWriteOnParent = canWriteOnParent;
    this.canDelete = canDelete;
    this.zoom = zoom;
    this.addListNum = addListNum;
    this.boardNum = boardNum;
    this.projectName = projectName;
  }

  public String getBoardName() {
    return boardName;
  }

  public void setBoardName(String boardName) {
    this.boardName = boardName;
  }

  public List<User> getProjectMembers() {
    return resources;
  }

  public int getGanttNum() {
    return ganttNum;
  }

  public void setGanttNum(int ganttNum) {
    this.ganttNum = ganttNum;
  }

  public void setProjectMembers(List<User> projectMembers) {
    this.resources = projectMembers;
  }

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  public List<Card> getCards() {
    return tasks;
  }

  public void setCards(List<Card> cards) {
    this.tasks = cards;
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

  public boolean isCanDelete() {
    return canDelete;
  }

  public void setCanDelete(boolean canDelete) {
    this.canDelete = canDelete;
  }

  public String getZoom() {
    return zoom;
  }

  public void setZoom(String zoom) {
    this.zoom = zoom;
  }

  public int getListNum() {
    return addListNum;
  }

  public void setListNum(int addListNum) {
    this.addListNum = addListNum;
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + boardNum;
    result = prime * result + (canDelete ? 1231 : 1237);
    result = prime * result + (canWrite ? 1231 : 1237);
    result = prime * result + (canWriteOnParent ? 1231 : 1237);
    result = prime * result + ((tasks == null) ? 0 : tasks.hashCode());
    result = prime * result + ganttNum;
    result = prime * result + addListNum;
    result = prime * result + ((resources == null) ? 0 : resources.hashCode());
    result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
    result = prime * result + ((roles == null) ? 0 : roles.hashCode());
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
    if (canDelete != other.canDelete)
      return false;
    if (canWrite != other.canWrite)
      return false;
    if (canWriteOnParent != other.canWriteOnParent)
      return false;
    if (tasks == null) {
      if (other.tasks != null)
        return false;
    } else if (!tasks.equals(other.tasks))
      return false;
    if (ganttNum != other.ganttNum)
      return false;
    if (addListNum != other.addListNum)
      return false;
    if (resources == null) {
      if (other.resources != null)
        return false;
    } else if (!resources.equals(other.resources))
      return false;
    if (projectName == null) {
      if (other.projectName != null)
        return false;
    } else if (!projectName.equals(other.projectName))
      return false;
    if (roles == null) {
      if (other.roles != null)
        return false;
    } else if (!roles.equals(other.roles))
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
    return "Gantt [projectMembers=" + resources + ", roles=" + roles + ", cards=" + tasks + ", canWrite=" + canWrite + ", canWriteOnParent=" + canWriteOnParent + ", canDelete=" + canDelete + ", zoom=" + zoom + ", addListNum=" + addListNum + ", ganttNum=" + ganttNum + ", boardNum=" + boardNum + ", projectName=" + projectName + "]";
  }
}
