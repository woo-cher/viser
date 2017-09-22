package viser.web.assignee;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import viser.dao.assignee.AssigneeDAO;
import viser.domain.assignee.Assignee;
import viser.service.support.SessionUtils;

@WebServlet("/assignees/updateAssignee")
public class UpdateAssigneeServlet extends HttpServlet{
  private static final Logger logger = LoggerFactory.getLogger(UpdateAssigneeServlet.class);
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      AssigneeDAO assigneeDAO = new AssigneeDAO();
      Assignee assignee = new Assignee();
      HttpSession session = request.getSession();
      
      int assigneeNum = Integer.parseInt(request.getParameter("assigneeNum"));
      String assigneeMember = request.getParameter("assigneeMember");
      String roleName = request.getParameter("roleName");
      String projectName = SessionUtils.getStringValue(session, "projectName");
      logger.debug("UpdateAssigneeServlet Para :\nassigneeNum : " + assigneeNum + "\nassigneeMember : " + assigneeMember + "\nroleName : " + roleName + "\nprojectName : " + projectName);
     
      try {
        assigneeDAO.updateAssignee(assigneeNum, assigneeMember, roleName, projectName);
        assignee.setAssigneeNum(assigneeNum);
        assignee.setUserId(assigneeMember);
        assignee.setRoleName(roleName);
        Gson gson = new Gson();
        String gsonData = gson.toJson(assignee);
        PrintWriter out = response.getWriter();
        out.println(gsonData);
      } catch (Exception e) {
        logger.debug("UpdateAssigneeServlet Error : " + e.getMessage());
      }
  }
}
