package viser.web.gantt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import viser.dao.gantt.GanttDAO;
import viser.service.support.SessionUtils;

@WebServlet("/gantts/loadGantt")
public class ReadGanttServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    
    GanttDAO ganttDAO = new GanttDAO();
    List ganttlist = new ArrayList();
    
    int ganttNum = Integer.parseInt(request.getParameter("ganttNum"));
    session.setAttribute("ganttNum", ganttNum);
    
    String projectName = SessionUtils.getStringValue(session, "projectName");
    request.setAttribute("isReadBoard", true);
    request.setAttribute("projectName", projectName);
    request.setAttribute("ganttList", ganttlist = ganttDAO.getGantts(projectName));
    
    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/gantt.jsp");
    rd.forward(request, response);
  }
}
