package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Model.ProductModel;
import Model.SaleModel;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpRequest {

	public HttpRequest() {
	}

	public static String url = "http://localhost:9000/products";

	private OkHttpClient client = new OkHttpClient().newBuilder().build();
	private Response response;
	private Request request;

	// Get all products from api
	public ArrayList<ProductModel> getProducts() {
		ArrayList<ProductModel> productList = new ArrayList<>();
		try {
			request = new Request.Builder().url(url).method("GET", null).build();
			response = client.newCall(request).execute();
			JSONObject jsonObj = new JSONObject(response.body().string());
			JSONArray jsonArr = (JSONArray) jsonObj.get("Product");

			int i = jsonArr.length() - 1;
			while (i >= 0) {
				JSONObject jsonObjs = (JSONObject) jsonArr.get(i);
				productList.add(new ProductModel(jsonObjs.get("Product Code").toString(),
						jsonObjs.get("Product Name").toString(), jsonObjs.get("Product Price").toString()));
				i--;
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Bind exception caught here");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return productList;
	}

	// record a sale in the api
	public void recordSale(int noOfItems, Double totalPaid, String servedBy) {

		try {

			JSONObject sale = new JSONObject();
			sale.put("noOfItems", String.valueOf(noOfItems));
			sale.put("totalPaid", String.valueOf(totalPaid));
			sale.put("servedBy", servedBy);
			String encodedString = Base64.getEncoder().encodeToString(sale.toString().getBytes());

			request = new Request.Builder().url(url + "?" + encodedString).method("GET", null).build();
			response = client.newCall(request).execute();
			System.out.println("Response generated: " + response.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
