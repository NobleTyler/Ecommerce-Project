package ctrl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.BookBean;
import model.BookDAO;
import model.ShoppingCartDAO;

/**
 * Servlet implementation class Cart
 */
@WebServlet("/Cart")
public class Cart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Cart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("Cart get called! Username:" + request.getSession().getAttribute("username"));															
			
		String username = request.getSession().getAttribute("username").toString();
		//<p><a href="#">Product 1</a> <span class="price">$15</span></p>
		String cartTable = createCartTable(username);		//create the cart table for a specific user
		
		request.setAttribute("cartTable", cartTable);
		request.getRequestDispatcher("shoppingCart.jspx").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("Cart post called! Username:" + request.getParameter("username") + " bid:" + request.getParameter("bid"));
		
		String username = request.getSession().getAttribute("username").toString();
		ShoppingCartDAO sc = new ShoppingCartDAO();
		
		if (request.getParameter("incBid") != null) {								//wanting to increment quantity of a bid in the cart page
			int bid = Integer.parseInt(request.getParameter("incBid"));
			PrintWriter out = response.getWriter();
			
			
			try {
				sc.addItemToCart(username, bid);						
				String cartTable = createCartTable(username);
				
				out.print(cartTable);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if (request.getParameter("bid") != null) {								//request is for adding a single bid to the cart
			int bid = Integer.parseInt(request.getParameter("bid"));
			PrintWriter out = response.getWriter();
			
			
			try {
				sc.addItemToCart(username, bid);								//getting the cart size after the addition of the new item for updating the display
				int cartSize = sc.getCartSize(username);
				
				request.getSession().setAttribute("cartSize", cartSize);
				out.print("Cart(" + cartSize + ")");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if (request.getParameter("removeBid") != null) {					//user has pressed the remove button on the cart page
			PrintWriter out = response.getWriter();
			if (request.getParameter("removeBid").equals("all")) {
				try {
					sc.clearCart(username);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if (request.getParameter("quant") != null) {
				System.out.println("QUANT NOT NULL: " + request.getParameter("quant"));
				int quantToRemove = Integer.parseInt(request.getParameter("quant"));
				int bidToRemove = Integer.parseInt(request.getParameter("removeBid"));
				
				try {
					sc.removeItemFromCart(username, bidToRemove, quantToRemove);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else {
				int bidToRemove = Integer.parseInt(request.getParameter("removeBid"));
				try {
					sc.removeItemFromCart(username, bidToRemove);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			String cartTable = createCartTable(username);
			out.print(cartTable);							//update the cart table
		}
	}
	
	protected String createCartTable(String username) {
		BookDAO bd = new BookDAO();
		ShoppingCartDAO sc = new ShoppingCartDAO();
		StringBuilder cartTable = new StringBuilder();
		try {
			Map<Integer, Integer> cart = sc.retrieveCartItems(username);
			//<h4>Cart <span class="price" style="color:black"> <b>4</b></span></h4>
			cartTable.append("<div class='row'>"
							+ "<div class='col-25'></div>"
							+ "<div class='col-50'><h4>Cart <a href=\"javascript:removeItemFromCart('/Bookstore/Cart?removeBid=all')\">[Click here to clear cart]</a></h4></div> "
							+ "<div class='col-25'><h4><span class=\"price\" style=\"color:black\"> <b>" + sc.getCartSize(username) + "</b></span></h4></div>"
							+ "</div>");
			for (Entry<Integer, Integer> entry : cart.entrySet()) {
				BookBean b = bd.retrieveBookByBid(entry.getKey());
				String title = /*(b.getTitle().length() > 25) ? b.getTitle().substring(0, 25) + "..." :*/ b.getTitle();			//shortening title to fit in cart area
				//<a href=\"javascript:removeItemFromCart('/Bookstore/Cart?removeBid=" + b.getBid() + "')
				cartTable.append("<div class='row'>"
								+ "<div class='col-25'>"
								+ 	"<div class='row'>"
								+ 		"<div class='col-50'>"
								+ 		"<a href=\"javascript:removeItemFromCart('/Bookstore/Cart?removeBid=" + b.getBid() + "')\">[X]</a>"
								+ 		"</div>"
								+ 		"<div class='col-50'>"
								+ 		"<a href=\"javascript:removeItemFromCart('/Bookstore/Cart?removeBid=" + b.getBid() + "&quant=1')\">-</a>" 
								+ 			entry.getValue() 
								+ 		"<a href=\"javascript:removeItemFromCart('/Bookstore/Cart?incBid=" + b.getBid() + "')\">+</a>"
								+ 		"</div>"
								+ 	"</div>"
								+ "</div>"
								+ "<div class='col-50'><a href=\"/Bookstore/Book?bid=" + b.getBid() + "\">" + title + "</a></div> "
								+ "<div class='col-25'><span class=\"price\">$" + b.getPrice() + "</span></div>"
							   + "</div>");
			}
			
			//<hr />
		    //<p>Total <span class="price" style="color:black"><b>$30</b></span></p>
			
			float priceTotal = sc.getCartTotalPrice(username);
			cartTable.append("<div class='row'><hr /></div>");
			cartTable.append("<div class='row'>"
							+ "<div class='col-25'></div>"
							+ "<div class='col-50'>Total </div>"
							+ "<div class='col-25'><span class=\"price\" style=\"color:black\"><b>$" + priceTotal + "</b></span></div>"
						   + "</div>");

			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cartTable.toString();
	}

}
