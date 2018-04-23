package com.XCoin.GUI;
//Imports
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

import com.XCoin.Core.BlockChain;
import com.XCoin.Core.cli.Main;

import java.awt.event.*;
import java.awt.*;

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
                //Scene scene = new Scene(grid, 300, 275);
               //primaryStage.setScene(scene);
               /// scene.getStylesheets().add
               //(Login.class.getResource("Login.css").toExternalForm());
               // primaryStage.show();
		try { //Using Nimbus for a cleaner ui
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}

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
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
			Main.help();
		}
	}
}


