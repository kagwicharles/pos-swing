package View;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Model.SaleModel;
import server.HttpRequest;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class SalesView extends JFrame {

	private JPanel contentPane;
	private JTable table;

	String[] tblHead = { "Item no", "Items sold", "Total paid", "Sold by" };
	DefaultTableModel dtm = new DefaultTableModel(tblHead, 0);

	private ArrayList<SaleModel> sales;

	public SalesView() {
		sales = new ArrayList<SaleModel>();
		populateSales();
		setBounds(100, 100, 700, 310);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		table = new JTable(dtm);
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	// Fill sales table
	public void populateSales() {
		int i = 0;
		while (i != sales.size()) {
			Object[] row = { sales.get(i).getNoOfItems(), sales.get(i).getTotalPaid(), sales.get(i).getServedBy() };
			dtm.addRow(row);
			i++;
		}
	}

}
