package ui;

import controller.ControllerFactory;
import controller.ControllerImplementation.CreateSaleOrderControllerImplementation;
import controller.ControllerInterfaces.CreateSaleOrderController;
import model.Customer;
import model.Product;
import model.Provider;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;

public class SalesPopup extends PopupWindow{
	private JPanel panel;
	private JTextField customerField;
	private JTextField productField;
	private JTextField quantityField;
	private JComboBox comboBox;
	private JButton addButton;
	private JButton searchButton;
	private String customerName;

	private JComboBox customerComboBox;

	HashMap<String, Customer> customerNameLinkedToProviderObject;
	HashMap<String, Product> productNameLinkedToProductObject;

	private CreateSaleOrderControllerImplementation createSaleOrderController;



	public SalesPopup() {
		customerNameLinkedToProviderObject = new HashMap<>();
		productNameLinkedToProductObject = new HashMap<>();
		createSaleOrderController = (CreateSaleOrderControllerImplementation) ControllerFactory.getCreateSaleOrderController();
		setTitle("New Sale");
		panel = new JPanel();
		panel.setBackground(ProgramFrame.getBgcolor());
		super.getPanel().add(panel, BorderLayout.EAST);
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JLabel customerLabel = new JLabel("Customer", SwingConstants.RIGHT);
		JLabel productLabel = new JLabel("Product", SwingConstants.RIGHT);
		JLabel quantityLabel = new JLabel("Quantity", SwingConstants.RIGHT);
		customerField = new JTextField();
		productField = new JTextField();
		quantityField = new JTextField();
		//JTextField summaryField = new JTextField();
		addButton = new JButton("Add");
		searchButton = new JButton("Search");

		JTable table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);

		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(0, 0, 30, 10);
		c.ipadx = 150;
		panel.add(customerLabel, c);

		c.gridx = 1;
		c.gridy = 0;
		c.anchor = GridBagConstraints.BASELINE;
		panel.add(customerField, c);

		c.gridx = 2;
		c.gridy = 0;
		c.ipadx = 10;
		panel.add(searchButton, c);

		c.ipadx = 150;
		c.gridx = 1;
		c.gridy = 1;
		customerComboBox = new JComboBox<>();
		panel.add(customerComboBox, c);

		c.ipadx = 150;
		c.insets = new Insets(0, 0, 5, 10);
		c.gridx = 0;
		c.gridy = 2;

		panel.add(productLabel, c);

		c.gridx = 1;
		c.gridy = 2;
		panel.add(productField, c);

		c.gridx = 0;
		c.gridy = 3;
		panel.add(quantityLabel, c);

		c.gridx = 1;
		c.gridy = 3;
		panel.add(quantityField, c);

		c.gridx = 2;
		c.gridy = 3;
		c.ipadx = 25;
		panel.add(addButton, c);


		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

					List<Customer> customerListForComboBox = createSaleOrderController.findAllCustomersWithThisName(customerField.getText());

					String[] arrayOfCustomers = new String[customerListForComboBox.size()];
					customerNameLinkedToProviderObject.clear();

					for (int i = 0; i < customerListForComboBox.size(); i++) {
						Customer customer = customerListForComboBox.get(i);
						String customerFullName = customer.getFullName();
						customerNameLinkedToProviderObject.put(customerFullName, customer);
						arrayOfCustomers[i] = customerFullName;
					}
					customerComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(arrayOfCustomers));

			}

		});
	}

	private void createSaleOrderFromCustomerField() throws SQLException {
		Customer customerToAdd = createSaleOrderController.findCustomerByFullName(customerField.getName());
		createSaleOrderController.createSaleOrder(customerToAdd);

	}






//	private class TheHandler implements ItemListener {
//		public void itemStateChanged(ItemEvent event) {
//
//			if(event.getStateChange() == ItemEvent.SELECTED) {
//				Object source = event.getSource();
//				if (source instanceof JComboBox) {
//					JComboBox cb = (JComboBox)source;
//					Object selectedItem = cb.getSelectedItem();
//
//				}
//			}
//		}
//	}
}
