package API;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class NestedDocuments {

    public static void main(String[] args) throws IOException {

        String textCompanies = new String(Files.readAllBytes(Paths.get("C:/Users/matte/Desktop/Companies.json")), StandardCharsets.UTF_8);

        List <JSONArray> listSummary = new ArrayList<>();
        listSummary.add(new JSONArray(new String(Files.readAllBytes(Paths.get("C:/Users/matte/Desktop/Summary1_mc.json")), StandardCharsets.UTF_8)));
        listSummary.add(new JSONArray(new String(Files.readAllBytes(Paths.get("C:/Users/matte/Desktop/Summary2_mc.json")), StandardCharsets.UTF_8)));
        listSummary.add(new JSONArray(new String(Files.readAllBytes(Paths.get("C:/Users/matte/Desktop/Summary3_mc.json")), StandardCharsets.UTF_8)));
        listSummary.add(new JSONArray(new String(Files.readAllBytes(Paths.get("C:/Users/matte/Desktop/Summary4_mc.json")), StandardCharsets.UTF_8)));
        listSummary.add(new JSONArray(new String(Files.readAllBytes(Paths.get("C:/Users/matte/Desktop/Summary5_mc.json")), StandardCharsets.UTF_8)));

        FileWriter file = new FileWriter("C:/Users/matte/Desktop/Companies_All.json");

        JSONArray listCompanies = new JSONArray(textCompanies);

        int spacesToIndentEachLevel = 2;

        for(int i = 0; i < listCompanies.length(); i++){

            JSONObject company = new JSONObject();
            company.put("Symbol",listCompanies.getJSONObject(i).getString("Symbol"));
            company.put("Name", listCompanies.getJSONObject(i).getString("Name"));
            company.put("Exchange", listCompanies.getJSONObject(i).getString("Exchange"));
            company.put("Sector", listCompanies.getJSONObject(i).getString("Sector"));
            company.put("Summary", new JSONArray());

            for (JSONArray objects : listSummary) {
                for (int j = 0; j < objects.length(); j++) {
                    if (listCompanies.getJSONObject(i).getString("Symbol").equals(objects.getJSONObject(j).getString("Symbol"))) {

                        JSONObject summary = new JSONObject();
                        summary.put("Date", objects.getJSONObject(j).getString("Date"));
                        summary.put("Close", objects.getJSONObject(j).getFloat("Close"));
                        summary.put("Open", objects.getJSONObject(j).getFloat("Open"));
                        summary.put("Volume", objects.getJSONObject(j).getFloat("Volume"));
                        summary.put("Avg_Volume", objects.getJSONObject(j).getFloat("Avg_Volume"));
                        summary.put("Market_cap", objects.getJSONObject(j).getFloat("Market_cap"));
                        summary.put("PE_Ratio", objects.getJSONObject(j).getFloat("PE_Ratio"));
                        summary.put("EPS", objects.getJSONObject(j).getFloat("EPS"));
                        summary.put("Dividend", objects.getJSONObject(j).getString("Dividend"));
                        summary.put("Target_price", objects.getJSONObject(j).getFloat("Target_price"));

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
