package listener;

import java.sql.SQLException;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import model.PODAO;

import model.POItemDAO;

/**
 * Application Lifecycle Listener implementation class MostPopular
 *
 */
@WebListener
public class Analistener implements HttpSessionAttributeListener {
	private POItemDAO pd = new POItemDAO();

    /**
     * Default constructor. 
     */
    public Analistener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
     */
    public void attributeAdded(HttpSessionBindingEvent arg0)  { 
    }

	/**
     * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
     */
    public void attributeRemoved(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
     * Checks on http event if the cart changes size, and if it does it sets the most popular via a query
     * To get the most popular you must have the product item DAO to find out how many of each item was sold
     */
    public void attributeReplaced(HttpSessionBindingEvent event)  { 
    	if (event.getName().equals("cartSize")) {
	    	try {
	    
				event.getSession().setAttribute("mostpopular", pd.mostPopular());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    }
	
}