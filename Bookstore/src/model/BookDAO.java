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
		//There should only be 1 result value since bid is the pk
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
