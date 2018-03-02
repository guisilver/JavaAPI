package nl.javaapi.model.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class is used as a representation for the table orders
 * 
 * @author Guilherme Silveira
 *
 */
@XmlRootElement
public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Date date;
	private String dateStr;
	private Restaurant restaurant = new Restaurant();
	private List<OrderLine> orderLines = new ArrayList<OrderLine>();

	public Order() {

	}

	public Order(Date date, Restaurant restaurant, List<OrderLine> orderLines) {
		this.date = date;
		this.restaurant = restaurant;
		this.orderLines = orderLines;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		this.dateStr = sf.format(date);
		this.date = date;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		try {
			this.date = sf.parse(dateStr);
		} catch (ParseException e) {
			// do nothing
		}
		this.dateStr = dateStr;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public List<OrderLine> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(List<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((dateStr == null) ? 0 : dateStr.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((orderLines == null) ? 0 : orderLines.hashCode());
		result = prime * result + ((restaurant == null) ? 0 : restaurant.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (dateStr == null) {
			if (other.dateStr != null)
				return false;
		} else if (!dateStr.equals(other.dateStr))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (orderLines == null) {
			if (other.orderLines != null)
				return false;
		} else if (!orderLines.equals(other.orderLines))
			return false;
		if (restaurant == null) {
			if (other.restaurant != null)
				return false;
		} else if (!restaurant.equals(other.restaurant))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", date=" + date + ", dateStr=" + dateStr + ", restaurant=" + restaurant
				+ ", orderLines=" + orderLines + "]";
	}

}
