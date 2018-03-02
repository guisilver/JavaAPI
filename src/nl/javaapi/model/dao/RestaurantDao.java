package nl.javaapi.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nl.javaapi.model.connection.Database;
import nl.javaapi.model.entity.Restaurant;

/**
 * This class is used for accessing the database. It follows the Data Access
 * Object concept.
 * 
 * @author Guilherme Silveira
 *
 */
public class RestaurantDao implements InterfaceRestaurantDao {

	private Connection con;

	public RestaurantDao() {
		this.con = Database.getConnection();
	}

	@Override
	public List<Restaurant> listRestaurants() throws SQLException {
		String query = "SELECT r.id, r.name, r.street, r.zipcode, r.city, r.country, r.phone, r.streetnumber FROM Restaurants r ORDER BY name";
		PreparedStatement pstmt = this.con.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		List<Restaurant> listResturants = new ArrayList<Restaurant>();
		while (rs.next()) {
			Restaurant restaurant = new Restaurant();
			restaurant.setId(rs.getInt("r.id"));
			restaurant.setName(rs.getString("r.name"));
			restaurant.setStreet(rs.getString("r.street"));
			restaurant.setZipCode(rs.getString("r.zipcode"));
			restaurant.setCity(rs.getString("r.city"));
			restaurant.setCountry(rs.getString("r.country"));
			restaurant.setPhone(rs.getString("r.phone"));
			restaurant.setStreetNumber(rs.getString("r.streetnumber"));
			listResturants.add(restaurant);
		}
		rs.close();
		pstmt.close();
		this.con.close();
		return listResturants;
	}

}
