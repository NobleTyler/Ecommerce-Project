package bean;
/*
 * Has everything you need to know about the book minus the iamge url
 */
public class BookBean {

	private int bid;
	private String title;
	private float price;
	
	public BookBean(int bid, String title, float price) {
		super();
		this.bid = bid;
		this.title = title;
		this.price = price;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
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
