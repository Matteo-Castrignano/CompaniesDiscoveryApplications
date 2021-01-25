package API;

import Entities.Companies;
import Entities.Summary;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bson.json.JsonReader;

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
import java.util.List;

public class NestedDocuments {
    private static final Type COMP_TYPE = new TypeToken<List<Companies>>(){}.getType();

    public static void main(String[] args) throws IOException {
        Gson gson = new Gson();
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
                    asList.get(i).getSummaryOfWeek().add(s);
                    break;
                }
            }


        }
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(asList);
        //System.out.println(json);
        try (FileWriter file = new FileWriter("companiesPlusSummary.json")) {

            file.write(json);
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
