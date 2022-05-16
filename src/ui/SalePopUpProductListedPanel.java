package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.ControllerInterfaces.CreateSaleOrderController;
import model.LineItem;
import model.Product;
import javax.swing.JLabel;

public class SalePopUpProductListedPanel extends JPanel {

	private Product product;
	private int quantity;
	private CreateSaleOrderController createController;
    // NOT Implemented Yet
    //CRUDSaleOrderController crudController;
	private JPanel listOfAllProductPanels;
	private JButton deleteButton;
	private JLabel productNameLabel;
	private JLabel timesSymbolLabel;
	private JLabel quantityLabel;
	private LineItem lineItem;

    public SalePopUpProductListedPanel(CreateSaleOrderController controller, JPanel listOfAllProductPanels, Product product, int quantity) {
        this.listOfAllProductPanels = listOfAllProductPanels;
        this.createController = controller;
        this.product = product;
        this.quantity = quantity;

        productNameLabel = new JLabel("productName");
        add(productNameLabel);

        timesSymbolLabel = new JLabel("x");
        add(timesSymbolLabel);

        quantityLabel = new JLabel("quantity");
        add(quantityLabel);

        deleteButton = new JButton("delete");
        add(deleteButton);

        if(product != null) {
            productNameLabel.setText(product.toString());
            quantityLabel.setText(quantity+"");
        }

        deleteButton.addActionListener(e -> {
                createController.deleteProductFromSaleOrder(product);
                listOfAllProductPanels.remove(this);
                listOfAllProductPanels.revalidate();
                listOfAllProductPanels.repaint();
        });

    }

    public SalePopUpProductListedPanel(JPanel listOfAllProductPanels, LineItem lineItem) {
        this.listOfAllProductPanels = listOfAllProductPanels;
        //this.crudController = controller;
        this.lineItem = lineItem;

        productNameLabel = new JLabel("productName");
        add(productNameLabel);

        timesSymbolLabel = new JLabel("x");
        add(timesSymbolLabel);

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

        // NOT Implemented Yet
        deleteButton.addActionListener(e -> {
//                crudController.deleteLineItemFromSaleOrder(lineItem);
//                listOfAllProductPanels.remove(self);
//                listOfAllProductPanels.revalidate();
//                listOfAllProductPanels.repaint();
        });


    }

    public SalePopUpProductListedPanel(JPanel listOfAllProductPanels, Product selectedProduct, int quantity) {
        this.listOfAllProductPanels = listOfAllProductPanels;
        // NOT Implemented Yet
        //this.crudController = controller;
        this.product = product;
        this.quantity = quantity;

        productNameLabel = new JLabel("productName");
        add(productNameLabel);

        timesSymbolLabel = new JLabel("x");
        add(timesSymbolLabel);

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

        deleteButton.addActionListener(e -> {
                // NOT Implemented Yet

//                crudController.deleteProductInProductToAdd(product);
//                listOfAllProductPanels.remove(self);
//                listOfAllProductPanels.revalidate();
//                listOfAllProductPanels.repaint();
        });


    }

}
