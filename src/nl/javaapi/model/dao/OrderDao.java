package nl.javaapi.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nl.javaapi.model.connection.Database;
import nl.javaapi.model.entity.Order;
import nl.javaapi.model.entity.OrderLine;
import nl.javaapi.model.entity.Restaurant;

/**
 * This class is used for accessing the database. It follows the Data Access
 * Object concept.
 * 
 * @author Guilherme Silveira
 *
 */
public class OrderDao implements InterfaceOrderDao {

	private Connection con;

	public OrderDao() {
		this.con = Database.getConnection();
	}

	public void addNewOrder(Order order) throws SQLException {
		try {
			this.con.setAutoCommit(false);
			String query = "INSERT INTO orders(date, restaurant_id) VALUES (?, ?)";
			PreparedStatement pstmt = this.con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			if (order.getDate() != null) {
				pstmt.setTimestamp(1, new java.sql.Timestamp(order.getDate().getTime()));
			} else {
				pstmt.setTimestamp(1, null);
			}
			pstmt.setInt(2, order.getRestaurant().getId());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				order.setId(rs.getInt(1));
			}

			query = "INSERT INTO order_lines(order_id, productname, number, unit_price) VALUES (?, ?, ?, ?)";

			for (OrderLine orderLine : order.getOrderLines()) {
				pstmt = this.con.prepareStatement(query);
				pstmt.setInt(1, order.getId());
				pstmt.setString(2, orderLine.getProductName());
				pstmt.setInt(3, orderLine.getNumber());
				pstmt.setDouble(4, orderLine.getUnitPrice());
				pstmt.executeUpdate();
			}

			this.con.commit();
			rs.close();
			pstmt.close();
			this.con.close();
		} catch (Exception e) {
			this.con.rollback();
			e.printStackTrace();
		}
	}

	public void updateOrder(Order order) throws SQLException {
		try {
			this.con.setAutoCommit(false);
			String query = "UPDATE orders SET date = ?, restaurant_id = ? WHERE id = ?";
			PreparedStatement pstmt = this.con.prepareStatement(query);
			if (order.getDate() != null) {
				pstmt.setTimestamp(1, new java.sql.Timestamp(order.getDate().getTime()));
			} else {
				pstmt.setTimestamp(1, null);
			}
			pstmt.setInt(2, order.getRestaurant().getId());
			pstmt.setInt(3, order.getId());
			pstmt.executeUpdate();

			query = "DELETE FROM order_lines WHERE order_id = ?";
			pstmt = this.con.prepareStatement(query);
			pstmt.setInt(1, order.getId());
			pstmt.executeUpdate();

			query = "INSERT INTO order_lines(order_id, productname, number, unit_price) VALUES (?, ?, ?, ?)";

			for (OrderLine orderLine : order.getOrderLines()) {
				pstmt = this.con.prepareStatement(query);
				pstmt.setInt(1, order.getId());
				pstmt.setString(2, orderLine.getProductName());
				pstmt.setInt(3, orderLine.getNumber());
				pstmt.setDouble(4, orderLine.getUnitPrice());
				pstmt.executeUpdate();
			}
			this.con.commit();
			pstmt.close();
			this.con.close();
		} catch (Exception e) {
			this.con.rollback();
			e.printStackTrace();
		}
	}

	public void removeOrder(Integer orderId) throws SQLException {
		try {
			this.con.setAutoCommit(false);
			String query = "DELETE FROM orders WHERE id = ?";
			PreparedStatement pstmt = this.con.prepareStatement(query);
			pstmt.setInt(1, orderId);
			pstmt.executeUpdate();

			query = "DELETE FROM order_lines WHERE order_id = ?";
			pstmt = this.con.prepareStatement(query);
			pstmt.setInt(1, orderId);
			pstmt.executeUpdate();

			this.con.commit();
			pstmt.close();
			this.con.close();
		} catch (Exception e) {
			this.con.rollback();
			e.printStackTrace();
		}
	}

	public Order getOrder(Integer orderId) throws SQLException {
		String query = "SELECT o.id, o.date, o.restaurant_id," + "l.id, l.productname, l.number, l.unit_price,"
				+ "r.id, r.name, r.street, r.streetnumber, r.zipcode, r.city, r.country, r.phone FROM orders o"
				+ " INNER JOIN order_lines l on o.id = l.order_id"
				+ " INNER JOIN restaurants r on o.restaurant_id = r.id WHERE o.id = ?";
		PreparedStatement pstmt = this.con.prepareStatement(query);
		pstmt.setInt(1, orderId);
		ResultSet rs = pstmt.executeQuery();
		Order order = new Order();
		while (rs.next()) {
			order.setId(rs.getInt("o.id"));
			if (rs.getTimestamp("o.date") != null) {
				order.setDate(new Date(rs.getTimestamp("o.date").getTime()));
			}

			OrderLine orderLine = new OrderLine();
			orderLine.setId(rs.getInt("l.id"));
			orderLine.setProductName(rs.getString("l.productname"));
			orderLine.setNumber(rs.getInt("l.number"));
			orderLine.setUnitPrice(rs.getDouble("l.unit_price"));
			order.getOrderLines().add(orderLine);

			Restaurant restaurant = new Restaurant();
			restaurant.setId(rs.getInt("r.id"));
			restaurant.setName(rs.getString("r.name"));
			restaurant.setStreet(rs.getString("r.street"));
			restaurant.setZipCode(rs.getString("r.zipcode"));
			restaurant.setCity(rs.getString("r.city"));
			restaurant.setCountry(rs.getString("r.country"));
			restaurant.setPhone(rs.getString("r.phone"));
			restaurant.setStreetNumber(rs.getString("r.streetnumber"));
			order.setRestaurant(restaurant);
		}
		rs.close();
		pstmt.close();
		this.con.close();
		return order;
	}

	public List<Order> listOrders(Integer restaurantId, Date date) throws SQLException {
		List<Order> listOrders = new ArrayList<Order>();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT o.id, o.date, o.restaurant_id," + "l.id, l.productname, l.number, l.unit_price,"
					+ "r.id, r.name, r.street, r.streetnumber, r.zipcode, r.city, r.country, r.phone FROM orders o"
					+ " LEFT JOIN order_lines l on o.id = l.order_id"
					+ " INNER JOIN restaurants r on o.restaurant_id = r.id WHERE 1 = 1");
			if (restaurantId != null && restaurantId > 0) {
				sb.append(" and r.id = ?");
			}
			if (date != null) {
				sb.append(" and DATE(o.date) = ?");
			}
			sb.append(" ORDER BY o.date");
			String query = sb.toString();
			PreparedStatement pstmt = this.con.prepareStatement(query);
			if (restaurantId != null && restaurantId > 0 && date != null) {
				pstmt.setInt(1, restaurantId);
				pstmt.setDate(2, new java.sql.Date(date.getTime()));
			} else if (restaurantId != null && restaurantId > 0 && date == null) {
				pstmt.setInt(1, restaurantId);
			} else if (date != null && (restaurantId == null || restaurantId == 0)) {
				pstmt.setDate(1, new java.sql.Date(date.getTime()));
			}
			ResultSet rs = pstmt.executeQuery();
			int lastId = 0;
			Order order = null;
			while (rs.next()) {
				if (rs.getInt(1) != lastId) {
					order = new Order();
					order.setId(rs.getInt("o.id"));
					if (rs.getTimestamp("o.date") != null) {
						order.setDate(new Date(rs.getTimestamp("o.date").getTime()));
					}
					listOrders.add(order);
				}

				OrderLine orderLine = new OrderLine();
				if (rs.getInt("l.id") > 0) {
					orderLine.setId(rs.getInt("l.id"));
					orderLine.setProductName(rs.getString("l.productname"));
					orderLine.setNumber(rs.getInt("l.number"));
					orderLine.setUnitPrice(rs.getDouble("l.unit_price"));
					order.getOrderLines().add(orderLine);
				}

				Restaurant restaurant = new Restaurant();
				restaurant.setId(rs.getInt("r.id"));
				restaurant.setName(rs.getString("r.name"));
				restaurant.setStreet(rs.getString("r.street"));
				restaurant.setZipCode(rs.getString("r.zipcode"));
				restaurant.setCity(rs.getString("r.city"));
				restaurant.setCountry(rs.getString("r.country"));
				restaurant.setPhone(rs.getString("r.phone"));
				restaurant.setStreetNumber(rs.getString("r.streetnumber"));
				order.setRestaurant(restaurant);

				lastId = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
			this.con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOrders;
	}

}
