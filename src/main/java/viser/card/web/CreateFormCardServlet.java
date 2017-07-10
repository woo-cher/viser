package viser.card.web;

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

import viser.card.Card;
import viser.support.SessionUtils;
import viser.user.web.LogInServlet;

@WebServlet("/cards/createcardForm")
public class CreateFormCardServlet extends HttpServlet {
  public static Logger logger = LoggerFactory.getLogger(CreateFormCardServlet.class);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    HttpSession session = request.getSession();
    String userId = SessionUtils.getStringValue(session, LogInServlet.SESSION_USER_ID);
    PrintWriter out = response.getWriter();

    int listNum = Integer.parseInt(request.getParameter("listNum"));
    int cardOrder = Integer.parseInt(request.getParameter("cardOrder"));

    try {
      Gson gson = new Gson();
      String jsonData = gson.toJson(new Card(listNum, cardOrder));
      out.print(jsonData);
    } catch (Exception e) {
      logger.debug("CreateFormCardServlet error:" + e.getMessage());
    } finally {
      out.close();
    }
  }
}
