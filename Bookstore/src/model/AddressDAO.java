package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.AddressBean;

public class AddressDAO {
	private Connection conn;
	
	public AddressDAO() {
		
	}
	
	/*
	 * Adds an adress to the database
	 * this is used for registration, and only called on each new registration
	 * If an adress is already there for the user update 
	 * as well remove all spaces in the zip so its a proper zip code
	 */
	public void addAddress(AddressBean a) throws SQLException {
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String query = "SELECT * FROM ADDRESS WHERE USERNAME=?";
		PreparedStatement p = conn.prepareStatement(query);
		p.setString(1, a.getUsername());
		ResultSet r = p.executeQuery();
		
		if (!r.next()) {				
			
			String statement = "INSERT INTO ADDRESS(username, full_name, street, city, province, postal_code) "
								+ "VALUES(?,?,?,?,?,?)";
			
			p = conn.prepareStatement(statement);
			p.setString(1, a.getUsername());
			p.setString(2, a.getFullName());
			p.setString(3, a.getStreet());
			p.setString(4, a.getCity());
			p.setString(5, a.getProvince());
			p.setString(6, a.getZip().replaceAll("\\s+","")); 						
			p.execute();
		}
		else {
			updateAddress(a);
		}
		
		r.close();
		p.close();
		conn.close();
	}
	
	/*
	 * updated address information for a particular user (contained in the address bean)
	 * This is just incase somebody moves, it remains unused however
	 */
	
	public void updateAddress(AddressBean a) throws SQLException {
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String statement = "DELETE FROM ADDRESS WHERE USERNAME=?";		
		PreparedStatement p = conn.prepareStatement(statement);
		p.setString(1, a.getUsername());
		p.execute();
		
		addAddress(a);												
		
		p.close();
		conn.close();
	}
	
	/*
	 * @returns address information for a particular user
	 * Retrieves the adress of a user, may be displayed upon order
	 * For billing or shipping information
	 */
	public AddressBean retrieveAddress(String username) throws SQLException {
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String query = "SELECT * FROM ADDRESS WHERE USERNAME=?";
		PreparedStatement p = conn.prepareStatement(query);
		p.setString(1, username);
		ResultSet r = p.executeQuery();
		
		AddressBean a = null;
		if (r.next()) {
			String full_name = r.getString("FULL_NAME");
			String street = r.getString("STREET");
			String city = r.getString("CITY");
			String province = r.getString("PROVINCE");
			String postal_code = r.getString("POSTAL_CODE");
			
			a = new AddressBean(username, full_name, street, city, province, postal_code);
		}
		
		p.close();
		r.close();
		conn.close();
		return a;
	}
}
