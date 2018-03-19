package bean;

public class POItemBean {

	private String id, bid;
	private float price;
	
	public POItemBean(String id, String bid, float price) {
		super();
		this.id = id;
		this.bid = bid;
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
}
