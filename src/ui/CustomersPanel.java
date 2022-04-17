package ui;

public class CustomersPanel extends TablePanel{
	
	public CustomersPanel() {
		ProgramFrame.getFrame().setTitle("Customers");
		getNewButton().setText("New Customer");
		getNewButton().addActionListener(e -> new CustomersPopup());
	}
}
