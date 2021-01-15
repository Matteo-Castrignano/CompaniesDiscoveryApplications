package API;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static API.companyAttributes.readFileSimbol;

public class retriveQuotes {

    public void downloadQuotes() throws IOException {

        List<String> listSymbol = readFileSimbol();

    }

    public static void main(String[] args) throws IOException {

        Stock stock = YahooFinance.get("TSLA");

       stock.print();

    }

}
