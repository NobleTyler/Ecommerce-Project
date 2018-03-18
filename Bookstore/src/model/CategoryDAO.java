package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
	
	private Connection conn;
	
	public CategoryDAO() {
		
	}
	
	/*
	 * @returns all categories in the database
	 */
	public List<String> retrieveAllCategories() throws SQLException {
		
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String query = "select distinct(category) from categories";
		PreparedStatement p = conn.prepareStatement(query);
		
		ResultSet r = p.executeQuery();
		
		List<String> categories = new ArrayList<String>();
		
		while (r.next()) {
			categories.add(r.getString("CATEGORY"));
		}
		
		conn.close();
		p.close();
		r.close();
		
		return categories;
	}
	
	/*
	 * @returns a list of categories for a given bid
	 */
	public List<String> retrieveCategoriesForBook(String bid) throws SQLException {
		
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String query = "select category from categories where bid=?";
		PreparedStatement p = conn.prepareStatement(query);
		p.setString(1, bid);
		
		ResultSet r = p.executeQuery();
		
		List<String> categories = new ArrayList<String>();
		
		while (r.next()) {
			categories.add(r.getString("CATEGORY"));
		}
		
		conn.close();
		p.close();
		r.close();
		
		return categories;
	}
	
	/*
	 * @returns list of bids for a given category
	 */
	public List<String> retrieveBooksForCategory(String category) throws SQLException {
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String query = "select bid from categories where category=?";
		PreparedStatement p = conn.prepareStatement(query);
		p.setString(1, category);
		
		ResultSet r = p.executeQuery();
		
		List<String> books = new ArrayList<String>();
		
		while (r.next()) {
			books.add(r.getString("BID"));
		}
		
		conn.close();
		p.close();
		r.close();
		
		return books;
	}
	
}
