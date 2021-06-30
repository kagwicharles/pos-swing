package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Products {

	private Statement st;
	private ResultSet rs;
	private String query = "SELECT * FROM product";

	public Products() {

	}

	public JSONObject getAllProducts() throws SQLException, ClassNotFoundException {
		JSONObject productObject = new JSONObject();
		JSONArray productsArray = new JSONArray();

		st = new MysqlConnect().getConnection().createStatement(); // create db connection
		rs = st.executeQuery(query);

		while (rs.next()) {
			JSONObject record = new JSONObject();
			record.put("Product Code", rs.getString("product_code"));
			record.put("Product Name", rs.getString("product_name"));
			record.put("Product Price", rs.getDouble("price"));
			productsArray.add(record);
		}

		productObject.put("Product", productsArray); // Put array in object
		return productObject;
	}
}
