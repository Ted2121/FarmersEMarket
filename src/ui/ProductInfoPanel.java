package ui;

public class ProductInfoPanel extends TablePanel{
	
	public ProductInfoPanel() {
		ProgramFrame.getFrame().setTitle("Products");
		getNewButton().setText("New Product");
		getNewButton().addActionListener(e -> new ProductInfoPopup());
	}
}
