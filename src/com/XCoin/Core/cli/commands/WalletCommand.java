/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.XCoin.Core.cli.commands;

import com.XCoin.Core.Wallet;
import com.XCoin.Core.cli.Main;
import com.XCoin.Util.KeyUtil;
import java.security.GeneralSecurityException;
import java.security.interfaces.ECPrivateKey;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author student
 */
public class WalletCommand implements Command{

    private Wallet userWallet;
    
    @Override
    public String getHelp() {
        return "cmd: wallet \n" +
				"- description: A tool for creating a wallet \n" +
				"- usage: key-util param [situational...] \n"+
				"- param: 'create' [-private],'info', '-help', '-params' \n"+
				"------------------------------------------------------------------------";
    }

    @Override
    public String[] getParams() {
        return new String[]{ "-help", "private", "create", "info", "-get"};
    }

    @Override
    public void run(String[] args) {
        if(!Arrays.asList(getParams()).contains(args[0]) ){
            System.out.println("- " + "ERROR ! unknown parameters...");
            System.out.println("- " + Arrays.toString(getParams()));
        }
        
        if(args[0].equals("create")) {
            if(args.length > 2 && args[1].equals("-private")) {
                 userWallet = new Wallet();
            }
            
            try {
                userWallet = new Wallet(KeyUtil.stringToPrivateKey(args[2]));
            } catch (GeneralSecurityException e) {
               e.printStackTrace();
            }
            Main.wallets.put(userWallet, userWallet.getAdress());
        }else if(args[0].equals("-help")) {
            System.out.println("- " + getHelp());
        }else {
            System.out.println("Not Supported");
        }
    }
    
}
