package services.po;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

//import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import bean.AddressBean;
import bean.POBean;
import bean.POItemBean;
import model.AddressDAO;
import model.DatabaseConnector;
import model.PODAO;
import model.POItemDAO;

@Path("os")		//path of the service
public class OrderService {
	
	private PODAO po; 
	private POItemDAO pi;
	private AddressDAO a;
	
	public OrderService() {
		po = new PODAO();
		pi = new POItemDAO();
		a = new AddressDAO();
	}
	
	@GET
	@Path("/orders/")
	@Produces(MediaType.APPLICATION_XML)		//returns xml
	public List<POWrapper> getAllOrders() throws SQLException {
		List <POBean> orders = po.retrieveAllPO();
		List<POWrapper> orderWrapper = new ArrayList<POWrapper>();
		
		for (POBean order : orders) {
			
			AddressBean aBean = a.retrieveAddress(order.getUsername());
			String orderId = order.getId();
			
			ItemListWrapper items = getItemsFormatted(orderId);
			
			POWrapper pow = new POWrapper(order.getDate(), aBean, aBean, items);
			orderWrapper.add(pow);
		}
		
		return orderWrapper;
	}
	
	/*
	 * retrieves all the items by id formatted for the ItemWrapper class
	 */
	public ItemListWrapper getItemsFormatted(String id) throws SQLException {
		List<ItemWrapper> items = new ArrayList<ItemWrapper>();
		
		Connection conn = null;
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String query = "SELECT book.title, po_item.quantity, book.price, po.date "
				+ "FROM po_item, book, po "
				+ "WHERE po_item.bid = book.bid and po_item.id = po.id "
				+ "and po_item.id=?";
		
		PreparedStatement p = conn.prepareStatement(query);
		p.setString(1, id);
		
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			String title = r.getString("title");
			int quantity = r.getInt("quantity");
			float price = r.getFloat("price");
			Timestamp date = r.getTimestamp("date");
			
			ItemWrapper i = new ItemWrapper(title, quantity, price, date);
			items.add(i);
		}
		
		r.close();
		p.close();
		conn.close();
		
		return new ItemListWrapper(items);
	}
	
}
