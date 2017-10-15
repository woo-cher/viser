package viser.web.gantt;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import viser.dao.gantt.GanttDAO;

@WebServlet("/gantts/ganttList")
public class ReadGanttListServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    GanttDAO ganttDAO = new GanttDAO();

    String projectName = request.getParameter("projectName");
    session.setAttribute("projectName", projectName);

    request.setAttribute("list", ganttDAO.getGantts(projectName));

    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/gantt_list.jsp");
    rd.forward(request, response);
  }
}
