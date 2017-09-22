package viser.domain.role;

public class Role {
  
  private int roleNum;
  private String roleName;
  private String projectName;
  private int ganttNum;
  
  public Role() {
    
  }
  
  public Role(int roleNum, String roleName, String projectName, int ganttNum) {
    this.roleNum = roleNum;
    this.roleName = roleName;
    this.projectName = projectName;
    this.ganttNum = ganttNum;
  }
  
  public Role(int roleNum, String roleName, String projectName) {
    this.roleNum = roleNum;
    this.roleName = roleName;
    this.projectName = projectName;
  }

  public Role(String roleName, String projectName) {
    this.roleName = roleName;
    this.projectName = projectName;
  }
  
  public int getRoleNum() {
    return roleNum;
  }
  
  public void setRoleNum(int roleNum) {
    this.roleNum = roleNum;
  }
  
  public String getRoleName() {
    return roleName;
  }
  
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }
  
  public String getProjectName() {
    return projectName;
  }
  
  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }
  
  public int getGanttNum() {
    return ganttNum;
  }
  
  public void setGanttNum(int ganttNum) {
    this.ganttNum = ganttNum;
  }
  
  @Override
  public String toString() {
    return "Role [roleNum=" + roleNum + ", roleName=" + roleName + ", projectName=" + projectName + ", ganttNum=" + ganttNum + "]";
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ganttNum;
    result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
    result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
    result = prime * result + roleNum;
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
    Role other = (Role) obj;
    if (ganttNum != other.ganttNum)
      return false;
    if (projectName == null) {
      if (other.projectName != null)
        return false;
    } else if (!projectName.equals(other.projectName))
      return false;
    if (roleName == null) {
      if (other.roleName != null)
        return false;
    } else if (!roleName.equals(other.roleName))
      return false;
    if (roleNum != other.roleNum)
      return false;
    return true;
  }

}
