package com.kagwi;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpRequest {

	private final String url_products = "http://localhost:8080/POS-web/pos/allProducts";
	private OkHttpClient client = new OkHttpClient().newBuilder().build();
	private Response response;
	private Request request;

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
}