package API;

import Entities.Companies;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class NestedDocuments {
    private static final Type COMP_TYPE = new TypeToken<List<Companies>>(){}.getType();

    public static void main(String[] args) throws IOException {

        String textCompanies = new String(Files.readAllBytes(Paths.get("C:/Users/matte/Desktop/Companies.json")), StandardCharsets.UTF_8);

        List <JSONArray> listSummary = new ArrayList<>();
        listSummary.add(new JSONArray(new String(Files.readAllBytes(Paths.get("C:/Users/matte/Desktop/Summary1_mc.json")), StandardCharsets.UTF_8)));
        listSummary.add(new JSONArray(new String(Files.readAllBytes(Paths.get("C:/Users/matte/Desktop/Summary2_mc.json")), StandardCharsets.UTF_8)));
        listSummary.add(new JSONArray(new String(Files.readAllBytes(Paths.get("C:/Users/matte/Desktop/Summary3_mc.json")), StandardCharsets.UTF_8)));

        FileWriter file = new FileWriter("C:/Users/matte/Desktop/Companies5.json");

        JSONArray listCompanies = new JSONArray(textCompanies);

        int spacesToIndentEachLevel = 2;

        for(int i = 0; i < listCompanies.length(); i++){

            JSONObject company = new JSONObject();
            company.put("Symbol",listCompanies.getJSONObject(i).getString("Symbol"));
            company.put("Name", listCompanies.getJSONObject(i).getString("Name"));
            company.put("Exchange", listCompanies.getJSONObject(i).getString("Exchange"));
            company.put("Sector", listCompanies.getJSONObject(i).getString("Sector"));
            company.put("Summary", new JSONArray());

        for(int k = 0; k < listSummary.size(); k++) {
            for (int j = 0; j < listSummary.get(k).length(); j++) {
                if (listCompanies.getJSONObject(i).getString("Symbol").equals(listSummary.get(k).getJSONObject(j).getString("Symbol"))) {

                    JSONObject summary = new JSONObject();
                    summary.put("Date", listSummary.get(k).getJSONObject(j).getString("Date"));
                    summary.put("Close", listSummary.get(k).getJSONObject(j).getFloat("Close"));
                    summary.put("Open", listSummary.get(k).getJSONObject(j).getFloat("Open"));
                    summary.put("Volume", listSummary.get(k).getJSONObject(j).getFloat("Volume"));
                    summary.put("Avg_Volume", listSummary.get(k).getJSONObject(j).getFloat("Avg_Volume"));
                    summary.put("Market_cap", listSummary.get(k).getJSONObject(j).getFloat("Market_cap"));
                    summary.put("PE_Ratio", listSummary.get(k).getJSONObject(j).getFloat("PE_Ratio"));
                    summary.put("EPS", listSummary.get(k).getJSONObject(j).getFloat("EPS"));
                    summary.put("Dividend", listSummary.get(k).getJSONObject(j).getString("Dividend"));
                    summary.put("Target_prize", listSummary.get(k).getJSONObject(j).getFloat("Target_prize"));

                    company.append("Summary", summary);

                    break;
                }
            }
        }

        file.write(company.toString(spacesToIndentEachLevel));
        file.flush();
        }

        file.close();
    }
}
