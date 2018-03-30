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
		System.out.println("Cart get called! Username:" + request.getParameter("username"));
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
		else {																	//bid is not in parameters, request is for entire cart contents
			
			//<p><a href="#">Product 1</a> <span class="price">$15</span></p>
			BookDAO bd = new BookDAO();
			StringBuilder cartTable = new StringBuilder();
			try {
				Map<Integer, Integer> cart = sc.retrieveCartItems(username);
				float priceTotal = 0.0f;
				for (Entry<Integer, Integer> entry : cart.entrySet()) {
					BookBean b = bd.retrieveBookByBid(entry.getKey());
					
					
					cartTable.append("<p><a href=\"/Bookstore/Book?bid= " + b.getBid() + ">");
					cartTable.append(b.getTitle() + "</a> <span class=\"price\">" + b.getPrice() + "</span></p>");
				}
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
