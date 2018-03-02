package nl.javaapi.model.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import nl.javaapi.model.dao.InterfaceOrderDao;
import nl.javaapi.model.dao.OrderDao;
import nl.javaapi.model.entity.Order;

/**
 * 
 * This class is used to intermediate the communication between the REST service
 * and the database. It also validates the data and implements the business
 * logic.
 * 
 * @author Guilherme Silveira
 *
 */
public class OrderService {

	private InterfaceOrderDao orderDao = new OrderDao();

	public void addNewOrder(Order order) throws Exception {
		if (order.getOrderLines() == null || order.getOrderLines().isEmpty()) {
			throw new Exception("Please, add one or more products to this order.");
		}
		if (order.getRestaurant() == null) {
			throw new Exception("Please, select the restaurant.");
		}
		this.orderDao.addNewOrder(order);
	}

	public void updateOrder(Order order) throws Exception {
		if (order.getOrderLines() == null || order.getOrderLines().isEmpty()) {
			throw new Exception("Please, add one or more products to this order.");
		}
		if (order.getRestaurant() == null) {
			throw new Exception("Please, select the restaurant.");
		}
		this.orderDao.updateOrder(order);
	}

	public void removeOrder(Integer orderId) throws SQLException {
		this.orderDao.removeOrder(orderId);
	}

	public Order getOrder(Integer orderId) throws SQLException {
		return this.orderDao.getOrder(orderId);
	}

	public List<Order> listOrders(Integer restaurantId, String date) throws SQLException, ParseException {
		SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
		Date fDate = null;
		if (date != null) {
			fDate = sf.parse(date);
		}
		return this.orderDao.listOrders(restaurantId, fDate);
	}

}
