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
       

	/**
	 * Initialize the model
	 * in this case BookDAO
	 */
	BookDAO b;
	public void init() {
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
	 * When searching we query all books by grabbing the booksearch parameter
	 * we then call what queries the list of books for the string
	 * we then set the attribute and display those books in a later jspx
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
