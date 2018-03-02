package nl.javaapi.model.service;

import java.sql.SQLException;
import java.util.List;

import nl.javaapi.model.dao.InterfaceRestaurantDao;
import nl.javaapi.model.dao.RestaurantDao;
import nl.javaapi.model.entity.Restaurant;

/**
 * 
 * This class is used to intermediate the communication between the REST service
 * and the database. It also validates the data and implements the business
 * logic.
 * 
 * @author Guilherme Silveira
 *
 */
public class RestaurantService {

	private InterfaceRestaurantDao restaurantDao = new RestaurantDao();

	public List<Restaurant> listRestaurants() throws SQLException {
		return this.restaurantDao.listRestaurants();
	}

}
