package viser.project;

import java.sql.Date;

public class Project {
	
private String projectName;
private Date projectDate;

public String getProjectName() {
	return projectName;
}
public void setProjectName(String projectName) {
	this.projectName = projectName;
}
public Date getProjectDate() {
	return projectDate;
}
public void setProjectDate(Date projectDate) {
	this.projectDate = projectDate;
}

@Override
public String toString() {
	return "Project [projectName=" + projectName + ", projectDate=" + projectDate + "]";
}



}
