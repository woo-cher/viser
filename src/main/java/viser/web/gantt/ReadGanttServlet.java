package viser.web.gantt;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/gantts/loadGantt")
public class ReadGanttServlet extends HttpServlet {
 @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   HttpSession session =request.getSession();
   
   int ganttNum=Integer.parseInt(request.getParameter("ganttNum"));
   session.setAttribute("ganttNum", ganttNum);
   
   RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/gantt.jsp");
   rd.forward(request, response);
 }
}
