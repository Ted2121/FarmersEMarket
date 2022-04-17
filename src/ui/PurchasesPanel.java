package ui;

public class PurchasesPanel extends TablePanel{

	public PurchasesPanel() {
		ProgramFrame.getFrame().setTitle("Purchases");
		getNewButton().setText("New Purchase");
		getNewButton().addActionListener(e -> new PurchasesPopup());
	}
}
