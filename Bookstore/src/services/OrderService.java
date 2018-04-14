package services;

import java.sql.SQLException;
import java.util.List;

//import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import bean.POBean;
import model.PODAO;

@Path("os")		//path of the service
public class OrderService {
	
	private PODAO po; 
	
	public OrderService() {
		po = new PODAO();
	}
	
	@GET
	@Path("/orders/")
	@Produces(MediaType.APPLICATION_XML)		//returns xml
	public List<POBean> getAllOrders() throws SQLException {
		return po.retrieveAllPO();
	}
	
}
