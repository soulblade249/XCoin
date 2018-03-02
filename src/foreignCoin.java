import java.util.*;
import java.io.*;

public class foreignCoin {

	public static ArrayList<String> currencyList = new ArrayList<String>();
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
			for(int a = 0; in.hasNextLine(); a++) {
				currencyList.add(a, c1.getCurrencyCode());
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
