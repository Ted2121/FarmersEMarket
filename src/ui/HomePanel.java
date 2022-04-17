package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class HomePanel extends JPanel{
	
	public HomePanel() {
		ProgramFrame.getFrame().setTitle("Home");
		ProgramFrame.getFrame().setResizable(true);
		setBackground(ProgramFrame.getBgcolor());
		setLayout(new BorderLayout());
		add(new MenuSelectorPanel(), BorderLayout.NORTH);
		JPanel tablesPanel = new JPanel();
		tablesPanel.setBackground(new Color(0xdddddd));
		tablesPanel.setLayout(new GridLayout(1,2));
		SmallTablePanel leftTable = new SmallTablePanel(SmallTablePanel.LEFT, "Inbound Orders");
		leftTable.getNewButton().setText("New Purchase");
		SmallTablePanel rightTable = new SmallTablePanel(SmallTablePanel.RIGHT, "Outbound Orders");
		rightTable.getNewButton().setText("New Sale");
		tablesPanel.add(leftTable);
		tablesPanel.add(rightTable);
		leftTable.getJumpToTableButton().addActionListener(e -> ProgramFrame.changePanel(new PurchasesPanel()));
		leftTable.getNewButton().addActionListener(f -> new PurchasesPopup());
		rightTable.getJumpToTableButton().addActionListener(g -> ProgramFrame.changePanel(new SalesPanel()));
		rightTable.getNewButton().addActionListener(h -> new SalesPopup());
		add(tablesPanel, BorderLayout.CENTER);
	}
	
	private class SmallTablePanel extends JPanel{
		public static final int LEFT = 0;
		public static final int RIGHT = 1;
		private JTable table;
		private JButton jumpToTableButton;
		private JButton newButton;
		
		public SmallTablePanel(int buttonAlign, String tableName) {
			setLayout(new GridBagLayout());
			setBackground(ProgramFrame.getBgcolor());
			GridBagConstraints c = new GridBagConstraints();
			JLabel tableNameLabel = new JLabel(tableName);
			jumpToTableButton = new JButton("Show");
			JTable table = new JTable();
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setPreferredSize(new Dimension(300,400));
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			newButton = new JButton();
			
			c.gridx = 0;
			c.gridy = 0;
			add(tableNameLabel, c);
			
			c.gridx = 1;
			c.anchor = GridBagConstraints.EAST;
			add(jumpToTableButton, c);
			
			c.gridx = 0;
			c.gridy = 1;
			c.gridwidth = 2;
			add(scrollPane, c);
			
			c.gridx = buttonAlign;
			c.gridy = 2;
			c.gridwidth = 1;
			if(buttonAlign==0) c.anchor = GridBagConstraints.WEST;
			add(newButton, c);
		}

		public JTable getTable() {
			return table;
		}

		public JButton getJumpToTableButton() {
			return jumpToTableButton;
		}

		public JButton getNewButton() {
			return newButton;
		}
	}
}
