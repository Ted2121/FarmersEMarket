package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public abstract class TablePanel extends JPanel{
	private JTable table;
	private JButton newButton;
	private JButton deleteButton;
	private JButton editButton;
	private PopupWindow relatedPopupWindow;
	
	public TablePanel() {
		setBackground(ProgramFrame.getBgcolor());
		setLayout(new BorderLayout());
		add(new MenuSelectorPanel(), BorderLayout.NORTH);
		table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		add(scrollPane, BorderLayout.CENTER);
		JPanel eastPadding = new JPanel();
		eastPadding.setBackground(ProgramFrame.getBgcolor());
		eastPadding.setPreferredSize(new Dimension(50, 600));
		JPanel westPadding = new JPanel();
		westPadding.setBackground(ProgramFrame.getBgcolor());
		westPadding.setPreferredSize(new Dimension(50, 600));
		add(eastPadding, BorderLayout.EAST);
		add(westPadding, BorderLayout.WEST);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground(ProgramFrame.getBgcolor());
		buttonsPanel.setPreferredSize(new Dimension(800, 50));
		buttonsPanel.setLayout(new FlowLayout());
		newButton = new JButton();
		deleteButton = new JButton("Delete");
		editButton = new JButton("Edit");
		JButton backButton = new JButton("Back");
		buttonsPanel.add(newButton);
		buttonsPanel.add(deleteButton);
		buttonsPanel.add(editButton);
		buttonsPanel.add(backButton);
		add(buttonsPanel, BorderLayout.SOUTH);
		
		deleteButton.setForeground(new Color(0xff0000));
		backButton.addActionListener(e -> {
			ProgramFrame.changePanel(new HomePanel());
		});
	}
	
	public JTable getTable() {
		return table;
	}
	
	public JButton getNewButton() {
		return newButton;
	}
	
	public JButton getEditButton() {
		return editButton;
	}
	
	public JButton getDeleteButton() {
		return deleteButton;
	}
}
