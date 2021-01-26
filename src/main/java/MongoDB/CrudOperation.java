package MongoDB;

import Entities.*;
import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import com.mongodb.client.MongoCursor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import static com.mongodb.client.model.Filters.*;


public class CrudOperation {

    public static void main(String args[])
    {
        MongoClient mongoClient = MongoClients.create("mongodb://172.16.3.150:27020/");

        MongoDatabase database = mongoClient.getDatabase("CompaniesApplication");

    }



    //CRUD Operation Companies
    public boolean createCompany(MongoDatabase database, Companies c)
    {
        MongoCollection<Document> collection = database.getCollection("companies");

        Document doc = new Document("Symbol", c.getSymbol())
                .append("Name", c.getName())
                .append("Exchange", c.getExchange())
                .append("Sector", c.getSector())
                .append("Summary", Arrays.asList ());

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

                List<Summary> summaries = new ArrayList<>();
                JSONArray a = new JSONArray(json.getJSONArray("Summary"));
                Summary s = new Summary();

                for (int j = 0; j < a.length(); j++)
                {
                    s.setDate(a.getJSONObject(j).getString("Date"));

                    s.setClose(a.getJSONObject(j).getFloat("Close"));
                    s.setOpen(a.getJSONObject(j).getFloat("Open"));
                    s.setVolume(a.getJSONObject(j).getFloat("Volume"));
                    s.setAvgVolume(a.getJSONObject(j).getFloat("Avg_Volume"));
                    s.setMarketCap(a.getJSONObject(j).getFloat("Market_cap"));
                    s.setPeRatio(a.getJSONObject(j).getFloat("PE_Ratio"));
                    s.setEPS(a.getJSONObject(j).getFloat("EPS"));
                    s.setDividend(a.getJSONObject(j).getString("Dividend"));
                    s.setTargetPrice(a.getJSONObject(j).getFloat("Target_prize"));

                    summaries.add(s);
                }

                c = new Companies(json.getString("Symbol"), json.getString("Name"), json.getString("StockExchange"),
                        json.getString("Sector"), summaries );
            }

        } finally {
            cursor.close();
        }
        return c;
    }


   /* public Companies readCompany_byName(MongoDatabase database, String name)
    {
        MongoCollection<Document> collection = database.getCollection("companies");
        MongoCursor cursor = collection.find(eq("Name", name)).iterator();
        Companies c = new Companies();

        try {
            while (cursor.hasNext()) {
                JSONObject json = new JSONObject(cursor.next());
                System.out.println(json.toString());

                List<Summary> summaries = new ArrayList<>();
                JSONArray a = new JSONArray(json.getJSONArray("Summary"));
                Summary s = new Summary();

                for (int j = 0; j < a.length(); j++)
                {
                    s.setDate(a.getJSONObject(j).getString("Date"));

                    s.setClose(a.getJSONObject(j).getFloat("Close"));
                    s.setOpen(a.getJSONObject(j).getFloat("Open"));
                    s.setVolume(a.getJSONObject(j).getFloat("Volume"));
                    s.setAvgVolume(a.getJSONObject(j).getFloat("Avg_Volume"));
                    s.setMarketCap(a.getJSONObject(j).getFloat("Market_cap"));
                    s.setPeRatio(a.getJSONObject(j).getFloat("PE_Ratio"));
                    s.setEPS(a.getJSONObject(j).getFloat("EPS"));
                    s.setDividend(a.getJSONObject(j).getString("Dividend"));
                    s.setTargetPrice(a.getJSONObject(j).getFloat("Target_prize"));

                    summaries.add(s);
                }

                c = new Companies(json.getString("Symbol"), json.getString("Name"), json.getString("StockExchange"),
                        json.getString("Sector"), summaries );
            }

        } finally {
            cursor.close();
        }

        return c;
    }*/


    public long updateCompany_Summary(MongoDatabase database, String symbol, String description)
    {
        //DA FARE
        /*
        MongoCollection<Document> collection = database.getCollection("companies");

        BasicDBObject query = new BasicDBObject();
        query.put("Symbol", symbol);

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("Description", description);

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", newDocument);

        UpdateResult updateResult = collection.updateOne(query, updateObject);

        return UpdateResult.unacknowledged().getModifiedCount();*/
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


/*
    //CRUD Operation Summary
    public boolean createSummary(MongoDatabase database, Summary s)
    {
        MongoCollection<Document> collection = database.getCollection("summary");

        Document doc = new Document("Capitalization", s.getMarketCap())
                .append("PE_Ratio", s.getPeRatio())
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
    //fare delete di summary ATTRAVERSO UNA DATA

    */


    //CRUD Operation Report
    public boolean createReport(MongoDatabase database, Report r)
    {
        MongoCollection<Document> collection = database.getCollection("report");

        Document doc = new Document("Date", r.getDateReport())
                .append("Username", r.getUsername())
                .append("Symbol", r.getSymbol())
                .append("Title", r.getTitle())
                .append("Type", r.getTypeReport())
                .append("AnalizedValue", r.getAnalizedValues())
                .append("Text", r.getDetails());

        collection.insertOne(doc);
        return true;
    }

    public List<Report> readReports_bySymbol(MongoDatabase database, String symbol)
    {

        MongoCollection<Document> collection = database.getCollection("report");
        MongoCursor cursor = collection.find(eq("Symbol", symbol)).iterator();
        List<Report> reportsList = new ArrayList<>();
        Report r;

        try {
            while (cursor.hasNext()) {
                JSONObject json = new JSONObject(cursor.next());
                System.out.println(json.toString());
                r = new Report(json.getString("Title"),json.getString("Date"),json.getString("Type"),json.getString("AnalizedValue"),
                        json.getString("Text"),json.getString("Symbol"),json.getString("Username"));
                reportsList.add(r);
                }

        } finally {
            cursor.close();
        }
        return reportsList;
    }

    public List<Report> readReports_byUsername(MongoDatabase database, String username)
    {

        MongoCollection<Document> collection = database.getCollection("report");
        MongoCursor cursor = collection.find(eq("Username", username)).iterator();
        List<Report> reportsList = new ArrayList<>();
        Report r;

        try {
            while (cursor.hasNext()) {
                JSONObject json = new JSONObject(cursor.next());
                System.out.println(json.toString());
                r = new Report(json.getString("Title"),json.getString("Date"),json.getString("Type"),json.getString("AnalizedValue"),
                        json.getString("Text"),json.getString("Symbol"),json.getString("Username"));
                reportsList.add(r);
            }

        } finally {
            cursor.close();
        }
        return reportsList;
    }

    public long updateReport_Text_byTitle(MongoDatabase database, String title, String text)
    {
        MongoCollection<Document> collection = database.getCollection("report");

        BasicDBObject query = new BasicDBObject();
        query.put("Title", title);

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("Text", text);

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", newDocument);

        UpdateResult updateResult = collection.updateOne(query, updateObject);

        return UpdateResult.unacknowledged().getModifiedCount();
    }

    public long deleteRepert_byTitle(MongoDatabase database, String title)
    {
        MongoCollection<Document> collection = database.getCollection("report");

        DeleteResult deleteResult = collection.deleteMany(eq("Title", title));
        return deleteResult.getDeletedCount();
    }
}
