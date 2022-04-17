package ui;

public class SalesPanel extends TablePanel{

	public SalesPanel() {
		ProgramFrame.getFrame().setTitle("Sales");
		getNewButton().setText("New Sale");
		getNewButton().addActionListener(e -> new SalesPopup());
	}
}
