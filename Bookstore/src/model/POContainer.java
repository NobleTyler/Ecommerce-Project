package model;

import java.sql.SQLException;
import java.util.Map;

import bean.POBean;

//container class to keep poitem and po associated properly
public class POContainer {
	
	private PODAO po;
	private POItemDAO poItem;
	
	public POContainer() {
		po = new PODAO();
		poItem = new POItemDAO();
	}
	
	// takes a new pobean and the list of books from the user's shopping cart
	// to add as a new product order
	public void addProductOrder(POBean poBean, Map<Integer, Integer> books) throws SQLException {
		po.insertProductOrder(poBean);							//add the product order
		poItem.insertItemsToProductOrder(poBean, books);		//then add items to the product order
	}
}
