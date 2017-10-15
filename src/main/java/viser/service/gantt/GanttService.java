
package viser.service.gantt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import viser.dao.assignee.AssigneeDAO;
import viser.dao.card.CardDAO;
import viser.dao.cardlist.CardListDAO;
import viser.dao.gantt.GanttDAO;
import viser.dao.project.ProjectDAO;
import viser.dao.role.RoleDAO;
import viser.domain.assignee.Assignee;
import viser.domain.card.Card;
import viser.domain.cardlist.CardList;
import viser.domain.gantt.Gantt;

public class GanttService {
  GanttDAO ganttDAO = new GanttDAO();
  CardDAO cardDAO = new CardDAO();
  CardListDAO cardListDAO = new CardListDAO();
  RoleDAO roleDAO = new RoleDAO();
  AssigneeDAO assigneeDAO = new AssigneeDAO();
  ProjectDAO projectDAO = new ProjectDAO();

  private static final Logger logger = LoggerFactory.getLogger(GanttService.class);

  public void saveGantt(String zoom, Integer ganttNum) {
    ganttDAO.updateGantt(zoom, ganttNum);
  }

  public void saveTasks(JsonArray jsonTasks, List<Integer> deletedTaskIds, List<Integer> deletedAssigIds, String userId, int listNum, HashMap newRoles) {
    // deleted
    for (int deleteTask : deletedTaskIds) {
      cardDAO.removeCard(deleteTask);
    }

    for (int i = 0; i < jsonTasks.size(); i++) {
      // getFromJson
      int taskOrder = i;
      long cardNum = new Long(jsonTasks.get(i).getAsJsonObject().get("cardNum").getAsLong());
      String subject = new String(jsonTasks.get(i).getAsJsonObject().get("subject").getAsString());
      int progress = new Integer(jsonTasks.get(i).getAsJsonObject().get("progress").getAsInt());
      String content = new String(jsonTasks.get(i).getAsJsonObject().get("content").getAsString());
      int level = new Integer(jsonTasks.get(i).getAsJsonObject().get("level").getAsInt());
      String status = new String(jsonTasks.get(i).getAsJsonObject().get("status").getAsString());
      boolean taskCanWrite = new Boolean(jsonTasks.get(i).getAsJsonObject().get("canWrite").getAsBoolean());
      long start = new Long(jsonTasks.get(i).getAsJsonObject().get("start").getAsLong());
      int duration = new Integer(jsonTasks.get(i).getAsJsonObject().get("duration").getAsInt());
      boolean assigUnchanged = new Boolean(jsonTasks.get(i).getAsJsonObject().get("assigUnchanged").getAsBoolean());
      boolean unchanged = new Boolean(jsonTasks.get(i).getAsJsonObject().get("unchanged").getAsBoolean());
      boolean hasChild = new Boolean(jsonTasks.get(i).getAsJsonObject().get("hasChild").getAsBoolean());

      if (cardNum < 0) { // new
        logger.debug("addCard");
        cardNum = cardDAO.addCard(new Card(userId, subject, content, listNum, progress, level, status, taskCanWrite, start, duration, hasChild), taskOrder);
      } else { // changed
        if (unchanged == false) {
          logger.debug("updateCard");
          cardDAO.updateCard(new Card((int) cardNum, userId, subject, content, progress, level, status, start, duration, hasChild, taskCanWrite), taskOrder);
        }
      }

      // saveAssignees
      saveAssignees(jsonTasks.get(i).getAsJsonObject().get("assigs").getAsJsonArray(), (int) cardNum, deletedAssigIds, assigUnchanged, newRoles);
    }
  }

  public void saveAssignees(JsonArray assigns, int cardNum, List<Integer> deletedAssigIds, Boolean assigUnchanged, HashMap<Long, Integer> newRoles) {
    // deleted
    for (int deleteAssigneeNum : deletedAssigIds) {
      assigneeDAO.removeAssignee(deleteAssigneeNum);
    }

    for (JsonElement index : assigns) {
      JsonObject assign = index.getAsJsonObject();
      logger.debug("assign: {}", assign);
      long assigneeNum = assign.get("assigneeNum").getAsLong();
      int projectMemberNum = assign.get("projectMemberNum").getAsInt();
      long roleNum = assign.get("roleNum").getAsLong();
      if (roleNum < 0) {
        roleNum = newRoles.get(roleNum).longValue();
      }

      if (assigUnchanged == false) {
        if (assigneeNum < 0) { // new
          logger.debug("newAssignee: {}", assigneeNum);
          assigneeDAO.addAssignee(projectMemberNum, (int) roleNum, cardNum);
        } else { // changed
          assigneeDAO.updateAssignee((int) assigneeNum, projectMemberNum, (int) roleNum);
        }
      }
    }
  }

  public void saveRoles(JsonArray jsonRoles, List<Integer> deletedRoleIds, int boardNum, Boolean roleUnchanged, HashMap newRoles) {
    // delete role
    for (int deleteRoleNum : deletedRoleIds) {
      roleDAO.deleteRole(deleteRoleNum);
    }

    // update role
    for (JsonElement index : jsonRoles) {
      long roleNum = index.getAsJsonObject().get("roleNum").getAsLong();
      String roleName = index.getAsJsonObject().get("roleName").getAsString();

      if (roleUnchanged == false) {
        if (roleNum < 0) { // new
          newRoles.put(roleNum, roleDAO.addRole(roleName, boardNum).getRoleNum());
        } else { // changed
          roleDAO.updateRole((int) roleNum, roleName);
        }
      }
    }
  }

  public Gantt loadGantt(int ganttNum) {
    Gantt gantt = ganttDAO.getGantt(ganttNum);
    logger.debug("gantt: {}", gantt);
    int boardNum = gantt.getBoardNum();
    String projectName = gantt.getProjectName();
    // boardNum으로 board에 있는 listNum 가져옴
    List<CardList> cardLists = cardListDAO.getLists(boardNum);
    // listNum으로 getCardsForGantt cards에 추가
    gantt.setCards(new LinkedList());
    for (CardList index : cardLists) {
      List<Card> cards = cardDAO.getCardsForGantt(index.getListNum());
      for (Card temp : cards) {
        temp.setAssignees(assigneeDAO.getAssignees(temp.getCardNum()));
        gantt.getCards().add(temp);
      }
    }
    // boardNum으로 getRoleList gantt에 추가
    gantt.setRoles(roleDAO.getRoleList(boardNum));
    // projectName으로 getProjectMemberList gantt에 추가
    gantt.setProjectMembers(projectDAO.getProjectMemberList(projectName));

    return gantt;
  }
}
