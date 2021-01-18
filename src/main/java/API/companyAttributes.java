package API;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class companyAttributes {

    //private static final String key = "1b03f4651b64d5649bb16296b403b6f2";
    private static final String key = "b0c73ab87858bbe3a09d0a0ddcd52529";
    private static final String urlCompany = "https://financialmodelingprep.com/api/v3/profile/";

    public static void companyInformations(List<String> ListSymbols) throws IOException {

        FileWriter file = new FileWriter("C:/Users/matte/Desktop/Companies.json");
        String companyName;
        String symbol;
        String exchangeShortName;
        String sector;
        String website;
        String ceo;
        String fullTimeEmployees;
        String phone;
        String address;
        String city;
        String state;
        String ipoDate;

        for(int i=0; i<ListSymbols.size(); i++){

            String json = "";
            URL url = new URL(urlCompany + ListSymbols.get(i) + "?apikey=" + key);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {

                for (String line; (line = reader.readLine()) != null; ) {
                    json += line;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            JSONArray arr = new JSONArray(json);
            /*
            if(!arr.isEmpty() && !arr.getJSONObject(0).isNull("companyName") && !arr.getJSONObject(0).isNull("symbol") && !arr.getJSONObject(0).isNull("exchangeShortName")
                    && !arr.getJSONObject(0).isNull("sector") && !arr.getJSONObject(0).isNull("website") && !arr.getJSONObject(0).isNull("ceo")
                    && !arr.getJSONObject(0).isNull("fullTimeEmployees") && !arr.getJSONObject(0).isNull("phone") && !arr.getJSONObject(0).isNull("address")
                    && !arr.getJSONObject(0).isNull("city") && !arr.getJSONObject(0).isNull("state") && !arr.getJSONObject(0).isNull("ipoDate")) {
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
                */

            if(!arr.isEmpty()) {

                if(!arr.getJSONObject(0).isNull("companyName"))
                    companyName = arr.getJSONObject(0).getString("companyName");
                else
                    companyName = "";

                if(!arr.getJSONObject(0).isNull("symbol"))
                    symbol = arr.getJSONObject(0).getString("symbol");
                else
                    symbol = "";

                if(!arr.getJSONObject(0).isNull("exchangeShortName"))
                    exchangeShortName = arr.getJSONObject(0).getString("exchangeShortName");
                else
                    exchangeShortName ="";

                if(!arr.getJSONObject(0).isNull("sector"))
                    sector = arr.getJSONObject(0).getString("sector");
                else
                    sector = "";

                if(!arr.getJSONObject(0).isNull("website"))
                    website = arr.getJSONObject(0).getString("website");
                else
                    website = "";

                if(!arr.getJSONObject(0).isNull("ceo"))
                    ceo = arr.getJSONObject(0).getString("ceo");
                else
                    ceo = "";

                if(!arr.getJSONObject(0).isNull("fullTimeEmployees"))
                    fullTimeEmployees = arr.getJSONObject(0).getString("fullTimeEmployees");
                else
                    fullTimeEmployees = "";

                if(!arr.getJSONObject(0).isNull("phone"))
                    phone = arr.getJSONObject(0).getString("phone");
                else
                    phone = "";

                if(!arr.getJSONObject(0).isNull("address"))
                    address = arr.getJSONObject(0).getString("address");
                else
                    address = "";

                if(!arr.getJSONObject(0).isNull("city"))
                    city = arr.getJSONObject(0).getString("city");
                else
                    city = "";

                if(!arr.getJSONObject(0).isNull("state"))
                    state = arr.getJSONObject(0).getString("state");
                else
                    state = "";

                if(!arr.getJSONObject(0).isNull("ipoDate"))
                    ipoDate = arr.getJSONObject(0).getString("ipoDate");
                else
                    ipoDate = "";


                JSONObject jo = new JSONObject();

                jo.put("companyName", companyName);
                jo.put("symbol", symbol);
                jo.put("exchangeShortName", exchangeShortName);
                jo.put("sector", sector);
                jo.put("website", website);
                jo.put("ceo", ceo);
                jo.put("fullTimeEmployees", fullTimeEmployees);
                jo.put("phone", phone);
                jo.put("address", address);
                jo.put("city", city);
                jo.put("state", state);
                jo.put("ipoDate", ipoDate);

                file.write(jo.toString());
                if (i < ListSymbols.size() - 1)
                    file.write(",\r\n");

                file.flush();
            }

            else
                System.out.println(ListSymbols.get(i));

        }
        file.close();

    }

    public static List<String> readFileSimbol() throws IOException {

        List<String> listSymbols = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("C:/Users/matte/Desktop/ListaSimboli.txt"));

        for (String line; (line = reader.readLine()) != null;) {
           listSymbols.add(line);
        }

        return listSymbols;
    }

    public static void main(String[] args) throws IOException {
       companyInformations(readFileSimbol());
    }

}
