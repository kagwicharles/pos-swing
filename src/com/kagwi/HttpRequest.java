package com.kagwi;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpRequest {

	private final String url_products = "http://localhost:8080/POS-web/pos/Products/allProducts";
	private final String url_add_sales = "http://localhost:8080/POS-web/pos/Sales/addSale";
	private final String url_get_sales = "http://localhost:8080/POS-web/pos/Sales/allSales";

	private OkHttpClient client = new OkHttpClient().newBuilder().build();
	private Response response;
	private Request request;

	// Get all products from api
	public ArrayList<ProductModel> getProducts() {
		ArrayList<ProductModel> productList = new ArrayList<>();
		try {
			request = new Request.Builder().url(url_products).method("GET", null).build();
			response = client.newCall(request).execute();
			JSONObject jsonObj = new JSONObject(response.body().string());
			JSONArray jsonArr = (JSONArray) jsonObj.get("Stock");

			int i = jsonArr.length() - 1;
			while (i >= 0) {
				JSONObject jsonObjs = (JSONObject) jsonArr.get(i);
				productList.add(new ProductModel(jsonObjs.get("Product Code").toString(),
						jsonObjs.get("Product Name").toString(), jsonObjs.get("Product Price").toString()));
				i--;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return productList;
	}

	// record a sale in the api
	public void recordSale(int noOfItems, Double totalPaid, String servedBy) {
		try {
			request = new Request.Builder().url(
					url_add_sales + "/" + String.valueOf(noOfItems) + "/" + String.valueOf(totalPaid) + "/" + servedBy)
					.method("GET", null).build();
			response = client.newCall(request).execute();
			System.out.println(response.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Get all completed sales
	public ArrayList<SaleModel> getSales() {
		ArrayList<SaleModel> salesList = new ArrayList<>();
		try {
			request = new Request.Builder().url(url_get_sales).method("GET", null).build();
			response = client.newCall(request).execute();
			JSONObject jsonObj = new JSONObject(response.body().string());
			JSONArray jsonArr = (JSONArray) jsonObj.get("Sales");

			int i = jsonArr.length() - 1;
			while (i >= 0) {
				JSONObject jsonObjs = (JSONObject) jsonArr.get(i);
				salesList.add(new SaleModel(jsonObjs.get("No. of items").toString(),
						jsonObjs.get("Total paid").toString(), jsonObjs.get("Served by").toString()));
				i--;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return salesList;
	}
}
