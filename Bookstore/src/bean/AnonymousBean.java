package bean;

public class AnonymousBean {
	private String username;
	private float total;
	private String postalCode;

	public AnonymousBean(String username, float total, String postalCode) {
		super();
		this.setUsername(username);
		this.setTotal(total);
		this.setPostalCode(postalCode);
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
