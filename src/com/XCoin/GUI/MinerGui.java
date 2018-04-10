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

public class MinerGui extends JFrame implements ActionListener{

	private JButton startMine;
	private JButton stopMine;
	private JTextArea loggerOutput;
	private JPanel middlePanel;
	private JScrollPane pane;

	/**
	 * Default Constructor to start the gui program
	 */
	public MinerGui() {
		super("XCoin Miner G.U.I"); // Calls the JFrame class to create a new frame with title
		JPanel panel = new JPanel(new GridBagLayout()); // Creates  a new panel with a layout
		GridBagConstraints constraints = new GridBagConstraints(); //Creates the layout

		constraints.fill = GridBagConstraints.HORIZONTAL; // Horizontal Layout

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
		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(button, BorderLayout.PAGE_END);
		pack(); // Pack the frame
		setResizable(true);
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource().equals(startMine)) {
			System.out.println("Start Mining");
			BufferedReader f = null;
			try {
				f = new BufferedReader(new FileReader("MinerTestFile.in"));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}                                   
			Scanner input = new Scanner(f);
			while(input.hasNextLine()) {
				String output = input.nextLine();
				loggerOutput.append(output + "\n");
				loggerOutput.setCaretPosition(loggerOutput.getDocument().getLength());
				this.setLayout(new FlowLayout());
			}
		}else {
			System.out.println("Stop Mining");
			loggerOutput.setText("");
		}
	}
}


