package viser.web.gantt;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import viser.dao.board.BoardDAO;
import viser.domain.gantt.Gantt;
import viser.service.gantt.GanttService;

@WebServlet("/gantts/saveGantt")
public class UpdateGanttServlet extends HttpServlet {

  private static final Logger logger = LoggerFactory.getLogger(UpdateGanttServlet.class);

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    Gson gson = new Gson();
    
    GanttService ganttService = new GanttService();
    String userId = request.getParameter("userId");
    String ganJson = request.getParameter("gantt");
    Integer ganttNum = Integer.parseInt(request.getParameter("ganttNum"));
    HashMap<Long,Integer> newRoles=new HashMap<>();

    JsonParser parser = new JsonParser();
    JsonElement element = parser.parse(ganJson);
    JsonObject gantt = element.getAsJsonObject();
    logger.debug("SaveGantt: {}", gantt);

    Integer addListNum = gantt.get("addListNum").getAsInt();
    Integer boardNum = gantt.get("boardNum").getAsInt();
    String zoom = gantt.get("zoom").getAsString();

    // saveGantt
    ganttService.saveGantt(zoom, ganttNum);

    // deletedAssigIds
    JsonArray jsonDeletedAssigIds = gantt.getAsJsonArray("deletedAssigIds");
    List<Integer> deletedAssigIds = new ArrayList<Integer>();
    for (JsonElement temp : jsonDeletedAssigIds) {
      deletedAssigIds.add(temp.getAsInt());
    }
    logger.debug("deletedAssig: {}", deletedAssigIds);

    // deletedRoleIds
    JsonArray jsonDeletedRoleIds = gantt.getAsJsonArray("deletedRoleIds");
    List<Integer> deletedRoleIds = new ArrayList<Integer>();
    for (JsonElement temp : jsonDeletedRoleIds) {
      deletedRoleIds.add(temp.getAsInt());
    }
    logger.debug("deletedRoleIds: {}", deletedRoleIds);

    // deletedTaskIds
    JsonArray jsonDeletedTaskIds = gantt.getAsJsonArray("deletedTaskIds");
    List<Integer> deletedTaskIds = new ArrayList<Integer>();
    for (JsonElement temp : jsonDeletedTaskIds) {
      deletedTaskIds.add(temp.getAsInt());
    }
    logger.debug("deletedTaskIds: {}", deletedTaskIds);


    // getRoles
    ganttService.saveRoles(gantt.getAsJsonArray("roles"), deletedRoleIds, boardNum, gantt.get("roleUnchanged").getAsBoolean(),newRoles);

    // getTasks
    logger.debug("tasks: {}",gantt.getAsJsonArray("tasks"));
    ganttService.saveTasks(gantt.getAsJsonArray("tasks"), deletedTaskIds,deletedAssigIds, userId, addListNum,newRoles);
    
    // boardProgress update
    BoardDAO boardDAO = new BoardDAO();
    boardDAO.updateBoardProgress(addListNum);
    
    Gantt loadGantt = ganttService.loadGantt(ganttNum);
    out.print(gson.toJson(loadGantt));

  }
}
