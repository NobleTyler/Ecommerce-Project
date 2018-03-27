package ctrl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.BookBean;
import model.CategoryDAO;

/**
 * Servlet implementation class Categories
 */
@WebServlet({"/Categories", "/Categories/*"})
public class Categories extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	CategoryDAO c;
	
	public void init() {
		c = new CategoryDAO();
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Categories() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("activenav", "category");								//sets the highlighted link in the navbar
		String cat = request.getParameter("cat");
		
		if (request.getParameter("cat") != null) {
			try {
				Map<Integer, BookBean> books = c.retrieveBooksForCategory(cat);
				request.setAttribute("books", books);
				request.getRequestDispatcher("searchResult.jspx").forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				List<String> categories = c.retrieveAllCategories();
				request.setAttribute("categories", categories);
				request.getRequestDispatcher("categories.jspx").forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
