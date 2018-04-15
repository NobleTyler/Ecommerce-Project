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
	
	public void insertItemsToProductOrder(POBean poBean, Map<Integer, Integer> books) throws SQLException {
		
		//add each book in the book map to the database
		for (int bid : books.keySet()) {
			POItemBean poItemBean = new POItemBean(poBean.getId(), bid, books.get(bid));
			insertItemToProductOrder(poItemBean);
		}
		
	}
	
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
		String aggregateQuery="SELECT book.title, book.price, book.bid, sum(quantity) as purchases FROM po_item, po, book where po_item.id=po.id and book.bid=po_item.bid group by bid order by purchases desc limit 10";
		PreparedStatement p = conn.prepareStatement(aggregateQuery);
	
		

		ResultSet r = p.executeQuery();
		
		List<BookBean> items = new ArrayList<BookBean>();
		while (r.next()) {
			int bid = r.getInt("bid");
			String title = r.getString("title");
			String id = r.getString("id");
			float price= r.getFloat("price");
			System.out.println(title);
			items.add(new BookBean( bid, title, price));
		}
		
		r.close();
		p.close();
		conn.close();
		return items;
		
	}
	
}
