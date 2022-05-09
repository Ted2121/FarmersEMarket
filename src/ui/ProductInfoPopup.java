package ui;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.management.remote.SubjectDelegationPermission;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;

import controller.ControllerFactory;
import controller.ControllerInterfaces.CRUDProductInformationController;
import model.ModelFactory;
import model.Product;
import model.Product.Unit;
import model.Product.WeightCategory;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProductInfoPopup extends PopupWindow{
	private JTextField textFieldProductName;
	private JTextField textFieldPurchasingPrice;
	private JTextField textFieldSellingPrice;
	private JTextField textFieldUnit;
	private JTextField textFieldWeightCategory;
	private JTextField textFieldQuantity;
	private JTextField textFieldLocationCode;
	private Product product = null;
	private CRUDProductInformationController controller;
	private ProductInfoPanel panel;
	
	public void setPanel(ProductInfoPanel panel) {
		this.panel = panel;
	}

	public ProductInfoPopup() {
		setTitle("New Product");
		controller = ControllerFactory.getCRUDProductInformationController();
		initComponent();
		saveOrEdit();
	}
	
	public ProductInfoPopup(Product product) {
		setTitle("Edit Product");
		controller = ControllerFactory.getCRUDProductInformationController();
		this.product = product;
		
		initComponentEditVersion();
		saveOrEdit();
	}
	
	public void initComponent() {
		JPanel panel = new JPanel();
		getPanel().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 71, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{28, 0, 29, 0, 0, 0, 14, 0, 0, 0, 30, 18, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel labelProductName = new JLabel("ProductName");
		GridBagConstraints gbc_labelProductName = new GridBagConstraints();
		gbc_labelProductName.insets = new Insets(0, 0, 5, 5);
		gbc_labelProductName.gridx = 2;
		gbc_labelProductName.gridy = 0;
		panel.add(labelProductName, gbc_labelProductName);
		
		textFieldProductName = new JTextField();
		GridBagConstraints gbc_textFieldProductName = new GridBagConstraints();
		gbc_textFieldProductName.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldProductName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldProductName.gridx = 4;
		gbc_textFieldProductName.gridy = 0;
		panel.add(textFieldProductName, gbc_textFieldProductName);
		textFieldProductName.setColumns(10);
		
		JLabel labelPurchasingPrice = new JLabel("Purchasing price");
		GridBagConstraints gbc_labelPurchasingPrice = new GridBagConstraints();
		gbc_labelPurchasingPrice.insets = new Insets(0, 0, 5, 5);
		gbc_labelPurchasingPrice.gridx = 2;
		gbc_labelPurchasingPrice.gridy = 2;
		panel.add(labelPurchasingPrice, gbc_labelPurchasingPrice);
		
		textFieldPurchasingPrice = new JTextField();
		GridBagConstraints gbc_textFieldPurchasingPrice = new GridBagConstraints();
		gbc_textFieldPurchasingPrice.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldPurchasingPrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPurchasingPrice.gridx = 4;
		gbc_textFieldPurchasingPrice.gridy = 2;
		panel.add(textFieldPurchasingPrice, gbc_textFieldPurchasingPrice);
		textFieldPurchasingPrice.setColumns(10);
		
		JLabel labelSellingPrice = new JLabel("Selling price");
		GridBagConstraints gbc_labelSellingPrice = new GridBagConstraints();
		gbc_labelSellingPrice.insets = new Insets(0, 0, 5, 5);
		gbc_labelSellingPrice.gridx = 2;
		gbc_labelSellingPrice.gridy = 4;
		panel.add(labelSellingPrice, gbc_labelSellingPrice);
		
		textFieldSellingPrice = new JTextField();
		GridBagConstraints gbc_textFieldSellingPrice = new GridBagConstraints();
		gbc_textFieldSellingPrice.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldSellingPrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldSellingPrice.gridx = 4;
		gbc_textFieldSellingPrice.gridy = 4;
		panel.add(textFieldSellingPrice, gbc_textFieldSellingPrice);
		textFieldSellingPrice.setColumns(10);
		
		JLabel labelUnit = new JLabel("Unit");
		GridBagConstraints gbc_labelUnit = new GridBagConstraints();
		gbc_labelUnit.insets = new Insets(0, 0, 5, 5);
		gbc_labelUnit.gridx = 2;
		gbc_labelUnit.gridy = 6;
		panel.add(labelUnit, gbc_labelUnit);
		
		textFieldUnit = new JTextField();
		GridBagConstraints gbc_textFieldUnit = new GridBagConstraints();
		gbc_textFieldUnit.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldUnit.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldUnit.gridx = 4;
		gbc_textFieldUnit.gridy = 6;
		panel.add(textFieldUnit, gbc_textFieldUnit);
		textFieldUnit.setColumns(10);
		
		JLabel labelWeightCategory = new JLabel("Weight Category");
		GridBagConstraints gbc_labelWeightCategory = new GridBagConstraints();
		gbc_labelWeightCategory.insets = new Insets(0, 0, 5, 5);
		gbc_labelWeightCategory.gridx = 2;
		gbc_labelWeightCategory.gridy = 8;
		panel.add(labelWeightCategory, gbc_labelWeightCategory);
		
		textFieldWeightCategory = new JTextField();
		GridBagConstraints gbc_textFieldWeightCategory = new GridBagConstraints();
		gbc_textFieldWeightCategory.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldWeightCategory.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldWeightCategory.gridx = 4;
		gbc_textFieldWeightCategory.gridy = 8;
		panel.add(textFieldWeightCategory, gbc_textFieldWeightCategory);
		textFieldWeightCategory.setColumns(10);
		
		JLabel labelQuantity = new JLabel("Quantity");
		GridBagConstraints gbc_labelQuantity = new GridBagConstraints();
		gbc_labelQuantity.insets = new Insets(0, 0, 5, 5);
		gbc_labelQuantity.gridx = 2;
		gbc_labelQuantity.gridy = 10;
		panel.add(labelQuantity, gbc_labelQuantity);
		
		textFieldQuantity = new JTextField();
		GridBagConstraints gbc_textFieldQuantity = new GridBagConstraints();
		gbc_textFieldQuantity.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldQuantity.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldQuantity.gridx = 4;
		gbc_textFieldQuantity.gridy = 10;
		panel.add(textFieldQuantity, gbc_textFieldQuantity);
		textFieldQuantity.setColumns(10);
		
		JLabel labelLocationCode = new JLabel("Location Code");
		GridBagConstraints gbc_labelLocationCode = new GridBagConstraints();
		gbc_labelLocationCode.insets = new Insets(0, 0, 0, 5);
		gbc_labelLocationCode.gridx = 2;
		gbc_labelLocationCode.gridy = 12;
		panel.add(labelLocationCode, gbc_labelLocationCode);
		
		textFieldLocationCode = new JTextField();
		GridBagConstraints gbc_textFieldLocationCode = new GridBagConstraints();
		gbc_textFieldLocationCode.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldLocationCode.gridx = 4;
		gbc_textFieldLocationCode.gridy = 12;
		panel.add(textFieldLocationCode, gbc_textFieldLocationCode);
		textFieldLocationCode.setColumns(10);
		//TODO
	}
	
	public void initComponentEditVersion() {
		JPanel panel = new JPanel();
		getPanel().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 71, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{28, 0, 29, 0, 0, 0, 14, 0, 0, 0, 30, 18, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel labelProductName = new JLabel("ProductName");
		GridBagConstraints gbc_labelProductName = new GridBagConstraints();
		gbc_labelProductName.insets = new Insets(0, 0, 5, 5);
		gbc_labelProductName.gridx = 2;
		gbc_labelProductName.gridy = 0;
		panel.add(labelProductName, gbc_labelProductName);
		
		textFieldProductName = new JTextField(product.getProductName());
		GridBagConstraints gbc_textFieldProductName = new GridBagConstraints();
		gbc_textFieldProductName.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldProductName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldProductName.gridx = 4;
		gbc_textFieldProductName.gridy = 0;
		panel.add(textFieldProductName, gbc_textFieldProductName);
		textFieldProductName.setColumns(10);
		
		JLabel labelPurchasingPrice = new JLabel("Purchasing price");
		GridBagConstraints gbc_labelPurchasingPrice = new GridBagConstraints();
		gbc_labelPurchasingPrice.insets = new Insets(0, 0, 5, 5);
		gbc_labelPurchasingPrice.gridx = 2;
		gbc_labelPurchasingPrice.gridy = 2;
		panel.add(labelPurchasingPrice, gbc_labelPurchasingPrice);
		
		textFieldPurchasingPrice = new JTextField(Double.toString(product.getPurchasingPrice()));
		GridBagConstraints gbc_textFieldPurchasingPrice = new GridBagConstraints();
		gbc_textFieldPurchasingPrice.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldPurchasingPrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPurchasingPrice.gridx = 4;
		gbc_textFieldPurchasingPrice.gridy = 2;
		panel.add(textFieldPurchasingPrice, gbc_textFieldPurchasingPrice);
		textFieldPurchasingPrice.setColumns(10);
		
		JLabel labelSellingPrice = new JLabel("Selling price");
		GridBagConstraints gbc_labelSellingPrice = new GridBagConstraints();
		gbc_labelSellingPrice.insets = new Insets(0, 0, 5, 5);
		gbc_labelSellingPrice.gridx = 2;
		gbc_labelSellingPrice.gridy = 4;
		panel.add(labelSellingPrice, gbc_labelSellingPrice);
		
		textFieldSellingPrice = new JTextField(Double.toString(product.getSellingPrice()));
		GridBagConstraints gbc_textFieldSellingPrice = new GridBagConstraints();
		gbc_textFieldSellingPrice.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldSellingPrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldSellingPrice.gridx = 4;
		gbc_textFieldSellingPrice.gridy = 4;
		panel.add(textFieldSellingPrice, gbc_textFieldSellingPrice);
		textFieldSellingPrice.setColumns(10);
		
		JLabel labelUnit = new JLabel("Unit");
		GridBagConstraints gbc_labelUnit = new GridBagConstraints();
		gbc_labelUnit.insets = new Insets(0, 0, 5, 5);
		gbc_labelUnit.gridx = 2;
		gbc_labelUnit.gridy = 6;
		panel.add(labelUnit, gbc_labelUnit);
		
		textFieldUnit = new JTextField(product.getUnit());
		GridBagConstraints gbc_textFieldUnit = new GridBagConstraints();
		gbc_textFieldUnit.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldUnit.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldUnit.gridx = 4;
		gbc_textFieldUnit.gridy = 6;
		panel.add(textFieldUnit, gbc_textFieldUnit);
		textFieldUnit.setColumns(10);
		
		JLabel labelWeightCategory = new JLabel("Weight Category");
		GridBagConstraints gbc_labelWeightCategory = new GridBagConstraints();
		gbc_labelWeightCategory.insets = new Insets(0, 0, 5, 5);
		gbc_labelWeightCategory.gridx = 2;
		gbc_labelWeightCategory.gridy = 8;
		panel.add(labelWeightCategory, gbc_labelWeightCategory);
		
		textFieldWeightCategory = new JTextField(Integer.toString(product.getWeightCategory()));
		GridBagConstraints gbc_textFieldWeightCategory = new GridBagConstraints();
		gbc_textFieldWeightCategory.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldWeightCategory.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldWeightCategory.gridx = 4;
		gbc_textFieldWeightCategory.gridy = 8;
		panel.add(textFieldWeightCategory, gbc_textFieldWeightCategory);
		textFieldWeightCategory.setColumns(10);
		
		JLabel labelQuantity = new JLabel("Quantity");
		GridBagConstraints gbc_labelQuantity = new GridBagConstraints();
		gbc_labelQuantity.insets = new Insets(0, 0, 5, 5);
		gbc_labelQuantity.gridx = 2;
		gbc_labelQuantity.gridy = 10;
		panel.add(labelQuantity, gbc_labelQuantity);
		
		textFieldQuantity = new JTextField(Integer.toString(product.getRelatedProductInformation().getQuantity()));
		GridBagConstraints gbc_textFieldQuantity = new GridBagConstraints();
		gbc_textFieldQuantity.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldQuantity.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldQuantity.gridx = 4;
		gbc_textFieldQuantity.gridy = 10;
		panel.add(textFieldQuantity, gbc_textFieldQuantity);
		textFieldQuantity.setColumns(10);
		
		JLabel labelLocationCode = new JLabel("Location Code");
		GridBagConstraints gbc_labelLocationCode = new GridBagConstraints();
		gbc_labelLocationCode.insets = new Insets(0, 0, 0, 5);
		gbc_labelLocationCode.gridx = 2;
		gbc_labelLocationCode.gridy = 12;
		panel.add(labelLocationCode, gbc_labelLocationCode);
		
		textFieldLocationCode = new JTextField(Integer.toString(product.getRelatedProductInformation().getLocationCode()));
		GridBagConstraints gbc_textFieldLocationCode = new GridBagConstraints();
		gbc_textFieldLocationCode.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldLocationCode.gridx = 4;
		gbc_textFieldLocationCode.gridy = 12;
		panel.add(textFieldLocationCode, gbc_textFieldLocationCode);
		textFieldLocationCode.setColumns(10);
		//TODO
	}
	
	public void saveOrEdit() {
		getSaveButton().addActionListener(e -> {
			if(product == null) {
				controller.createProductInformationAndProduct(textFieldProductName.getText(), 
						Double.parseDouble(textFieldPurchasingPrice.getText()), Double.parseDouble(textFieldSellingPrice.getText()), 
						textFieldUnit.getText(), Integer.parseInt(textFieldWeightCategory.getText()), 
						Integer.parseInt(textFieldLocationCode.getText()), Integer.parseInt(textFieldQuantity.getText()));
			}
			else {
				String weightCategory = "";
				switch(Integer.parseInt(textFieldWeightCategory.getText())) {
					case 1 -> weightCategory = "ONE";
					case 5 -> weightCategory = "FIVE";
					case 10 -> weightCategory = "TEN";
				}
				product = ModelFactory.getProductModel(textFieldProductName.getText(), 
						Double.parseDouble(textFieldPurchasingPrice.getText()), Double.parseDouble(textFieldSellingPrice.getText()), 
						WeightCategory.valueOf(weightCategory), Unit.valueOf(textFieldUnit.getText()));
				controller.updateProductInformationAndProduct(product, Integer.parseInt(textFieldLocationCode.getText()), Integer.parseInt(textFieldQuantity.getText()));
			}
			panel.refreshTable();
			dispose();
		});
	}
}
