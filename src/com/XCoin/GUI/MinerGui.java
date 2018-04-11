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
	private static Thread mining;

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
		pane.setPreferredSize(new Dimension(400, 400));
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

		this.setSize(500,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);

	}

	protected void displayText(String todisplay) {
		loggerOutput.append(todisplay + "\n");
		loggerOutput.setCaretPosition(loggerOutput.getDocument().getLength());	
	}


	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource().equals(startMine)) {
			BlockChain bc = new BlockChain();
			mining = new Thread(new Runnable() {
	            public void run() {
	                try {
	                    bc.mine();
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        });
			mining.start();
		}else {
			mining.interrupt();
			displayText("Stopped Miner");
			System.out.println("Stopped Mining");
		}
	}
}


