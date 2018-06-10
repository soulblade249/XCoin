package com.XCoin.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.XCoin.Core.Wallet;

public class TransactionUtil {

	public static int getIndex(byte[] data, Byte find, int prevIndex) {
		for(int a = prevIndex; a < data.length; a++) {
			Byte b = data[a];
			if(b.equals(find)) {
				return a;
			}
		}
		return 0;
	}

	public static boolean hasBalance(Wallet w, String code) {
		try {
			HashMap<String, Long> walletBal = w.getBal();
			for(String currentKey : walletBal.keySet()) {
				if(code.equals(currentKey)) {
					return true;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean hasAmount(Wallet w, String code, long amount) {
		try {
			HashMap<String, Long> walletBal = w.getBal();
			for(int a = 0; a < walletBal.size(); a++) {
				if(code.equals(walletBal.keySet().toArray()[a])) {
					if(walletBal.get(walletBal.keySet().toArray()[a]) > amount) {
						return true;
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
