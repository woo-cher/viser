package viser.web.gantt;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import viser.domain.assignee.Assignee;
import viser.domain.card.Card;
import viser.service.gantt.GanttService;

@WebServlet("/gantts/saveGantt")
public class SaveGanttServlet extends HttpServlet{

private static final Logger logger = LoggerFactory.getLogger(SaveGanttServlet.class);
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    GanttService ganttService=new GanttService();
    String userId =request.getParameter("userId");
    String ganJson=request.getParameter("gantt");
    
    JsonParser parser=new JsonParser();
    JsonElement element=parser.parse(ganJson);
    JsonObject gantt=element.getAsJsonObject();
    logger.debug("SaveGantt: {}",gantt);
    
    Integer addListNum=gantt.get("addListNum").getAsInt();
    Integer boardNum=gantt.get("boardNum").getAsInt();
    Boolean canDelete=gantt.get("canDelete").getAsBoolean();
    Boolean canWrite=gantt.get("canWrite").getAsBoolean();
    Boolean canWriteOnParent=gantt.get("canWriteOnParent").getAsBoolean();
    String  zoom=gantt.get("zoom").getAsString();
    
    //deletedAssigIds
    JsonArray jsonDeletedAssigIds=gantt.getAsJsonArray("deletedAssigIds");
    List<Integer> deletedAssigIds=new ArrayList<Integer>();
    for(JsonElement temp:jsonDeletedAssigIds){
      deletedAssigIds.add(temp.getAsInt());
    }
    logger.debug("deletedAssig: {}",deletedAssigIds);
    
    //deletedRoleIds
    JsonArray jsonDeletedRoleIds=gantt.getAsJsonArray("deletedRoleIds");
    List<Integer> deletedRoleIds=new ArrayList<Integer>();
    for(JsonElement temp:jsonDeletedRoleIds){
      deletedRoleIds.add(temp.getAsInt());
    }
    logger.debug("deletedRoleIds: {}",deletedRoleIds);
    
    //deletedTaskIds
    JsonArray jsonDeletedTaskIds=gantt.getAsJsonArray("deletedTaskIds");
    List<Integer> deletedTaskIds=new ArrayList<Integer>();
    for(JsonElement temp:jsonDeletedTaskIds){
      deletedTaskIds.add(temp.getAsInt());
    }
    logger.debug("deletedTaskIds: {}",deletedTaskIds);
    
    //getRoles
    ganttService.saveRoles(gantt.getAsJsonArray("roles"), deletedRoleIds, boardNum, gantt.get("roleUnchanged").getAsBoolean());
    
    //getTasks
    ganttService.saveTasks(gantt.getAsJsonArray("tasks"),deletedTaskIds,userId,addListNum);
    
    
    
  }
}
