package ctrl;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.*;

/**
 * Servlet implementation class MainPage
 */
@WebServlet("/Start")
public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;

	AccountDAO a;
	BookDAO b;
	
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
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String URI = request.getRequestURI();
		System.out.println("URI: " + URI);
		String submitParam = request.getParameter("submit");
		System.out.println("submitParam:" + submitParam);
		
		if (submitParam != null) {
			if (submitParam.equals("login")) {			//user has requested to login
				
			}
			else if (submitParam.equals("register")) {	//user has requested to register
				
			}
			else if (submitParam.equals("booksearchsubmit")) {		//user has requested to search for a book
				request.getSession().setAttribute("loggedIn", request.getParameter("booksearch"));
				System.out.println("searching for a book");
			}
		}
		
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
