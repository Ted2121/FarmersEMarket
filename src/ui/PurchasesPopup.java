package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;
import javax.swing.text.NumberFormatter;

import controller.ControllerImplementation.CreatePurchaseOrderControllerImplementation;
import controller.ControllerInterfaces.CreatePurchaseOrderController;
import model.Product;
import model.Provider;

public class PurchasesPopup extends PopupWindow{
	private static PurchasesPopup self;
	private JPanel mainPanel;
	
	private JFormattedTextField quantityTextField;
	private JPanel productPanel;
	private JButton addProductButton;
	private JButton createPurchaseOrderButton;

	private List<Product> productSubsetList;
	private List<Provider> providerSubsetList;
	
	CreatePurchaseOrderController controller;
	
	public PurchasesPopup() {
		setTitle("New Purchase");
		this.self = this;
		
		controller = new CreatePurchaseOrderControllerImplementation();
		
		initComponent();
		try {
			productSubsetList = controller.retrieveAllObjectsSubset(Product.class);
			providerSubsetList = controller.retrieveAllObjectsSubset(Provider.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void initComponent() {
		mainPanel = new JPanel();
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{25, 105, 0, 91, 0, 0};
		gridBagLayout.rowHeights = new int[]{21, 0, 0, 20, 0, 0, 0, 0, 14, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		mainPanel.setLayout(gridBagLayout);
		
		JLabel providerNameLabel = new JLabel("Provider name:");
		GridBagConstraints gbc_providerNameLabel = new GridBagConstraints();
		gbc_providerNameLabel.anchor = GridBagConstraints.EAST;
		gbc_providerNameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_providerNameLabel.gridx = 1;
		gbc_providerNameLabel.gridy = 1;
		mainPanel.add(providerNameLabel, gbc_providerNameLabel);
		
		JComboBox providerSelectionComboBox = new JComboBox();
		providerSelectionComboBox.setEditable(true);
		GridBagConstraints gbc_providerSelectionComboBox = new GridBagConstraints();
		gbc_providerSelectionComboBox.gridwidth = 2;
		gbc_providerSelectionComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_providerSelectionComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_providerSelectionComboBox.gridx = 3;
		gbc_providerSelectionComboBox.gridy = 1;
		mainPanel.add(providerSelectionComboBox, gbc_providerSelectionComboBox);
		
		providerSelectionComboBox.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyReleased(KeyEvent event) {
		    	if (event.getKeyChar() == KeyEvent.VK_ENTER) {
		        	
			    	Thread searchingProviderThread = new Thread( () ->
			    			{
					        	Component sourceEvent = (Component) event.getSource();
					        	JComboBox comboBoxSourceEvent = (JComboBox) sourceEvent.getParent();
					        	JTextComponent textComponentOfTheComboBox = (JTextComponent) comboBoxSourceEvent.getEditor().getEditorComponent();
					        	
				        		List<Provider> providerList = controller.searchProviderUsingThisName(textComponentOfTheComboBox.getText());
								if(providerList.size()>0) {
									Provider[] providerArray = providerList.toArray(new Provider[0]);
								providerSelectionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(providerArray));
								createPurchaseOrderButton.setEnabled(true);
								}else {
									providerSelectionComboBox.removeAllItems();
									createPurchaseOrderButton.setEnabled(false);
								}

					        }
		    			);
			    	searchingProviderThread.start();
		    	}
		    }
		});
		
		JComboBox productSelectionComboBox = new JComboBox();
		productSelectionComboBox.setEditable(true);
		
		productSelectionComboBox.addActionListener(productSelectionComboBox);
		
		productSelectionComboBox.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {

		    @Override
		    public void keyReleased(KeyEvent event) {
		    	if (event.getKeyChar() == KeyEvent.VK_ENTER) {
		        	
			    	Thread searchingProductThread = new Thread( () ->
			    			{
					        	Component sourceEvent = (Component) event.getSource();
					        	JComboBox comboBoxSourceEvent = (JComboBox) sourceEvent.getParent();
					        	JTextComponent textComponentOfTheComboBox = (JTextComponent) comboBoxSourceEvent.getEditor().getEditorComponent();
					        	
				        		List<Product> productList = controller.searchProductUsingThisName(textComponentOfTheComboBox.getText());
								if(productList.size()>0) {
									Product[] productArray = productList.toArray(new Product[0]);
								productSelectionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(productArray));
								addProductButton.setEnabled(true);
								
								}else {
									productSelectionComboBox.removeAllItems();
									addProductButton.setEnabled(false);
								}

					        }
		    			);
			    	searchingProductThread.start();
		    	}
		    }
		});
		
		
		JLabel productNameLabel = new JLabel("Product name");
		GridBagConstraints gbc_productNameLabel = new GridBagConstraints();
		gbc_productNameLabel.anchor = GridBagConstraints.NORTHEAST;
		gbc_productNameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_productNameLabel.gridx = 1;
		gbc_productNameLabel.gridy = 3;
		mainPanel.add(productNameLabel, gbc_productNameLabel);
		

		
		GridBagConstraints gbc_productSelectionComboBox = new GridBagConstraints();
		gbc_productSelectionComboBox.gridwidth = 2;
		gbc_productSelectionComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_productSelectionComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_productSelectionComboBox.gridx = 3;
		gbc_productSelectionComboBox.gridy = 3;
		mainPanel.add(productSelectionComboBox, gbc_productSelectionComboBox);
		
		//Format the JFormattedTextField to allow only numbers
		NumberFormat format = NumberFormat.getInstance();
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(0);
	    formatter.setMaximum(Integer.MAX_VALUE);
	    formatter.setAllowsInvalid(false);
	    formatter.setCommitsOnValidEdit(true);
	    
	    JLabel lblQuantity = new JLabel("Quantity:");
	    GridBagConstraints gbc_lblQuantity = new GridBagConstraints();
	    gbc_lblQuantity.anchor = GridBagConstraints.EAST;
	    gbc_lblQuantity.insets = new Insets(0, 0, 5, 5);
	    gbc_lblQuantity.gridx = 1;
	    gbc_lblQuantity.gridy = 4;
	    mainPanel.add(lblQuantity, gbc_lblQuantity);
		
		addProductButton = new JButton("Add product to the list");
		addProductButton.setEnabled(false);
		addProductButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Product selectedProduct = (Product) productSelectionComboBox.getSelectedItem();
				int quantity=1;
				if(!controller.isProductAlreadyInThePurchaseOrder(selectedProduct)) {
					
					if(!quantityTextField.getText().isEmpty())
						quantity = (int) Integer.parseInt(quantityTextField.getText());
					JPanel productWithQuantityPanel = new PurchasePopUpProductListedPanel(controller, productPanel, selectedProduct, quantity);
					productWithQuantityPanel.setPreferredSize(new java.awt.Dimension(productPanel.getPreferredSize().width, 32));
					productWithQuantityPanel.setMaximumSize(new java.awt.Dimension(getWidth(), 32));
					
					controller.addProductToPurchaseOrder(selectedProduct, quantity);
					
					productPanel.add(productWithQuantityPanel);
					productPanel.revalidate();
					productPanel.repaint();
				}else {
					JOptionPane.showMessageDialog(null, selectedProduct + " already present in the order");
				}
			}
		});
		quantityTextField = new JFormattedTextField(formatter);
		
		
		quantityTextField.setColumns(10);
		GridBagConstraints gbc_quantityTextField = new GridBagConstraints();
		gbc_quantityTextField.insets = new Insets(0, 0, 5, 5);
		gbc_quantityTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_quantityTextField.gridx = 3;
		gbc_quantityTextField.gridy = 4;
		mainPanel.add(quantityTextField, gbc_quantityTextField);
		
		
		
		GridBagConstraints gbc_addProductButton = new GridBagConstraints();
		gbc_addProductButton.gridwidth = 5;
		gbc_addProductButton.insets = new Insets(0, 0, 5, 5);
		gbc_addProductButton.gridx = 0;
		gbc_addProductButton.gridy = 5;
		mainPanel.add(addProductButton, gbc_addProductButton);
		
		JScrollPane productScrollPanel = new JScrollPane();
		productScrollPanel.setViewportBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		productPanel = new JPanel();
		productPanel.setBorder(null);
		productPanel.setMaximumSize(new java.awt.Dimension(getMaximumSize().width, productScrollPanel.getHeight()));
		productPanel.setLayout(new javax.swing.BoxLayout(productPanel, javax.swing.BoxLayout.Y_AXIS));
		
		
		productScrollPanel.setViewportView(productPanel);

		GridBagConstraints gbc_productPanel = new GridBagConstraints();
		gbc_productPanel.insets = new Insets(0, 0, 5, 5);
		gbc_productPanel.gridwidth = 4;
		gbc_productPanel.fill = GridBagConstraints.BOTH;
		gbc_productPanel.gridx = 1;
		gbc_productPanel.gridy = 6;
		mainPanel.add(productScrollPanel, gbc_productPanel);
		
		createPurchaseOrderButton = new JButton("Create purchase order");
		createPurchaseOrderButton.setEnabled(false);
		
		createPurchaseOrderButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(productPanel.getComponentCount()!=0) {
					Provider selectedProvider = (Provider) providerSelectionComboBox.getSelectedItem();
					controller.createPurchaseOrder(selectedProvider);
					self.dispose();
					self = null;
				}else {
					JOptionPane.showMessageDialog(null, "No products have been registered in the order");
				}
				
			}
		});
		
		GridBagConstraints gbc_createPurchaseOrderButton = new GridBagConstraints();
		gbc_createPurchaseOrderButton.insets = new Insets(0, 0, 5, 5);
		gbc_createPurchaseOrderButton.gridx = 3;
		gbc_createPurchaseOrderButton.gridy = 7;
		mainPanel.add(createPurchaseOrderButton, gbc_createPurchaseOrderButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				self.dispose();
				self = null;
			}
		});
		
		GridBagConstraints gbc_cancelButton = new GridBagConstraints();
		gbc_cancelButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_cancelButton.insets = new Insets(0, 0, 5, 5);
		gbc_cancelButton.gridx = 4;
		gbc_cancelButton.gridy = 7;
		mainPanel.add(cancelButton, gbc_cancelButton);
		
		
	
		add(mainPanel);
		
	}

}
