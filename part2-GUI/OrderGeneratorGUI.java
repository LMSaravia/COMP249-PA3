package part2;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OrderGeneratorGUI extends JFrame {
	
	public static final int WIDTH = 500;
	public static final int HEIGHT = 225;
	public static final String DEFAULT_ID_TEXT = "Enter name here.";
	public static final String DEFAULT_PRICE_TEXT = "Enter price here.";
	public static final String DEFAULT_VOLUME_TEXT = "Enter volume here.";
	public static final String ERROR_TEXT = "Error: Enter price/volume as a number.";
	
	//private JFrame theWindow;
	private String newId;		// Not used but required.
	private double newPrice;
	private int newVolume;		// number of shares
	private boolean submitted;
	
	private JTextField priceField;
	private JTextField volumeField;
	private JTextField nameField;
	private JButton submitButton;
	private JButton resetButton;
	private JLabel statusLabel;
	
	private JPanel upperPanel;
	private JPanel lowerPanel;
	private JPanel lowerUpperPanel;
	private JPanel lowerCenterPanel;
	private JPanel lowerLowerPanel;
	private JPanel buttonPanel;

	public OrderGeneratorGUI() {
		
		super("Order Generator");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);				
		setLayout(new BorderLayout());
		newId = "";
		newPrice = 0.00;
		newVolume = 0;
		submitted = false;
		
		// Create widgets
		priceField = new JFormattedTextField(DEFAULT_PRICE_TEXT);
		volumeField = new JTextField(DEFAULT_VOLUME_TEXT);
		nameField = new JTextField(DEFAULT_ID_TEXT);
		submitButton = new JButton("Submit");
		submitButton.addActionListener(new GeneratorListener());
		resetButton = new JButton("Reset");
		resetButton.addActionListener(new GeneratorListener());
		statusLabel = new JLabel(printStatus()); 

		// Create Panels	
			// main upper panel
		upperPanel = new JPanel();
		GridLayout grid = new GridLayout(3,0);
		grid.setVgap(3);
		upperPanel.setLayout(grid);
		
			// main lower panel
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new GridLayout(3, 0));
//---------										
			// lower upper panel
		lowerUpperPanel = new JPanel();
		
			// lower center panel
		lowerCenterPanel = new JPanel();
		lowerCenterPanel.setBackground(Color.LIGHT_GRAY);
			// lower lower panel
		lowerLowerPanel = new JPanel();
//-----------
			// button panel
		buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.LIGHT_GRAY);		
		
		// Add widgets to a panel
		upperPanel.add(priceField);
		upperPanel.add(volumeField);
		upperPanel.add(nameField);
		buttonPanel.add(submitButton);
		buttonPanel.add(resetButton);
		lowerLowerPanel.add(statusLabel);

		// Add panels to panel or frame
		lowerCenterPanel.add(buttonPanel);
		
		lowerPanel.add(lowerUpperPanel);
		lowerPanel.add(lowerCenterPanel);
		lowerPanel.add(lowerLowerPanel);
		
		add(upperPanel, BorderLayout.NORTH);
		add(lowerPanel, BorderLayout.SOUTH);
	
//		pack();
		
	}

	private String printStatus() {
		
		String temp = "";
		
		if (submitted == true)
			temp = "Last Order: " + newVolume + " shares at $" + newPrice;
		else
			temp = "Last Order: none";
		
		return temp;
	}
	
	private class GeneratorListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			String buttonString = e.getActionCommand();
			double price = 0.00;
			int volume = 0;
			String name = "";
			
			if (buttonString.equals("Submit")) {	
				try {
					price = Double.parseDouble(priceField.getText());
					volume = Integer.parseInt(volumeField.getText());
					name = nameField.getText().trim();
				}
				catch (NumberFormatException ex) {
					statusLabel.setText(ERROR_TEXT);
					resetFields();
				}				
				if (price > 0 &&
						volume > 0 && volume > 0 &&
						name != null && !(name.equals(""))) {
				newId = name;
				newPrice = price;
				newVolume = volume;
				submitted = true;
				statusLabel.setText(printStatus());
				resetFields();
				}					
			}
			else {
				resetFields();
			}
		}

		private void resetFields() {
			priceField.setText(DEFAULT_PRICE_TEXT);
			volumeField.setText(DEFAULT_VOLUME_TEXT);
			nameField.setText(DEFAULT_ID_TEXT);
		}
	} // class GeneratorListener

}
