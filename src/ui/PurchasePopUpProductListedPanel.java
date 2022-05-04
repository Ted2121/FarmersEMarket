package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.ControllerInterfaces.CreatePurchaseOrderController;
import model.Product;
import javax.swing.JLabel;

public class PurchasePopUpProductListedPanel extends JPanel {

	PurchasePopUpProductListedPanel self;
	Product product;
	int quantity;
	CreatePurchaseOrderController controller;
	JPanel listOfAllProductPanels;
	
	JButton deleteButton;
	JLabel productNameLabel;
	JLabel timesSymboleLabel;
	JLabel quantityLabel;

	public PurchasePopUpProductListedPanel(CreatePurchaseOrderController controller, JPanel listOfAllProductPanels, Product product, int quantity) {
		this.listOfAllProductPanels = listOfAllProductPanels;
		this.controller = controller;
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
				controller.deleteProductFromPurchaseOrder(product);
				listOfAllProductPanels.remove(self);
				listOfAllProductPanels.revalidate();
				listOfAllProductPanels.repaint();
			}
		});
		
		
	}

}
