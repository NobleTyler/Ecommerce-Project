package ctrl;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.OrderHistoryBean;
import model.PODAO;

/**
 * Servlet implementation class OrderHistory
 */
@WebServlet("/OrderHistory")
public class OrderHistory extends HttpServlet {
	private static final long serialVersionUID = 1L;
    PODAO orders;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderHistory() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init() {
    	orders = new PODAO();
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username=request.getSession().getAttribute("username").toString();
		ArrayList<OrderHistoryBean> odb=null;
		try {
			odb= (ArrayList<OrderHistoryBean>) orders.retrieveOrderHistory(username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		for(OrderHistoryBean ob: odb) {
//			ro.write(ob.getDate().toString()+"\t" +ob.getStatus() +"\t"+ob.getTitle()+"\t"+ob.getQuantity()+"\t"+ob.getTotal()+"\n");
//		}
		
		if(!odb.isEmpty()) {
			String notEmpty="notEmpty";
			request.setAttribute("odb", odb);
			request.setAttribute("notEmpty", notEmpty);
		}
		
		request.getRequestDispatcher("OrderHistory.jspx").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
