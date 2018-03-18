package bean;

import java.util.Date;

public class BookReviewBean {

	private String reviewText, username;
	private int bid, rating;
	private Date reviewDate;
	
	public BookReviewBean(int bid, String reviewText, String username, int rating, Date reviewDate) {
		super();
		this.bid = bid;
		this.reviewText = reviewText;
		this.username = username;
		this.rating = rating;
		this.reviewDate = reviewDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public String getReviewText() {
		return reviewText;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
	
}
