package model;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;

public class AccountDAO {
	
	private Connection conn;
	
	/*
	 * registers a new user with the given password
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
		if (r.next()) {		//username is already taken!
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
		System.out.println(salt);
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
