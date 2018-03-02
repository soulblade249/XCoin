import java.util.*;
import java.io.*;

public class foreignCoin {

	public static HashMap<String, String> currencyList = new HashMap<String, String>(); // A list of all the currencies
	public static String coinId;
	public long valueToDollar;

	public foreignCoin(String c, long v) { 
		this.coinId = c;
		this.valueToDollar = v;
	}

	public static void setupCurrencies() throws IOException{
		BufferedReader g = new BufferedReader(new FileReader("currencyCodes.dat"));
		Scanner in = new Scanner(g);
		while(in.hasNextLine()) {
			String currencyCode = in.nextLine();
			Currency c1 = Currency.getInstance(currencyCode);
			currencyList.put(c1.getCurrencyCode(), c1.getDisplayName());
		}
	}
	
	public static int getCurrencyCode(String coin) {
		Currency c1 = Currency.getInstance(coin);
		return c1.getNumericCode();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
