package ctrl;


import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BookDAO;
import model.BookReviewDAO;
import bean.BookBean;
import bean.BookReviewBean;

/**
 * Servlet implementation class BookReviewAdd
 */
@WebServlet("/BookReviewAdd")
public class BookReviewAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;
       BookDAO b;
       BookReviewDAO br;
       BookBean book;
       BookReviewBean bookReview;
       Date now;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookReviewAdd() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init() {
		br = new BookReviewDAO();
		b=new BookDAO();
		//book=new BookBean();
		now=new Date();
	}
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username=request.getSession().getAttribute("username").toString();
		String bid = request.getSession().getAttribute("bookID").toString();
		int bookID=Integer.parseInt(bid);
		//boolean rate=false;
		String rawComment=request.getParameter("comment");
		String bookname="";
		float rating=-1;
		
		//Date format to match that accepted by MySQL
		String datePattern="yyyy-MM-dd";
		SimpleDateFormat formatter=new SimpleDateFormat(datePattern);
		String finalDate=formatter.format(now);
		
		try {
			rating=Float.parseFloat(request.getParameter("rating"));
		} catch(Exception e) {
			rating=-1;
		}
		System.out.println(bookID);
		try {
			book=b.retrieveBookByBid(bookID);
			bookname=book.getTitle();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		java.sql.Date date= new java.sql.Date(System.currentTimeMillis());
//		try {
//			 date=formatter.parse(finalDate);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//response.getWriter().append("BookID= ").print(bookID+" bookname= " +bookname+" rating= "+rating+ " Date= "+finalDate+" Comment: "+rawComment);
		
		if (rating != -1) {
			bookReview = new BookReviewBean(bookID, rawComment, username, rating, date);
			try {
				br.addReview(bookReview);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.getWriter().append("Everything went fine");
			String redirection=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()+"/Book?bid="+bid;
			response.sendRedirect(redirection);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
