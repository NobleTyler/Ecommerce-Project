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
	   public Analytics() {
	        super();
	        // TODO Auto-generated constructor stub
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
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	
		if (request.getParameter("submitted") != null) {
		populateByMonth(request, response);
		}
	
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
			
			request.setAttribute("anonymizedpo", po.anonymousOrders());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
