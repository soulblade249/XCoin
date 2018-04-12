package com.XCoin.GUI;
//Imports
import javax.swing.*;
import com.XCoin.Core.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.*;
import java.io.*;


public class WalletGui extends JFrame implements ActionListener{

	private JTabbedPane walletPaneTabs;
	private Wallet userWallet = new Wallet();
	private JTextArea stuff;
	private JButton sendTransaction;

	/**
	 * Default Constructor to start the gui program
	 */
	public WalletGui() {
		super("XCoin Wallet G.U.I");
		walletPaneTabs = new JTabbedPane();
		walletPaneTabs.addTab("Send Money", createPanel("money"));
		walletPaneTabs.addTab("Test", createPanel("Test"));
		walletPaneTabs.addTab("Test2", createPanel("Test2"));
		walletPaneTabs.addTab("Test3", createPanel("Test3"));
		walletPaneTabs.addTab("Test4", createPanel("Test4"));
		walletPaneTabs.addTab("Test5", createPanel("Test5"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000,1000);
		this.add(walletPaneTabs);
		setResizable(false);
		setVisible(true);
		
	}
	
	private JPanel createPanel(String type) {
		JPanel panel = new JPanel();
		switch(type) {
		case "money":
			stuff = new JTextArea(2, 50);
			stuff.setEditable(false);
			stuff.append("Your Public Key: " + userWallet.getPublic());
			stuff.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			sendTransaction = new JButton("Send Transaction");
			sendTransaction.addActionListener(this);
			panel.add(stuff);
			break;
		case "userInfo":
			break;
		}
		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals("sendTransaction")) {
			JPanel pain = new JPanel();
		}
		
	}

}
