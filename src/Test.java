import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Test extends JFrame implements ActionListener{
	
	static JPanel panel = new JPanel();
	static JButton button = new JButton("Login");
	static JButton yetAnotherButton = new JButton("Register");
	static JTextField userField = new JTextField();
	static JTextField passField = new JTextField();
	
	public static void main(String args[]) {
		new Test(); 
	}
	
	public Test() {
		//Create Java Swing Window
		super("Basic Swing Test");
		
		setSize(600, 500); // Size of Window
		setResizable(true); // Can we resize it?
		
		//Add all the elements to the panel.
		panel.add(button);
		panel.setLayout(new GridLayout(10,1));
		{
		yetAnotherButton.addActionListener(this);
		button.addActionListener(this);
		}
		//panel.add(field);
		//panel.add(area);
		add(panel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src.equals(button)) {
			panel.add(userField);
			panel.add(passField);
			
		}else if(src.equals(yetAnotherButton)) {
			panel.add(area);
		}
		
		setVisible(true);
	}
}