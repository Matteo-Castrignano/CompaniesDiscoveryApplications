package MongoDB;

import Entities.*;
import com.mongodb.client.*;
import com.mongodb.client.model.Updates;
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
import static com.mongodb.client.model.Updates.*;


public class CrudOperation {

    public static void main(String[] args)
    {
        MongoClient mongoClient = MongoClients.create("mongodb://172.16.3.150:27020,172.16.3.121:27020,172.16.3.119:27020/"
                                                    + "?retryWrites=true&w=majority&readPreference=secondary&timeout=10000");

        MongoDatabase database = mongoClient.getDatabase("CompaniesApplication");

        //Companies c = new Companies("PROVA","Pippo","NYS","info");

        //ystem.out.println("Esito inserimento " + createCompany(database,c));

        //Companies c1 = readCompany_bySymbol(database, "PROVA");
        //System.out.println(c1.toString());

        //Summary s1 = new Summary("PROVA","2021-02-02",100,255,925,"natale",89,8,9,7,10);

        //System.out.println("Esito update summary: "+ updateCompany_Summary(database,s1));
        //System.out.println("Esito update summary: "+ updateCompany_Summary(database,s1));

        //Companies c2 = readCompany_bySymbol(database, "PROVA");
        //System.out.println(c2.toString());

        //System.out.println("Esito delete companies" + deleteCompany_bySymbol(database,"PROVA"));

        //List<History> lh = readHistory_byPeriod(database, "2020-11-11", "2021-01-10");
        //System.out.println(lh.toString());

        //History h = new History("PROVA","2021-02-12",3, 5, 7, 230,10,10);
        //System.out.println("Esito inserimento history "+ createHistory(database,h));

        //System.out.println("Esito delete history " + deleteHistory_bySymbol(database,"PROVA"));
        //System.out.println("Esito delete history " + deleteHistory_byPeriod(database,"2021-02-01","2021-02-27"));

        //Report r1 = new Report("prova","2021-02-01","budello di tu ma", "buo di ulo", "è troppo peloso", "PROVA", "ennio");
        //System.out.println("Esito inserimento report " + createReport(database, r1) );

        //List<Report> r = readReports_bySymbol(database,"PROVA");
        //System.out.println(r.toString());

        //r = readReports_byUsername(database,"ennio");
        //System.out.println(r.toString());

        //System.out.println("Esisto update report" + updateReport_Text_byTitle(database,"prova","è sempre infiammato"));

        //r = readReports_bySymbol(database,"PROVA");
        //System.out.println(r.toString());

        //System.out.println("Esisto delete report" + deleteRepert_byTitle(database,"prova"));

        //r = readReports_byUsername(database,"ennio");
        //System.out.println(r.toString());

    }



    //CRUD Operation Companies
    public static boolean createCompany(MongoDatabase database, Companies c) //OK
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


    public static Companies readCompany_bySymbol(MongoDatabase database, String symbol) //OK
    {
        MongoCollection<Document> collection = database.getCollection("companies");
        FindIterable<Document> iterable = collection.find(eq("Symbol", symbol));
        MongoCursor<Document> cursor = iterable.iterator();

        String profit;
        Companies c = new Companies();

        try {
            while (cursor.hasNext()) {
                JSONObject json = new JSONObject(cursor.next());

                List<Summary> summaries = new ArrayList<>();

                if (!json.getJSONArray("Summary").isEmpty())
                {
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
                }

                 if(json.isNull("mostProfitablePeriod"))
                     profit = null;
                 else
                     profit = json.getString("mostProfitablePeriod");

                c = new Companies(json.getString("Symbol"), json.getString("Name"), json.getString("Exchange"),
                        json.getString("Sector"), profit, summaries );
            }

        } finally {
            cursor.close();
        }
        return c;
    }


    public static long updateCompany_Summary(MongoDatabase database, Summary s)//OK
    {
        MongoCollection<Document> collection = database.getCollection("companies");

        Document newSummary = new Document("Target_prize", s.getTargetPrice())
                .append("Market_cap", s.getMarketCap())
                .append("Volume", s.getVolume())
                .append("PE_Ratio", s.getPeRatio())
                .append("EPS", s.getEPS())
                .append("Close", s.getClose())
                .append("Date", s.getDate())
                .append("Avg_Volume",s.getAvgVolume())
                .append("Open", s.getOpen())
                .append("Dividend", s.getDividend());

        UpdateResult updateResult = collection.updateOne(eq("Symbol", s.getSymbol()),
                                                        Updates.push("Summary", newSummary));
        return updateResult.getModifiedCount();
    }


    public static long deleteCompany_bySymbol(MongoDatabase database, String symbol) //OK
    {
        MongoCollection<Document> collection = database.getCollection("companies");

        DeleteResult deleteResult = collection.deleteMany(eq("Symbol", symbol));
        return deleteResult.getDeletedCount();
    }


    //CRUD Operation History
    public static boolean createHistory(MongoDatabase database, History h)
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


    public static List<History> readHistory_bySymbol(MongoDatabase database, String symbol)//OK
    {
        MongoCollection<Document> collection = database.getCollection("history");
        List<History> historyList = new ArrayList<>();
        History h;
        FindIterable<Document> iterable = collection.find(eq("Symbol", symbol));
        MongoCursor<Document> cursor = iterable.iterator();

        try{
            while (cursor.hasNext()) {
                JSONObject json = new JSONObject(cursor.next());
                h = new History(json.getString("Symbol"), json.getString("Date"), json.getFloat("Open"), json.getFloat("Close"),
                        json.getFloat("AdjClose"), json.getInt("Volume"), json.getFloat("High"), json.getFloat("Low"));
                historyList.add(h);
            }

        } finally {
            cursor.close();
        }

        return historyList;
    }


    public static List<History> readHistory_byPeriod(MongoDatabase database, String start_date, String end_date) //OK
    {

        MongoCollection<Document> collection = database.getCollection("history");
        List<History> historyList = new ArrayList<>();
        History h;
        FindIterable<Document> iterable = collection.find(and(gt("Date", start_date), lt("Date", end_date)));
        MongoCursor<Document> cursor = iterable.iterator();

        try {
            while (cursor.hasNext()) {
                JSONObject json = new JSONObject(cursor.next());
                //System.out.println(json.toString());
                h = new History(json.getString("Symbol"), json.getString("Date"), json.getFloat("Open"), json.getFloat("Close"),
                        json.getFloat("AdjClose"), json.getInt("Volume"), json.getFloat("High"), json.getFloat("Low"));
                historyList.add(h);
            }

        } finally {
            cursor.close();
        }

        return historyList;
    }


    public static long deleteHistory_bySymbol(MongoDatabase database, String symbol)//OK
    {
        MongoCollection<Document> collection = database.getCollection("history");

        DeleteResult deleteResult = collection.deleteMany(eq("Symbol", symbol));
        return deleteResult.getDeletedCount();
    }


    public static long deleteHistory_byPeriod(MongoDatabase database, String start_date, String end_date)//OK
    {
        MongoCollection<Document> collection = database.getCollection("history");

        DeleteResult deleteResult = collection.deleteMany(and(gt("Date", start_date), lt("Date", end_date)));
        return deleteResult.getDeletedCount();
    }



    //CRUD Operation Report
    public static boolean createReport(MongoDatabase database, Report r)//OK
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

    public static List<Report> readReports_bySymbol(MongoDatabase database, String symbol)//OK
    {

        MongoCollection<Document> collection = database.getCollection("report");
        FindIterable<Document> iterable = collection.find(eq("Symbol", symbol));
        MongoCursor<Document> cursor = iterable.iterator();
        List<Report> reportsList = new ArrayList<>();
        Report r;

        try {
            while (cursor.hasNext()) {
                JSONObject json = new JSONObject(cursor.next());
                r = new Report(json.getString("Title"),json.getString("Date"),json.getString("Type"),json.getString("AnalizedValue"),
                        json.getString("Text"),json.getString("Symbol"),json.getString("Username"));
                reportsList.add(r);
                }

        } finally {
            cursor.close();
        }
        return reportsList;
    }

    public static List<Report> readReports_byUsername(MongoDatabase database, String username)//OK
    {

        MongoCollection<Document> collection = database.getCollection("report");
        FindIterable<Document> iterable = collection.find(eq("Username", username));
        MongoCursor<Document> cursor = iterable.iterator();

        List<Report> reportsList = new ArrayList<>();
        Report r;

        try {
            while (cursor.hasNext()) {
                JSONObject json = new JSONObject(cursor.next());
                r = new Report(json.getString("Title"),json.getString("Date"),json.getString("Type"),json.getString("AnalizedValue"),
                        json.getString("Text"),json.getString("Symbol"),json.getString("Username"));
                reportsList.add(r);
            }

        } finally {
            cursor.close();
        }
        return reportsList;
    }

    public static long updateReport_Text_byTitle(MongoDatabase database, String title, String text)//OK
    {
        MongoCollection<Document> collection = database.getCollection("report");

        UpdateResult updateResult =collection.updateMany(eq("Title", title), set("Text", text));
        return updateResult.getModifiedCount();

    }

    public static long deleteRepert_byTitle(MongoDatabase database, String title)//OK
    {
        MongoCollection<Document> collection = database.getCollection("report");

        DeleteResult deleteResult = collection.deleteMany(eq("Title", title));
        return deleteResult.getDeletedCount();
    }
}


/*
        MongoCollection<Document> collection = database.getCollection("companies");
        Document doc = collection.find(eq("Symbol", symbol)).first();
        System.out.println(doc.toJson());
        Gson gson = new Gson();
        Companies c = gson.fromJson(doc.toJson(), Companies.class);
        List<Summary> summ = c.getSummary();
        for(Summary s : summ){
            System.out.println(s.toString());
        }
        */