package ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PurchasesPopup extends PopupWindow{
//	private JPanel panel;
	
	public PurchasesPopup() {
		setTitle("New Purchase");
		add(new PurchasePopupPanel());
//		panel = new JPanel();
//		panel.setBackground(ProgramFrame.getBgcolor());
//		super.getPanel().add(panel, BorderLayout.CENTER);
//		panel.setLayout(new GridBagLayout());
//		GridBagConstraints c = new GridBagConstraints();
//		JLabel customerLabel = new JLabel("Provider", SwingConstants.RIGHT);
//		JLabel productLabel = new JLabel("Product", SwingConstants.RIGHT);
//		JLabel quantityLabel = new JLabel("Quantity", SwingConstants.RIGHT);
//		JTextField customerField = new JTextField();
//		JTextField productField = new JTextField();
//		JTextField quantityField = new JTextField();
//		JTextField summaryField = new JTextField();
//		JButton addButton = new JButton("Add");
//		JTable table = new JTable();
//		JScrollPane scrollPane = new JScrollPane(table);
//		
//		c.gridx = 0;
//		c.gridy = 0;
//		c.anchor = GridBagConstraints.EAST;
//		c.insets = new Insets(0,0, 30, 10);
//		panel.add(customerLabel,c);
//		c.gridx = 1;
//		c.ipadx = 100;
//		c.anchor = GridBagConstraints.BASELINE;
//		panel.add(customerField,c);
//		c.gridx = 0;
//		c.gridy = 1;
//		c.insets = new Insets(0,0,5,10);
//		panel.add(productLabel,c);
//		c.gridx = 1;
//		panel.add(productField,c);
//		c.gridy = 2;
//		c.gridx = 0;
//		panel.add(quantityLabel,c);
//		c.gridx = 1;
//		panel.add(quantityField,c);
//		c.gridy = 3;
//		c.gridx = 1;
//		panel.add(summaryField,c);
//		c.gridx = 0;
//		c.ipadx = 0;
//		panel.add(addButton,c);
	}
}
