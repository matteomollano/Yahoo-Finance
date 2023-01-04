# Yahoo-Finance

This is a java project that displays the current top stocks using the Yahoo Finance API.

In the src directory, you can find two files, myStock.java and myStock2.java. 
myStock2.java is the updated implementation. 

The data directory contains two text files, StockSymbols.txt and US-Tech-Symbols.txt
StockSymbols.txt is a smaller file with 20 companies, which can be used for testing.
US-Tech-Symbols.txt is the file that contains every company that can be accessed from the Yahoo API database.

This project uses a HashMap and TreeSet to store a company's name and its corresponding stock value, which is retrieved using the Yahoo Finance API.
It does this by reading each line of the US-Tech-Symbols.txt, and splitting the line of text into the company's symbol and full name 
(for example, MSFT and Microsoft Corporation). It then creates a stockInfo object containing the company's full name and its corresponding stock value
retrieved from the API. The company symbol and this new stockInfo object are added to the HashMap and TreeSet data structures as a key-value pair. This
process is repeated for each company in the US-Tech-Symbols.txt file.

To return the top stocks, we iterate through the TreeSet and return the top 'k' stocks, dependent upon how many stocks you would like to view. In this
project, we return the top 10 stocks.
