package bean;
/*
 * This class just holds the account necessary stuff
 * its used in the login for methods like attempt login
 */
public class AccountBean {
	
	private String username, password, cardID;

	public AccountBean(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
