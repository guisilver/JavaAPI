package nl.javaapi.model.dao;

import java.sql.SQLException;
import java.util.List;

import nl.javaapi.model.entity.Restaurant;

/**
 * This is an interface for the RestaurantDao class
 * 
 * @author Guilherme Silveira
 *
 */
public interface InterfaceRestaurantDao {

	public List<Restaurant> listRestaurants() throws SQLException;

}
