package viser.user.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import viser.support.MyvalidatorFactory;
import viser.user.User;
import viser.user.UserDAO;

@WebServlet("/users/create")
public class CreateUserServlet extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    String userId = request.getParameter("userId");
    String password = request.getParameter("password");
    String name = request.getParameter("name");
    String age = request.getParameter("age");
    String email = request.getParameter("email");
    String gender = request.getParameter("gender");

    User user = new User(userId, password, name, age, email, gender);
    // Validator 유효성 체크
    Validator validator = MyvalidatorFactory.createValidator();
    Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

    if (constraintViolations.size() > 0) {
      request.setAttribute("user", user);
      String errorMessage = constraintViolations.iterator().next().getMessage();
      errorForward(request, response, errorMessage);
      return;
    }

    UserDAO userDAO = new UserDAO();
    try {
      userDAO.addUser(user);
    } catch (SQLException e) {
    }
    response.sendRedirect("/index.jsp");
  }

  private void errorForward(HttpServletRequest request, HttpServletResponse response, String errorMessage) throws ServletException, IOException {
    request.setAttribute("formErrorMessage", errorMessage);
    RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
    rd.forward(request, response);
  }
}
