package bean;

public class POItemBean {
	
	private String id;
	private int bid, quantity;
	
	public POItemBean(String id, int bid, int quantity) {
		super();
		this.id = id;
		this.bid = bid;
		this.quantity = quantity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
