package View;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Model.ProductModel;
import server.HttpRequest;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Color;
import java.awt.Component;

import javax.swing.UIManager;
import javax.swing.JSpinner;
import java.awt.Font;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

public class Dashboard extends JFrame {

	private static JPanel contentPane;
	private static JTextField product_name;
	private static JTextField price;
	private static JTable table;
	private static JSpinner quantity;
	private static JLabel totalamount;
	private JPanel panel_1;
	private JLabel lblNo, lblSub, lblVt, lbltotalAmnt;

	String[] tblHead = { "Product", "Quantity", "Price", "Amount" };
	DefaultTableModel dtm = new DefaultTableModel(tblHead, 0);

	private static ArrayList<ProductModel> productList;
	private JComboBox product_code;

	public Dashboard(ArrayList<ProductModel> products) {
		productList = new ArrayList<>();
		productList = products;

		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1243, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnAdd = new JButton("Add Item");
		btnAdd.setFont(new Font("Monospaced", Font.BOLD, 16));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dtm.addRow(new Object[] { product_name.getText().toString(), quantity.getValue(),
						price.getText().toString(), totalamount.getText().toString() });
			}
		});
		btnAdd.setBounds(707, 155, 117, 25);
		contentPane.add(btnAdd);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setForeground(Color.WHITE);
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBounds(152, 210, 670, 300);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setFillsViewportHeight(true);
		table = new JTable(dtm);
		scrollPane.setViewportView(table);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(new LineBorder(Color.GRAY));
		panel.setBounds(154, 90, 670, 56);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblProductName = new JLabel("Product code");
		lblProductName.setFont(new Font("Monospaced", Font.BOLD, 16));
		lblProductName.setBounds(12, 10, 130, 15);
		panel.add(lblProductName);

		JLabel lblProductName_2 = new JLabel("Product name");
		lblProductName_2.setFont(new Font("Monospaced", Font.BOLD, 16));
		lblProductName_2.setBounds(170, 10, 130, 15);
		panel.add(lblProductName_2);

		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Monospaced", Font.BOLD, 16));
		lblPrice.setBounds(458, 10, 60, 15);
		panel.add(lblPrice);

		product_name = new JTextField();
		product_name.setBounds(170, 30, 100, 19);
		panel.add(product_name);
		product_name.setColumns(10);

		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setFont(new Font("Monospaced", Font.BOLD, 16));
		lblQuantity.setBounds(316, 10, 100, 15);
		panel.add(lblQuantity);

		price = new JTextField();
		price.setBounds(460, 30, 70, 19);
		panel.add(price);
		price.setColumns(10);

		JLabel lblAmount = new JLabel("Amount");
		lblAmount.setFont(new Font("Monospaced", Font.BOLD, 16));
		lblAmount.setBounds(570, 10, 100, 15);
		panel.add(lblAmount);

		product_code = new JComboBox();
		product_code.setBounds(12, 30, 100, 19);
		panel.add(product_code);
		product_code.setSelectedItem("Select code...");
		product_code.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				populateOtherFields(product_code.getSelectedIndex());
			}
		});

		totalamount = new JLabel("Total");
		totalamount.setBackground(Color.LIGHT_GRAY);
		totalamount.setBounds(582, 30, 50, 20);
		panel.add(totalamount);

		quantity = new JSpinner();
		quantity.setModel(new SpinnerNumberModel(1, 1, 100, 1));
		quantity.setBounds(326, 30, 50, 20);
		panel.add(quantity);

		JLabel lblWelcome = new JLabel("Welcome");
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(new Font("Monospaced", Font.BOLD, 25));
		lblWelcome.setBounds(562, 12, 117, 25);
		contentPane.add(lblWelcome);

		JButton btnGenerateReceipt = new JButton("Generate Receipt");
		btnGenerateReceipt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateReceipt();
			}
		});
		btnGenerateReceipt.setFont(new Font("Monospaced", Font.BOLD, 16));
		btnGenerateReceipt.setBounds(587, 560, 237, 25);
		contentPane.add(btnGenerateReceipt);

		JButton btnNewCustomer = new JButton("New");
		btnNewCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newCustomer();
			}
		});
		btnNewCustomer.setFont(new Font("Monospaced", Font.BOLD, 16));
		btnNewCustomer.setBounds(5, 90, 100, 25);
		contentPane.add(btnNewCustomer);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.getViewport().setBackground(Color.WHITE);
		scrollPane_1.setBounds(881, 90, 350, 500);
		contentPane.add(scrollPane_1);

		panel_1 = new JPanel();
		scrollPane_1.setViewportView(panel_1);
		panel_1.setBackground(Color.WHITE);
		panel_1.setLayout(null);
		panel_1.setVisible(false);

		JLabel lblNoOfItems = new JLabel("No. of items");
		lblNoOfItems.setBounds(12, 186, 130, 19);
		lblNoOfItems.setFont(new Font("Monospaced", Font.BOLD, 16));
		panel_1.add(lblNoOfItems);

		JLabel lblThankYou = new JLabel("Thank you.");
		lblThankYou.setHorizontalAlignment(SwingConstants.CENTER);
		lblThankYou.setFont(new Font("Monospaced", Font.BOLD, 16));
		lblThankYou.setBounds(134, 433, 130, 19);
		panel_1.add(lblThankYou);

		JLabel lblKagwiPosSystems = new JLabel("Kagwi POS Systems");
		lblKagwiPosSystems.setHorizontalAlignment(SwingConstants.CENTER);
		lblKagwiPosSystems.setFont(new Font("Monospaced", Font.BOLD, 16));
		lblKagwiPosSystems.setBounds(83, 33, 200, 19);
		panel_1.add(lblKagwiPosSystems);

		JLabel lblMoiAvenueStreet = new JLabel("Moi Avenue street");
		lblMoiAvenueStreet.setHorizontalAlignment(SwingConstants.CENTER);
		lblMoiAvenueStreet.setFont(new Font("Monospaced", Font.PLAIN, 16));
		lblMoiAvenueStreet.setBounds(83, 64, 200, 19);
		panel_1.add(lblMoiAvenueStreet);

		JLabel lblTel = new JLabel("Tel: +254712464436");
		lblTel.setHorizontalAlignment(SwingConstants.CENTER);
		lblTel.setFont(new Font("Monospaced", Font.PLAIN, 16));
		lblTel.setBounds(83, 95, 200, 19);
		panel_1.add(lblTel);

		JLabel lblOpenHoursam = new JLabel("Open hours: 8am - 9pm Mon - Fri");
		lblOpenHoursam.setHorizontalAlignment(SwingConstants.CENTER);
		lblOpenHoursam.setFont(new Font("Monospaced", Font.PLAIN, 16));
		lblOpenHoursam.setBounds(12, 132, 326, 19);
		panel_1.add(lblOpenHoursam);

		JLabel lblTotal = new JLabel("Total Amount");
		lblTotal.setFont(new Font("Monospaced", Font.BOLD, 16));
		lblTotal.setBounds(12, 313, 130, 19);
		panel_1.add(lblTotal);

		JLabel lblSubtotal = new JLabel("Subtotal");
		lblSubtotal.setFont(new Font("Monospaced", Font.BOLD, 16));
		lblSubtotal.setBounds(12, 217, 130, 19);
		panel_1.add(lblSubtotal);

		JLabel lblTotalVat = new JLabel("Total VAT");
		lblTotalVat.setFont(new Font("Monospaced", Font.BOLD, 16));
		lblTotalVat.setBounds(12, 282, 130, 19);
		panel_1.add(lblTotalVat);

		JLabel lblLessDisocount = new JLabel("Less discount 10%");
		lblLessDisocount.setFont(new Font("Monospaced", Font.BOLD, 16));
		lblLessDisocount.setBounds(12, 251, 179, 19);
		panel_1.add(lblLessDisocount);

		lblNo = new JLabel("0");
		lblNo.setFont(new Font("Monospaced", Font.PLAIN, 16));
		lblNo.setBounds(248, 186, 90, 19);
		panel_1.add(lblNo);

		lblSub = new JLabel("0.00");
		lblSub.setFont(new Font("Monospaced", Font.PLAIN, 16));
		lblSub.setBounds(248, 217, 90, 19);
		panel_1.add(lblSub);

		lblVt = new JLabel("0.00");
		lblVt.setFont(new Font("Monospaced", Font.PLAIN, 16));
		lblVt.setBounds(248, 282, 90, 19);
		panel_1.add(lblVt);

		lbltotalAmnt = new JLabel("0.00");
		lbltotalAmnt.setFont(new Font("Monospaced", Font.PLAIN, 16));
		lbltotalAmnt.setBounds(248, 313, 90, 19);
		panel_1.add(lbltotalAmnt);

		JLabel lblServedByKagwi = new JLabel("Served by Kagwi");
		lblServedByKagwi.setHorizontalAlignment(SwingConstants.CENTER);
		lblServedByKagwi.setFont(new Font("Monospaced", Font.BOLD, 16));
		lblServedByKagwi.setBounds(110, 402, 164, 19);
		panel_1.add(lblServedByKagwi);

		JLabel lblReceipt = new JLabel("**********Receipt**********");
		lblReceipt.setHorizontalAlignment(SwingConstants.CENTER);
		lblReceipt.setFont(new Font("Monospaced", Font.BOLD, 16));
		lblReceipt.setBounds(12, 2, 326, 19);
		panel_1.add(lblReceipt);

		JButton btnSales = new JButton("Sales");
		btnSales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							new SalesView().setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnSales.setFont(new Font("Monospaced", Font.BOLD, 16));
		btnSales.setBounds(5, 143, 100, 25);
		contentPane.add(btnSales);
		quantity.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				totalamount.setText(updateAmount());
			}
		});
		setTitle("Dashboard");

		populateProductCodes();
		populateOtherFields(0);
	}

	// Method to populate product codes from api to combobox
	public void populateProductCodes() {
		String[] productCodes = new String[productList.size()];
		for (int i = 0; i < productList.size(); i++) {
			productCodes[i] = productList.get(i).getProductCode();
			System.out.println("Product code | " + productList.get(i).getProductCode());
		}
		product_code.setModel(new DefaultComboBoxModel(productCodes));
	}

	// Populate other fields upon selecting item from combobox
	public static void populateOtherFields(int selected) {
		product_name.setText(productList.get(selected).getProductName());
		price.setText(productList.get(selected).getProductPrice());
		totalamount.setText(getTotalAmount(selected));
	}

	// Method returns total amount of an item entry
	public static String getTotalAmount(int selected) {
		Double productPrice = Double.parseDouble(productList.get(selected).getProductPrice());
		int noOfItems = (Integer) quantity.getValue();
		return String.valueOf(productPrice * noOfItems);
	}

	// Update item total amount on changing quantity value
	public static String updateAmount() {
		Double productPrice = Double.parseDouble(price.getText().toString());
		int noOfItems = (Integer) quantity.getValue();
		return String.valueOf(productPrice * noOfItems);
	}

	// Generates receipt after completion of purchase
	public void generateReceipt() {
		int itemCount = dtm.getRowCount();
		Double sumTotal = 0.00;
		Double totalPayable = 0.00;
		Double vat = 10.00;
		lblNo.setText(String.valueOf(itemCount));
		for (int count = 0; count < dtm.getRowCount(); count++) {
			sumTotal = sumTotal + Double.parseDouble(dtm.getValueAt(count, 3).toString());
		}
		lblSub.setText(String.valueOf(sumTotal));
		lblVt.setText(String.valueOf(vat));
		totalPayable = (sumTotal - (sumTotal * 0.10)) + vat;
		lbltotalAmnt.setText(String.valueOf(totalPayable));
		recordSaleOption(itemCount, totalPayable, "kagwi");
		panel_1.setVisible(true);
	}

	// Clear items table and receipt
	public void newCustomer() {

		// Loop to remove current table entries
		while (dtm.getRowCount() > 0) {
			dtm.removeRow(0);
		}
		panel_1.setVisible(false);
	}

	// show alert
	public void recordSaleOption(int itemCount, Double totalPayable, String servedBy) {
		int result = JOptionPane.showConfirmDialog((Component) null, "Record sale!", "alert",
				JOptionPane.OK_CANCEL_OPTION);
		if (result == 0) {
			new HttpRequest().recordSale(itemCount, totalPayable, servedBy);
			dispose();
		}
	}
}
