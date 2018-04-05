package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.BookBean;

public class CategoryDAO {
	
	private Connection conn;
	
	public CategoryDAO() {
		
	}
	
	/*
	 * @returns all categories in the database
	 * This is used when we want to display all categories
	 * Mainly for the categories page/menu	 
	 */
	public List<String> retrieveAllCategories() throws SQLException {
		
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String query = "select distinct(category) from category order by category";
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
	 * This selects the books from a category based on a book id number
	 * Then prints a list of its categories
	 * Most likely used to display similar books in a category
	 */
	public List<String> retrieveCategoriesForBook(int bid) throws SQLException {
		
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String query = "select category from category where bid=?";
		PreparedStatement p = conn.prepareStatement(query);
		p.setInt(1, bid);
		
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
	 * This is used whenever we go to the category menu and select a category
	 * just a quick select statment is executed then put into a hashmap
	 * it then puts them in a bean and inserts it into the hashmap
	 * @returns list of bids for a given category
	 */
	public Map<Integer, BookBean> retrieveBooksForCategory(String category) throws SQLException {
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String query = "select * from book inner join category on book.bid = category.bid where category=?";
		PreparedStatement p = conn.prepareStatement(query);
		p.setString(1, category);
		
		ResultSet r = p.executeQuery();
		
		Map<Integer, BookBean> books = new HashMap<Integer, BookBean>();
		
		while (r.next()) {
			int bid = r.getInt("BID");
			String title = r.getString("TITLE");
			float price = r.getBigDecimal("PRICE").floatValue();
			BookBean b = new BookBean(bid, title, price);
			
			books.put(bid, b);
		}
		
		conn.close();
		p.close();
		r.close();
		
		return books;
	}
	
}
