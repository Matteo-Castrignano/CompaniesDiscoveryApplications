package Neo4j;

import Entities.Companies;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import java.util.ArrayList;
import java.util.List;

import static org.neo4j.driver.Values.parameters;

public class Analytics extends Neo4jDatabaseAccess{

    //Analytics1
    public static List<Integer> userFollowing(String username)//OK
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
            return n_follower;
        }
    }

    //Analytics2
    public static List<Integer> professiaonalUserFollow(String username)//OK
    {
        try ( Session session = driver.session() )
        {
            List<Integer> n_follower = session.readTransaction((TransactionWork<List<Integer>>) tx -> {
                Result result = tx.run( "MATCH (u1:User)-[:FOLLOW]->(u2:Professional_User),(u2:Professional_User)-[:WATCHLIST]-(c1:Company)" +
                                "WHERE u2.username = $username RETURN count(DISTINCT u2) as NumberFollower, count(DISTINCT c1) as NumberFollowerCompany",
                        parameters( "username", username) );

                ArrayList<Integer> number = new ArrayList<>();
                if(result.hasNext()){
                    Record r = result.next();
                    number.add(r.get("NumberFollower").asInt());
                    number.add(r.get("NumberFollowerCompany").asInt());
                }

                return number;
            });
            return n_follower;
        }
    }


    //Analytics3
    public static List<String> suggestedCompany(String username)//OK
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
            return symbol_list;
        }
    }


    //Analytics4
    public static List<Companies> listFollowedCompany(String username)//OK
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
                            v.get("website").asString());
                    symbol.add(c1);
                }
                return symbol;
            });
            return symbol_list;
        }
    }

    //Analytics5
    public static List<Companies> listFollowedCompany_byProfessionalUser(String username)//OK
    {
        try ( Session session = driver.session() )
        {
            List<Companies> symbol_list = session.readTransaction((TransactionWork<List<Companies>>) tx -> {
                Result result = tx.run( "MATCH (u1:Professional_User)-[:WATCHLIST]-(c1:Company) WHERE u1.username = $username RETURN c1",
                        parameters( "username", username) );

                ArrayList<Companies> symbol = new ArrayList<>();
                while(result.hasNext()){
                    Record r = result.next();

                    Value v = r.get(0);

                    Companies c1 = new Companies( v.get("symbol").asString(), v.get("name").asString(), v.get("exchange").asString(),
                            v.get("sector").asString(), v.get("fullTimesEmployees").asInt(),
                            v.get("description").asString(), v.get("city").asString(),
                            v.get("phone").asString(), v.get("state").asString(), v.get("country").asString(), v.get("address").asString(),
                            v.get("website").asString());
                    symbol.add(c1);
                }
                return symbol;
            });
            return symbol_list;
        }
    }


    //Analytics6
    public static List<String> listFollowedUser(String username)//OK
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
            return  user_list;
        }
    }

    //Analytics7
    public static List<String> listFollowedProfessionalUser(String username)//OK
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
            return  user_list;
        }
    }

    //Analytics8
    public static List<String> listAllUser()
    {
        try ( Session session = driver.session() )
        {
            List<String> user_list = session.readTransaction((TransactionWork<List<String>>) tx -> {
                Result result = tx.run( "MATCH (n:User) RETURN n ORDER BY n.username");

                ArrayList<String> user = new ArrayList<>();
                while(result.hasNext()){
                    Record r = result.next();
                    Value v = r.get(0);
                    user.add("Username: " + v.get("username").asString() + ", Name: " + v.get("name").asString() + ", Surname: " + v.get("surname").asString());
                }
                return user;
            });
            return  user_list;
        }
    }

    //Analytics9
    public static List<String> listAllProfessionalUser() {
        try (Session session = driver.session()) {
            List<String> profuser_list = session.readTransaction((TransactionWork<List<String>>) tx -> {
                Result result = tx.run("MATCH (n:Professional_User) RETURN n ORDER BY n.username");

                ArrayList<String> profuser = new ArrayList<>();
                while (result.hasNext()) {
                    Record r = result.next();
                    Value v = r.get(0);
                    profuser.add("Username: " + v.get("username").asString() + ", Name: " + v.get("name").asString() + ", Surname: " + v.get("surname").asString());
                }
                return profuser;
            });
            return profuser_list;
        }
    }

    //Analytics10 match (n:Company) where n.symbol =~ 'AB.*' return n.symbol, n.name;
    public static List<String> listAllCompanies(String car) //modifica con una stringa
    {
        try ( Session session = driver.session() )
        {
            List<String> company_list = session.readTransaction((TransactionWork<List<String>>) tx -> {
                Result result = tx.run( "MATCH (n:Company) WHERE n.symbol STARTS WITH $car RETURN n ORDER BY n.symbol",
                        parameters( "car", car ) );

                ArrayList<String> company = new ArrayList<>();
                while(result.hasNext()){
                    Record r = result.next();
                    Value v = r.get(0);
                    company.add("Symbol: " + v.get("symbol").asString() + ", Name: " + v.get("name").asString());
                }

                return company;
            });
            return  company_list;
        }
    }
}
