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

public class PODAO {
	
	private Connection conn;
	
	public PODAO() {
		
	}
	
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
	
	//only return product orders that are processed
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
		
		if (r.next()) {				//return true if the user has purchased the book
			result = true;
		}
		
		return result;
	}
	
}
