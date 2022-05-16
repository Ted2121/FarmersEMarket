package ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ProvidersPopup extends PopupWindow{
	private JPanel panel;

	
	public ProvidersPopup() {
		setTitle("New Provider");
		panel = new JPanel();
		panel.setBackground(ProgramFrame.getBgcolor());
		super.getPanel().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JLabel idLabel = new JLabel("ID", SwingConstants.RIGHT);
		JLabel firstNameLabel = new JLabel("First Name", SwingConstants.RIGHT);
		JLabel lastNameLabel = new JLabel("Last Name", SwingConstants.RIGHT);
		JLabel countryLabel = new JLabel("Country", SwingConstants.RIGHT);
		JLabel cityLabel = new JLabel("City", SwingConstants.RIGHT);
		JTextField idField = new JTextField();
		JTextField firstNameField = new JTextField();
		JTextField lastNameField = new JTextField();
		JTextField countryField = new JTextField();
		JTextField cityField = new JTextField();
		
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(0,0,5,10);
		panel.add(idLabel,c);
		c.gridy = 1;
		panel.add(firstNameLabel,c);
		c.gridy = 2;
		panel.add(lastNameLabel,c);
		c.gridy = 3;
		panel.add(countryLabel,c);
		c.gridy = 4;
		panel.add(cityLabel,c);
		c.gridx = 1;
		c.gridy = 0;
		c.ipadx = 150;
		panel.add(idField,c);
		c.gridy = 1;
		panel.add(firstNameField,c);
		c.gridy = 2;
		panel.add(lastNameField,c);
		c.gridy = 3;
		panel.add(countryField,c);
		c.gridy = 4;
		panel.add(cityField,c);
	}
}
