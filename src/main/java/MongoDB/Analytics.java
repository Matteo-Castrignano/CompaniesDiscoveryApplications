package MongoDB;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Filters.ne;
import static com.mongodb.client.model.Sorts.*;


public class Analytics extends MongoDatabaseAccess{

    public static void main(String[] args) {
        /*openConnection();

        System.out.println("Insert cap");
        Scanner input = new Scanner(System.in);
        String s = input.nextLine();
        System.out.println("Ho letto: " + s);
        long i = Long.valueOf(s).longValue();
        System.out.println("Numero: " + i);

        Analytics1();

        Analytics2(100);

        Analytics3(i);

        closeConnection();*/
    }

    private static Consumer<Document> printDocuments() {
        return doc -> System.out.println(doc.toJson());
    }

    private static Consumer<Document> printFormattedDocuments() {
        return doc -> System.out.println(doc.toJson(JsonWriterSettings.builder().indent(true).build()));
    }


    public static void Analytics1()
    {
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

        Bson sort3 = sort(descending("differenceMounth"));

        Bson group3 = new Document("$group",
                new Document("_id", new Document("symbol","$_id.symbol"))
                        .append("maxValue", new Document("$max", "$differenceMounth" ))
                        .append("Year", new Document("$first","$_id.year"))
                        .append("Month", new Document("$first", "$_id.mounth")));

        Bson project1 = new Document("$project", new Document("_id", 0)
                .append("Symbol", "$_id.symbol")
                .append("mostProfitablePeriod", new Document("$concat", Arrays.asList( new Document ("$toString", "$Year"), "-",  new Document ("$toString", "$Month"), "-", new Document ("$toString", "$maxValue")))));

        Bson merge1 = new Document("$merge", new Document( "into", "companies").append( "on", "Symbol").append("whenMatched", "merge").append("whenNotMatched","insert"));

        List<Document> results = collectionHistory.aggregate(Arrays.asList(group1, sort1, group2, sort2, addFields, sort3, group3, project1, merge1)).allowDiskUse(true).into(new ArrayList<>());

        System.out.println("Operation complete");
        //results.forEach(printFormattedDocuments());
    }


    public static void Analytics2(float AvgVolume)
    {
        Bson match1 = match(lt("Summary.Avg_Volume", AvgVolume));

        Bson unwind1 = unwind("$Summary");

        Bson project1 = new Document("$project",
                new Document("_id", new Document("symbol", "$Symbol"))
                        .append("count", new Document("$cond", Arrays.asList( new Document("$gt",Arrays.asList("$Summary.Volume" , "$Summary.Avg_Volume")), 1, 0))));

        Bson group1 = new Document("$group",
                new Document("_id", new Document("symbol","$_id.symbol"))
                        .append("tot", new Document("$sum", "$count" )));

        Bson project2 = new Document("$project",
                new Document("_id", new Document("symbol", "$_id.symbol"))
                        .append("interesse", new Document("$cond", Arrays.asList( new Document("$gt", Arrays.asList("$tot" , 2)) , "yes", "no"))));

        Bson sort = sort(descending("interesse"));

        List<Document> results = collectionCompanies.aggregate(Arrays.asList(match1, unwind1, project1, group1, project2, sort)).into(new ArrayList<>());

        System.out.println("Per ogni titolo verificare se nell'ultima settimana l'interesse è cresiuto o diminuito, ovvero se per ogni giorno il volume è maggiore del volume medio");
        results.forEach(printFormattedDocuments());
    }

    public static void Analytics3(long cap)
    {
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

        List<Document> results = collectionCompanies.aggregate(Arrays.asList(match1, unwind1, sort1, group1, match2, match3, sort2, group2)).into(new ArrayList<>());

        System.out.println("Per ogni settore, trovare le aziende con capitalizzazione superiore ad un miliardo che hanno il rapporto PE più basso (le aziende più sottovalutate)");
        results.forEach(printFormattedDocuments());
    }
}