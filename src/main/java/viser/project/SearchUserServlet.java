package viser.project;

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

import viser.support.SessionUtils;
import viser.user.LogInServlet;

@WebServlet("/projects/searchUser")
public class SearchUserServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(SearchUserServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");

    List list = new ArrayList();
    ProjectDAO prjDao = new ProjectDAO();
    HttpSession session = request.getSession();

    session.removeAttribute("keyword");

    String loginUserId = SessionUtils.getStringValue(session, LogInServlet.SESSION_USER_ID);

    String keyword = request.getParameter("keyword");
    session.setAttribute("keyword", keyword);

    logger.debug("검색 키 : " + keyword);
    try {
      list = prjDao.getUserList(keyword, loginUserId);
      Gson gson = new Gson();
      String jsonData = gson.toJson(list);

      PrintWriter out = response.getWriter();
      out.print(jsonData);

    } catch (Exception e) {
      logger.debug("Search fail : " + e);
    }
  }
}
