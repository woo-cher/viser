package viser.web.user;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import viser.dao.user.UserDAO;
import viser.domain.user.User;
import viser.service.support.MyvalidatorFactory;
import viser.service.support.SessionUtils;

@WebServlet("/users/update")
public class UpdateUserServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(UpdateUserServlet.class);

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    String sessionUseId = SessionUtils.getStringValue(session, LogInServlet.SESSION_USER_ID);

    if (sessionUseId == null) {
      response.sendRedirect("/");
      logger.debug("UpdateUserServlet error");
      return;
    }

    String userId = (String) sessionUseId;
    String password = request.getParameter("password");
    String name = request.getParameter("name");
    String age = request.getParameter("age");
    String email = request.getParameter("email");
    String gender = request.getParameter("gender");

    User user = new User(userId, password, name, age, email, gender);

    Validator validator = MyvalidatorFactory.createValidator();
    Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

    if (constraintViolations.size() > 0) {
      request.setAttribute("isUpdate", true);
      request.setAttribute("user", user);
      String errorMessage = constraintViolations.iterator().next().getMessage();
      errorForward(request, response, errorMessage);
      return;
    }

    UserDAO userDAO = new UserDAO();

    try {
      userDAO.updateUser(user);
      session.setAttribute("user", user);
      logger.debug("개인정보 수정 : " + password);
    } catch (SQLException e) {
      logger.debug("SQL Exception error" + e);
    }

    response.sendRedirect("/project/projectlist");
  }

  private void errorForward(HttpServletRequest request, HttpServletResponse response, String errorMessage) throws ServletException, IOException {
    request.setAttribute("formErrorMessage", errorMessage);
    RequestDispatcher rd = request.getRequestDispatcher("/commons/top.jspf");
    rd.forward(request, response);
  }
}
