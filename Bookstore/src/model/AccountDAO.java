package model;
import java.sql.*;

public class AccountDAO {
	
	private Connection conn;

	/*
	 * attempts to login using the given username and password
	 * @returns true if successful login
	 */
	public boolean attemptLogin(String username, String password) throws SQLException {
		
		String query = "select * from account where username=?";
		boolean result = false;
		
		try {
			conn = DatabaseConnector.getDatabaseConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		PreparedStatement p = conn.prepareStatement(query);
		p.setString(1, username);
		ResultSet r = p.executeQuery();
		
		//if the username is found check to see if the pw matches
		if (r.next()) {
			result = (r.getString("PASSWORD").equals(password));
		}
		else {
			result = false;
		}
		
		conn.close();
		p.close();
		r.close();
		
		return result;
	}
	
}
