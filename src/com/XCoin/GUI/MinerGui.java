package com.XCoin.GUI;
//Imports
import javax.swing.*;
import javax.swing.text.DefaultCaret;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class MinerGui extends JPanel implements ActionListener{

	private final JButton startMine;
	private final JButton stopMine;
	private final JTextArea loggerOutput;
	private final JScrollPane pane;

	/**
	 * Default Constructor to start the gui program
	 */
	public MinerGui() {
		super(new BorderLayout()); // Calls the JFrame class to create a new frame with title
		JPanel panel = new JPanel(new GridBagLayout()); // Creates  a new panel with a layout
		
		loggerOutput = new JTextArea(); //Set up the output log
		loggerOutput.setEditable(false); // No Editing output
		loggerOutput.setPreferredSize(new Dimension(400,400));
		pane = new JScrollPane(loggerOutput);
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel.add(pane);

		startMine = new JButton("Start Miner"); // Create new button for starting miner program
		startMine.addActionListener(this); //Adds a listener for a click of the button
		
		stopMine = new JButton("Stop Miner");// Create new button for stopping miner program
		stopMine.addActionListener(this);//Adds a listener for a click of the button
		
		JPanel button = new JPanel();
		button.add(startMine);
		button.add(stopMine);
		add(panel, BorderLayout.CENTER);
		add(button, BorderLayout.PAGE_END);
		setSize(500, 500);
	}
	
	protected void displayText(String todisplay) {
		//System.out.println("Displaying: " + todisplay);
		loggerOutput.append(todisplay + "\n");
		//System.out.println("Appended the string");
		loggerOutput.setCaretPosition(loggerOutput.getDocument().getLength());
		//System.out.println("Caret");
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
	
	public static void createAndShowGUI() {
		JFrame frame = new JFrame("XCoin");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(new MinerGui());
		
		frame.setResizable(true);
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e){
		BufferedReader f = null;
		Scanner input = null;
		try {
		f = new BufferedReader(new FileReader("MinerTestFile.in"));
		input = new Scanner(f);
		}catch(FileNotFoundException b) {
			b.printStackTrace();
		}
		if(e.getSource().equals(startMine)) {
			System.out.println("Start Mining");                                 
			while(input.hasNext()) {
				String output = input.next();
				System.out.println(output);
				displayText(output);
			}
		}else {
			System.out.println("Stop Mining");
			loggerOutput.setText("");
		}
	}
}


