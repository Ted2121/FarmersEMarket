package ui;

import controller.ControllerFactory;
import controller.ControllerInterfaces.CRUDPurchaseOrderController;
import model.PurchaseOrder;
import model.SaleOrder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;

public class SalesPanel extends TablePanel{

	//private CRUDSaleOrderController controller;
	private HashMap<Integer, SaleOrder> idRelatedToPurchaseOrder;
	private SalesPanel self;

	private JTable table;

	public SalesPanel() {
		self=this;
		table = getTable();
		//controller = ControllerFactory.getCRUDSaleOrderController();
		ProgramFrame.getFrame().setTitle("Sales");
		getNewButton().setText("New Sales");
		getNewButton().addActionListener(e -> {
			SalesPopup popup = new SalesPopup();
			popup.setParent(self);
		});

		refreshTable();

		getEditButton().setEnabled(true);
		getEditButton().addActionListener(e -> {

			//controller = ControllerFactory.getCRUDSaleOrderController();

			if(table.getSelectedRowCount() == 1) {
				int id = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());

				SaleOrder saleOrder = idRelatedToSaleOrder.get(id);
				PurchasesPopup popup = new PurchasesPopup(purchaseOrder);
				popup.setParent(this);
			}else if(table.getSelectedRowCount()>1){
				JOptionPane.showMessageDialog(null, "More than 1 line have been selected");
			}else {
				JOptionPane.showMessageDialog(null, "No line have been selected");
			}

		});


		getDeleteButton().addActionListener(e -> {

			controller = ControllerFactory.getCRUDPurchaseOrderController();

			if(table.getSelectedRowCount() == 1) {
				int id = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
				PurchaseOrder purchaseOrder = idRelatedToPurchaseOrder.get(id);

				int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this order?","Delete confirmation", JOptionPane.YES_NO_OPTION);
				switch(choice) {
					case 0 -> {controller.deletePurchaseOrder(purchaseOrder);
						refreshTable();
					}
				}

			}else if(table.getSelectedRowCount()>1){
				JOptionPane.showMessageDialog(null, "More than 1 line have been selected");
			}else {
				JOptionPane.showMessageDialog(null, "No line have been selected");
			}

		});


	}

	public void refreshTable() {

		String col[] = {"id", "Provider name","Date / Time", "Total price"};
		idRelatedToPurchaseOrder = controller.retrieveIdRelatedToPurchaseOrderHashMap();
		String data[][] = controller.retrieveTableData();

		DefaultTableModel tableModel = new DefaultTableModel(data,col);
		table.setModel(tableModel);
	}
}
