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
	 * removes an item from the users shopping cart (all of the quantity gets removed)
	 */
	public void removeItemFromCart(String username, int bid) throws SQLException {
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String statement = "DELETE FROM SHOPPING_CART WHERE username=? AND bid=?";
		PreparedStatement p = conn.prepareStatement(statement);
		p.setString(1, username);
		p.setInt(2, bid);
		
		p.execute();
		
		p.close();
		conn.close();
	}
	
	/*
	 * removes a certain quantity of an item from a shopping cart
	 */
	public void removeItemFromCart(String username, int bid, int quantity) throws SQLException {
		int currentQuantity = getQuantity(username, bid);
		
		if (currentQuantity <= quantity) {			//the quantity requested to remove will remove the entire item
			removeItemFromCart(username, bid);
		}
		else {										//remove a specific quantity
			try {
				conn = DatabaseConnector.getDatabaseConnection();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			String statement = "UPDATE SHOPPING_CART SET QUANTITY = QUANTITY - ? WHERE username=? and bid=?";
			PreparedStatement p = conn.prepareStatement(statement);
		
			p.setInt(1, quantity);
			p.setString(2, username);
			p.setInt(3, bid);
			p.execute();
			
			p.close();
			conn.close();
		}
	}
	
	/*
	 * removed all items from a users shopping cart
	 */
	public void clearCart(String username) throws SQLException {
		
	}
	
	/*
	 * add a single item to the user's shopping cart
	 */
	public void addItemToCart(String username, int bid) throws SQLException {
		int quantity = getQuantity(username, bid);			//the current quantity of the item in the user's shoppingcart
		
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String statement;
		PreparedStatement p;
		if (quantity >= 1) {		//if the user already has this item in their shopping cart update the quantity instead of adding a new row
			statement = "UPDATE SHOPPING_CART SET QUANTITY = QUANTITY + 1 WHERE username=? and bid=?";
			p = conn.prepareStatement(statement);
		}
		else {						//user does not have this item in their shopping cart, insert with default quantity = 1
			statement = "INSERT INTO SHOPPING_CART(username, bid) VALUES (?,?)";
			p = conn.prepareStatement(statement);
		}
		p.setString(1, username);
		p.setInt(2, bid);
		p.execute();
		
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
			//System.out.println("there is a next");
			quantity = r.getInt("QUANTITY");
		}
		
		r.close();
		p.close();
		conn.close();
		
		return quantity;
	}
}
