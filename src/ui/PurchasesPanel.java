package ui;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class PurchasesPanel extends TablePanel{

	public PurchasesPanel() {
		ProgramFrame.getFrame().setTitle("Purchases");
		getNewButton().setText("New Purchase");
		getNewButton().addActionListener(e -> new PurchasesPopup());
		
		JTable table = getTable();
//		TableColumn purchaseOrderId = new TableColumn(0);
//		purchaseOrderId.setHeaderValue("id");
//		TableColumn purchaseOrderTotalPrice = new TableColumn(1);
//		purchaseOrderTotalPrice.setHeaderValue("Total price");
//		TableColumn purchaseOrderDateTime = new TableColumn(2);
//		purchaseOrderDateTime.setHeaderValue("Date / Time");
//		TableColumn purchaseOrderProviderName = new TableColumn(2);
//		purchaseOrderProviderName.setHeaderValue("Provider name");
		
		String col[] = {"id", "Total price", "Date / Time", "Provider name"};
		String data[][] = {{"test", "test", "test", "test"}, 
							{"test", "test", "test", "test"}};
		
		DefaultTableModel tableModel = new DefaultTableModel(data,col);
		table.setModel(tableModel);
		
	}
}
