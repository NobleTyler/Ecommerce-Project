package model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import bean.POBean;
import bean.POItemBean;

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
	
	public List<POItemBean> getPOItems(String id) throws SQLException {
		return poItem.retrieveItemsById(id);
	}
	
	public POBean getPO(String id) throws SQLException {
		return po.retrievePO(id);
	}
	
	public List<POBean> getPOByMonth(int month) throws SQLException {
		return po.retrievePOByMonth(month);
	}
}
