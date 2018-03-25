package ctrl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.BookBean;
import model.*;

/**
 * Servlet implementation class MainPage
 */
@WebServlet("/Start")
public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;

	AccountDAO a;
	BookDAO b;
	List<Integer> allBooks;
	
    /**
     * Default constructor. 
     */
    public Start() {
        // TODO Auto-generated constructor stub
    }
    
    public void init() {
    	//initializes the model
    	a = new AccountDAO();
    	b = new BookDAO();
    	try {
    		allBooks = b.retrieveAllBooks();
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    /*
     * @return a random bid from the allBooks list
     */
    public int randomBook() {
    	Random rand = new Random();
    	int index = rand.nextInt(allBooks.size());
    	return allBooks.get(index);
    }
    
    public void setFrontPageBooks(HttpServletRequest request) {
    	for (int i = 0; i < 6; i++) {
			int bid = randomBook();
			/*try {
				BookBean book = b.retrieveBookByBid(bid);
				request.setAttribute("book" + i, book);
			} catch (SQLException e) {
				e.printStackTrace();
			}*/
			request.setAttribute("randbook" + i, bid);
		}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getSession().removeAttribute("username");						//new session or user is logging out
		
		setFrontPageBooks(request);
		request.getRequestDispatcher("Index.jspx").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
