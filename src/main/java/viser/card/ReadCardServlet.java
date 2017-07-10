package viser.card;

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

@WebServlet("/cards/viewcard")
public class ReadCardServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(ReadCardServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		HttpSession session = req.getSession();
		String userId = (String) session.getAttribute("userId");

		CardDAO cardDao = new CardDAO();
		Card card = new Card();

		int cardNum = Integer.parseInt(req.getParameter("cardNum"));

		try {
			card = cardDao.viewCard(cardNum);

			if (card == null) {
				logger.debug("card View null");
			}

			Gson gson = new Gson();
			String jsonData = gson.toJson(card);
			logger.debug("jsonData:" + jsonData.toString());
			out.print(jsonData);
		} catch (Exception e) {
			logger.debug("cardviewServlet error : " + e);
		} finally {
			out.close();
		}
	}

}