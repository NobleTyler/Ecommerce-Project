package ctrl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.BookBean;
import bean.BookReviewBean;
import model.BookDAO;
import model.BookReviewDAO;
import model.DatabaseConnector;
import model.POContainer;
import model.POItemDAO;

/**
 * Servlet implementation class Book
 */
@WebServlet("/Analytics")

public class Analytics extends HttpServlet {
	private static final long serialVersionUID = 1L;

	BookDAO b;
	BookReviewDAO br;
	POContainer pc;
	POItemDAO pd;
	private int mostPopCount = 10;

	public void init() {
		b = new BookDAO();
		br = new BookReviewDAO();
		pc = new POContainer();
		pd = new POItemDAO();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			
		try {
			List<BookBean> mostPop = pd.mostPopular();
			Iterator<BookBean> iterBook= mostPop.iterator();
			int i=0;
				while (iterBook.hasNext()) {
					request.setAttribute("popular" + i, iterBook.next().getBid());
					i++;
				}
			
			request.getRequestDispatcher("analytics.jspx").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		/*
		 * String bidd=request.getParameter("bid"); int bid =
		 * Integer.parseInt(bidd); String hasBought ="damn";
		 * 
		 * request.getSession().setAttribute("bookID", bidd); try { BookBean
		 * book = b.retrieveBookByBid(bid); String username=null; try {
		 * username=request.getSession().getAttribute("username").toString(); }
		 * catch(Exception e) {
		 * 
		 * }
		 * 
		 * if(username!=null) { if(pc.userPurchasedBook(username, bid)==true) {
		 * request.setAttribute("hasBought", hasBought); } }
		 * 
		 * List<BookReviewBean> reviews = br.retrieveBookReviews(bid);
		 * //retrieving reviews for this particular book
		 * request.setAttribute("book", book); if(!reviews.isEmpty()) { String
		 * notEmpty=""; request.setAttribute("notEmpty", notEmpty); }
		 * request.setAttribute("reviews", reviews);
		 * //request.getRequestDispatcher("BookReviewAdd").forward(request,
		 * response);
		 * 
		 * 
		 * request.getRequestDispatcher("book.jspx").forward(request, response);
		 * 
		 * 
		 * } catch (SQLException e) { e.printStackTrace(); }
		 */
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
