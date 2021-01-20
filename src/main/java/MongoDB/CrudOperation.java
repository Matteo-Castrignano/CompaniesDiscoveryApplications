package MongoDB;

import Entities.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class CrudOperation {

    public static void main(String args[])
    {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27020/");

        MongoDatabase database = mongoClient.getDatabase("CompaniesApplication");

        MongoClient.close();
    }


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

    public boolean createReport(MongoDatabase database, Report c)
    {
        MongoCollection<Document> collection = database.getCollection("report");

        Document doc = new Document("",)
                .append("", )
                .append("", )
                .append("", );

        collection.insertOne(doc);
        return true;
    }

}
