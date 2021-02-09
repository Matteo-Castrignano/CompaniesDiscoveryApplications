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


public class CrudOperation extends MongoDatabaseAccess{

    //CRUD Operation Companies
    public static boolean createCompany(Companies c) //OK
    {
        Document doc = new Document("Symbol", c.getSymbol())
                .append("Name", c.getName())
                .append("Exchange", c.getExchange())
                .append("Sector", c.getSector())
                .append("Summary", Arrays.asList ());

        collectionCompanies.insertOne(doc);
        return true;
    }


    public static Companies readCompany_bySymbol(String symbol) //OK
    {
        FindIterable<Document> iterable = collectionCompanies.find(eq("Symbol", symbol));
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
                        s.setTargetPrice(a.getJSONObject(j).getFloat("Target_price"));

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


    public static long updateCompany_Summary(Summary s)//OK
    {
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

        UpdateResult updateResult = collectionCompanies.updateOne(eq("Symbol", s.getSymbol()),
                                                        Updates.push("Summary", newSummary));
        return updateResult.getModifiedCount();
    }


    public static long deleteCompany(String symbol) //OK
    {
        DeleteResult deleteResult = collectionCompanies.deleteMany(eq("Symbol", symbol));
        return deleteResult.getDeletedCount();
    }


    //CRUD Operation History
    public static boolean createHistory(History h)
    {
        Document doc = new Document("Date", h.getDateHistory())
                .append("Open", h.getOpen())
                .append("Close", h.getClose())
                .append("High", h.getHigh())
                .append("Low", h.getLow())
                .append("AdjClose", h.getAdjustedClose())
                .append("Volume", h.getVolume())
                .append("Symbol", h.getSymbol());

        collectionHistory.insertOne(doc);
        return true;
    }


    public static List<History> readHistory_bySymbol(String symbol)//OK
    {
        List<History> historyList = new ArrayList<>();
        History h;
        FindIterable<Document> iterable = collectionHistory.find(eq("Symbol", symbol)).sort(new Document("Date",-1)).limit(100);
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


    public static List<History> readHistory_byPeriod(String start_date, String end_date, String symbol) //OK
    {
        List<History> historyList = new ArrayList<>();
        History h;
        FindIterable<Document> iterable = collectionHistory.find(and(eq("Symbol", symbol),gt("Date", start_date), lt("Date", end_date)));
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


    public static long deleteHistory_bySymbol(String symbol)//OK
    {
        DeleteResult deleteResult = collectionHistory.deleteMany(eq("Symbol", symbol));
        return deleteResult.getDeletedCount();
    }


    public static long deleteHistory_byPeriod(String start_date, String end_date)//OK
    {
        DeleteResult deleteResult = collectionHistory.deleteMany(and(gt("Date", start_date), lt("Date", end_date)));
        return deleteResult.getDeletedCount();
    }



    //CRUD Operation Report
    public static boolean createReport( Report r)//OK
    {
        Document doc = new Document("Date", r.getDateReport())
                .append("Username", r.getUsername())
                .append("Symbol", r.getSymbol())
                .append("Title", r.getTitle())
                .append("Type", r.getTypeReport())
                .append("AnalizedValue", r.getAnalizedValues())
                .append("Text", r.getDetails());

        collectionReport.insertOne(doc);
        return true;
    }

    public static List<Report> readReports_bySymbol(String symbol)//OK
    {
        FindIterable<Document> iterable = collectionReport.find(eq("Symbol", symbol));
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

    public static List<Report> readReports_byUsername(String username)//OK
    {
        FindIterable<Document> iterable = collectionReport.find(eq("Username", username));
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

    public static long updateReport_Text_byTitle(String username, String title, String text)//OK
    {
        UpdateResult updateResult = collectionReport.updateMany(and(eq("Title", title),eq("Username", username)), set("Text", text));
        return updateResult.getModifiedCount();

    }

    public static long deleteRepert_byTitle(String title)//OK
    {
        DeleteResult deleteResult = collectionReport.deleteMany(eq("Title", title));
        return deleteResult.getDeletedCount();
    }
}
