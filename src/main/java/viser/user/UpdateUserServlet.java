package viser.user;

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

import viser.support.MyvalidatorFactory;
import viser.support.SessionUtils;

@WebServlet("/users/update")
public class UpdateUserServlet extends HttpServlet {
  private static final Logger logger = LoggerFactory.getLogger(UpdateUserServlet.class);

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");

    HttpSession session = req.getSession();
    String sessionUseId = SessionUtils.getStringValue(session, LogInServlet.SESSION_USER_ID);

    // ★ 로그인 여부 판단
    if (sessionUseId == null) {
      resp.sendRedirect("/");
      logger.debug("UpdateUserServlet error");
      return;
    }

    // ★ 로그인의 상태일 때, 세션에 저장된 유저와 로그인 유저 ID 비교
    String userId = (String) sessionUseId;

    /*
     * if (!sessionUseId.equals(userId)) { logger.debug("\nsession id = " + sessionUseId +
     * "\nlogin id = " + userId); resp.sendRedirect("/"); return; }
     */

    String password = req.getParameter("password");
    String name = req.getParameter("name");
    String age = req.getParameter("age");
    String email = req.getParameter("email");
    String gender = req.getParameter("gender");

    User user = new User(userId, password, name, age, email, gender);

    // Validator 유효성 체크
    Validator validator = MyvalidatorFactory.createValidator();
    Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

    if (constraintViolations.size() > 0) {
      req.setAttribute("isUpdate", true);
      req.setAttribute("user", user);
      String errorMessage = constraintViolations.iterator().next().getMessage();
      errorForward(req, resp, errorMessage);
      return;
    }

    UserDAO userDao = new UserDAO();

    try {
      userDao.updateUser(user);
      session.setAttribute("user", user);
      logger.debug("개인정보 수정 : " + password);
    } catch (SQLException e) {
      logger.debug("SQL Exception error" + e);
    }

    resp.sendRedirect("/project/projectlist"); // 개인정보 수정하고 반응해줄 응답페이지 필요함.

  }

  private void errorForward(HttpServletRequest request, HttpServletResponse response, String errorMessage) throws ServletException, IOException {
    request.setAttribute("formErrorMessage", errorMessage);
    RequestDispatcher rd = request.getRequestDispatcher("/commons/top.jspf"); // 에러메시지
                                                                              // 보낼
                                                                              // 페이지
                                                                              // 필요
                                                                              // (에이젝스)
    rd.forward(request, response);
  }

}
