package MongoDB;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Connection {

    public static void main(String args[])
    {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27020/");

        MongoDatabase database = mongoClient.getDatabase("CompaniesApplication");

    }




}
