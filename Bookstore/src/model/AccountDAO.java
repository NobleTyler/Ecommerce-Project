package model;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;

import bean.AccountBean;

public class AccountDAO {
	
	private Connection conn;
	
	/*
	 * Registers a user by adding them to the database
	 * First query to make sure they don't exist
	 * After querying if the query contains an entry that is shared return the name is taken
	 * If not it is salted and added to the database of accounts
	 */
	public boolean registerUser(String username, String password) throws SQLException, NoSuchAlgorithmException {
		
		String statement = "INSERT INTO account (username, password, pass_salt) VALUES(?,?,?)";
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
		if (r.next()) {		
			result = false;
		}
		else {
			result = true;
			int salt = generateSalt();
			p = conn.prepareStatement(statement);
			p.setString(1, username);
			p.setString(2, encryptPassword(password, salt));
			p.setInt(3, salt);
			p.execute();
		}
		conn.close();
		p.close();
		r.close();
		return result;
	}
	
	/*
	 * register user using an AccountBean object
	 */
	public boolean registerUser(AccountBean a) throws SQLException, NoSuchAlgorithmException {
		return registerUser(a.getUsername(), a.getPassword());
	}

	/*
	 * attempts to login using the given username and password
	 * This works by selecting from the database where the user name is found
	 * It then uses the salt and password to match to the username
	 * all of which are set through queries.
	 * If they are matching the connection is closed and the user is logged in.
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
		
		if (r.next()) {
			String pw = r.getString("PASSWORD");
			int salt = r.getInt("PASS_SALT");
			String candidatePw = encryptPassword(password, salt);
			result = pw.equals(candidatePw);
		}
		else {
			result = false;
		}
		
		conn.close();
		p.close();
		r.close();
		
		return result;
	}
	
	/*
	 * attempt login using an account bean object
	 */
	public boolean attemptLogin(AccountBean a) throws SQLException {
		return attemptLogin(a.getUsername(), a.getPassword());
	}
	
	/*
	 * @returns an encrypted version of the password using the given salt
	 * string
	 */
	private static String encryptPassword(String password, int salt) {
		String base = salt + password;
		return sha256(base);
	}
	
	/*
	 * generates a random int for salt to be used to encrypt
	 */
	private static int generateSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		int salt = sr.nextInt();
		//System.out.println(salt);
		return salt;
	}
	
	/*
	 * hashing function to encrypt data using sha256
	 */
	private static String sha256(String base) {
	    try {
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hash = digest.digest(base.getBytes("UTF-8"));
	        StringBuffer hexString = new StringBuffer();

	        for (int i = 0; i < hash.length; i++) {
	            String hex = Integer.toHexString(0xff & hash[i]);
	            if(hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }

	        return hexString.toString();
	    } 
	    catch(Exception ex) {
	    	throw new RuntimeException(ex);
	    }
	}
	
}
