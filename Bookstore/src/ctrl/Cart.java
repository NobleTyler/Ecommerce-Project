package ctrl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//System.out.println("Cart post called!");
		
		String username = request.getParameter("username");
		int bid = Integer.parseInt(request.getParameter("bid"));
		PrintWriter out = response.getWriter();
		
		ShoppingCartDAO sc = new ShoppingCartDAO();
		try {
			sc.addItemToCart(username, bid);								//getting the cart size after the addition of the new item for updating the display
			Map<Integer, Integer> cart = sc.retrieveCartItems(username);
			request.getSession().setAttribute("cartSize", cart.size());
			out.print("Cart(" + cart.size() + ")");
			
			System.out.println(cart.size());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
