package MongoDB;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDatabaseAccess {

    protected static MongoClient mongoClient;
    protected static MongoDatabase database;
    protected static MongoCollection<Document> collectionHistory;
    protected static MongoCollection<Document> collectionCompanies;
    protected static MongoCollection<Document> collectionReport;

    public static void openConnection()
    {
        mongoClient = MongoClients.create("mongodb://172.16.3.150:27020,172.16.3.121:27020,172.16.3.119:27020/"
                + "?retryWrites=true&w=majority&readPreference=secondary&wtimeout=10000");

        database = mongoClient.getDatabase("CompaniesApplication");

        collectionHistory = database.getCollection("history");
        collectionCompanies = database.getCollection("companies");
        collectionReport = database.getCollection("report");
    }

    public static void closeConnection(){
        mongoClient.close();
    }
}
