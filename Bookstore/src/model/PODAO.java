package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

import bean.POBean;
import bean.POItemBean;
/*
 * Used for acessing the PO overall
 * for things like the users name and adress can be queried from here
 * Anything used in overall orders comes from here 
 */
public class PODAO {
	
	private Connection conn;
	
	public PODAO() {
		
	}
	/*
	 * This method inserts all necessary information about the user into the product order
	 */
	public void insertProductOrder(POBean productOrder) throws SQLException {
		
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String statement = "INSERT INTO po(id, username, lname, fname, date, status) "
						+ "VALUES(?,?,?,?,?,?)";
		
		PreparedStatement p = conn.prepareStatement(statement);
		p.setString(1, productOrder.getId());
		p.setString(2, productOrder.getUsername());
		p.setString(3, productOrder.getLname());
		p.setString(4, productOrder.getFname());
		p.setTimestamp(5, productOrder.getDate());
		p.setString(6, productOrder.getStatus());
		
		p.execute();
		
		p.close();
		conn.close();
	}
	/*
	 * This is a retreive method for the product order
	 *see the PO Container for its usage
	 */
	public POBean retrievePO(String id) throws SQLException {
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String query = "SELECT * FROM po WHERE id=?";
		PreparedStatement p = conn.prepareStatement(query);
		p.setString(1, id);
		
		ResultSet r = p.executeQuery();
		
		POBean po = null;
		if (r.next()) {
			String lname = r.getString("lname");
			String fname = r.getString("fname");
			String username = r.getString("username");
			String status = r.getString("status");
			Timestamp date = r.getTimestamp("date");
			po = new POBean(id, lname, fname, username, status, date);
		}
		
		return po;
	}
	
	/*
	 * This is a retreive method for the product order
	 * sorted by month ordered
	 *see the PO Container for its usage
	 */
	public List<POBean> retrievePOByMonth(int month) throws SQLException {
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String query = "SELECT * FROM po WHERE MONTH(DATE)=? and status='PROCESSED'";
		PreparedStatement p = conn.prepareStatement(query);
		p.setInt(1, month);
		
		ResultSet r = p.executeQuery();
		List<POBean> orders = new ArrayList<POBean>();
		
		while (r.next()) {
			String id = r.getString("id");
			String lname = r.getString("lname");
			String fname = r.getString("fname");
			String username = r.getString("username");
			String status = r.getString("status");
			Timestamp date = r.getTimestamp("date");
			
			orders.add(new POBean(id, lname, fname, username, status, date));
		}
		
		p.close();
		r.close();
		conn.close();
		
		return orders;
	}
	/*
	 * This checks if a certain user has purchased an item
	 * it is called for the review to make sure they have purchased the order
	 */
	public boolean userPurchasedBook(String username, int bid) throws SQLException {
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String query = "SELECT username, bid, status "
				+ "FROM po INNER JOIN po_item on po.id = po_item.id "
				+ "WHERE status='PROCESSED' AND username=? AND bid=?";
		
		PreparedStatement p = conn.prepareStatement(query);
		p.setString(1, username);
		p.setInt(2, bid);
		
		ResultSet r = p.executeQuery();
		
		boolean result = false;
		
		if (r.next()) {				
			result = true;
		}
		
		r.close();
		p.close();
		conn.close();
		
		return result;
	}
	
}
