package ui;

import javax.swing.JTable;
import javax.swing.Popup;
import javax.swing.table.DefaultTableModel;

import controller.ControllerFactory;
import controller.ControllerInterfaces.CRUDProductInformationController;
import model.Product;

public class ProductInfoPanel extends TablePanel{
	private JTable table;
	private CRUDProductInformationController controller;
	
	public ProductInfoPanel() {
		controller = ControllerFactory.getCRUDProductInformationController();
		table = getTable();
		ProgramFrame.getFrame().setTitle("Products");
		getNewButton().setText("New Product");
		getNewButton().addActionListener(e -> {
			ProductInfoPopup popup = new ProductInfoPopup();
			popup.setPanel(this);
		});
		
		refreshTable();
		
		getEditButton().addActionListener(e -> {
			int numberOfSelectedRows = table.getSelectedRowCount();
			
			if(numberOfSelectedRows == 1) {
				int id = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
				Product product = controller.searchAProductById(id);
				ProductInfoPopup popup = new ProductInfoPopup(product);
				popup.setPanel(this);
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
