package bean;

import java.util.List;
/*
 * this one is used to attach a username to a list of BID's
 * anyawys thats all this holds other than that its still a bean
 */
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
