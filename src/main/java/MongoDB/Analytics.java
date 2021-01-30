package MongoDB;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.*;


public class Analytics {

    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb://172.16.3.150:27020/");

        MongoDatabase database = mongoClient.getDatabase("CompaniesApplication");

        Scanner input = new Scanner(System.in);
        String s = input.nextLine();
        System.out.println("Ho letto: " + s);
        long i = Long.valueOf(s).longValue();
        System.out.println("Numero: " + i);


        //Analytics1(database);

        //Analytics2(database, 100);

        //Analytics3(database, i);

    }

    private static Consumer<Document> printDocuments() {
        return doc -> System.out.println(doc.toJson());
    }

    private static Consumer<Document> printFormattedDocuments() {
        return doc -> System.out.println(doc.toJson(JsonWriterSettings.builder().indent(true).build()));
    }


    public static void Analytics1(MongoDatabase database)
    {
        MongoCollection<Document> collection = database.getCollection("history");

        Bson group1 = new Document("$group",
                        new Document("_id", new Document("symbol", "$Symbol")
                                    .append("year", new Document("$year", new Document("$dateFromString", new Document("dateString","$Date").append("format", "%Y-%m-%d"))))
                                    .append("month", new Document("$month", new Document("$dateFromString", new Document("dateString","$Date").append("format", "%Y-%m-%d"))))
                                    .append("day", new Document("$dayOfMonth", new Document("$dateFromString", new Document("dateString","$Date").append("format", "%Y-%m-%d"))))
                                    .append("close", "$Close")));

        Bson sort1 = sort(ascending("_id"));

        Bson group2 = new Document("$group",
                new Document("_id", new Document("symbol","$_id.symbol").append("year", "$_id.year").append("mounth", "$_id.month"))
                        .append("lastCloseMonth", new Document("$last", "$_id.close" ))
                        .append("firstCloseMonth", new Document("$first", "$_id.close")));

        Bson sort2 = sort(ascending("_id"));

        Bson addFields = new Document("$addFields", new Document("differenceMounth",
                            new Document("$subtract", Arrays.asList("$lastCloseMonth","$firstCloseMonth")))); //"[$lastCloseMonth,$firstCloseMonth]"

        Bson sort3 = sort(orderBy(ascending("_id.year"), descending("differenceMounth")));

        Bson group3 = new Document("$group",
                new Document("_id", new Document("symbol","$_id.symbol").append("year", "$_id.year"))
                        .append("maxValue", new Document("$max", "$differenceMounth" ))
                        .append("Month", new Document("$first", "$_id.mounth")));

        Bson sort4 = sort(ascending("_id"));

        List<Document> results = collection.aggregate(Arrays.asList(group1, sort1, group2, sort2, addFields, sort3, group3, sort4)).allowDiskUse(true).into(new ArrayList<>());

        System.out.println("Nell'arco degli ultimi 5 anni, trovare per ogni azienda quale sia stato il mese dell'anno più redditizio secondo il valore di chiusura");
        results.forEach(printFormattedDocuments());
    }


    public static void Analytics2(MongoDatabase database, float AvgVolume)
    {
        MongoCollection<Document> collection = database.getCollection("companies");

        Bson match1 = match(lt("Summary.Avg_Volume", AvgVolume));

        Bson unwind1 = unwind("$Summary");

        Bson project1 = new Document("$project",
                new Document("_id", new Document("symbol", "$Symbol"))
                        .append("count", new Document("$cond", Arrays.asList( new Document("$gt",Arrays.asList("$Summary.Volume" , "$Summary.Avg_Volume")), 1, 0)))); //"$gt: [ \"$Summary.Volume\" , \"$Summary.Avg_Volume\"

        Bson group1 = new Document("$group",
                new Document("_id", new Document("symbol","$_id.symbol"))
                        .append("tot", new Document("$sum", "$count" )));

        Bson project2 = new Document("$project",
                new Document("_id", new Document("symbol", "$_id.symbol"))
                        .append("interesse", new Document("$cond", Arrays.asList( new Document("$gt", Arrays.asList("$tot" , 2)) , "yes", "no"))));

        Bson sort = sort(descending("interesse"));

        List<Document> results = collection.aggregate(Arrays.asList(match1, unwind1, project1, group1, project2, sort)).into(new ArrayList<>());

        System.out.println("Per ogni titolo verificare se nell'ultima settimana l'interesse è cresiuto o diminuito, ovvero se per ogni giorno il volume è maggiore del volume medio");
        results.forEach(printFormattedDocuments());
    }

    public static void Analytics3(MongoDatabase database, long cap)
    {
        MongoCollection<Document> collection = database.getCollection("companies");

        Bson match1 = match(lt("Summary.Market_cap", cap));

        Bson unwind1 = unwind("$Summary");

        Bson sort1 = sort(descending("Summary.Date"));

        Bson group1 = new Document("$group",
                new Document("_id", new Document("sector", "$Sector").append("symbol","$Symbol"))
                        .append("data", new Document("$last", "$Summary.Date"))
                        .append("PE", new Document("$last", "$Summary.PE_Ratio")));

        Bson match2 = match(ne("PE", 0));

        Bson match3 = match(ne("_id.sector", ""));

        Bson sort2 = sort(orderBy(descending("_id.sector"), descending("PE")));

        Bson group2 = new Document("$group",
                new Document("_id", new Document("sector","$_id.sector"))
                        .append("symbol", new Document("$last", "$_id.symbol" ))
                        .append("PE", new Document("$last", "$PE" )));

        List<Document> results = collection.aggregate(Arrays.asList(match1, unwind1, sort1, group1, match2, match3, sort2, group2)).into(new ArrayList<>());

        System.out.println("Per ogni settore, trovare le aziende con capitalizzazione superiore ad un miliardo che hanno il rapporto PE più basso (le aziende più sottovalutate)");
        results.forEach(printFormattedDocuments());
    }
}