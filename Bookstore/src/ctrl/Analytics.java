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

import model.BookDAO;
import model.BookReviewDAO;
import model.PODAO;
import model.POItemDAO;

/**
 * The analytics class ties together all the analytics
 * it requires multiple DAO's as seen below to display information
 * in the analytics.jspx
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
	   public Analytics() {
	        super();
	        // TODO Auto-generated constructor stub
	    }
	/**
	 * This is used to bring us to the analytics jspx all it requires is the analytics jspx
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
			anonPO(request, response);
			populatePopular(request, response);	
			serveJSP(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//populateByMonth(request, response);
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 *      Handles user submitted requests when the page is open.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	
		if (request.getParameter("submitted") != null) {
			populateByMonth(request, response);
		}
	
		doGet(request, response);
	}
	/*
	 * Querys for the most popular books, requires the product item dao
	 */
	private void populatePopular(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		List<BookBean> mostPop = pd.mostPopular();
		request.setAttribute("popular", mostPop);
	}
	/*
	 * Querys for the most popular books by month, requires the product item dao
	 * works by basically setting the query result as an attribute
	 */
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
	
	/*
	 * Again calls a query then sets it as an attribute
	 * requires the PODAO 
	 */
	private void anonPO(HttpServletRequest request, HttpServletResponse response){
		try {
			if(po.anonymousOrders().size()>0)
				request.setAttribute("anonymizedpo", po.anonymousOrders());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
