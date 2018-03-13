import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Test extends JFrame implements ActionListener{
	
	static JPanel panel = new JPanel();
	static JButton button = new JButton("Hello");
	static JButton yetAnotherButton = new JButton("Cool!");
	static JTextField field = new JTextField("Test");
	static JTextArea area = new JTextArea("How \n are \n you");
	
	public static void main(String args[]) {
		new Test(); 
	}
	
	public Test() {
		//Create Java Swing Window
		super("Basic Swing Test");
		
		setSize(400, 300); // Size of Window
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
	
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if(src.equals(button)) {
			panel.add(field);
			panel.add(yetAnotherButton);
			System.out.println("Test");
		}else if(src.equals(yetAnotherButton)) {
			panel.add(area);
		}
		
		setVisible(true);
	}
}