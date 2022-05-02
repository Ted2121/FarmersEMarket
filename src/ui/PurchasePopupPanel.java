package ui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import controller.ControllerImplementation.CreatePurchaseOrderControllerImplementation;
import controller.ControllerInterfaces.CreatePurchaseOrderController;
import model.Product;
import model.Provider;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Choice;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.JComboBox;

public class PurchasePopupPanel extends JPanel {
	private JTextField providerNameTextField;
	private JTextField productNameTextField;
	CreatePurchaseOrderController controller;
	HashMap<String, Provider> providerNameLinkedToProviderObject;
	HashMap<String, Product> productNameLinkedToProductObject;
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public PurchasePopupPanel() {
		controller = new CreatePurchaseOrderControllerImplementation();
		providerNameLinkedToProviderObject = new HashMap<>();
		productNameLinkedToProductObject = new HashMap<>();
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 74, 0, 91, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 20, 0, 0, 24, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel providerNameLabel = new JLabel("Provider name:");
		GridBagConstraints gbc_providerNameLabel = new GridBagConstraints();
		gbc_providerNameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_providerNameLabel.gridx = 0;
		gbc_providerNameLabel.gridy = 0;
		add(providerNameLabel, gbc_providerNameLabel);
		
		providerNameTextField = new JTextField();
		GridBagConstraints gbc_providerNameTextField = new GridBagConstraints();
		gbc_providerNameTextField.gridwidth = 2;
		gbc_providerNameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_providerNameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_providerNameTextField.gridx = 1;
		gbc_providerNameTextField.gridy = 0;
		add(providerNameTextField, gbc_providerNameTextField);
		providerNameTextField.setColumns(10);
		
		JButton searchProviderButton = new JButton("Search");
		GridBagConstraints gbc_searchProviderButton = new GridBagConstraints();
		gbc_searchProviderButton.insets = new Insets(0, 0, 5, 0);
		gbc_searchProviderButton.gridx = 3;
		gbc_searchProviderButton.gridy = 0;
		add(searchProviderButton, gbc_searchProviderButton);
		
		JLabel providerSelectionLabel = new JLabel("Select a provider:");
		GridBagConstraints gbc_providerSelectionLabel = new GridBagConstraints();
		gbc_providerSelectionLabel.anchor = GridBagConstraints.EAST;
		gbc_providerSelectionLabel.insets = new Insets(0, 0, 5, 5);
		gbc_providerSelectionLabel.gridx = 0;
		gbc_providerSelectionLabel.gridy = 1;
		add(providerSelectionLabel, gbc_providerSelectionLabel);
		
		JComboBox providerSelectionComboBox = new JComboBox();
		GridBagConstraints gbc_providerSelectionComboBox = new GridBagConstraints();
		gbc_providerSelectionComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_providerSelectionComboBox.gridwidth = 3;
		gbc_providerSelectionComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_providerSelectionComboBox.gridx = 1;
		gbc_providerSelectionComboBox.gridy = 1;
		add(providerSelectionComboBox, gbc_providerSelectionComboBox);
		
		JLabel productNameLabel = new JLabel("Product Name");
		GridBagConstraints gbc_productNameLabel = new GridBagConstraints();
		gbc_productNameLabel.anchor = GridBagConstraints.NORTH;
		gbc_productNameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_productNameLabel.gridx = 0;
		gbc_productNameLabel.gridy = 3;
		add(productNameLabel, gbc_productNameLabel);
		
		productNameTextField = new JTextField();
		productNameTextField.setColumns(10);
		GridBagConstraints gbc_productNameTextField = new GridBagConstraints();
		gbc_productNameTextField.gridwidth = 2;
		gbc_productNameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_productNameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_productNameTextField.gridx = 1;
		gbc_productNameTextField.gridy = 3;
		add(productNameTextField, gbc_productNameTextField);
		
		JButton searchProductButton = new JButton("Search");
		GridBagConstraints gbc_searchProductButton = new GridBagConstraints();
		gbc_searchProductButton.insets = new Insets(0, 0, 5, 0);
		gbc_searchProductButton.gridx = 3;
		gbc_searchProductButton.gridy = 3;
		add(searchProductButton, gbc_searchProductButton);
		
		JLabel productSelectionLabel = new JLabel("Select a product:");
		GridBagConstraints gbc_productSelectionLabel = new GridBagConstraints();
		gbc_productSelectionLabel.anchor = GridBagConstraints.EAST;
		gbc_productSelectionLabel.insets = new Insets(0, 0, 5, 5);
		gbc_productSelectionLabel.gridx = 0;
		gbc_productSelectionLabel.gridy = 4;
		add(productSelectionLabel, gbc_productSelectionLabel);
		
		JComboBox productSelectionComboBox = new JComboBox();
		GridBagConstraints gbc_productSelectionComboBox = new GridBagConstraints();
		gbc_productSelectionComboBox.gridwidth = 3;
		gbc_productSelectionComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_productSelectionComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_productSelectionComboBox.gridx = 1;
		gbc_productSelectionComboBox.gridy = 4;
		add(productSelectionComboBox, gbc_productSelectionComboBox);
		
		JLabel lblQuantity = new JLabel("Quantity:");
		GridBagConstraints gbc_lblQuantity = new GridBagConstraints();
		gbc_lblQuantity.anchor = GridBagConstraints.EAST;
		gbc_lblQuantity.insets = new Insets(0, 0, 5, 5);
		gbc_lblQuantity.gridx = 0;
		gbc_lblQuantity.gridy = 5;
		add(lblQuantity, gbc_lblQuantity);
		
		textField = new JTextField();
		textField.setColumns(10);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 5;
		add(textField, gbc_textField);
		
		JButton addProductButton = new JButton("Add product to the list");
		GridBagConstraints gbc_addProductButton = new GridBagConstraints();
		gbc_addProductButton.gridwidth = 7;
		gbc_addProductButton.insets = new Insets(0, 0, 5, 0);
		gbc_addProductButton.gridx = 0;
		gbc_addProductButton.gridy = 6;
		add(addProductButton, gbc_addProductButton);
		
		JPanel productPanel = new JPanel();
		GridBagConstraints gbc_productPanel = new GridBagConstraints();
		gbc_productPanel.gridheight = 2;
		gbc_productPanel.gridwidth = 4;
		gbc_productPanel.fill = GridBagConstraints.BOTH;
		gbc_productPanel.gridx = 0;
		gbc_productPanel.gridy = 7;
		add(productPanel, gbc_productPanel);
		
		searchProviderButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Provider> providerList = controller.searchProviderUsingThisName(providerNameTextField.getText());
				String[] providerListArray = new String[providerList.size()];
				providerNameLinkedToProviderObject.clear();
				for(int i=0 ; i< providerList.size();i++) {
					Provider provider = providerList.get(i);
					String providerFullName = provider.getFullName();
					providerNameLinkedToProviderObject.put(providerFullName, provider);
					providerListArray[i] = providerFullName;
				}
				providerSelectionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(providerListArray));
			}
		});
		
		searchProductButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Product> productList = controller.searchProductUsingThisName(productNameTextField.getText());
				String[] productListArray = new String[productList.size()];
				providerNameLinkedToProviderObject.clear();
				for(int i=0 ; i< productList.size();i++) {
					Product product = productList.get(i);
					String productFullName = product.getProductName() + " " + product.getWeightCategory() + " " + product.getUnit();
					productNameLinkedToProductObject.put(productFullName, product);
					productListArray[i] = productFullName;
				}
				productSelectionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(productListArray));
			}
		});

	}

}
