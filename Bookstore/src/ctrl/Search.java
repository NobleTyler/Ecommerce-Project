package ctrl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.BookBean;
import model.AccountDAO;
import model.BookDAO;

/**
 * Servlet implementation class Search
 */
@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	BookDAO b;
	
	public void init() {
    	//initializes the model
    	b = new BookDAO();
    }
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String queryString = request.getParameter("booksearch");
		try {
			Map<Integer, BookBean> books = b.retrieveBooksByTitle(queryString);
			request.setAttribute("books", books);
			request.getRequestDispatcher("searchResult.jspx").forward(request, response);
			
			/*	BELOW CODE IS WORKING
			for (Entry<Integer, BookBean> book : books.entrySet()) {
				BookBean bb = book.getValue();
				System.out.println("Title: " + bb.getTitle() + ", price: " + bb.getPrice());
			}
			*/
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
