package ui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;

import controller.ControllerImplementation.CreatePurchaseOrderControllerImplementation;
import controller.ControllerInterfaces.CreatePurchaseOrderController;
import model.Product;
import model.Provider;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Choice;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.JComboBox;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;

public class PurchasePopupPanel extends JPanel {
	private JTextField textField;
	private List<Product> productSubsetList;
	private List<Provider> providerSubsetList;
	
	private static boolean writingProductTimer;
	private int testThreadCount =0;
	
	private CreatePurchaseOrderController controller;

	/**
	 * Create the panel.
	 */
	public PurchasePopupPanel() {
		controller = new CreatePurchaseOrderControllerImplementation();
		
		initComponent();
		
		productSubsetList = controller.retrieveAllObjectsSubset(Product.class);
		providerSubsetList = controller.retrieveAllObjectsSubset(Provider.class);
		
		
		
	}

	private void initComponent() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 74, 0, 91};
		gridBagLayout.rowHeights = new int[]{0, 0, 20, 0, 0, 0, 24, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel providerNameLabel = new JLabel("Provider name:");
		GridBagConstraints gbc_providerNameLabel = new GridBagConstraints();
		gbc_providerNameLabel.anchor = GridBagConstraints.EAST;
		gbc_providerNameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_providerNameLabel.gridx = 0;
		gbc_providerNameLabel.gridy = 0;
		add(providerNameLabel, gbc_providerNameLabel);
		
		JComboBox providerSelectionComboBox = new JComboBox();
		providerSelectionComboBox.setEditable(true);
		GridBagConstraints gbc_providerSelectionComboBox = new GridBagConstraints();
		gbc_providerSelectionComboBox.gridwidth = 2;
		gbc_providerSelectionComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_providerSelectionComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_providerSelectionComboBox.gridx = 1;
		gbc_providerSelectionComboBox.gridy = 0;
		add(providerSelectionComboBox, gbc_providerSelectionComboBox);
		
		providerSelectionComboBox.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {

		    @Override
		    public void keyReleased(KeyEvent event) {
		    	if (event.getKeyChar() == KeyEvent.VK_ENTER) {
		        	
			    	Thread searchingProviderThread = new Thread( () ->
			    			{
			    				testThreadCount++;
					        	Component sourceEvent = (Component) event.getSource();
					        	JComboBox comboBoxSourceEvent = (JComboBox) sourceEvent.getParent();
					        	JTextComponent textComponentOfTheComboBox = (JTextComponent) comboBoxSourceEvent.getEditor().getEditorComponent();
					        	
				        		List<Provider> providerList = controller.searchProviderUsingThisName(textComponentOfTheComboBox.getText());
								if(providerList.size()>0) {
									Provider[] providerArray = providerList.toArray(new Provider[0]);
								providerSelectionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(providerArray));
								}else {
									providerSelectionComboBox.removeAllItems();
								}

					        }
		    			);
			    	searchingProviderThread.start();
		    	}
		    }
		});
		
		
		JLabel productNameLabel = new JLabel("Product name");
		GridBagConstraints gbc_productNameLabel = new GridBagConstraints();
		gbc_productNameLabel.anchor = GridBagConstraints.NORTH;
		gbc_productNameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_productNameLabel.gridx = 0;
		gbc_productNameLabel.gridy = 1;
		add(productNameLabel, gbc_productNameLabel);
		
		JComboBox productSelectionComboBox = new JComboBox();
		productSelectionComboBox.setEditable(true);
		
		productSelectionComboBox.addActionListener(productSelectionComboBox);
		
		productSelectionComboBox.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {

		    @Override
		    public void keyReleased(KeyEvent event) {
		    	if (event.getKeyChar() == KeyEvent.VK_ENTER) {
		        	
			    	Thread searchingProductThread = new Thread( () ->
			    			{
			    				testThreadCount++;
					        	Component sourceEvent = (Component) event.getSource();
					        	JComboBox comboBoxSourceEvent = (JComboBox) sourceEvent.getParent();
					        	JTextComponent textComponentOfTheComboBox = (JTextComponent) comboBoxSourceEvent.getEditor().getEditorComponent();
					        	
				        		List<Product> productList = controller.searchProductUsingThisName(textComponentOfTheComboBox.getText());
								if(productList.size()>0) {
									Product[] productArray = productList.toArray(new Product[0]);
								productSelectionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(productArray));
								}else {
									productSelectionComboBox.removeAllItems();
								}

					        }
		    			);
			    	searchingProductThread.start();
		    	}
		    }
		});
		

		
		GridBagConstraints gbc_productSelectionComboBox = new GridBagConstraints();
		gbc_productSelectionComboBox.gridwidth = 2;
		gbc_productSelectionComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_productSelectionComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_productSelectionComboBox.gridx = 1;
		gbc_productSelectionComboBox.gridy = 1;
		add(productSelectionComboBox, gbc_productSelectionComboBox);
		
		JLabel lblQuantity = new JLabel("Quantity:");
		GridBagConstraints gbc_lblQuantity = new GridBagConstraints();
		gbc_lblQuantity.anchor = GridBagConstraints.EAST;
		gbc_lblQuantity.insets = new Insets(0, 0, 5, 5);
		gbc_lblQuantity.gridx = 0;
		gbc_lblQuantity.gridy = 2;
		add(lblQuantity, gbc_lblQuantity);
		
		textField = new JTextField();
		textField.setColumns(10);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 2;
		add(textField, gbc_textField);

		for(int i=0 ; i< 9 ; i++) {
			JPanel panel = new JPanel();
			JLabel label = new JLabel("test");
			panel.add(label);
			panel.setMaximumSize(new java.awt.Dimension(getMaximumSize().width, 20));
//			productPanel.add(panel);
		}
		
		JButton addProductButton = new JButton("Add product to the list");
		GridBagConstraints gbc_addProductButton = new GridBagConstraints();
		gbc_addProductButton.gridwidth = 5;
		gbc_addProductButton.insets = new Insets(0, 0, 5, 0);
		gbc_addProductButton.gridx = 0;
		gbc_addProductButton.gridy = 3;
		add(addProductButton, gbc_addProductButton);
		
		JScrollPane productScrollPanel = new JScrollPane();
		JPanel productPanel = new JPanel();
		
		productScrollPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JPanel productLabelPanel = new JPanel();
		productLabelPanel.setMaximumSize(new java.awt.Dimension(32767, 20));
		
			JLabel productLabel = new JLabel();
			productLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			productLabel.setText("Product List");
			
	
			productPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
			productPanel.setToolTipText("");
			productPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
			productPanel.setFocusCycleRoot(true);
			productPanel.setLayout(new javax.swing.BoxLayout(productPanel, javax.swing.BoxLayout.Y_AXIS));
			
			javax.swing.GroupLayout layout = new javax.swing.GroupLayout(productLabelPanel);
			layout.setHorizontalGroup(
				layout.createParallelGroup(Alignment.LEADING)
					.addComponent(productLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
			);
			layout.setVerticalGroup(
				layout.createParallelGroup(Alignment.TRAILING)
					.addGroup(Alignment.LEADING, layout.createSequentialGroup()
						.addComponent(productLabel)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);
			productLabelPanel.setLayout(layout);
			productPanel.add(productLabelPanel);
			
			productScrollPanel.setViewportView(productPanel);
			productPanel.setBackground(Color.black);
			
			GridBagConstraints gbc_productPanel = new GridBagConstraints();
			gbc_productPanel.insets = new Insets(0, 0, 5, 0);
			gbc_productPanel.gridheight = 6;
			gbc_productPanel.gridwidth = 5;
			gbc_productPanel.fill = GridBagConstraints.BOTH;
			gbc_productPanel.gridx = 0;
			gbc_productPanel.gridy = 4;
			add(productScrollPanel, gbc_productPanel);
		
	}


}
