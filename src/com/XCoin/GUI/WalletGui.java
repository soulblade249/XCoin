package com.XCoin.GUI;
//Imports
import javax.swing.*;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.*;
import java.io.*;


public class WalletGui extends JFrame implements ActionListener{

	private JButton startMine = new JButton("Start Miner");
	private JButton stopMine = new JButton("Stop Miner");
	private JTextArea  loggerOutput = new JTextArea();
	private JScrollPane pane = null;
	
	/**
	 * Default Constructor to start the gui program
	 */
	public WalletGui() {
		super("XCoin Miner G.U.I"); // Calls the JFrame class to create a new frame with title
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // Creates a dimension tool to center the frame on the screen
		setSize(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		pane = new JScrollPane(loggerOutput);
		getContentPane().add(pane);
		setVisible(true);
	}
	
	/**
	   * This method appends the data to the text area.
	   * @param data the miner information data
	   */
	  public void showInfo(String data) {
		  loggerOutput.append(data);
		  this.getContentPane().validate();
	  }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
