package services.po;

import java.sql.Timestamp;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import bean.AddressBean;
import bean.POItemBean;

@XmlRootElement(name="productOrder")
@XmlAccessorType(XmlAccessType.FIELD)
public class POWrapper {
	
	@XmlAttribute(name="orderDate")
	@XmlJavaTypeAdapter(services.po.DateAdapter.class)
	private Timestamp orderDate;

	@XmlElement(name="shipTo")
	private AddressBean ship;
	
	@XmlElement(name="billTo")
	private AddressBean bill;
	
	private ItemListWrapper items;
	
	public POWrapper() {}
	
	public POWrapper(Timestamp date, AddressBean s, AddressBean b, ItemListWrapper items) {
		super();
		this.orderDate = date;
		this.ship = s;
		this.bill = b;
		this.items = items;
	}
	
}
