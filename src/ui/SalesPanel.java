package ui;

import model.SaleOrder;

import javax.swing.*;

import java.util.HashMap;

public class SalesPanel extends TablePanel{

	//private CRUDSaleOrderController controller;
	private HashMap<Integer, SaleOrder> idRelatedToSaleOrder;
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
				SalesPopup popup = new SalesPopup(saleOrder);
				popup.setParent(this);
			}else if(table.getSelectedRowCount()>1){
				JOptionPane.showMessageDialog(null, "More than 1 line has been selected");
			}else {
				JOptionPane.showMessageDialog(null, "No line has been selected");
			}

		});


		getDeleteButton().addActionListener(e -> {

			//controller = ControllerFactory.getCRUDSaleOrderController();

			if(table.getSelectedRowCount() == 1) {
				int id = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
				SaleOrder purchaseOrder = idRelatedToSaleOrder.get(id);

				int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this order?","Delete confirmation", JOptionPane.YES_NO_OPTION);
				// Not Implemented
				switch(choice) {
					case 0 -> {//controller.deleteSaleOrder(saleOrder);
						//refreshTable();
					}
				}

			}else if(table.getSelectedRowCount()>1){
				JOptionPane.showMessageDialog(null, "More than 1 line has been selected");
			}else {
				JOptionPane.showMessageDialog(null, "No line has been selected");
			}

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
