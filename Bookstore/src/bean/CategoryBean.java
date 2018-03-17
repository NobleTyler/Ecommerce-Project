package bean;

public class CategoryBean {

	private String category, bid;

	public CategoryBean(String category, String bid) {
		super();
		this.category = category;
		this.bid = bid;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}
	
}
