package services.po;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="items")
public class ItemListWrapper {

	@XmlElement(name="item")
	private List<ItemWrapper> items;
	
	public ItemListWrapper() { }
	
	public ItemListWrapper(List<ItemWrapper> items) {
		this.items = items;
	}
	
}
