package API;

import Entities.Companies;
import Entities.Summary;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bson.json.JsonReader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class NestedDocuments {
    private static final Type COMP_TYPE = new TypeToken<List<Companies>>(){}.getType();

    public static void main(String[] args) throws IOException {
        /*Gson gson = new Gson();
        //JsonReader reader = new JsonReader((new FileReader("./Companies.json")));
        //String ciao = String.valueOf(reader);
        String text = new String(Files.readAllBytes(Paths.get("./Companies.json")), StandardCharsets.UTF_8);

        //System.out.println(text);
        Companies[] comp = gson.fromJson(text, Companies[].class);

        List<Companies> asList = Arrays.asList(comp);

        String text2 = new String(Files.readAllBytes(Paths.get("./Summary_mc1.json")), StandardCharsets.UTF_8);
        Summary[] summ = gson.fromJson(text2, Summary[].class);

        List<Summary> asListSummary = Arrays.asList(summ);
        ObjectMapper mapper = new ObjectMapper();
        //asList.get(0).setSummaryOfWeek(new ArrayList<>());
        //asList.get(0).getSummaryOfWeek().add(asListSummary.get(0));

        for(int i = 0; i < asList.size(); i++){
            if(asList.get(i).getSummaryOfWeek() == null)
                asList.get(i).setSummaryOfWeek(new ArrayList<>());

            for(Summary s: asListSummary){
                if(asList.get(i).getSymbol().equals(s.getSymbol())){
                    Summary sm = new Summary();
                    sm.setAvgVolume(s.getAvgVolume());
                    sm.setClose(s.getClose());
                    sm.setDate(s.getDate());
                    sm.setDividend(s.getDividend());
                    sm.setEPS(s.getEPS());
                    sm.setMarketCap(s.getMarketCap());
                    sm.setOpen(s.getOpen());
                    sm.setPeRatio(s.getPeRatio());
                    sm.setTargetPrice(s.getTargetPrice());
                    sm.setVolume(s.getVolume());
                    asList.get(i).getSummaryOfWeek().add(sm);
                    break;
                }
            }


        }
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(asList);
        json.replace("symbol : null", "");
        //System.out.println(json);
        try (FileWriter file = new FileWriter("companiesPlusSummary.json")) {

            file.write(json);
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }*/


        String textCompanies = new String(Files.readAllBytes(Paths.get("./Companies.json")), StandardCharsets.UTF_8);
        String textSummary = new String(Files.readAllBytes(Paths.get("./Summary_mc1.json")), StandardCharsets.UTF_8);
        JSONArray listCompanies = new JSONArray(textCompanies);
        JSONArray listSummary = new JSONArray(textSummary);
        int spacesToIndentEachLevel = 2;
        for(int i = 0; i < listCompanies.length(); i++){
            for(int j = 0; j < listSummary.length(); j++){
                if(listCompanies.getJSONObject(i).getString("symbol").equals(listSummary.getJSONObject(j).getString("symbol"))){

                    JSONObject summary = new JSONObject();
                    summary.put("date", listSummary.getJSONObject(j).getString("date"));
                    summary.put("close", listSummary.getJSONObject(j).getFloat("close"));
                    summary.put("open", listSummary.getJSONObject(j).getFloat("open"));
                    summary.put("volume", listSummary.getJSONObject(j).getFloat("volume"));
                    summary.put("avgVolume", listSummary.getJSONObject(j).getFloat("avgVolume"));
                    summary.put("marketCap", listSummary.getJSONObject(j).getFloat("marketCap"));
                    summary.put("peRatio", listSummary.getJSONObject(j).getFloat("peRatio"));
                    summary.put("EPS", listSummary.getJSONObject(j).getFloat("EPS"));
                    summary.put("dividend", listSummary.getJSONObject(j).getString("dividend"));
                    summary.put("targetPrice", listSummary.getJSONObject(j).getFloat("targetPrice"));
                    //boolean c = listCompanies.getJSONObject(i).getJSONArray("summaryOfWeek").isEmpty();
                    //Collection<JSONObject> items = new ArrayList<JSONObject>();
                    //items.add(summary);
                    JSONArray summ = new JSONArray();
                    summ.put(summary);
                    summ.put(summary);
                    listCompanies.getJSONObject(i).put("summaryOfWeek", new JSONArray(summ));
                    //System.out.println(i);
                    //System.out.println(summary.toString(spacesToIndentEachLevel));
                    break;
                }
            }
        }

        System.out.println(listCompanies.toString(spacesToIndentEachLevel));


    }
}
