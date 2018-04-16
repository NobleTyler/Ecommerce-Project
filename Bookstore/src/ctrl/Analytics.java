package ctrl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.BookBean;
import bean.BookReviewBean;
import bean.OrderHistoryBean;
import model.BookDAO;
import model.BookReviewDAO;
import model.DatabaseConnector;
import model.POContainer;
import model.PODAO;
import model.POItemDAO;

/**
 * Servlet implementation class Book
 */
@WebServlet("/Analytics")

public class Analytics extends HttpServlet {
	private static final long serialVersionUID = 1L;

	BookDAO b;
	BookReviewDAO br;
	PODAO po;
	POItemDAO pd;
	private int mostPopCount = 10;

	public void init() {
		b = new BookDAO();
		br = new BookReviewDAO();
		po = new PODAO();
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
			System.out.println("WHATS UP BITCH");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		//Current Date to get report period
		
		
		try {
			//UC A1: Set report with book id and quantity as attribute.
			request.setAttribute("report", po.retrievePOByMonth(month));
			System.out.println(request.getAttribute("report"));
			
			List<OrderHistoryBean> report =  po.retrieveOrderHistory(yearMonthFormat.format(date)+"01");
			Iterator<OrderHistoryBean> iteReport= report.iterator();
			while(iteReport.hasNext())
				System.out.println(iteReport.next().getTitle());
			
			//UC A3: Set all PO records as attribute.
			request.setAttribute("anonymizedpo", po.retrieveAllPO());
			List<OrderHistoryBean> anonPO =  po.retrieveOrderHistory(yearMonthFormat.format(date)+"01");
			Iterator<OrderHistoryBean> iteAnonPO= anonPo.iterator();
			while(iteReport.hasNext())
				System.out.println(iteAnonPO.next().getTitle());
			//Forward to page
			request.getRequestDispatcher("/analytics.jspx").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
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
