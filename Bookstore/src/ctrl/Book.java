package ctrl;

import java.io.IOException;
import java.sql.SQLException;
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

/**
 * Servlet implementation class Book
 */
@WebServlet("/Book")
public class Book extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	BookDAO b;
	BookReviewDAO br;
	
	public void init() {
		b = new BookDAO();
		br = new BookReviewDAO();
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Book() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int bid = Integer.parseInt(request.getParameter("bid"));
		
		try {
			BookBean book = b.retrieveBookByBid(bid);
			List<BookReviewBean> reviews = br.retrieveBookReviews(bid);		//retrieving reviews for this particular book
			
			request.setAttribute("book", book);
			request.setAttribute("reviews", reviews);
			request.getRequestDispatcher("book.jspx").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
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
