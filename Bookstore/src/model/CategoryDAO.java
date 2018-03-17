package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
	
	private Connection conn;
	
	public CategoryDAO() {
		
	}
	
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
	
}
