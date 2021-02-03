package API;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;

public class UpdateHistory {

    private static Consumer<Document> printFormattedDocuments() {
        return doc -> System.out.println(doc.toJson(JsonWriterSettings.builder().indent(true).build()));
    }

    public static void main(String[] args) throws IOException {

        MongoClient mongoClient = MongoClients.create("mongodb://172.16.3.150:27020/");

        MongoDatabase database = mongoClient.getDatabase("CompaniesApplication");

        MongoCollection<Document> collection = database.getCollection("companies");

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

        List<Document> results = collection.aggregate(Arrays.asList(unwind, sort, group, project, merge("history"))).into(new ArrayList<>());
        System.out.println(collection.countDocuments());
        //collection2.insertMany(results);
        //System.out.println(collection2.countDocuments());
        System.out.println("Fatto");
        //results.forEach(printFormattedDocuments());

        UpdateResult updateResult = collection.updateMany( new Document(),
                Updates.set("Summary", Arrays.asList ()));

    }
}
