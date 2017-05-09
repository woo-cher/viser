package viser.card;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/card/Searchlist")
public class SearchCardlistServlet extends HttpServlet{
	private static final Logger logger = LoggerFactory.getLogger(SearchCardlistServlet.class);
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		CardDAO cardDao = new CardDAO();
		List list = new ArrayList();
		
		int page = 1;	// 기본 페이지
		int limit = 10; // 최대 페이지
		
		
		// 사용자의 요청(req)을 통해 "page" 파라미터가 있는 확인
		if(req.getParameter("page") != null){
			page = Integer.parseInt(req.getParameter("page"));
		}
		
		req.setCharacterEncoding("utf-8");
		String keyfield = req.getParameter("keyField");
		String keyword = req.getParameter("keyWord");
		
		int listcount;
		
		try {
			logger.debug(keyfield + " " + keyword);
			list = cardDao.getSearchcardList(page, limit, keyfield, keyword); 	// 게시물을 LIST 객체에 담습니다. 
			listcount = list.size() + 1;	// 게시물의 총 개수를 가져옵니다.
																	
		// 최대 페이지를 구합니다.
   		int maxpage = (int)((double)listcount / limit + 0.95); // 0.95 올림처리
   		
   		// 시작 페이지를 구합니다. ex ) start page count(1, 11, 21...)
   		int startpage = (((int)((double)page / 10 + 0.9)) - 1) * 10 + 1;
   		
   		// 마지막 페이지를 구합니다. ex ) last page count(10, 20, 30...)
   		int endpage = maxpage;
   		if(endpage > startpage + 10 - 1) endpage = startpage + 10 - 1;
   		
   		req.setAttribute("page", page);		  		// 현재 페이지
   		req.setAttribute("maxpage", maxpage); 		// 최대 페이지
   		req.setAttribute("startpage", startpage); 	// 시작 페이지
   		req.setAttribute("endpage", endpage);     	// 마지막 페이지
		req.setAttribute("count", listcount); 		// 게시물 총 개수
		req.setAttribute("list", list);
		
		RequestDispatcher rd = req.getRequestDispatcher("/card.jsp");
		rd.forward(req, resp);
	
		} catch (Exception e) {
			System.out.println(e);
		}
	
		
	}

}
