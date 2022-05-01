package ui;

import db_access.DaoFactory;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class SalesPopup extends PopupWindow{
	private JTextField searchable = new JTextField(30);
	private JButton searchBtn = new JButton("Search");
//	private JTable result = new JTable();
	private JLabel resultedCustomer = new JLabel();
	private JPanel panel = new JPanel();
//  private JScrollPane scrollPane = new JScrollPane(result);

	public SalesPopup() throws HeadlessException {

		setTitle("New Sale");
		setSize(600, 600);
		setResizable(true);
		addComponents();
		setTable();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void addComponents() {
		panel.add(searchable);
		panel.add(searchBtn);
		panel.add(resultedCustomer);
		//panel.add(scrollPane);
		add(panel);
	}

	private void setTable() {
		searchBtn.addActionListener(e -> {
			try {
				resultedCustomer.setText(DaoFactory.getCustomerDao().findCustomerByFullName(searchable.getText()).getFullName());
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		});
	}
}
