package db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Sales {

	private PreparedStatement pst;
	private String query_1 = "INSERT INTO sale (no_of_items, total_paid, served_by) VALUES (?, ?, ?)";

	public Sales() {

	}

	public int recordSale(int noOfItems, Double totalPaid, String servedBy) {

		int i = 0;
		try {
			pst = new MysqlConnect().getConnection().prepareStatement(query_1);
			pst.setInt(1, noOfItems);
			pst.setDouble(2, totalPaid);
			pst.setString(3, servedBy);

			i = pst.executeUpdate();
			new MysqlConnect().closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
}
