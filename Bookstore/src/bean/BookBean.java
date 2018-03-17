package bean;

public class BookBean {

	private String bid, title;
	private float price;
	
	public BookBean(String bid, String title, float price) {
		super();
		this.bid = bid;
		this.title = title;
		this.price = price;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
}
