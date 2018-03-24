package ctrl;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.AccountDAO;
import model.BookDAO;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	AccountDAO a;
	
	public void init() {
    	//initializes the model
    	a = new AccountDAO();
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
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
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
				//request.setAttribute("username", username);
			}
			else if (submitButton.equals("logout")){
				request.getSession().removeAttribute("username");
				//request.removeAttribute("username");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("session username: " + request.getSession().getAttribute("username"));
		request.getRequestDispatcher("Index.jspx").forward(request, response);
	}

}
