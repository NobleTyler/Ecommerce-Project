package ctrl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.AddressBean;
import model.AccountDAO;
import model.AddressDAO;
import model.BookDAO;
import model.ShoppingCartDAO;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/**
	 * Initiaslize the account Shopping Cart and Adress used in the login.
	 * All of the databases are used in the login 
	 */
	AccountDAO a;
	ShoppingCartDAO sc;
	AddressDAO ad;
	public void init() {
    	a = new AccountDAO();
    	sc = new ShoppingCartDAO();
    	ad = new AddressDAO();
    }
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * Basically just lets you know where it was served at. Not super useful
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * This just grabs the login and if it was true
	 * then we set the username to the stored username and then fill their card
	 * and redirect them back to the bookstore with their username
	 * This then persists across the ession
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String username = request.getParameter("uname");
		String password = request.getParameter("psw");
		
		try {
			boolean result = a.attemptLogin(username, password);
			String submitButton = request.getParameter("submit");
			System.out.println("Login: " + submitButton);
			
			
			if (result) {
				request.getSession().setAttribute("username", username);
				request.getSession().setAttribute("cartSize", sc.getCartSize(username));  		//setting the cart size for the nav bar

				AddressBean userAddress = ad.retrieveAddress(username);
				request.getSession().setAttribute("userAddress", userAddress);
				
				response.sendRedirect("/Bookstore/Start");
			}
			else {
				PrintWriter out = response.getWriter();
				out.print("wrong");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("session username: " + request.getSession().getAttribute("username"));
	}

}
