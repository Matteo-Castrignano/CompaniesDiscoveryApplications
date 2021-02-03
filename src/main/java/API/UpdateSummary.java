package API;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.json.JSONArray;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static com.mongodb.client.model.Filters.eq;

public class UpdateSummary {

    public static void main(String[] args) throws IOException {

        MongoClient mongoClient = MongoClients.create("mongodb://172.16.3.150:27020/");

        MongoDatabase database = mongoClient.getDatabase("CompaniesApplication");

        MongoCollection<Document> collection = database.getCollection("companies");



        JSONArray toAddSummary = new JSONArray(new String(Files.readAllBytes(Paths.get("C:/Users/matte/Desktop/Summary6.json")), StandardCharsets.UTF_8));

        for(int i=0; i< toAddSummary.length(); i++)
        {
            String symbol =  toAddSummary.getJSONObject(i).getString("Symbol");
            Document summary = new Document("Market_cap", toAddSummary.getJSONObject(i).getFloat("Market_cap"))
                    .append("PE_Ratio", toAddSummary.getJSONObject(i).getFloat("PE_Ratio"))
                    .append("EPS", toAddSummary.getJSONObject(i).getFloat("EPS"))
                    .append("Dividend", toAddSummary.getJSONObject(i).getString("Dividend"))
                    .append("Target_price", toAddSummary.getJSONObject(i).getFloat("Target_price"))
                    .append("Open", toAddSummary.getJSONObject(i).getFloat("Open"))
                    .append("Close", toAddSummary.getJSONObject(i).getFloat("Close"))
                    .append("Avg_Volume", toAddSummary.getJSONObject(i).getFloat("Avg_Volume"))
                    .append("Volume", toAddSummary.getJSONObject(i).getFloat("Volume"))
                    .append("Date", toAddSummary.getJSONObject(i).getString("Date"));

            UpdateResult updateResult1 = collection.updateOne(eq("Symbol", symbol),
                    Updates.push("Summary", summary));

        }



    }

}
