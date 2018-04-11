package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import bean.POBean;
import bean.POItemBean;

public class PODAO {
	
	private Connection conn;
	
	public PODAO() {
		
	}
	
	public void insertProductOrder(POBean productOrder) throws SQLException {
		
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String statement = "INSERT INTO po(id, username, lname, fname, date, status) "
						+ "VALUES(?,?,?,?,?,?)";
		
		PreparedStatement p = conn.prepareStatement(statement);
		p.setString(1, productOrder.getId());
		p.setString(2, productOrder.getUsername());
		p.setString(3, productOrder.getLname());
		p.setString(4, productOrder.getFname());
		p.setTimestamp(5, productOrder.getDate());
		p.setString(6, productOrder.getStatus());
		
		p.execute();
		
		p.close();
		conn.close();
	}
	
}
