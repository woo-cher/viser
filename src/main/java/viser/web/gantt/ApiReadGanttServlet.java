package viser.web.gantt;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import viser.domain.gantt.Gantt;
import viser.service.gantt.GanttService;
import viser.service.support.SessionUtils;

@WebServlet("/api/gantts/loadGantt")
public class ApiReadGanttServlet extends HttpServlet{
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    GanttService ganttService=new GanttService();
    Gson gson=new Gson();
    PrintWriter out=response.getWriter();
    HttpSession session=request.getSession();
    
    Gantt gantt=ganttService.loadGantt(SessionUtils.getIntegerValue(session, "ganttNum"));
    
    out.print(gson.toJson(gantt));
  }
}
