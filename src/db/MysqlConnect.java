package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnect {

	private Connection connection = null;
	private String host = "localhost"; // Server address
	private String db = "javapos"; // Database name
	private final String url = "jdbc:mysql://" + host + "/" + db + "?"
			+ "useSSL=true&verifyServerCertificate=false&serverTimezone=UTC"; // Database url
	private final String user = "kagwicharles"; // Database user
	private final String password = "kagwi"; // Database pass

	public MysqlConnect() {

	}

	// Method that returns connection object
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		if (connection == null)
			connection = DriverManager.getConnection(url, user, password);
		return connection;
	}

	// Method to close connection
	public void closeConnection() throws SQLException {
		if (connection != null)
			connection.close();
	}

}
