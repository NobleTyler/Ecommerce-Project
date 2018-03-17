package model;
import java.sql.*;

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
