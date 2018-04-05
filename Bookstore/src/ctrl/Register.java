package ctrl;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.AccountBean;
import bean.AddressBean;
import model.AccountDAO;
import model.AddressDAO;

/**
 * 
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	/**
	 * Initialize the Account and Address model
	 * These are both required to tie the account details together
	 */
	AccountDAO a;
	AddressDAO ad;
	public void init() {
		a = new AccountDAO();
		ad = new AddressDAO();
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Grab all the parameters from the form
	 * Then we use the account.register user to call that function
	 * once called the adressbean takes in the rest of the user info.
	 * Add that adress to the database as well as the user and it is complete
	 * the user is registered then inform the user
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("uname");
		String password = request.getParameter("psw");
		String rpassword = request.getParameter("rpsw");		//re-entered password
		String fname = request.getParameter("fname");
		String street = request.getParameter("street");
		String city = request.getParameter("city");
		String province = request.getParameter("province");
		String pcode = request.getParameter("pcode");
		
		System.out.println("Pcode: " + pcode);
		
		if (username != null && password != null && rpassword != null) {
			
			try {
				PrintWriter out = response.getWriter();				//output for the form
				
				if (a.registerUser(username, password)) {
					AddressBean ab = new AddressBean(username, fname, street, city, province, pcode);
					ad.addAddress(ab);
					out.print("Registration successful!");
				}
				else {
					out.print("Username: " + username + " is already taken!");
				}
				
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
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
