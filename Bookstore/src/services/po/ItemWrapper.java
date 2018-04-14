package services.po;

import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class ItemWrapper {
	
	private String bookName;
	private int quantity;
	private float price;
	@XmlJavaTypeAdapter(services.po.DateAdapter.class)
	private Timestamp shipDate;
	
	public ItemWrapper(String bookName, int quantity, float price, Timestamp shipDate) {
		super();
		this.bookName = bookName;
		this.quantity = quantity;
		this.price = price;
		this.shipDate = shipDate;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Timestamp getShipDate() {
		return shipDate;
	}

	public void setShipDate(Timestamp shipDate) {
		this.shipDate = shipDate;
	}
	
}
