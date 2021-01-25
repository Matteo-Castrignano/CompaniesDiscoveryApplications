package MongoDB;

import Entities.*;
import java.util.Arrays;
import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import com.mongodb.client.MongoCursor;
import java.util.ArrayList;
import org.bson.conversions.Bson;
import org.json.JSONObject;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.ConnectionString;
import com.mongodb.client.*;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Sorts.*;
import static java.util.Arrays.*;


public class Analitics {

    public static void main(String args[]) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27020/");

        MongoDatabase database = mongoClient.getDatabase("CompaniesApplication");

    }


    public void Analitcs1(MongoDatabase database)
    {
        MongoCollection<Document> collection = database.getCollection("history");


        Bson group1 = new Document("$group",
                        new Document("_id", new Document("symbol", "$Symbol")
                                    .append("year", new Document("$year", new Document("$dateFromString", new Document("dateString","$Date").append("format", "%Y-%m-%d"))))
                                    .append("month", new Document("$month", new Document("$dateFromString", new Document("dateString","$Date").append("format", "%Y-%m-%d"))))
                                    .append("day", new Document("$day", new Document("$dateFromString", new Document("dateString","$Date").append("format", "%Y-%m-%d"))))
                                    .append("close", "$Close")));

        Bson sort1 = sort(ascending("_id"));

        Bson group2 = new Document("$group",
                new Document("_id", new Document("symbol","$_id.symbol").append("year", "$_id.year").append("mounth", "$_id.month"))
                        .append("lastCloseMonth", new Document("$last", "$_id.close" ))
                        .append("firstCloseMonth", new Document("$first", "$_id.close")));

        Bson sort2 = sort(ascending("_id"));

        Bson addFields = new Document("$addFields", new Document("$subtract", "[$lastCloseMonth,$firstCloseMonth]"));

        Bson sort3 = sort(orderBy(ascending("_id.year"), descending("differenceMounth")));

        Bson group3 = new Document("$group",
                new Document("_id", new Document("symbol","$_id.symbol").append("year", "$_id.year")
                        .append("maxValue", new Document("$max", "$differenceMounth" ))
                        .append("Month", new Document("$first", "$_id.mounth"))));

        Bson sort4 = sort(ascending("_id"));

    }


    public void Analitcs2(MongoDatabase database)
    {

    }

    public void Analitcs3(MongoDatabase database)
    {

    }

}
