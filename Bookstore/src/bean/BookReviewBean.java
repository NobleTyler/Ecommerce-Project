package bean;

import java.sql.Date;
/*
 * This bean holds everything used in the review
 * All it has are getters and setters and a constructor
 * you know like every other bean here
 */
public class BookReviewBean {

	private String reviewText, username;
	private int bid;
	private float rating;
	private Date reviewDate;
	
	public BookReviewBean(int bid, String reviewText, String username, float rating, Date reviewDate) {
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

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
	
}
