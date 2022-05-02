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
	}
}
