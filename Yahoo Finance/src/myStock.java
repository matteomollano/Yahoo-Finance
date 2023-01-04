import java.io.*;
import java.math.*;
import java.util.*;
import yahoofinance.*;
import java.util.Map.Entry;
import java.util.Comparator;

public class myStock {

	// TODO: declare the data structures that store the stock info as the database
	// here
	// HINT: you may consider to use HashMap which provides fast retrieval
	// it can be declared as: HashMap<String, stockInfo>
	// HINT: you may also consider to use TreeSet which allows key-value pairs to be
	// sorted by value
	// it can be declared as: TreeSet<Map.Entry<String, stockInfo>> so that we can
	// easily return top k stocks with type of Map.Entry<String, stockInfo>
	// HINT: you're allowed to use more than one data structures, each of which
	// holds the same data and serve for different purposes.

	// This is the nested class provided for you to store the information associated
	// with a stock symbol
	Map<String, stockInfo> database;

	private static class stockInfo {
		private String name;
		private BigDecimal price;

		public stockInfo(String nameIn, BigDecimal priceIn) {
			name = nameIn;
			price = priceIn;
		}

		public String toString() {
			StringBuilder stockInfoString = new StringBuilder("");
			stockInfoString.append(name + " " + price.toString());
			return stockInfoString.toString();
		}
	}

	public myStock() {
		// TODO: implement the constructor to create the database
		// HINT: you need to create the data structures used for the database here,
		// and override the data structure's compare method if needed
		// such that the stocks would be sorted by price in the data structure
		database = new HashMap<String, stockInfo>();
	}
			
	
	public void insertOrUpdate(String symbol, stockInfo stock) {
		// TODO: implement this method which is used to initialize and update the
		// database
		// HINT: make sure the time complexity is at least O(log(n))
		// HINT: if you use multiple data structures, make sure all of them are updated
		database.put(symbol, stock);
	}

	public stockInfo get(String symbol) {
		// TODO: implement this method to quickly retrieve record from database
		// HINT: time complexity should be O(1) constant time
		return database.get(symbol);
	}

	public List<Map.Entry<String, stockInfo>> top(int k) {
		// TODO: implement this method to return the stock records with top k prices.
		// HINT: this retrieval should be done in O(k)
		// HINT: you can use Iterator to retrieve items in the sorted order. For example,
		// if you use TreeSet,
		// the Iterator can be created like:
		// set = new TreeSet<Map.Entry<String, stockInfo>>;
		// Iterator<Map.Entry<String, stockInfo>> setIterator = set.iterator();
		// more usages of iterator can be learned from:
		// https://www.geeksforgeeks.org/treeset-iterator-method-in-java/
		List<Map.Entry<String, stockInfo>> database_array = new ArrayList<Map.Entry<String, stockInfo>>();
		List<Map.Entry<String, stockInfo>> TopTenStocks = new ArrayList<Map.Entry<String, stockInfo>>();
		
		for (Map.Entry<String, stockInfo> e: database.entrySet()) {
			database_array.add(e); // adds all of the key-value pairs into an array
		}
		
		Comparator<Map.Entry<String, stockInfo>> finance_comparator = new Comparator<Map.Entry<String, stockInfo>>(){

			@Override
			public int compare(Entry<String, myStock.stockInfo> object1, Entry<String, myStock.stockInfo> object2) {
				// TODO Auto-generated method stub
				BigDecimal firstStockPrice = object1.getValue().price;
				BigDecimal secondStockPrice = object2.getValue().price;
				return firstStockPrice.compareTo(secondStockPrice);
			}
		};
		
		Collections.sort(database_array, finance_comparator); // sort the database_array in ascending order first
		Collections.reverse(database_array); // then reverse the database_array so that you can retrieve the companies with the greatest stock prices
		
		// add the top 10 highest stocks from reversed array to a new List
		int count = 0;
		for(Map.Entry<String, stockInfo> e: database_array) {
			if (count >= k) {
				return TopTenStocks;
			}
			else {
				TopTenStocks.add(e);
				count++;
			}
		}
		return TopTenStocks;
	}

	public static void main(String[] args) throws IOException {

		// test the database creation based on the input file
		myStock techStock = new myStock();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("/Users/matteomollano/Downloads/Starter code/data/US-Tech-Symbols.txt"));
			String line = reader.readLine();
			while (line != null) {
				String[] var = line.split(":");

				// YahooFinance API is used
				// make sure the library files are included in the project build path
				Stock stock = null;
				try {
					stock = YahooFinance.get(var[0]);
				} catch (IOException e) {
					System.out.println("do nothing and skip the invalid stock");
				}

				// test the insertOrUpdate operation
				// here we are initializing the database
				if (stock != null && stock.getQuote().getPrice() != null) {
					techStock.insertOrUpdate(var[0], new stockInfo(var[1], stock.getQuote().getPrice()));
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int i = 1;
		System.out.println("===========Top 10 stocks===========");

		// test the top operation
		for (Map.Entry<String, stockInfo> element : techStock.top(10)) {
			System.out.println("[" + i + "]" + element.getKey() + " " + element.getValue());
			i++;
		}

		// test the get operation
		System.out.println('\n' + "===========Stock info retrieval===========");
		System.out.println("VMW" + " " + techStock.get("VMW"));
		System.out.println("MSFT" + " "+ techStock.get("MSFT"));
		System.out.println("GOOG" + " " + techStock.get("GOOG"));
		System.out.println("GOOGL" + " " + techStock.get("GOOGL"));
	}
}
