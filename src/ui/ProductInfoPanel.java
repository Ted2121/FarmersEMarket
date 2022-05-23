package ui;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Popup;
import javax.swing.table.DefaultTableModel;

import controller.ControllerFactory;
import controller.ControllerInterfaces.CRUDProductInformationController;
import model.Product;
import model.PurchaseOrder;

public class ProductInfoPanel extends TablePanel{
	private JTable table;
	private CRUDProductInformationController controller;
	
	
	public ProductInfoPanel() {
		
		getEditButton().setEnabled(false);
		getDeleteButton().setEnabled(false);
		getNewButton().setEnabled(false);
		
		Thread gettingController = new Thread(() -> {
			controller = ControllerFactory.getCRUDProductInformationController();
			refreshTable();
			getEditButton().setEnabled(true);
			getDeleteButton().setEnabled(true);
			getNewButton().setEnabled(true);
		});
		gettingController.start();
		
		table = getTable();
		ProgramFrame.getFrame().setTitle("Products");
		getNewButton().setText("New Product");
		getNewButton().addActionListener(e -> {
			ProductInfoPopup popup = new ProductInfoPopup();
			popup.setPanel(this);
		});
		
		
		getEditButton().addActionListener(e -> {
			int numberOfSelectedRows = table.getSelectedRowCount();
			
			if(numberOfSelectedRows == 1) {
				int id = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
				Product product = controller.searchAProductById(id);
				ProductInfoPopup popup = new ProductInfoPopup(product);
				popup.setPanel(this);
			}
		});
		
		getDeleteButton().addActionListener(e -> {
			int numberOfSelectedRows = table.getSelectedRowCount();
			
			if(table.getSelectedRowCount() == 1) {
				int id = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
				Product product = controller.searchAProductById(id);
						
				int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this product?","Delete confirmation", JOptionPane.YES_NO_OPTION);
				switch(choice) {
					case 0 -> {
						new Thread(() -> {
							controller.deleteProductInformationAndProduct(product);
							refreshTable();
						}).start();
						
					}
				}

			}
		});
	}
	
	public void refreshTable() {
		
		String col[] = {"id", "Product name","Purchasing Price", "Selling Price", "Unit", "Weight category", "Location Code", "Quantity"};
		String data[][] = controller.retrieveTableData();
		
		DefaultTableModel tableModel = new DefaultTableModel(data,col);
		table.setModel(tableModel);
	}
}
