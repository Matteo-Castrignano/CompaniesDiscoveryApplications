package API;


import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class listaSimboli {

    private static final String key = "b0c73ab87858bbe3a09d0a0ddcd52529";
    private static final String urlDowjones = "https://financialmodelingprep.com/api/v3/historical/dowjones_constituent?apikey=";
    private static final String urlNasdaq = "https://financialmodelingprep.com/api/v3/nasdaq_constituent?apikey=";
    private static final String urlCompany = "https://financialmodelingprep.com/api/v3/profile/";

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

        //System.out.println(listSymbols);
        //System.out.println(listSymbols.size());

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

        //System.out.println(listSymbols);
        //System.out.println(listSymbols.size());

        return listSymbols;
    }

    public static void companyInformations() throws MalformedURLException {

        List<String> ListSymbols = new ArrayList<>();
        ListSymbols.addAll(Dowjones_list());
        ListSymbols.addAll(Nasdaq_list());
        List<String> attributes = new ArrayList<>();
        String json = "";

        //for(int i=0; i< ListSymbols.size(); i++)
        for(int i=0; i< 2; i++) {

            URL url = new URL(urlCompany + ListSymbols.get(i) + "?apikey=" + key);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {

                for (String line; (line = reader.readLine()) != null; ) {
                    json += line;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            JSONArray arr = new JSONArray(json);

            String companyName = arr.getJSONObject(0).getString("companyName");
            String symbol = arr.getJSONObject(0).getString("symbol");
            String exchangeShortName = arr.getJSONObject(0).getString("exchangeShortName");
            String sector = arr.getJSONObject(0).getString("sector");
            String website = arr.getJSONObject(0).getString("website");
            String ceo = arr.getJSONObject(0).getString("ceo");
            String fullTimeEmployees = arr.getJSONObject(0).getString("fullTimeEmployees");
            String phone = arr.getJSONObject(0).getString("phone");
            String address = arr.getJSONObject(0).getString("address");
            String city = arr.getJSONObject(0).getString("city");
            String state = arr.getJSONObject(0).getString("state");
            String ipoDate = arr.getJSONObject(0).getString("ipoDate");

            attributes.add(companyName);
            attributes.add(symbol);
            attributes.add(exchangeShortName);
            attributes.add(sector);
            attributes.add(website);
            attributes.add(ceo);
            attributes.add(fullTimeEmployees);
            attributes.add(phone);
            attributes.add(address);
            attributes.add(city);
            attributes.add(state);
            attributes.add(ipoDate);

            System.out.println(attributes);

            }

    }


    public static void main(String[] args) throws MalformedURLException {
        companyInformations();
    }

}
