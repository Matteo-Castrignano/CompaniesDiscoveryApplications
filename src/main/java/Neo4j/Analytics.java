package Neo4j;

import org.neo4j.driver.*;
import org.neo4j.driver.Record;

import java.util.ArrayList;
import java.util.List;

import static org.neo4j.driver.Values.parameters;

public class Analytics implements AutoCloseable{

    private final Driver driver;

    public Analytics(String uri, String user, String password )
    {
        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
    }

    public void close() throws Exception
    {
        driver.close();
    }

    //Analytics1

    private void userFollowing(String username)
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
                    System.out.println(r.get("NumberFollowerUser"));
                    System.out.println(r.get("NumberFollowerCompany"));
                    number.add(r.get("NumberFollowerUser").asInt());
                    number.add(r.get("NumberFollowerCompany").asInt());
                }

                return number;
            });
            System.out.println("NumberFollowerUser: " + n_follower.get(0) + " NumberFollowerCompany:" + n_follower.get(1));
        }
    }



    //Analytics2





    public static void main(String[] args) throws Exception
    {
        try ( Analytics neo4j = new Analytics( "neo4j://localhost:7687", "neo4j", "root" ) )
        {
            neo4j.userFollowing("cristina23");
        }
    }
}
