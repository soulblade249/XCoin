package com.XCoin.Util;

import java.util.ArrayList;
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
			for(Map.Entry<String, Long> b : w.getBal().entrySet()) {
				if(code.equals(b.getKey())) {
					return true;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
