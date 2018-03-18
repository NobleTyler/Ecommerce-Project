package bean;

import java.util.Date;

public class BookReviewBean {

	private String reviewText;
	private int bid, rating;
	private Date reviewDate;
	
	public BookReviewBean(int bid, String reviewText, int rating, Date reviewDate) {
		super();
		this.bid = bid;
		this.reviewText = reviewText;
		this.rating = rating;
		this.reviewDate = reviewDate;
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
