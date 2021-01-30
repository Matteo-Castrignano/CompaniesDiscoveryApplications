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

    public void addUser()
    {

    }

    public void readUser_byUsername()
    {

    }

    public void addUser_toFollow()
    {

    }

    public void deleteUser_toFollow()
    {

    }

    public void addCompany_toFollow()
    {

    }

    public void deleteCompany_toFollow()
    {

    }

    public void addProfessionalUser_toFollow()
    {

    }

    public void deleteProfessionalUser_toFollow()
    {

    }

    public void rate_ProfessionalUser()
    {

    }

    public void updateRate_ProfessionalUser()
    {

    }

    public void deleteUser()
    {

    }



    //CRUD Professional user

    public void addProfessionlUser()
    {

    }

    public void readProfessionlUser_byUsername()
    {

    }

    public void addUser_toFollow_byProfessionlUser()
    {

    }

    public void deleteUser_toFollow_byProfessionlUser()
    {

    }

    public void addCompany_toFollow_byProfessionlUser()
    {

    }

    public void deleteCompany_toFollow_byProfessionlUser()
    {

    }

    public void addProfessionalUser_toFollow_byProfessionlUser()
    {

    }

    public void deleteProfessionalUser_toFollow_byProfessionlUser()
    {

    }

    public void deleteProfessionlUser()
    {

    }


    //CRUD Company

    public void addCompany()
    {

    }

    public void deleteCompany()
    {

    }



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
