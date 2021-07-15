package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import Model.ProductModel;
import View.Dashboard;
import db.Products;
import db.Sales;

public class MyServer {

	private static Dashboard dashboard;

	public static void main(String[] args) throws Exception {
		HttpServer server = HttpServer.create(new InetSocketAddress(9000), 0);
		server.createContext("/products", new MyHandler());
		server.setExecutor(null);
		server.start();
	}

	static class MyHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			String query_params = t.getRequestURI().getQuery();
			if (query_params != null) {
				byte[] decodedBytes = Base64.getDecoder().decode(query_params);
				String decodedString = new String(decodedBytes);
				System.out.println("Decoded String: " + decodedString);
				JSONObject jsonObj;
				try {
					jsonObj = new JSONObject(decodedString);
					int noOfItems = Integer.parseInt(jsonObj.get("noOfItems").toString());
					Double totalPaid = Double.parseDouble(jsonObj.get("totalPaid").toString());
					String servedBy = jsonObj.get("servedBy").toString();

					recordSale(noOfItems, totalPaid, servedBy);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			try {
				String response = new Products().getAllProducts().toString();
				t.sendResponseHeaders(200, response.length());
				OutputStream os = t.getResponseBody();
				os.write(response.getBytes());
				dashboard = new Dashboard(populateDashboard(response));
				openUI();
				os.close();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void openUI() {
		dashboard.setVisible(true);
	}

	public static ArrayList<ProductModel> populateDashboard(String res) {
		ArrayList<ProductModel> productList = new ArrayList<>();
		JSONObject jsonObj;
		try {
			jsonObj = new JSONObject(res);
			JSONArray jsonArr = (JSONArray) jsonObj.get("Product");

			int i = jsonArr.length() - 1;
			while (i >= 0) {
				JSONObject jsonObjs = (JSONObject) jsonArr.get(i);
				productList.add(new ProductModel(jsonObjs.get("Product Code").toString(),
						jsonObjs.get("Product Name").toString(), jsonObjs.get("Product Price").toString()));
				i--;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return productList;
	}

	public static void recordSale(int noOfItems, Double totalPaid, String servedBy) {
		new Sales().recordSale(noOfItems, totalPaid, servedBy);
	}

	public static Map<String, String> getQueryMap(String query) {

		String[] params = query.split("&");
		Map<String, String> map = new HashMap<String, String>();

		for (String param : params) {
			String name = param.split("=")[0];
			String value = param.split("=")[1];
			map.put(name, value);
		}
		return map;
	}
}
