package com.XCoin.GUI;
//Imports
import javax.swing.*;

import com.XCoin.Core.BlockChain;

import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.io.*;

public class MinerGui extends JFrame implements ActionListener{

	private final JButton startMine;
	private final JButton stopMine;
	private final JTextArea loggerOutput;
	private final JScrollPane pane;
	public static Thread mining = new Thread();
	private BlockChain bc = new BlockChain();

	/**
	 * Default Constructor to start the gui program
	 */
	public MinerGui() {
		super("XCoin Miner G.U.I");

		//Create text area with 5 rows, 30 col.
		loggerOutput = new JTextArea(5,30);
		//Make it scrollable
		pane = new JScrollPane(loggerOutput);
		//Custom Size(T.B.C)
		pane.setPreferredSize(new Dimension(900, 900));
		//Have a scroll bar
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		//Line Wrapping
		loggerOutput.setLineWrap(true);
		//Make it non-editable
		loggerOutput.setEditable(false);

		//Set up the buttons
		JPanel button = new JPanel();
		startMine = new JButton("Start Miner");
		startMine.addActionListener(this);
		stopMine = new JButton("Stop Miner");
		stopMine.addActionListener(this);
		button.add(startMine);
		button.add(stopMine);

		this.setLayout(new FlowLayout());
		this.add(pane, SwingConstants.CENTER);
		this.add(button, SwingConstants.CENTER);

		this.setSize(1000,1000);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);

	}

	public void displayText(String todisplay) {
		loggerOutput.append(todisplay + "\n");
		loggerOutput.setCaretPosition(loggerOutput.getDocument().getLength());	
	}


	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource().equals(startMine)) {
			mining.start();
		}else {
			bc.bMining = false;
			displayText("Stopped Miner");
			System.out.println("Stopped Mining");
		}
	}
}


