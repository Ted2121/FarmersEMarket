package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.ControllerInterfaces.CRUDPurchaseOrderController;
import controller.ControllerInterfaces.CreatePurchaseOrderController;
import model.LineItem;
import model.Product;
import javax.swing.JLabel;

public class PurchasePopUpProductListedPanel extends JPanel {

	PurchasePopUpProductListedPanel self;
	Product product;
	int quantity;
	CreatePurchaseOrderController createController;
	CRUDPurchaseOrderController crudController;
	JPanel listOfAllProductPanels;
	
	JButton deleteButton;
	JLabel productNameLabel;
	JLabel timesSymboleLabel;
	JLabel quantityLabel;
	
	LineItem lineItem;

	public PurchasePopUpProductListedPanel(CreatePurchaseOrderController controller, JPanel listOfAllProductPanels, Product product, int quantity) {
		this.listOfAllProductPanels = listOfAllProductPanels;
		this.createController = controller;
		this.product = product;
		this.quantity = quantity;
		this.self = this;
		
		productNameLabel = new JLabel("productName");
		add(productNameLabel);
		
		timesSymboleLabel = new JLabel("x");
		add(timesSymboleLabel);
		
		quantityLabel = new JLabel("quantity");
		add(quantityLabel);
		
		deleteButton = new JButton("delete");
		add(deleteButton);
		
		if(product != null) {
			productNameLabel.setText(product.toString());
			quantityLabel.setText(quantity+"");
		}
		
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createController.deleteProductFromPurchaseOrder(product);
				listOfAllProductPanels.remove(self);
				listOfAllProductPanels.revalidate();
				listOfAllProductPanels.repaint();
			}
		});
		
		
	}
	
	public PurchasePopUpProductListedPanel(CRUDPurchaseOrderController controller, JPanel listOfAllProductPanels, LineItem lineItem) {
		this.listOfAllProductPanels = listOfAllProductPanels;
		this.crudController = controller;
		this.lineItem = lineItem;
		this.self = this;
		
		productNameLabel = new JLabel("productName");
		add(productNameLabel);
		
		timesSymboleLabel = new JLabel("x");
		add(timesSymboleLabel);
		
		quantityLabel = new JLabel("quantity");
		add(quantityLabel);
		
		deleteButton = new JButton("delete");
		add(deleteButton);
		
		Product product = lineItem.getProduct();
		int quantity = lineItem.getQuantity();
		
		if(product != null) {
			productNameLabel.setText(product.toString());
			quantityLabel.setText(quantity+"");
		}
		
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				crudController.deleteLineItemFromPurchaseOrder(lineItem);
				listOfAllProductPanels.remove(self);
				listOfAllProductPanels.revalidate();
				listOfAllProductPanels.repaint();
			}
		});
		
		
	}
	
	public PurchasePopUpProductListedPanel(CRUDPurchaseOrderController controller, JPanel listOfAllProductPanels, Product selectedProduct, int quantity) {
		this.listOfAllProductPanels = listOfAllProductPanels;
		this.crudController = controller;
		this.product = product;
		this.quantity = quantity;
		this.self = this;
		
		productNameLabel = new JLabel("productName");
		add(productNameLabel);
		
		timesSymboleLabel = new JLabel("x");
		add(timesSymboleLabel);
		
		quantityLabel = new JLabel("quantity");
		add(quantityLabel);
		
		deleteButton = new JButton("delete");
		add(deleteButton);
		
		Product product = selectedProduct;
		int quantityToAdd = quantity;
		
		if(product != null) {
			productNameLabel.setText(product.toString());
			quantityLabel.setText(quantityToAdd+"");
		}
		
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				crudController.deleteProductInProductToAdd(product);
				listOfAllProductPanels.remove(self);
				listOfAllProductPanels.revalidate();
				listOfAllProductPanels.repaint();
			}
		});
		
		
	}

}
