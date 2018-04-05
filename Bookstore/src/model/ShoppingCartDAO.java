package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ShoppingCartDAO {
	private Connection conn;
	
	public ShoppingCartDAO() {
		
	}
	
	/*
	 * Retrieves car items by using the query string
	 * @returns a map<bid, quantity> representing the items in a user's shopping cart
	 */
	public Map<Integer, Integer> retrieveCartItems(String username) throws SQLException {
		Map<Integer, Integer> result = new HashMap<Integer, Integer>();
		
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String query = "SELECT BID, QUANTITY FROM SHOPPING_CART WHERE USERNAME=?";
		
		PreparedStatement p = conn.prepareStatement(query);
		p.setString(1, username);
		
		ResultSet r = p.executeQuery();
		while(r.next()) {
			int bid = r.getInt("BID");
			int quantity = r.getInt("QUANTITY");
			
			result.put(bid, quantity);
		}
		
		p.close();
		r.close();
		conn.close();
		return result;
	}
	
	/*
	 * @returns the total cart size taking into account the quantity of each item
	 * Works by iterating through the cart and adding to an int that is later retrurned	 
	 */
	public int getCartSize(String username) throws SQLException {
		int result = 0;
		
		Map<Integer, Integer> cart = retrieveCartItems(username);
	
		for (Entry<Integer, Integer> entry : cart.entrySet()) {
			result += entry.getValue();
		}
		
		return result;
	}
	/*
	 * Opens the database and again itterates through the query set
	 * Takes the result set then adds to a float which is later returned as
	 * the total price of the cart
	 */
	public float getCartTotalPrice(String username) throws SQLException {
		float result = 0.0f;
		
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String query = "SELECT sum(price * quantity) as total " +
							"from shopping_cart inner join book on shopping_cart.bid = book.bid " +
							"where username=? " +
							"group by shopping_cart.username";
							
		PreparedStatement p = conn.prepareStatement(query);
		p.setString(1, username);
		ResultSet r = p.executeQuery();
		
		if (r.next()) {
			result = r.getFloat("total");
		}
		
		r.close();
		p.close();
		conn.close();
		return result;
	}
	
	/*
	 * Calls an sql statement that deletes from the table whatever book id is passed
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
	 * An overloaded method of the original this one accounts for quantity
	 * Calls an update statement rather than a delete statement as the previous ones did
	 * this is because we dont want to drop the entry necessarily we may just want to reduce the quantity
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
	 * Deletes the entire column to remove that entire cart
	 * Does this by you know just setting the username to the Delete function
	 * removed all items from a users shopping cart (ie when they make a purchase)
	 */
	public void clearCart(String username) throws SQLException {
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String statement = "DELETE FROM SHOPPING_CART WHERE username=?";
		PreparedStatement p = conn.prepareStatement(statement);
		p.setString(1, username);
		
		p.execute();
		
		p.close();
		conn.close();
	}
	
	/*
	 * add a single item to the user's shopping cart by using update if the quantity is greater than one
	 * else it uses the insert into  and then executes the statement
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
	 * This gets the quantity in the cart of the user
	 * Used for a lot of those delete statements and print statements
	 * @returns the quantity of a single product a particular user has in their shopping cart
	 */
	public int getQuantity(String username, int bid) throws SQLException {
		int quantity = 0;			
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
