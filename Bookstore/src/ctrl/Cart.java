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
		
		if (request.getParameter("bid") != null) {								//request is for adding a single bid to the cart
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
			int bidToRemove = Integer.parseInt(request.getParameter("removeBid"));
			PrintWriter out = response.getWriter();
			
			try {
				sc.removeItemFromCart(username, bidToRemove);
				String cartTable = createCartTable(username);
				out.print(cartTable);							//update the cart table
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	protected String createCartTable(String username) {
		BookDAO bd = new BookDAO();
		ShoppingCartDAO sc = new ShoppingCartDAO();
		StringBuilder cartTable = new StringBuilder();
		try {
			Map<Integer, Integer> cart = sc.retrieveCartItems(username);
			//<h4>Cart <span class="price" style="color:black"> <b>4</b></span></h4>
			cartTable.append("<table>"
							+ "<tr>"
							+ "<td></td>"
							+ "<td><h4>Cart</h4></td> "
							+ "<td><h4><span class=\"price\" style=\"color:black\"> <b>" + sc.getCartSize(username) + "</b></span></h4></td>"
							+ "</tr>");
			for (Entry<Integer, Integer> entry : cart.entrySet()) {
				BookBean b = bd.retrieveBookByBid(entry.getKey());
				String title = (b.getTitle().length() > 25) ? b.getTitle().substring(0, 25) + "..." : b.getTitle();			//shortening title to fit in cart area
				//<a href=\"javascript:removeItemFromCart('/Bookstore/Cart?removeBid=" + b.getBid() + "')
				cartTable.append("<tr>"
								+ "<td><a href=\"javascript:removeItemFromCart('/Bookstore/Cart?removeBid=" + b.getBid() + "')\">[Remove]</a></td>"
								+ "<td><a href=\"/Bookstore/Book?bid=" + b.getBid() + "\">" + title + "</a></td> "
								+ "<td><span class=\"price\">$" + b.getPrice() + " x" + entry.getValue() + "</span></td>"
							   + "</tr>");
			}
			
			//<hr />
		    //<p>Total <span class="price" style="color:black"><b>$30</b></span></p>
			
			float priceTotal = sc.getCartTotalPrice(username);
			cartTable.append("<tr>"
							+ "<td></td>"
							+ "<td><hr /></td>"
							+ "<td><hr /></td>"
						   + "</tr>");
			cartTable.append("<tr>"
							+ "<td></td>"
							+ "<td>Total </td>"
							+ "<td><span class=\"price\" style=\"color:black\"><b>$" + priceTotal + "</b></span></td>"
						   + "</tr>"
						   + "</table>");

			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cartTable.toString();
	}

}
