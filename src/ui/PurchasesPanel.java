package ui;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controller.ControllerFactory;
import controller.ControllerInterfaces.CRUDPurchaseOrderController;
import model.Provider;
import model.PurchaseOrder;

public class PurchasesPanel extends TablePanel{
	private CRUDPurchaseOrderController controller;
	private HashMap<Integer, PurchaseOrder> idRelatedToPurchaseOrder;
	private JTable table;

	public PurchasesPanel() {
		
		getEditButton().setEnabled(false);
		getDeleteButton().setEnabled(false);
		getNewButton().setEnabled(false);
		
		Thread gettingController = new Thread(() -> {
			controller = ControllerFactory.getCRUDPurchaseOrderController();
			refreshTable();
			getEditButton().setEnabled(true);
			getDeleteButton().setEnabled(true);
			getNewButton().setEnabled(true);
		});
		gettingController.start();
		
		table = getTable();
		ProgramFrame.getFrame().setTitle("Purchases");
		getNewButton().setText("New Purchase");
		getNewButton().addActionListener(e -> {
			PurchasesPopup popup = new PurchasesPopup();
			popup.setParent(this);
		});
		
		
		
		
		getEditButton().addActionListener(e -> {
			if(table.getSelectedRowCount() == 1) {
				int id = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
				
				idRelatedToPurchaseOrder = controller.retrieveIdRelatedToPurchaseOrderHashMap();
				PurchaseOrder purchaseOrder = idRelatedToPurchaseOrder.get(id);
				PurchasesPopup popup = new PurchasesPopup(purchaseOrder);
				popup.setParent(this);
			}else if(table.getSelectedRowCount()>1){
				JOptionPane.showMessageDialog(null, "More than 1 line have been selected");
			}else {
				JOptionPane.showMessageDialog(null, "No line have been selected");
			}
		});
		
		
		getDeleteButton().addActionListener(e -> {
			if(table.getSelectedRowCount() == 1) {
				int id = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
				PurchaseOrder purchaseOrder = idRelatedToPurchaseOrder.get(id);
						
				int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this order?","Delete confirmation", JOptionPane.YES_NO_OPTION);
				switch(choice) {
					case 0 -> {
						new Thread(() ->{
							controller.deletePurchaseOrder(purchaseOrder);
							refreshTable();
						}).start();
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
