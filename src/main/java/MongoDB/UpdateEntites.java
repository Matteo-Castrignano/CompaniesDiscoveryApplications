package MongoDB;

import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;

public class UpdateEntites extends MongoDatabaseAccess{

    public static void updateSummary(String filename) throws IOException {
        JSONArray toAddSummary = new JSONArray(new String(Files.readAllBytes(Paths.get("C:/Users/matte/Desktop/" + filename)), StandardCharsets.UTF_8));

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

            UpdateResult updateResult1 = collectionCompanies.updateOne(eq("Symbol", symbol),
                    Updates.push("Summary", summary));
        }

        System.out.println("Operation complete");
    }

    public static void updateHistory() throws IOException
    {
        Bson unwind = unwind("$Summary");

        Bson sort = sort(descending("Summary.Date"));

        Bson group = new Document("$group",
                new Document("_id", new Document("symbol","$Symbol"))
                        .append("Date", new Document("$min", "$Summary.Date"))
                        .append("Open", new Document("$last", "$Summary.Open"))
                        .append("Close", new Document("$first", "$Summary.Close"))
                        .append("AdjClose", new Document("$first", "$Summary.Close"))
                        .append("High", new Document("$max", "$Summary.Close"))
                        .append("Low", new Document("$min", "$Summary.Close"))
                        .append("Volume", new Document("$sum", "$Summary.Volume")));

        Bson project = new Document("$project", new Document("_id", 0)
                .append("Symbol", "$_id.symbol")
                .append("Date", 1)
                .append("Open", 1)
                .append("Close", 1)
                .append("AdjClose", 1)
                .append("High", 1)
                .append("Low", 1)
                .append("Volume", 1));

        List<Document> results = collectionCompanies.aggregate(Arrays.asList(unwind, sort, group, project, merge("history"))).into(new ArrayList<>());

        UpdateResult updateResult = collectionCompanies.updateMany( new Document(),
                Updates.set("Summary", Arrays.asList ()));

        System.out.println("Operation complete");
    }
}
