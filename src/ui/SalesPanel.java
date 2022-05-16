package ui;

import model.PurchaseOrder;
import model.SaleOrder;

import javax.swing.*;

import java.util.HashMap;

public class SalesPanel extends TablePanel{

	//private CRUDSaleOrderController controller;
	private HashMap<Integer, SaleOrder> idRelatedToSaleOrder;
	private JTable table;

	public SalesPanel() {
		table = getTable();
		//controller = ControllerFactory.getCRUDSaleOrderController();
		ProgramFrame.getFrame().setTitle("Sales");
		getNewButton().setText("New Sales");
		getNewButton().addActionListener(e -> {
			SalesPopup popup = new SalesPopup();
			popup.setParent(this);
		});

		refreshTable();

		getEditButton().setEnabled(true);
		// CRUD NOT Implemented Yet
		getEditButton().addActionListener(e -> {

			//controller = ControllerFactory.getCRUDSaleOrderController();

//			if(table.getSelectedRowCount() == 1) {
//				int id = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
//
//				idRelatedToSaleOrder = controller.retrieveIdRelatedToPurchaseOrderHashMap();
//				PurchaseOrder purchaseOrder = idRelatedToPurchaseOrder.get(id);
//				PurchasesPopup popup = new PurchasesPopup(purchaseOrder);
//				popup.setParent(self);
//			}else if(table.getSelectedRowCount()>1){
//				JOptionPane.showMessageDialog(null, "More than 1 line has been selected");
//			}else {
//				JOptionPane.showMessageDialog(null, "No line has been selected");
//			}

		});

// CRUD NOT Implemented Yet
		getDeleteButton().addActionListener(e -> {

			//controller = ControllerFactory.getCRUDSaleOrderController();
//
//			if(table.getSelectedRowCount() == 1) {
//				int id = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
//				SaleOrder purchaseOrder = idRelatedToSaleOrder.get(id);
//
//				int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this order?","Delete confirmation", JOptionPane.YES_NO_OPTION);
//				// Not Implemented
//				switch(choice) {
//					case 0 -> {//controller.deleteSaleOrder(saleOrder);
//						//refreshTable();
//					}
//				}
//
//			}else if(table.getSelectedRowCount()>1){
//				JOptionPane.showMessageDialog(null, "More than 1 line has been selected");
//			}else {
//				JOptionPane.showMessageDialog(null, "No line has been selected");
//			}

		});


	}

	public void refreshTable() {

		String col[] = {"id", "Customer name","Date / Time", "Total price"};
		//idRelatedToSaleOrder = controller.retrieveIdRelatedToSaleOrderHashMap();
		//String data[][] = controller.retrieveTableData();

		//DefaultTableModel tableModel = new DefaultTableModel(data,col);
		//table.setModel(tableModel);
	}
}
