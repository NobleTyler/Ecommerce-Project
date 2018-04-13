package bean;

import java.sql.Timestamp;

public class OrderHistoryBean {
	
	private Timestamp date;
	private int quantity;
	private double total; 
	private String status, title;
	
	public OrderHistoryBean(Timestamp time, String stat, String ttl, int qty, double price) {
		this.setDate(time);
		this.setStatus(stat);
		this.setTitle(ttl);
		this.setQuantity(qty);
		this.setTotal(price);
	}
	
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}

}
