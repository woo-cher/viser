package viser.service.gantt;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import viser.dao.assignee.AssigneeDAO;
import viser.dao.card.CardDAO;
import viser.dao.role.RoleDAO;
import viser.domain.assignee.Assignee;
import viser.domain.card.Card;
import viser.domain.role.Role;

public class GanttService {

  private static final Logger logger = LoggerFactory.getLogger(GanttService.class);
   
  public static int getBoolaenValue(boolean value){
    if(value==true) return 1;
    else return 0;
  }
  
  public void saveTasks(JsonArray jsonTasks, List<Integer> deletedRoleIds, String userId, int listNum) {
    // getCards
    List<Card> tasks = new ArrayList<Card>();
    CardDAO cardDAO = new CardDAO();

    for (int i = 0; i < jsonTasks.size(); i++) {
      // init
      Integer cardNum;
      String subject;
      String content;
      Integer progress;
      Integer level;
      String status;
      Boolean taskCanWrite;
      Long start;
      Integer duration;
      Long end;
      Boolean assigUnchanged;
      Boolean unchanged;
      List<Assignee> assignees;
      Boolean hasChild;
      Integer taskOrder = i;
      Integer cardOrder;

      // getFromJson
      cardNum = new Integer(jsonTasks.get(i).getAsJsonObject().get("id").getAsInt());
      subject = new String(jsonTasks.get(i).getAsJsonObject().get("name").getAsString());
      progress = new Integer(jsonTasks.get(i).getAsJsonObject().get("progress").getAsInt());
      content = new String(jsonTasks.get(i).getAsJsonObject().get("description").getAsString());
      level = new Integer(jsonTasks.get(i).getAsJsonObject().get("level").getAsInt());
      status = new String(jsonTasks.get(i).getAsJsonObject().get("status").getAsString());
      taskCanWrite = new Boolean(jsonTasks.get(i).getAsJsonObject().get("canWrite").getAsBoolean());
      start = new Long(jsonTasks.get(i).getAsJsonObject().get("start").getAsLong());
      duration = new Integer(jsonTasks.get(i).getAsJsonObject().get("duration").getAsInt());
      end = new Long(jsonTasks.get(i).getAsJsonObject().get("end").getAsLong());
      assigUnchanged = new Boolean(jsonTasks.get(i).getAsJsonObject().get("assigUnchanged").getAsBoolean());
      unchanged = new Boolean(jsonTasks.get(i).getAsJsonObject().get("unchanged").getAsBoolean());
      hasChild = new Boolean(jsonTasks.get(i).getAsJsonObject().get("hasChild").getAsBoolean());

      if (unchanged == false) {
        if (cardNum < 0) {
          logger.debug("addCard");
          cardDAO.addCard(new Card(userId, subject, content, listNum, progress, level, status, taskCanWrite, start, duration, end, hasChild), taskOrder);
        } else {
          logger.debug("updateCard");
          cardDAO.updateCard(new Card(userId, subject, content, progress, level, status, start, duration, end, hasChild), taskOrder);
        }
      }

      // saveAssignees
      saveAssignees(jsonTasks.get(i).getAsJsonObject().get("assigs").getAsJsonArray(), cardNum, deletedRoleIds,assigUnchanged);
    }
  }

  public void saveAssignees(JsonArray assigns, int cardNum, List<Integer> deletedAssigIds,Boolean assigUnchanged) {
    AssigneeDAO assigneeDAO = new AssigneeDAO();

    for (JsonElement index : assigns) {
      JsonObject assign = index.getAsJsonObject();
      logger.debug("assign: {}", assign);
      int assigneeNum = assign.get("id").getAsInt();
      int projectMemberNum = assign.get("resourceId").getAsInt();
      int roleNum = assign.get("roleId").getAsInt();

      if (assigUnchanged == false) {
        if (assigneeNum < 0) {
          logger.debug("newAssignee: {}", assigneeNum);
          assigneeDAO.addAssignee(projectMemberNum, roleNum, cardNum);
        } else if (deletedAssigIds.contains(assigneeNum)) {
          assigneeDAO.removeAssignee(assigneeNum);
        } else { // changed
          assigneeDAO.updateAssignee(assigneeNum, projectMemberNum, roleNum);
        }
      }
    }
  }

  public void saveRoles(JsonArray jsonRoles,List<Integer> deletedRoleIds,int boardNum,Boolean roleUnchanged){
    RoleDAO roleDAO=new RoleDAO();
    
    for(JsonElement index:jsonRoles){
      int roleNum=index.getAsJsonObject().get("id").getAsInt();
      String roleName=index.getAsJsonObject().get("name").getAsString();
      
      if(roleUnchanged==false){
        if(roleNum<0){
          roleDAO.addRole(roleName, boardNum);
        }
        else if(deletedRoleIds.contains(roleNum)){
          roleDAO.deleteRole(roleNum);
        }
        else{ //changed
          roleDAO.updateRole(roleNum, roleName);
        }
      }
    }
  }
  
}
