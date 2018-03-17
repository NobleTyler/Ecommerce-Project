package model;
import java.math.BigDecimal;
import java.sql.*;
import bean.BookBean;

public class BookDAO {
	
	private Connection conn;
	
	public BookDAO() {
		
	}
	
	public BookBean retrieveBook(String bid) throws SQLException {
		
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String query = "select * from book where bid=?";
		PreparedStatement p = conn.prepareStatement(query);
		
		p.setString(1, bid);
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
	
	public void insertBook(BookBean book) throws SQLException {
		
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String insertStatement = "INSERT INTO book (bid, title, price) VALUES(?,?,?)";
		PreparedStatement p = conn.prepareStatement(insertStatement);
		
		p.setString(1, book.getBid());
		p.setString(2, book.getTitle());
		BigDecimal price = new BigDecimal(book.getPrice());
		p.setBigDecimal(3, price);
		
		p.execute();
		
		conn.close();
		p.close();
	}
	
}
