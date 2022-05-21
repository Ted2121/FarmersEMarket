package ui;

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
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;
import javax.swing.text.NumberFormatter;

import controller.ControllerImplementation.*;
import controller.ControllerInterfaces.*;
import model.*;

public class SalesPopup extends PopupWindow{
    private SalesPanel parent;
    private JPanel mainPanel;
    private JFormattedTextField quantityTextField;
    private JPanel productPanel;
    private JButton addProductButton;
    private JButton createSaleOrderButton;
    private JComboBox<Customer> customerSelectionComboBox;
    private JComboBox<Product> productSelectionComboBox;
    private List<Product> productSubsetList;
    private List<Customer> customerSubsetList;
    private SaleOrder saleOrder;
    private CreateSaleOrderController createController;
    private SearchProductInterface productSearchControllerPart;
    private SearchCustomerInterface customerSearchControllerPart;


    public SalesPopup() {
        setTitle("New Sale");
//		initSubsetsList();

        createController = new CreateSaleOrderControllerImplementation();
        productSearchControllerPart = (SearchProductInterface) createController;
        customerSearchControllerPart = (SearchCustomerInterface) createController;

        initSpecificCreateSaleOrderComponent();
        initComponent();


    }

    public SalesPopup(SaleOrder saleOrder) {
        setTitle("Edit Sale");
//		initSubsetsList();

//        crudController = new CRUDSaleOrderControllerImplementation();
//        productSearchControllerPart = (SearchProductInterface) crudController;
//        customerSearchControllerPart = (SearchCustomerInterface) crudController;

        this.saleOrder = saleOrder;

//        initSpecificCRUDSaleOrderComponent();
        initComponent();

        //initComponentWithInformation();

    }


    //private void initComponentWithInformation() {
        //List<LineItem> relatedLineItem = crudController.finAllLineItemRelatedToThisSaleOrder(saleOrder);
        //for(LineItem lineItem : relatedLineItem) {
//            JPanel productWithQuantityPanel = new SalePopUpProductListedPanel(crudController, productPanel, lineItem);
//            productWithQuantityPanel.setPreferredSize(new java.awt.Dimension(productPanel.getPreferredSize().width, 32));
//            productWithQuantityPanel.setMaximumSize(new java.awt.Dimension(getWidth(), 32));
//
//            productPanel.add(productWithQuantityPanel);
//            productPanel.revalidate();
//            productPanel.repaint();
//
//
//        }
//
//        customerSelectionComboBox.setSelectedItem(saleOrder.getCustomer());
//
//    }

        private void initComponent() {
            mainPanel = new JPanel();

            GridBagLayout gridBagLayout = new GridBagLayout();
            gridBagLayout.columnWidths = new int[]{25, 105, 0, 91, 0, 0};
            gridBagLayout.rowHeights = new int[]{21, 0, 0, 20, 0, 0, 0, 0, 14, 0};
            gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, 0.0, 0.0};
            gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
            mainPanel.setLayout(gridBagLayout);

            JLabel customerNameLabel = new JLabel("Customer name:");
            GridBagConstraints gbc_customerNameLabel = new GridBagConstraints();
            gbc_customerNameLabel.anchor = GridBagConstraints.EAST;
            gbc_customerNameLabel.insets = new Insets(0, 0, 5, 5);
            gbc_customerNameLabel.gridx = 1;
            gbc_customerNameLabel.gridy = 1;
            mainPanel.add(customerNameLabel, gbc_customerNameLabel);

            customerSelectionComboBox = new JComboBox();
            customerSelectionComboBox.setEditable(true);
            GridBagConstraints gbc_customerSelectionComboBox = new GridBagConstraints();
            gbc_customerSelectionComboBox.gridwidth = 2;
            gbc_customerSelectionComboBox.insets = new Insets(0, 0, 5, 5);
            gbc_customerSelectionComboBox.fill = GridBagConstraints.HORIZONTAL;
            gbc_customerSelectionComboBox.gridx = 3;
            gbc_customerSelectionComboBox.gridy = 1;
            mainPanel.add(customerSelectionComboBox, gbc_customerSelectionComboBox);

            customerSelectionComboBox.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent event) {
                    if (event.getKeyChar() == KeyEvent.VK_ENTER) {

                        Thread searchingCustomerThread = new Thread(() ->
                        {
                            Component sourceEvent = (Component) event.getSource();
                            JComboBox comboBoxSourceEvent = (JComboBox) sourceEvent.getParent();
                            JTextComponent textComponentOfTheComboBox = (JTextComponent) comboBoxSourceEvent.getEditor().getEditorComponent();

                            List<Customer> customerList = customerSearchControllerPart.searchCustomerUsingThisName(textComponentOfTheComboBox.getText());
                            if (customerList.size() > 0) {
                                Customer[] customerArray = customerList.toArray(new Customer[0]);
                                customerSelectionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(customerArray));
                                createSaleOrderButton.setEnabled(true);
                            } else {
                                customerSelectionComboBox.removeAllItems();
                                createSaleOrderButton.setEnabled(false);
                            }

                        }
                        );
                        searchingCustomerThread.start();
                    }
                }
            });

            productSelectionComboBox = new JComboBox();
            productSelectionComboBox.setEditable(true);

            productSelectionComboBox.addActionListener(productSelectionComboBox);

            productSelectionComboBox.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent event) {
                    if (event.getKeyChar() == KeyEvent.VK_ENTER) {

                        Thread searchingProductThread = new Thread(() ->
                        {
                            Component sourceEvent = (Component) event.getSource();
                            JComboBox comboBoxSourceEvent = (JComboBox) sourceEvent.getParent();
                            JTextComponent textComponentOfTheComboBox = (JTextComponent) comboBoxSourceEvent.getEditor().getEditorComponent();

                            List<Product> productList = productSearchControllerPart.searchProductUsingThisName(textComponentOfTheComboBox.getText());
                            if (productList.size() > 0) {
                                Product[] productArray = productList.toArray(new Product[0]);
                                productSelectionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(productArray));
                                addProductButton.setEnabled(true);

                            } else {
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

            GridBagConstraints gbc_createSaleOrderButton = new GridBagConstraints();
            gbc_createSaleOrderButton.insets = new Insets(0, 0, 5, 5);
            gbc_createSaleOrderButton.gridx = 3;
            gbc_createSaleOrderButton.gridy = 7;
            mainPanel.add(createSaleOrderButton, gbc_createSaleOrderButton);

            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(e -> {
                    dispose();
            });

            GridBagConstraints gbc_cancelButton = new GridBagConstraints();
            gbc_cancelButton.fill = GridBagConstraints.HORIZONTAL;
            gbc_cancelButton.insets = new Insets(0, 0, 5, 5);
            gbc_cancelButton.gridx = 4;
            gbc_cancelButton.gridy = 7;
            mainPanel.add(cancelButton, gbc_cancelButton);


            add(mainPanel);

        }

        private void initSpecificCreateSaleOrderComponent () {
            createSaleOrderButton = new JButton("Create sale order");
            createSaleOrderButton.setEnabled(false);

            createSaleOrderButton.addActionListener(e -> {
                        if (productPanel.getComponentCount() != 0) {
                            Customer selectedCustomer = (Customer) customerSelectionComboBox.getSelectedItem();
                            createController.createSaleOrder(selectedCustomer);
                            dispose();
                            parent.refreshTable();
                        } else {
                            JOptionPane.showMessageDialog(null, "No products have been registered in the order");
                        }

                    }
            );

            addProductButton = new JButton("Add product to the list");
            addProductButton.setEnabled(false);

            addProductButton.addActionListener(e -> {

                        Product selectedProduct = (Product) productSelectionComboBox.getSelectedItem();
                        int quantity = 1;
                        if (!createController.isProductAlreadyInTheSaleOrder(selectedProduct)) {

                            if (!quantityTextField.getText().isEmpty())
                                quantity = (int) Integer.parseInt(quantityTextField.getText().replace(",", ""));
                            JPanel productWithQuantityPanel = new SalePopUpProductListedPanel(createController, productPanel, selectedProduct, quantity);
                            productWithQuantityPanel.setPreferredSize(new java.awt.Dimension(productPanel.getPreferredSize().width, 32));
                            productWithQuantityPanel.setMaximumSize(new java.awt.Dimension(getWidth(), 32));
                            try {
                                if(createController.checkIfQuantityIsSufficient(selectedProduct, quantity)){

                                        createController.addProductToSaleOrder(selectedProduct, quantity);


                            productPanel.add(productWithQuantityPanel);
                            }else{ 
                                    JOptionPane.showMessageDialog(null, "Quantity unavailable for this product");}
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            quantityTextField.setValue(null);
            				productSelectionComboBox.removeAllItems();
            				addProductButton.setEnabled(false);
            				
                            productPanel.revalidate();
                            productPanel.repaint();
                        } else {
                            JOptionPane.showMessageDialog(null, selectedProduct + " already present in the order");
                        }
                    }
            );
        }

//        private void initSpecificCRUDSaleOrderComponent () {
//            createSaleOrderButton = new JButton("Save modification");
//            createSaleOrderButton.setEnabled(true);
//
//            createSaleOrderButton.addActionListener(e -> {
//                if (productPanel.getComponentCount() != 0) {
//                    Customer selectedCustomer = (Customer) customerSelectionComboBox.getSelectedItem();
//                    saleOrder.setCustomer(selectedCustomer);
//                    //crudController.updateSaleOrder(saleOrder);
//                    if (parent != null)
//                        parent.refreshTable();
//                    dispose();
//                } else {
//                    JOptionPane.showMessageDialog(null, "No products have been registered in the order");
//                }
//            });
//
//            addProductButton = new JButton("Add product to the list");
//            addProductButton.setEnabled(false);
//
//            addProductButton.addActionListener(e -> {
//                        Product selectedProduct = (Product) productSelectionComboBox.getSelectedItem();
//                        int quantity = 1;
////                        if (!crudController.isProductAlreadyInTheSaleOrder(selectedProduct)) {
////
////                            if (!quantityTextField.getText().isEmpty())
////                                quantity = (int) Integer.parseInt(quantityTextField.getText());
////                            JPanel productWithQuantityPanel = new SalePopUpProductListedPanel(crudController, productPanel, selectedProduct, quantity);
////                            productWithQuantityPanel.setPreferredSize(new java.awt.Dimension(productPanel.getPreferredSize().width, 32));
////                            productWithQuantityPanel.setMaximumSize(new java.awt.Dimension(getWidth(), 32));
////
////                            crudController.addProductToSaleOrder(selectedProduct, quantity);
////
////                            productPanel.add(productWithQuantityPanel);
////                            productPanel.revalidate();
////                            productPanel.repaint();
////                        } else {
////                            JOptionPane.showMessageDialog(null, selectedProduct + " already present in the order");
////                        }
//                    }
//            );
//        }

        public void setParent (SalesPanel parent){
            this.parent = parent;
        }
    }

