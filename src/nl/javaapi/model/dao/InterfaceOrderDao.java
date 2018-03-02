package nl.javaapi.model.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import nl.javaapi.model.entity.Order;

/**
 * This is an interface for the OrderDao class
 * 
 * @author Guilherme Silveira
 *
 */
public interface InterfaceOrderDao {

	public void addNewOrder(Order order) throws SQLException;

	public void updateOrder(Order order) throws SQLException;

	public void removeOrder(Integer orderId) throws SQLException;

	public Order getOrder(Integer orderId) throws SQLException;

	public List<Order> listOrders(Integer restaurantId, Date date) throws SQLException;

}
