package API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * Prova download dati
 *
 * */
public class prova {
    private static final String key = "b0c73ab87858bbe3a09d0a0ddcd52529";
    private static final String urlSymbols = "https://financialmodelingprep.com/api/v3/financial-statement-symbol-lists?apikey=";
    private static final String url2 = "https://financialmodelingprep.com/api/v3/search?query=AA&limit=10&exchange=NASDAQ&apikey=demo";
    private static final String urlCompany = "https://financialmodelingprep.com/api/v3/profile/";

    public static void main(String[] args) throws MalformedURLException {

       List<String> listaSymbol = null;
       try {
           listaSymbol = retrieveListSymbol();
       }
       catch (MalformedURLException ex){
           ex.printStackTrace();
       }
       if(listaSymbol != null) {
           int i = 0;
           for (String s : listaSymbol) {
               //System.out.println(s);
               if (i > 3)
                   break;
               URL url = new URL(urlCompany+s+"?apikey="+key);
               try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
                   for (String line; (line = reader.readLine()) != null;) {
                       System.out.println(line);
                   }
               }catch (IOException ex){
                   ex.printStackTrace();
               }
               i++;
           }
       }
   }

   private static List<String> retrieveListSymbol() throws MalformedURLException {
       URL url = new URL(urlSymbols+key);
       List<String> listSymbols = new ArrayList<>();
       try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
           String line = reader.readLine();
           String[] symbol = line.split(",");
           for (int j = 0; j < symbol.length; j++)
               if (!symbol[j].contains(".HK") && !symbol[j].contains("n")) {
                   String symbolFiltered = symbol[j].replaceAll("\"", "");
                   if(symbolFiltered.contains("]")){
                       symbolFiltered = symbolFiltered.replace("]", "");
                   }
                   listSymbols.add(symbolFiltered);
               }

       } catch (IOException e) {
           e.printStackTrace();
       }

       return listSymbols;
   }

}


