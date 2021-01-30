package Neo4j;

import org.neo4j.driver.*;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Path;
import java.util.ArrayList;
import java.util.List;
import static org.neo4j.driver.Values.parameters;

public class CrudOperation implements AutoCloseable{

    private final Driver driver;

    public CrudOperation( String uri, String user, String password )
    {
        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
    }

    public void close() throws Exception
    {
        driver.close();
    }





    //CRUD User




    //CRUD Professional user




    //CRUD Company




    public static void main(String[] args) throws Exception
    {
        try ( CrudOperation neo4j = new CrudOperation( "neo4j://localhost:7687", "neo4j", "root" ) )
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
