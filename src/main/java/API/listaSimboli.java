package API;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class listaSimboli {

    private static final String key = "1b03f4651b64d5649bb16296b403b6f2";
    //private static final String key = "b0c73ab87858bbe3a09d0a0ddcd52529";
    private static final String urlDowjones = "https://financialmodelingprep.com/api/v3/historical/dowjones_constituent?apikey=";
    private static final String urlNasdaq = "https://financialmodelingprep.com/api/v3/nasdaq_constituent?apikey=";


    public static List<String> Dowjones_list() throws MalformedURLException {
        URL url = new URL(urlDowjones+key);
        List<String> listSymbols = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
            String json = "";
            for (String line; (line = reader.readLine()) != null;) {
                json+=line;
            }

            JSONArray arr = new JSONArray(json);

            for (int i = 0; i < arr.length(); i++) {
                String symbol = arr.getJSONObject(i).getString("symbol");
                listSymbols.add(symbol);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listSymbols;
    }

    public static List<String> Nasdaq_list() throws MalformedURLException {
        URL url = new URL(urlNasdaq+key);
        List<String> listSymbols = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
            String json = "";
            for (String line; (line = reader.readLine()) != null;) {
                json+=line;
            }

            JSONArray arr = new JSONArray(json);

            for (int i = 0; i < arr.length(); i++) {
                String symbol = arr.getJSONObject(i).getString("symbol");
                listSymbols.add(symbol);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listSymbols;
    }

    public static void main(String[] args) throws IOException {

        List<String> ListSymbols = new ArrayList<>();
        ListSymbols.addAll(Dowjones_list());
        ListSymbols.addAll(Nasdaq_list());

        FileWriter file = new FileWriter("C:/Users/matte/Desktop/ListaSimboli.txt");

        for(int i=0; i< ListSymbols.size(); i++ ){
            file.write(ListSymbols.get(i)+"\r\n");
            file.flush();
        }
        file.close();

    }

}
