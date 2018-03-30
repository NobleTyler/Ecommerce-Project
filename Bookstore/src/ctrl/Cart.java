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
		BookDAO bd = new BookDAO();
		ShoppingCartDAO sc = new ShoppingCartDAO();
		StringBuilder cartTable = new StringBuilder();
		try {
			Map<Integer, Integer> cart = sc.retrieveCartItems(username);
			//<h4>Cart <span class="price" style="color:black"> <b>4</b></span></h4>
			cartTable.append("<h4>Cart <span class=\"price\" style=\"color:black\"> <b>" + sc.getCartSize(username) + "</b></span></h4>");
			for (Entry<Integer, Integer> entry : cart.entrySet()) {
				BookBean b = bd.retrieveBookByBid(entry.getKey());
				
				
				cartTable.append("<p><a href=\"/Bookstore/Book?bid=" + b.getBid() + "\">");
				
				String title = (b.getTitle().length() > 25) ? b.getTitle().substring(0, 25) + "..." : b.getTitle();
				
				cartTable.append(title + "</a> <span class=\"price\">$" + b.getPrice() + " x" + entry.getValue() + "</span></p>");
			}
			
			//<hr />
		    //<p>Total <span class="price" style="color:black"><b>$30</b></span></p>
			
			float priceTotal = sc.getCartTotalPrice(username);
			cartTable.append("<hr />");
			cartTable.append("<p>Total <span class=\"price\" style=\"color:black\"><b>$" + priceTotal + "</b></span></p>");
			
			request.setAttribute("cartTable", cartTable);
			request.getRequestDispatcher("shoppingCart.jspx").forward(request, response);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("Cart post called! Username:" + request.getParameter("username") + " bid:" + request.getParameter("bid"));
		
		String username = request.getParameter("username");
		ShoppingCartDAO sc = new ShoppingCartDAO();
		
		if (request.getParameter("bid") != null) {								//request is for adding a single bid to the cart
			int bid = Integer.parseInt(request.getParameter("bid"));
			PrintWriter out = response.getWriter();
			
			
			try {
				sc.addItemToCart(username, bid);								//getting the cart size after the addition of the new item for updating the display
				int cartSize = sc.getCartSize(username);
				
				request.getSession().setAttribute("cartSize", cartSize);
				out.print("Cart(" + cartSize + ")");
				
				System.out.println(cartSize);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
