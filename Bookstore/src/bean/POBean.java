package bean;

/*
 * Thats right its a bean and this bean is for the account stuff
 * but this has more to do with the adress DAO
 * thats what this ties to
 */
public class POBean {

	private String id, lname, fname, status;
	private AddressBean address;
	
	public POBean(String id, String lname, String fname, String status, AddressBean address) {
		super();
		this.id = id;
		this.lname = lname;
		this.fname = fname;
		this.status = status;
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public AddressBean getAddress() {
		return address;
	}

	public void setAddress(AddressBean address) {
		this.address = address;
	}
	
}
