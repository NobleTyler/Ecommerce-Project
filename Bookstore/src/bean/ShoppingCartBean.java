package bean;

import java.util.List;

public class ShoppingCartBean {

	private String username;
	private List<String> bid;
	
	public ShoppingCartBean(String username, List<String> bid) {
		super();
		this.username = username;
		this.bid = bid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getBid() {
		return bid;
	}

	public void setBid(List<String> bid) {
		this.bid = bid;
	}
	
}
