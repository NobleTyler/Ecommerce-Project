package ctrl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.POBean;
import model.POContainer;
import model.ShoppingCartDAO;

/**
 * Servlet implementation class Checkout
 *  This class works by adding attributes from the cart to a database
 *  also stores a lot to do with requests for request tracking
 */
@WebServlet("/Checkout")
public class Checkout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static int requestNumber;		//used to track
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Checkout() {
        super();
        requestNumber = 0;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * get the username, then get the cartsize of them
	 * then set the price through the get Cart total price method
	 * then foreward them to the checkout
	 * this is basically called when they click the checkout button
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String submitParam = request.getParameter("submit");
		
		//System.out.println(submitParam);
		String cartSubmit = "Continue to checkout";
		
		if (submitParam.equals(cartSubmit)) {
			ShoppingCartDAO sc = new ShoppingCartDAO();
			POContainer poContain = new POContainer();
			String username = request.getSession().getAttribute("username").toString();
			try {
				int cartSize = sc.getCartSize(username);
				System.out.println("Checkout cartSize: " + cartSize);
				request.setAttribute("cartPrice", sc.getCartTotalPrice(username));
				
				String lname = request.getParameter("cardlname");
				String fname = request.getParameter("cardfname");
				Timestamp date = new Timestamp(System.currentTimeMillis());
				String status = POBean.DENIED;		//default value until verified card (hard coded for every third request for now)
				String id = date.toString() + username;
				
				POBean po = new POBean(id, lname, fname, username, status, date);
				request.setAttribute("productOrder", po);			//passing the po to the confirm order page
				
				if (requestNumber % 3 == 0) {				//hard coded only to accept every third request
					po.setStatus(POBean.PROCESSED);
					request.setAttribute("orderApproved", true);
					sc.clearCart(username); 			//order has been processed, clear users cart
					request.getSession().setAttribute("cartSize", 0);
				}
				else {
					request.setAttribute("orderApproved", false);
				}
				requestNumber++;
				//default value for status is denied, so we only set to processed if it is the third request
				poContain.addProductOrder(po, sc.retrieveCartItems(po.getUsername()));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		request.getRequestDispatcher("checkout.jspx").forward(request, response);
	}

}
