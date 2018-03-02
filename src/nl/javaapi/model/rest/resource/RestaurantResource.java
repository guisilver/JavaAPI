package nl.javaapi.model.rest.resource;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nl.javaapi.model.entity.Restaurant;
import nl.javaapi.model.service.RestaurantService;

/**
 * This class was created following the REST architecture. It provides the data
 * in JSON and XML formats.
 * 
 * @author Guilherme Silveira
 *
 */
@Path("/restaurants")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes(MediaType.APPLICATION_JSON)
public class RestaurantResource {

	private RestaurantService rs = new RestaurantService();

	/**
	 * This method is used for listing the restaurants from the database.
	 * 
	 * @return
	 */
	@GET
	public List<Restaurant> listRestaurants() {
		try {
			return this.rs.listRestaurants();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new InternalServerErrorException();
		}
	}

}
