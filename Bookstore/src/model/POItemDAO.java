package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import bean.BookBean;
import bean.POBean;
import bean.POItemBean;

public class POItemDAO {

	private Connection conn;
	
	public POItemDAO() {
		
	}
	
	/*
	 * 	adds each book in the book map to the database
	 */
	public void insertItemsToProductOrder(POBean poBean, Map<Integer, Integer> books) throws SQLException {
	
		for (int bid : books.keySet()) {
			POItemBean poItemBean = new POItemBean(poBean.getId(), bid, books.get(bid));
			insertItemToProductOrder(poItemBean);
		}
		
	}
	/*
	 * Adds the item to the product order
	 */
	public void insertItemToProductOrder(POItemBean itemBean) throws SQLException {
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String statement = "INSERT INTO po_item(id, bid, quantity) "
				+ "VALUES(?,?,?)";
		
		PreparedStatement p = conn.prepareStatement(statement);
		p.setString(1, itemBean.getId());
		p.setInt(2, itemBean.getBid());
		p.setInt(3, itemBean.getQuantity());
		
		p.execute();
		p.close();
		conn.close();
	}
	/*
	 * Retrieves item by its id and returns it as a list
	 */
	public List<POItemBean> retrieveItemsById(String id) throws SQLException {
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String query = "SELECT * FROM po_item WHERE id=?";
		
		PreparedStatement p = conn.prepareStatement(query);
		p.setString(1, id);
		
		ResultSet r = p.executeQuery();
		
		List<POItemBean> items = new ArrayList<POItemBean>();
		while (r.next()) {
			int bid = r.getInt("bid");
			int quantity = r.getInt("quantity");
			
			items.add(new POItemBean(id, bid, quantity));
		}
		
		r.close();
		p.close();
		conn.close();
		return items;
	}
	/*
	 * This is used by our analytics to grab the most popular books
	 * works by checking through the order and querying for quantity and summing from the table
	 */
	public List<BookBean> mostPopular( )throws SQLException{
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		String aggregateQuery="SELECT book.title, book.price, book.bid, sum(quantity) as purchases FROM po_item, po, book WHERE po_item.id=po.id and book.bid=po_item.bid group by bid order by purchases DESC limit 10";
		PreparedStatement p = conn.prepareStatement(aggregateQuery);
	
		ResultSet r = p.executeQuery();
		
		List<BookBean> items = new ArrayList<BookBean>();
		while (r.next()) {
			int bid = r.getInt("bid");
			String title = r.getString("title");
			float price= r.getFloat("price");
		
			items.add(new BookBean( bid, title, price));
		}
		
		r.close();
		p.close();
		conn.close();
		return items;
		
	}
	/*
	 * Tells you the most popular books on a monthly basis
	 */
	public List<BookBean> mostPopularMonthly(int month )throws SQLException{
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		String formatted = String.format("%02d", month);
		String aggregateQuery="SELECT book.title, book.price, book.bid, po.date, po.username, sum(quantity) as purchases FROM po_item, po, book WHERE po_item.id=po.id and book.bid=po_item.bid and po.date like '%-"+formatted+"-%' group by bid order by purchases ";
	
		PreparedStatement p = conn.prepareStatement(aggregateQuery);
		//p.setString(1, formatted);
		ResultSet r = p.executeQuery();
		
		List<BookBean> items = new ArrayList<BookBean>();
		while (r.next()) {
			int bid = r.getInt("bid");
			String title = r.getString("title");
			float price= r.getFloat("price");
		
			items.add(new BookBean( bid, title, price));
		}
		
		r.close();
		p.close();
		conn.close();
		return items;
		
	}
}
