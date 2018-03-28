package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShoppingCartDAO {
	private Connection conn;
	
	public ShoppingCartDAO() {
		
	}
	
	/*
	 * add a single item to the user's shopping cart
	 */
	public void addItemToCart(String username, int bid) throws SQLException {
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String statement = "INSERT INTO SHOPPING_CART(username, bid) VALUES (?,?)";
		PreparedStatement p = conn.prepareStatement(statement);
		p.setString(1, username);
		p.setInt(2, bid);
		
		

		p.close();
		conn.close();
	}
	
	/*
	 * @returns the quantity of a single product a particular user has in their shopping cart
	 */
	public int getQuantity(String username, int bid) throws SQLException {
		int quantity = 0;			//default return value in the case that no results are returned
		
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String query = "SELECT QUANTITY FROM SHOPPING_CART WHERE username=? and bid=?";
		PreparedStatement p = conn.prepareStatement(query);
		p.setString(1, username);
		p.setInt(2, bid);
		
		ResultSet r = p.executeQuery();
		
		if (r.next()) {
			System.out.println("there is a next");
			quantity = r.getInt("QUANTITY");
		}
		
		r.close();
		p.close();
		conn.close();
		
		return quantity;
	}
}
