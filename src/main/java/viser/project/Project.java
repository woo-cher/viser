package viser.project;

import java.sql.Date;

public class Project {
private String Project_Name;
private Date Project_Date;
public String getProject_Name() {
	return Project_Name;
}
public void setProject_Name(String project_Name) {
	Project_Name = project_Name;
}
public Date getProject_Date() {
	return Project_Date;
}
public void setProject_Date(Date project_Date) {
	Project_Date = project_Date;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((Project_Date == null) ? 0 : Project_Date.hashCode());
	result = prime * result + ((Project_Name == null) ? 0 : Project_Name.hashCode());
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
	Project other = (Project) obj;
	if (Project_Date == null) {
		if (other.Project_Date != null)
			return false;
	} else if (!Project_Date.equals(other.Project_Date))
		return false;
	if (Project_Name == null) {
		if (other.Project_Name != null)
			return false;
	} else if (!Project_Name.equals(other.Project_Name))
		return false;
	return true;
}
@Override
public String toString() {
	return "Project [Project_Name=" + Project_Name + ", Project_Date=" + Project_Date + "]";
}

}
