package bean;

public class AccountBean {
	
	private String username, password, cardID;

	public AccountBean(String username, String password, String cardID) {
		super();
		this.username = username;
		this.password = password;
		this.cardID = cardID;
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

	public String getCardID() {
		return cardID;
	}

	public void setCardID(String cardID) {
		this.cardID = cardID;
	}
	
}
