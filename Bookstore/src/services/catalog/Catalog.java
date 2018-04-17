package services.catalog;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Catalog {

	public String getProductInfo(int productId) throws SQLException {
		StringBuffer result = new StringBuffer();
		result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		
		Connection conn = null;
		
		try {
			conn = model.DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String query = "select * from book where bid=?";
		PreparedStatement p = conn.prepareStatement(query);
		p.setInt(1, productId);
		ResultSet r = p.executeQuery();
		
		if (r.next()) {
			result.append("<book>");
			String title = r.getString("title");
			float price = r.getFloat("price");
			
			result.append("<title>" + title + "</title>");
			result.append("<price>" + price + "</price>");
			
			String categoryQuery = "select category from category where bid=?";
			p = conn.prepareStatement(categoryQuery);
			p.setInt(1, productId);
			r = p.executeQuery();
			
			String categories = "categories: ";
			
			result.append("<categories>");
			while (r.next()) {
				result.append("<category>" + r.getString("category") + "</category>");
			}
			result.append("</categories>");
			result.append("</book>");
		}
		else {
			result.append("<error>No book with bid=" + productId + " found!</error>");
		}
		
		
		r.close();
		p.close();
		conn.close();
		
		return result.toString();
	}
	
}
