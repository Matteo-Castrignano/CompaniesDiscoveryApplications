package API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

public class prova {

   public static void main(String[] args) throws MalformedURLException {
       /**Prova download dati*/
       URL url = new URL("https://financialmodelingprep.com/api/v3/search?query=AA&limit=10&exchange=NASDAQ&apikey=demo");
        URL urlSymbol = new URL("https://financialmodelingprep.com/api/v3/financial-statement-symbol-lists?apikey=b0c73ab87858bbe3a09d0a0ddcd52529");
       try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlSymbol.openStream(), "UTF-8"))) {
           for (int i = 0; i < 1; i++/*String line; (line = reader.readLine()) != null;*/) {
               String line = reader.readLine();
               String[] symbol = line.split(",");

               for(int j = 0; j < symbol.length; j++)
                   if(!symbol[j].contains(".HK"))
                    System.out.println(symbol[j]);
           }
       } catch (IOException e) {
           e.printStackTrace();
       }

   }

   }


