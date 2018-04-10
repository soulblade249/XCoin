package com.XCoin.GUI;
//Imports
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
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

	/**
	 * Default Constructor to start the gui program
	 */
	public MinerGui() {
		super("XCoin Miner G.U.I"); // Calls the JFrame class to create a new frame with title
		JPanel panel = new JPanel(new GridBagLayout()); // Creates  a new panel with a layout
		GridBagConstraints constraints = new GridBagConstraints(); //Creates the layout

		constraints.fill = GridBagConstraints.HORIZONTAL; // Horizontal Layout

		loggerOutput = new JTextArea(); //Set up the output log
		constraints.gridx = 0; //Center
		constraints.gridy = 0; //Center
		constraints.gridwidth = 1;
		loggerOutput.setEditable(false); // No Editing output
		panel.add(loggerOutput, constraints); // Add it to the panel

		startMine = new JButton("Start Miner"); // Create new button for starting miner program
		//constraints.gridx = 0; 
		//constraints.gridy = 1;
		///constraints.gridwidth = 2;
		//panel.add(startMine, constraints); // Add with coordinates
		startMine.addActionListener(this); //Adds a listener for a click of the button
		
		stopMine = new JButton("Stop Miner");// Create new button for stopping miner program
		////constraints.gridx = 0;
		//constraints.gridy = 2;
		//constraints.gridwidth = 2;
		//panel.add(stopMine, constraints);// Add with coordinates
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
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(startMine)) {
			System.out.println("Start Mining");
		}else {
			System.out.println("Stop Mining");
		}
	}
}


