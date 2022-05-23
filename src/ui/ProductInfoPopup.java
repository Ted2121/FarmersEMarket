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
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controller.ControllerFactory;
import controller.ControllerInterfaces.CRUDProductInformationController;
import model.ModelFactory;
import model.Product;
import model.Product.Unit;
import model.Product.WeightCategory;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Choice;

public class ProductInfoPopup extends PopupWindow{
	private JTextField textFieldProductName;
	private JTextField textFieldPurchasingPrice;
	private JTextField textFieldSellingPrice;
	private JTextField textFieldLocationCode;
	private Choice choiceUnit;
	private Choice choiceWeightCategory;
	private Product product;
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
		
		initComponent();
		saveOrEdit();
	}
	
	public void initComponent() {
		JPanel panel = new JPanel();
		getPanel().add(panel, BorderLayout.CENTER);
		panel.setBackground(ProgramFrame.getBgcolor());
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 71, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{28, 0, 29, 0, 0, 0, 14, 0, 0, 0, 30, 18, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		if(product == null) {
			getSaveButton().setEnabled(false);
		}
		else {
			getSaveButton().setEnabled(true);
		}
		
		DocumentListener listener = new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
				    enableButton();
				  }
				  public void removeUpdate(DocumentEvent e) {
				    enableButton();
				  }
				  public void insertUpdate(DocumentEvent e) {
				    enableButton();
				}

				public void enableButton() {
				     if (textFieldProductName.getText().equals("") || textFieldLocationCode.getText().equals("")
				    		 || textFieldPurchasingPrice.getText().equals("") || textFieldSellingPrice.getText().equals(""))
				     {
				        getSaveButton().setEnabled(false);
				     }
				     else
				     {
				    	 getSaveButton().setEnabled(true);
				     }
				 }
		};
		
		JLabel labelProductName = new JLabel("Product Name", SwingConstants.RIGHT);
		GridBagConstraints gbc_labelProductName = new GridBagConstraints();
		gbc_labelProductName.insets = new Insets(0, 0, 5, 5);
		gbc_labelProductName.gridx = 2;
		gbc_labelProductName.gridy = 0;
		panel.add(labelProductName, gbc_labelProductName);
		
		if(product == null) {
			textFieldProductName = new JTextField();
		}
		else {
			textFieldProductName = new JTextField(product.getProductName());
		}
		textFieldProductName.getDocument().addDocumentListener(listener);
		GridBagConstraints gbc_textFieldProductName = new GridBagConstraints();
		gbc_textFieldProductName.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldProductName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldProductName.gridx = 4;
		gbc_textFieldProductName.gridy = 0;
		panel.add(textFieldProductName, gbc_textFieldProductName);
		textFieldProductName.setColumns(10);
		
		JLabel labelPurchasingPrice = new JLabel("Purchasing Price", SwingConstants.RIGHT);
		GridBagConstraints gbc_labelPurchasingPrice = new GridBagConstraints();
		gbc_labelPurchasingPrice.insets = new Insets(0, 0, 5, 5);
		gbc_labelPurchasingPrice.gridx = 2;
		gbc_labelPurchasingPrice.gridy = 2;
		panel.add(labelPurchasingPrice, gbc_labelPurchasingPrice);
		
		if(product == null) {
			textFieldPurchasingPrice = new JTextField();
		}
		else {
			textFieldPurchasingPrice = new JTextField(Double.toString(product.getPurchasingPrice()));
		}
		textFieldPurchasingPrice.getDocument().addDocumentListener(listener);
		GridBagConstraints gbc_textFieldPurchasingPrice = new GridBagConstraints();
		gbc_textFieldPurchasingPrice.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldPurchasingPrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPurchasingPrice.gridx = 4;
		gbc_textFieldPurchasingPrice.gridy = 2;
		panel.add(textFieldPurchasingPrice, gbc_textFieldPurchasingPrice);
		textFieldPurchasingPrice.setColumns(10);
		textFieldPurchasingPrice.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) {
		        char c = e.getKeyChar();
		        if (c!= '.' && ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
		            e.consume();
		        }
		     }
		});
		
		JLabel labelSellingPrice = new JLabel("Selling Price", SwingConstants.RIGHT);
		GridBagConstraints gbc_labelSellingPrice = new GridBagConstraints();
		gbc_labelSellingPrice.insets = new Insets(0, 0, 5, 5);
		gbc_labelSellingPrice.gridx = 2;
		gbc_labelSellingPrice.gridy = 4;
		panel.add(labelSellingPrice, gbc_labelSellingPrice);
		
		if(product == null) {
			textFieldSellingPrice = new JTextField();
		}
		else {
			textFieldSellingPrice = new JTextField(Double.toString(product.getSellingPrice()));
		}
		textFieldSellingPrice.getDocument().addDocumentListener(listener);
		GridBagConstraints gbc_textFieldSellingPrice = new GridBagConstraints();
		gbc_textFieldSellingPrice.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldSellingPrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldSellingPrice.gridx = 4;
		gbc_textFieldSellingPrice.gridy = 4;
		panel.add(textFieldSellingPrice, gbc_textFieldSellingPrice);
		textFieldSellingPrice.setColumns(10);
		textFieldSellingPrice.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) {
		        char c = e.getKeyChar();
		        if (c!= '.' && ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
		            e.consume();
		        }
		     }
		});
		
		JLabel labelUnit = new JLabel("Unit", SwingConstants.RIGHT);
		GridBagConstraints gbc_labelUnit = new GridBagConstraints();
		gbc_labelUnit.insets = new Insets(0, 0, 5, 5);
		gbc_labelUnit.gridx = 2;
		gbc_labelUnit.gridy = 6;
		panel.add(labelUnit, gbc_labelUnit);
		
		choiceUnit = new Choice();
		choiceUnit.add("KG");
		choiceUnit.add("L");
		if(product != null) {
			choiceUnit.select(product.getUnit());
		}
		GridBagConstraints gbc_choice = new GridBagConstraints();
		gbc_choice.insets = new Insets(0, 0, 5, 0);
		gbc_choice.gridx = 4;
		gbc_choice.gridy = 6;
		panel.add(choiceUnit, gbc_choice);
		
		JLabel labelWeightCategory = new JLabel("Weight Category", SwingConstants.RIGHT);
		GridBagConstraints gbc_labelWeightCategory = new GridBagConstraints();
		gbc_labelWeightCategory.insets = new Insets(0, 0, 5, 5);
		gbc_labelWeightCategory.gridx = 2;
		gbc_labelWeightCategory.gridy = 8;
		panel.add(labelWeightCategory, gbc_labelWeightCategory);
		
		choiceWeightCategory = new Choice();
		choiceWeightCategory.add("1");
		choiceWeightCategory.add("5");
		choiceWeightCategory.add("10");
		if(product != null) {
			choiceWeightCategory.select(String.valueOf(product.getWeightCategory()));
		}
		GridBagConstraints gbc_choice_1 = new GridBagConstraints();
		gbc_choice_1.insets = new Insets(0, 0, 5, 0);
		gbc_choice_1.gridx = 4;
		gbc_choice_1.gridy = 8;
		panel.add(choiceWeightCategory, gbc_choice_1);
		
		JLabel labelLocationCode = new JLabel("Location Code", SwingConstants.RIGHT);
		GridBagConstraints gbc_labelQuantity = new GridBagConstraints();
		gbc_labelQuantity.insets = new Insets(0, 0, 5, 5);
		gbc_labelQuantity.gridx = 2;
		gbc_labelQuantity.gridy = 10;
		panel.add(labelLocationCode, gbc_labelQuantity);
		
		if(product == null) {
			textFieldLocationCode = new JTextField();
		}
		else {
			textFieldLocationCode = new JTextField(Integer.toString(product.getRelatedProductInformation().getLocationCode()));
		}
		textFieldLocationCode.getDocument().addDocumentListener(listener);
		GridBagConstraints gbc_textFieldLocationCode = new GridBagConstraints();
		gbc_textFieldLocationCode.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldLocationCode.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldLocationCode.gridx = 4;
		gbc_textFieldLocationCode.gridy = 10;
		panel.add(textFieldLocationCode, gbc_textFieldLocationCode);
		textFieldLocationCode.setColumns(10);
		textFieldLocationCode.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) {
		        char c = e.getKeyChar();
		        if (c!= '.' && ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
		            e.consume();
		        }
		     }
		});
	}
	
	public void saveOrEdit() {
		getSaveButton().addActionListener(e -> {
			if(product == null) {
				controller.createProductInformationAndProduct(textFieldProductName.getText(), 
						Double.parseDouble(textFieldPurchasingPrice.getText()), Double.parseDouble(textFieldSellingPrice.getText()), 
						choiceUnit.getSelectedItem(), Integer.parseInt(choiceWeightCategory.getSelectedItem()), 
						Integer.parseInt(textFieldLocationCode.getText()), 0);
			}
			else {
				String weightCategory = "";
				switch(Integer.parseInt(choiceWeightCategory.getSelectedItem())) {
					case 1 -> weightCategory = "ONE";
					case 5 -> weightCategory = "FIVE";
					case 10 -> weightCategory = "TEN";
				}
				product.setProductName(textFieldProductName.getText());
				product.setPurchasingPrice(Double.parseDouble(textFieldPurchasingPrice.getText()));
				product.setSellingPrice(Double.parseDouble(textFieldSellingPrice.getText()));
				product.setWeightCategory(WeightCategory.valueOf(weightCategory));
				product.setUnit(Unit.valueOf(choiceUnit.getSelectedItem()));
				controller.updateProductInformationAndProduct(product, Integer.parseInt(textFieldLocationCode.getText()), product.getRelatedProductInformation().getQuantity());
			}
			panel.refreshTable();
			dispose();
		});
	}
}
