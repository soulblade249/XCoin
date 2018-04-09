package com.XCoin.GUI;
//Imports
import javax.swing.*;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class MinerGui extends JFrame implements ActionListener{

	private JButton startMine = new JButton("Start Miner");
	private JButton stopMine = new JButton("Stop Miner");
	private JTextArea  loggerOutput = new JTextArea();
	private JPanel pane = null;
	private GridBagConstraints cs = null;

	/**
	 * Default Constructor to start the gui program
	 */
	public MinerGui() {
		super("XCoin Miner G.U.I"); // Calls the JFrame class to create a new frame with title
		pane = new JPanel(new GridBagLayout());
		cs.fill = GridBagConstraints.HORIZONTAL;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // Creates a dimension tool to center the frame on the screen
		setSize(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2); //Should Center Window
		pane.add(startMine);
		pane.add(stopMine);
		getContentPane().add(pane);
		loggerOutput.setEditable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	/**
	 * This method appends the data to the text area.
	 * @param data the miner prints out
	 */
	public void showInfo(String data) {
		loggerOutput.append(data);
		this.getContentPane().validate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
	
	public static void main(String[] args) {
		MinerGui gui = new MinerGui();
	}
}

