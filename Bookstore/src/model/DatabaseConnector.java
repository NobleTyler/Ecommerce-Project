package model;
import java.sql.*;
/*
 * This class just sets the driver and connection
 * You call it here so you can change the url throughout the entire program
 */
public class DatabaseConnector {
	private static String url 	   = "jdbc:mysql://localhost:3306/bookstore_db";
	private static String username = "root";
	private static String password = "";
	
	public static Connection getDatabaseConnection() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(url, username, password);
			return conn;
	}
}
