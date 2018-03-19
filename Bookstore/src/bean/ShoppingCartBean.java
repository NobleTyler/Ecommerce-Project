package bean;

import java.util.List;

public class ShoppingCartBean {

	private String cartID;
	private List<String> bid;
	
	public ShoppingCartBean(String cartID, List<String> bid) {
		super();
		this.cartID = cartID;
		this.bid = bid;
	}

	public String getCartID() {
		return cartID;
	}

	public void setCartID(String cartID) {
		this.cartID = cartID;
	}

	public List<String> getBid() {
		return bid;
	}

	public void setBid(List<String> bid) {
		this.bid = bid;
	}
	
}
