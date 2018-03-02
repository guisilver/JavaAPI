package nl.javaapi.model.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class used to create connections to the database using the Inversion Of
 * Control concept and JDBC specification.
 * 
 * @author Guilherme Silveira
 *
 */
public class Database {

	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/takeaway";
	private static final String USER = "root";
	private static final String PASSWORD = "123456";

	/**
	 * This method is used for retrieving a new connection to the database.
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}

}
