package Neo4j;

import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import java.util.ArrayList;
import java.util.List;

import static org.neo4j.driver.Values.parameters;

public class Analytics extends Neo4jDatabaseAccess{


    //Analytics1
    private void userFollowing(String username)//OK
    {
        try ( Session session = driver.session() )
        {
            List<Integer> n_follower = session.readTransaction((TransactionWork<List<Integer>>) tx -> {
                Result result = tx.run( "MATCH (u1:User)-[:FOLLOW]->(u2:User),(u1:User)-[:WATCHLIST]-(c1:Company)" +
                                "WHERE u1.username = $username RETURN count(DISTINCT u2) as NumberFollowerUser, count(DISTINCT c1) as NumberFollowerCompany",
                        parameters( "username", username) );

                ArrayList<Integer> number = new ArrayList<>();
                if(result.hasNext()){
                    Record r = result.next();
                    number.add(r.get("NumberFollowerUser").asInt());
                    number.add(r.get("NumberFollowerCompany").asInt());
                }

                return number;
            });
            System.out.println("NumberFollowerUser: " + n_follower.get(0) + " NumberFollowerCompany: " + n_follower.get(1));
        }
    }


    //Analytics2
    private void suggestedCompany(String username)//OK
    {
        try ( Session session = driver.session() )
        {
            List<String> symbol_list = session.readTransaction((TransactionWork<List<String>>) tx -> {
                Result result = tx.run( "MATCH (u1:User)-[*1..2]-(u2:User)-[w:WATCHLIST]-(c1:Company) WHERE u1.username = $username " +
                                "AND NOT (u1)-[:WATCHLIST]-(c1) AND NOT u1.username = u2.username RETURN DISTINCT c1.symbol as Company LIMIT 100",
                        parameters( "username", username) );

                ArrayList<String> symbol = new ArrayList<>();
                while(result.hasNext()){
                    Record r = result.next();
                    symbol.add(r.get("Company").asString());
                }

                return symbol;
            });
            System.out.println("Suggested Company: " + symbol_list);
        }
    }


    //Analytics3
    private void listFollowedCompany(String username)//OK
    {
        try ( Session session = driver.session() )
        {
            List<String> symbol_list = session.readTransaction((TransactionWork<List<String>>) tx -> {
                Result result = tx.run( "MATCH (u1:User)-[:WATCHLIST]-(c1:Company) WHERE u1.username = $username RETURN c1.symbol AS Company",
                        parameters( "username", username) );

                ArrayList<String> symbol = new ArrayList<>();
                while(result.hasNext()){
                    Record r = result.next();
                    symbol.add(r.get("Company").asString());
                }

                return symbol;
            });
            System.out.println("List followed company: " + symbol_list);
        }
    }


    //Analytics4
    private void listFollowedUser(String username)//OK
    {
        try ( Session session = driver.session() )
        {
            List<String> user_list = session.readTransaction((TransactionWork<List<String>>) tx -> {
                Result result = tx.run( "MATCH (u1:User)-[:FOLLOW]->(u2:User) WHERE u1.username = $username RETURN u2.username AS user",
                        parameters( "username", username) );

                ArrayList<String> user = new ArrayList<>();
                while(result.hasNext()){
                    Record r = result.next();
                    user.add(r.get("user").asString());
                }

                return user;
            });
            System.out.println("List user followed: " + user_list);
        }
    }


    public static void main(String[] args) throws Exception
    {
        initDriver();
        //neo4j.userFollowing("cristina23");
        //neo4j.suggestedCompany("aylin32");
        //neo4j.listFollowedUser("cristina23");
        //neo4j.listFollowedCompany("cristina23");
        close();
    }
}
