package ctrl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.BookBean;
import filter.Anonymizer;
import model.BookDAO;
import model.BookReviewDAO;
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

	public void init() {
		b = new BookDAO();
		br = new BookReviewDAO();
		po = new PODAO();
		pd = new POItemDAO();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	private void serveJSP(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String target = "/analytics.jspx";
		request.getRequestDispatcher(target).forward(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			populatePopular(request, response);
			anonPO(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//populateByMonth(request, response);
		serveJSP(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	
		if (request.getParameter("submitted") != null) {
		populateByMonth(request, response);
		}
		// UC A3: Set all PO records as attribute.
		/*
		 * request.setAttribute("anonymizedpo", po.retrieveAllPO());
		 * List<OrderHistoryBean> anonPO =
		 * po.retrieveOrderHistory(yearMonthFormat.format(date)+"01");
		 * Iterator<OrderHistoryBean> iteAnonPO= anonPo.iterator();
		 * while(iteReport.hasNext())
		 * System.out.println(iteAnonPO.next().getTitle()); //Forward to page
		 */

		doGet(request, response);
	}

	private void populatePopular(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		List<BookBean> mostPop = pd.mostPopular();
		request.setAttribute("popular", mostPop);
	}

	private void populateByMonth(HttpServletRequest request, HttpServletResponse response) {

		//StringBuilder monthTable = new StringBuilder();
		int month = 0;
		if(request.getParameter("submitted")!=null) {
			month=Integer.parseInt(request.getParameter("month"));
		
		}

		try {
			List<BookBean> report = pd.mostPopularMonthly(month);
	/*		Iterator<BookBean> iteReport = report.iterator();
				while (iteReport.hasNext())
					System.out.println(iteReport.next().getTitle());*/
				request.setAttribute("monthly", report);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private void anonPO(HttpServletRequest request, HttpServletResponse response){
		try {
			
			request.setAttribute("anonymizedpo", po.retrieveAllPO());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
