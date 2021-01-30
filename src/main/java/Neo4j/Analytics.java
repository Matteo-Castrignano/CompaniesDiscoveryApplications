package Neo4j;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

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




    //Analytics2




    //Analytics3




    public static void main(String[] args) throws Exception
    {
        try ( Analytics neo4j = new Analytics( "neo4j://localhost:7687", "neo4j", "root" ) )
        {
            /*System.out.println("---------------------------");
            neo4j.printMovieByPartOfTitle( "the");
            System.out.println("---------------------------");
            neo4j.printMovieCast( "The Matrix");
            System.out.println("---------------------------");
            neo4j.printLeastPopularDirector();
            System.out.println("---------------------------");
            neo4j.printTop3MoviesWithHighestActorsProducersRatio();
            System.out.println("---------------------------");
            neo4j.printShortestPathFromYoungestToOldest();
            System.out.println("---------------------------");*/
        }
    }
}
