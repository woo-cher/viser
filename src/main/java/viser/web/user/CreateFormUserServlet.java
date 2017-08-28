package viser.web.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import viser.domain.user.User;

@WebServlet("/users/createForm")
public class CreateFormUserServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setAttribute("user", new User());
    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/register.jsp");
    rd.forward(request, response);
  }
}
