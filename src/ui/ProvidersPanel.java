package ui;

public class ProvidersPanel extends TablePanel{
	
	public ProvidersPanel() {
		ProgramFrame.getFrame().setTitle("Providers");
		getNewButton().setText("New Provider");
		getNewButton().addActionListener(e -> new ProvidersPopup());
	}
}
