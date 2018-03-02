package nl.javaapi.model.rest.resource;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import nl.javaapi.model.entity.Order;
import nl.javaapi.model.service.OrderService;

/**
 * This class was created following the REST architecture. It provides the data
 * in JSON and XML formats.
 * 
 * @author Guilherme Silveira
 *
 */
@Path("/orders")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

	private OrderService os = new OrderService();

	/**
	 * This method is used for listing the orders from the database
	 * 
	 * @param restaurantId
	 * @param date
	 * @return
	 */
	@GET
	public List<Order> listOrders(@QueryParam("restaurantId") Integer restaurantId, @QueryParam("date") String date) {
		try {
			return this.os.listOrders(restaurantId, date);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new InternalServerErrorException();
		} catch (ParseException e) {
			e.printStackTrace();
			throw new BadRequestException();
		} catch (Exception e) {
			e.printStackTrace();
			throw new InternalServerErrorException();
		}
	}

	/**
	 * This method is used for searching one order by the order id
	 * 
	 * @param orderId
	 * @return
	 */
	@GET
	@Path("/{orderId}")
	public Order getOrder(@PathParam("orderId") Integer orderId) {
		try {
			Order retorno = this.os.getOrder(orderId);
			return retorno;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new InternalServerErrorException();
		}
	}

	/**
	 * This method is used for adding a new order to the database
	 * 
	 * @param order
	 */
	@POST
	public void addNewOrder(Order order) {
		try {
			if (order == null) {
				throw new BadRequestException();
			}
			this.os.addNewOrder(order);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new InternalServerErrorException();
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	/**
	 * This method is used for updating an order in the database
	 * 
	 * @param order
	 */
	@PUT
	public void updateOrder(Order order) {
		try {
			this.os.updateOrder(order);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new InternalServerErrorException();
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	/**
	 * This method is used for removing an order from the database
	 * 
	 * @param orderId
	 */
	@DELETE
	@Path("/{orderId}")
	public void removeOrder(@PathParam("orderId") Integer orderId) {
		try {
			this.os.removeOrder(orderId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new InternalServerErrorException();
		}
	}

}
