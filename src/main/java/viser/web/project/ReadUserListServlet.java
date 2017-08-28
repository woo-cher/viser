package viser.web.project;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import viser.dao.project.ProjectDAO;
import viser.service.support.SessionUtils;

@WebServlet("/projects/searchUser")
public class ReadUserListServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(ReadUserListServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List list = new ArrayList();
    ProjectDAO projectDAO = new ProjectDAO();
    HttpSession session = request.getSession();

    session.removeAttribute("keyword");

    String projectName=SessionUtils.getStringValue(session, "projectName");
    String keyword = request.getParameter("keyword");
    
    session.setAttribute("keyword", keyword);

    logger.debug("검색 키 : " + keyword);
    logger.debug("Session_projectName = " + projectName);
    try {
      list = projectDAO.getUserList(keyword, projectName);
      Gson gson = new Gson();
      String jsonData = gson.toJson(list);

      PrintWriter out = response.getWriter();
      out.print(jsonData);

    } catch (Exception e) {
      logger.debug("Search fail : " + e);
    }
  }
}

