package bean;
/*
 * Used mostly for registration and billing
 * only called for those purposes to set the registration and the billing
 */
public class AddressBean {

	private String username, fullName, street, province, city, zip;
	
	public AddressBean(String username, String fullName, String street, String city, String province, String zip) {
		super();
		this.username = username;
		this.fullName = fullName;
		this.street = street;
		this.province = province;
		this.city = city;
		this.zip = zip;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

}
