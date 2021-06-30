package server;

import db.Products;
import db.Sales;

import java.io.IOException;
import java.net.*;
import java.sql.SQLException;

import org.json.simple.JSONObject;

import View.Dashboard;

import java.io.PrintWriter;

public class ServerNode {

	private static JSONObject products;
	private static String productsString;

	private static ServerSocket serverSocket;

	public ServerNode() {
	}

	public static void main(String[] args) {

		try {
			startServer(1);
			new Dashboard(new HttpRequest().getProducts()).setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void startServer(int request_type) {

		productsString = getProducts().toString();

		try {
			System.out.println("Execution underway...");
			serverSocket = new ServerSocket(9000);// Instantiate ServerSocket and set port

			while (true) {
				try (Socket client = serverSocket.accept()) {
					handleClient(client, request_type);
					System.out.println("Client complete...");
				}
			}

		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// Method to handle client connection
	private static void handleClient(Socket client, int request_type) {

		System.out.println("Client...");
		PrintWriter out;
		try {
			out = new PrintWriter(client.getOutputStream());
			out.println("HTTP/1.1 200 OK");
			out.println("Content-Type: text/html");
			out.println("\r\n");
			out.println(productsString);

			out.flush();
			out.close(); // Close output stream
			client.close(); // Close client socket
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static JSONObject getProducts() {

		products = new JSONObject();
		try {
			products = new Products().getAllProducts();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}

	public void addSale(String query) {

		String[] params = query.split("&");
		String noOfItems = "";
		String totalPaid = "";
		String servedBy = "";

		for (String param : params) {
			noOfItems = param.split("=")[0];
			totalPaid = param.split("=")[1];
			servedBy = param.split("=")[2];
		}
		int i = new Sales().recordSale(Integer.parseInt(noOfItems), Double.parseDouble(totalPaid), servedBy);
		System.out.println(i + " sale added!");
	}

}
