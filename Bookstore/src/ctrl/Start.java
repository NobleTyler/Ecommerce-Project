package ctrl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
	private int numOnFrontPage = 6;
    /**
     * Default constructor. 
     */
    public Start() {
        // TODO Auto-generated constructor stub
    }
    /**
     * Initialize model with Data Access Objects for:
     * Account
     * Book
     * Then retrieve books. This is used in the start class to load
     * a query from the database then display the books on the home page
     */
    public void init() {
    	a = new AccountDAO();
    	b = new BookDAO();
    	try {
    		allBooks = b.retrieveAllBooks();
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    /*
     * This method is used to randomly generate which books
     * show up on the front page. It does this by calling a random
     * function and then mapping it to the books.
     */
    public int randomBook() {
    	Random rand = new Random();
    	int index = rand.nextInt(allBooks.size());
    	return allBooks.get(index);
    }
    /**
     * This iterates throught list of random books generated in randomBook
     * It does this 6 times which is one for each block in the table.
     * It also checks to make sure the random number is contained in bookID
     * and if it is found it sets the bid and sets the attribute.
     * The list used in randomBid's was meant to remove duplicates
     * @param request
     */
    public void setFrontPageBooks(HttpServletRequest request) {
    	int bid = randomBook();
    	List<Integer> randomBids = new ArrayList<Integer>();		
    	for (int i = 0; i < numOnFrontPage ; i++) {

			while (randomBids.contains(bid)) {
				bid = randomBook();
			}
			request.setAttribute("randbook" + i, bid);
			randomBids.add(bid);
		}
    }

	/**
	 * Sets the highlighted link in the navbar
	 * As well just reloads index.jspx whenever its called
	 * But it sets the Books before calling that
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setAttribute("activenav", "home");								
		setFrontPageBooks(request);
		request.getRequestDispatcher("Index.jspx").forward(request, response);
	}

	/**
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
