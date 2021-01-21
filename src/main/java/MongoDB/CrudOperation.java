package MongoDB;

import Entities.*;
import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import com.mongodb.client.MongoCursor;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import static com.mongodb.client.model.Filters.*;


public class CrudOperation {

    public static void main(String args[])
    {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27020/");

        MongoDatabase database = mongoClient.getDatabase("CompaniesApplication");

    }



    //CRUD Operation Companies
    public boolean createCompany(MongoDatabase database, Companies c)
    {
        MongoCollection<Document> collection = database.getCollection("companies");

        Document doc = new Document("Symbol", c.getSymbol() )
                .append("Name", c.getName())
                .append("Exchange", c.getStockExchange() )
                .append("Sector", c.getSector() );

        collection.insertOne(doc);
        return true;
    }


    public Companies readCompany_bySymbol(MongoDatabase database, String symbol)
    {

        MongoCollection<Document> collection = database.getCollection("companies");
        MongoCursor cursor = collection.find(eq("Symbol", symbol)).iterator();
        Companies c = new Companies();

        try {
            while (cursor.hasNext()) {
                JSONObject json = new JSONObject(cursor.next());
                System.out.println(json.toString());
                c = new Companies(json.getString("Symbol"), json.getString("Name"), json.getString("StockExchange"), json.getString("Sector"));
            }

        } finally {
            cursor.close();
        }
        return c;
    }


    public Companies readCompany_byName(MongoDatabase database, String name)
    {
        MongoCollection<Document> collection = database.getCollection("companies");
        MongoCursor cursor = collection.find(eq("Name", name)).iterator();
        Companies c = new Companies();

        try {
            while (cursor.hasNext()) {
                JSONObject json = new JSONObject(cursor.next());
                System.out.println(json.toString());
                c = new Companies(json.getString("Symbol"), json.getString("Name"), json.getString("StockExchange"), json.getString("Sector"));
            }

        } finally {
            cursor.close();
        }

        return c;
    }


    public long deleteCompany_bySymbol(MongoDatabase database, String symbol)
    {
        MongoCollection<Document> collection = database.getCollection("companies");

        DeleteResult deleteResult = collection.deleteMany(eq("Symbol", symbol));
        return deleteResult.getDeletedCount();

    }


    public long deleteCompany_byName(MongoDatabase database, String name)
    {
        MongoCollection<Document> collection = database.getCollection("companies");

        DeleteResult deleteResult = collection.deleteMany(eq("Name", name));
        return deleteResult.getDeletedCount();

    }



    //CRUD Operation History
    public boolean createHistory(MongoDatabase database, History h)
    {
        MongoCollection<Document> collection = database.getCollection("history");

        Document doc = new Document("Date", h.getDateHistory())
                .append("Open", h.getOpen())
                .append("Close", h.getClose())
                .append("High", h.getHigh())
                .append("Low", h.getLow())
                .append("AdjClose", h.getAdjustedClose())
                .append("Volume", h.getVolume())
                .append("Symbol", h.getSymbol());

        collection.insertOne(doc);
        return true;
    }


    public List<History> readHistory_bySymbol(MongoDatabase database, String symbol)
    {
        MongoCollection<Document> collection = database.getCollection("history");
        List<History> historyList = new ArrayList<>();
        History h;
        MongoCursor cursor = collection.find(eq("Symbol", symbol)).iterator();

        try {
            while (cursor.hasNext()) {
                JSONObject json = new JSONObject(cursor.next());
                System.out.println(json.toString());
                h = new History(json.getString("Symbol"), json.getString("Date"), json.getFloat("Open"), json.getFloat("Close"),
                        json.getFloat("AdjClose"), json.getInt("Volume"), json.getFloat("High"), json.getFloat("Low"));
                historyList.add(h);
            }

        } finally {
            cursor.close();
        }

        return historyList;
    }


    public List<History> readHistory_byPeriod(MongoDatabase database, String start_date, String end_date)
    {

        MongoCollection<Document> collection = database.getCollection("history");
        List<History> historyList = new ArrayList<>();
        History h;
        MongoCursor cursor = collection.find(and(gt("Date", start_date), lt("Date", end_date))).iterator();  //find({"Symbol": "BDC","Date" : {$lt:"2016-05-23", $gt:"2016-03-23"}})

        try {
            while (cursor.hasNext()) {
                JSONObject json = new JSONObject(cursor.next());
                System.out.println(json.toString());
                h = new History(json.getString("Symbol"), json.getString("Date"), json.getFloat("Open"), json.getFloat("Close"),
                        json.getFloat("AdjClose"), json.getInt("Volume"), json.getFloat("High"), json.getFloat("Low"));
                historyList.add(h);
            }

        } finally {
            cursor.close();
        }

        return historyList;

    }


    public long deleteHistory_bySymbol(MongoDatabase database, String symbol)
    {
        MongoCollection<Document> collection = database.getCollection("history");

        DeleteResult deleteResult = collection.deleteMany(eq("Symbol", symbol));
        return deleteResult.getDeletedCount();
    }


    public long deleteHistory_byPeriod(MongoDatabase database, String start_date, String end_date)
    {
        MongoCollection<Document> collection = database.getCollection("history");

        DeleteResult deleteResult = collection.deleteMany(and(gt("Date", start_date), lt("Date", end_date)));
        return deleteResult.getDeletedCount();
    }



    //CRUD Operation Summary
    public boolean createSummary(MongoDatabase database, Summary s)
    {
        MongoCollection<Document> collection = database.getCollection("summary");

        Document doc = new Document("Capitalization", s.getCapitalization())
                .append("PE_Ratio", s.getPE_ratio())
                .append("EPS", s.getEPS())
                .append("Dividend", s.getDividend())
                .append("Target_Price", s.getTargetPrice())
                .append("Open", s.getOpen())
                .append("Close", s.getClose())
                .append("AvgVolume",s.getAvgVolume())
                .append("Volume",s.getVolume());

        collection.insertOne(doc);
        return true;
    }

    //fare read,update,delete di summary




    //CRUD Operation Report
    public boolean createReport(MongoDatabase database, Report r)
    {
        MongoCollection<Document> collection = database.getCollection("report");

        Document doc = new Document("dateReport", r.getDateReport())
                .append("typeReport", r.getTypeReport())
                .append("analizedValues", r.getAnalizedValues())
                .append("details", r.getDetails());

        collection.insertOne(doc);
        return true;
    }
    //fare read,update,delete di report

}
