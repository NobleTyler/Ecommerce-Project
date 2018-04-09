package model;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.BookBean;

public class BookDAO {
	
	private Connection conn;
	
	public BookDAO() {
		
	}
	/*
	 * Book id's are a key value so there should be no duplicates
	 * this is used frequently to display the book and for many other purposes
	 * Then it just grabs the title and price, which you display when they view the book
	 */
	public BookBean retrieveBookByBid(int bid) throws SQLException {
		
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String query = "select * from book where bid=?";
		PreparedStatement p = conn.prepareStatement(query);
		
		p.setInt(1, bid);
		ResultSet r = p.executeQuery();
		
		BookBean book = null;
		while (r.next()) {
			String title = r.getString("TITLE");
			float price = r.getBigDecimal("PRICE").floatValue();
			book = new BookBean(bid, title, price);
		}
		
		conn.close();
		p.close();
		r.close();
		
		return book;
	}
	/*
	 * Gets every book based on the BID, doesnt pass anything just grabs every book
	 * useful for when you want to view every book
	 */
	public List<Integer> retrieveAllBooks() throws SQLException {
		List<Integer> bids = new ArrayList<Integer>();
		
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String query = "select bid from book";
		PreparedStatement p = conn.prepareStatement(query);
		ResultSet r = p.executeQuery();

		while (r.next()) {
			bids.add(r.getInt("BID"));
		}
		
		conn.close();
		p.close();
		r.close();
		
		return bids;
	}
	/*
	 * Retrieve all by title, is frequently used in the search function
	 * simply a select statment with a like this time
	 * then puts what it found in a bean and displays that later
	 */
	public Map<Integer, BookBean> retrieveBooksByTitle(String t) throws SQLException {
		Map<Integer, BookBean> m = new HashMap<Integer, BookBean>();
		
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String query = "select * from book where title like ?";
		PreparedStatement p = conn.prepareStatement(query);
		
		p.setString(1, "%" + t +"%");
		ResultSet r = p.executeQuery();
		
		BookBean book;
		
		while(r.next()) {
			int bid = r.getInt("BID");
			String title = r.getString("TITLE");
			float price = r.getBigDecimal("PRICE").floatValue();
			
			book = new BookBean(bid, title, price);
			m.put(bid, book);
		}
		
		conn.close();
		r.close();
		p.close();
		
		return m;
	}
	/*
	 * This is for adding a book usually done by an admin for when a new book comes out
	 * Its just an inser into statement really
	 */
	public void insertBook(BookBean book) throws SQLException {
		
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String insertStatement = "INSERT INTO book (bid, title, price) VALUES(?,?,?)";
		PreparedStatement p = conn.prepareStatement(insertStatement);
		
		p.setInt(1, book.getBid());
		p.setString(2, book.getTitle());
		BigDecimal price = new BigDecimal(book.getPrice());
		p.setBigDecimal(3, price);
		
		p.execute();
		
		conn.close();
		p.close();
	}
	
}
