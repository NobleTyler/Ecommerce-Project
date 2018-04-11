package bean;

import java.sql.Timestamp;

public class POBean {

	private String id;
	private String lname, fname, username, status;
	private Timestamp date;

	public final static String PROCESSED = "PROCESSED";
	public final static String DENIED = "DENIED";

	public POBean(String id, String lname, String fname, String username, String status, Timestamp date) {
		super();
		this.id = id;
		this.lname = lname;
		this.fname = fname;
		this.username = username;
		this.status = status;
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	
}
