package Neo4j;

import Entities.Companies;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import java.util.ArrayList;
import java.util.List;

import static org.neo4j.driver.Values.parameters;

public class Analytics extends Neo4jDatabaseAccess{


    //Analytics1
    private static List<Integer> userFollowing(String username)//OK
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
            //System.out.println("NumberFollowerUser: " + n_follower.get(0) + " NumberFollowerCompany: " + n_follower.get(1));
            return n_follower;
        }
    }


    //Analytics2
    private static List<String> suggestedCompany(String username)//OK
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
            //System.out.println("Suggested Company: " + symbol_list);
            return symbol_list;
        }
    }


    //Analytics3
    private static List<Companies> listFollowedCompany(String username)//OK
    {
        try ( Session session = driver.session() )
        {
            List<Companies> symbol_list = session.readTransaction((TransactionWork<List<Companies>>) tx -> {
                Result result = tx.run( "MATCH (u1:User)-[:WATCHLIST]-(c1:Company) WHERE u1.username = $username RETURN c1",
                        parameters( "username", username) );

                ArrayList<Companies> symbol = new ArrayList<>();
                while(result.hasNext()){
                    Record r = result.next();

                    Value v = r.get(0);

                    Companies c1 = new Companies( v.get("symbol").asString(), v.get("name").asString(), v.get("exchange").asString(),
                            v.get("sector").asString(), v.get("fullTimesEmployees").asInt(),
                            v.get("description").asString(), v.get("city").asString(),
                            v.get("phone").asString(), v.get("state").asString(), v.get("country").asString(), v.get("address").asString(),
                            v.get("website").asString(), null );
                    symbol.add(c1);
                }
                return symbol;
            });
            //System.out.println("List followed company: " + symbol_list);
            return symbol_list;
        }
    }


    //Analytics4
    private static List<String> listFollowedUser(String username)//OK
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
            //System.out.println("List user followed: " + user_list);
            return  user_list;
        }
    }

    private static List<String> listFollowedProfessionalUser(String username)//OK
    {
        try ( Session session = driver.session() )
        {
            List<String> user_list = session.readTransaction((TransactionWork<List<String>>) tx -> {
                Result result = tx.run( "MATCH (u1:User)-[:FOLLOW]->(u2:Professional_User) WHERE u1.username = $username RETURN u2.username AS user",
                        parameters( "username", username) );

                ArrayList<String> user = new ArrayList<>();
                while(result.hasNext()){
                    Record r = result.next();
                    user.add(r.get("user").asString());
                }

                return user;
            });
            //System.out.println("List user followed: " + user_list);
            return  user_list;
        }
    }


    public static void main(String[] args) throws Exception
    {
        initDriver();
        //System.out.println(userFollowing("cristina23").toString());
        //System.out.println(suggestedCompany("aylin32").toString());
        //System.out.println(listFollowedUser("cristina23").toString());
        List<Companies> c = listFollowedCompany("cristina23");
        for(Companies c1 : c)
             System.out.println( c1.toString2());
        //System.out.println(listFollowedProfessionalUser("cristina23").toString());
        close();
    }
}
