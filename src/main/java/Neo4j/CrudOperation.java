package Neo4j;

import Entities.*;
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

    public void addUser(User u)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "",
                        parameters( "username", u.getUsername(), "password", u.getPassword(), "name", u.getName(),
                                "surname", u.getSurname(), "dateofBirth", u.getDateOfBirth(), "gender", u.getGender(),
                                "email", u.getEmail(), "country", u.getCountry()) );
                return null;
            });
        }

    }

    public void readUser_byUsername(String username)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "", parameters( ));
                return null;
            });
        }
    }

    public void addUser_toFollow(String username1, String username2)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "", parameters("username1", username1, "username2", username2 ));
                return null;
            });
        }
    }

    public void unfollow_User(String username1, String username2)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "", parameters("username1", username1, "username2", username2 ));
                return null;
            });
        }
    }

    public void addCompany_toFollow(String username, String symbol)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "", parameters("username", username, "symbol", symbol));
                return null;
            });
        }
    }

    public void deleteCompany_toFollow(String username, String symbol)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "", parameters( "username", username, "symbol", symbol));
                return null;
            });
        }
    }

    public void addProfessionalUser_toFollow(String username, String username_pf)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "", parameters("username", username, "username_pf", username_pf ));
                return null;
            });
        }
    }

    public void deleteProfessionalUser_toFollow(String username, String username_pf)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "", parameters("username", username, "username_pf", username_pf ));
                return null;
            });
        }
    }

    public void rate_ProfessionalUser(String username, String username_pf, int voto)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "", parameters("username", username, "username_pf", username_pf, "voto", voto ));
                return null;
            });
        }
    }

    public void updateRate_ProfessionalUser(String username, String username_pf, int voto)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "", parameters("username", username, "username_pf", username_pf, "voto", voto ));
                return null;
            });
        }
    }

    public void deleteUser_byUsername(String username)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "",
                        parameters( "username", username));
                return null;
            });
        }
    }



    //CRUD Professional user

    public void addProfessionlUser(ProfessionalUser pu)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MERGE (p:Person {name: $name, from: $from, age: $age})",
                        parameters( "username", pu.getUsername(), "password", pu.getPassword(), "name", pu.getName(),
                                "surname", pu.getSurname(), "dateofBirth", pu.getDateOfBirth(), "gender", pu.getGender(),
                                "email", pu.getEmail(), "country", pu.getCountry(), "profession", pu.getProfession(),
                                "specializationSector", pu.getSpecializationSector(), "averageRating", 0) );
                return null;
            });
        }
    }

    public void readProfessionlUser_byUsername(String username)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "", parameters( ));
                return null;
            });
        }
    }

    public void follow_User_byProfessionlUser(String username1, String username2)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "", parameters("username1", username1, "username2", username2 ));
                return null;
            });
        }
    }

    public void unfollow_User_byProfessionlUser(String username1, String username2)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "", parameters("username1", username1, "username2", username2 ));
                return null;
            });
        }
    }

    public void addCompany_toAnalyze(String username_pf, String symbol)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "", parameters("username", username_pf, "symbol", symbol));
                return null;
            });
        }
    }

    public void removeCompany_toAnalyze(String username_pf, String symbol)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "", parameters( "username", username_pf, "symbol", symbol));
                return null;
            });
        }
    }

    public void deleteProfessionlUser(String username)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "",
                        parameters( "username", username));
                return null;
            });
        }
    }


    //CRUD Company

    public void addCompany(Companies c)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "",
                        parameters( "symbol", c.getSymbol(), "name", c.getName(), "exchange", c.getExchange(),
                                    "sector", c.getSector(), "fullTimesemployees", c.getFullTimesemployees(), "description", c.getDescription(),
                                    "city", c.getCity(), "phone", c.getPhone(), "state", c.getState(), "country", c.getCountry(),
                                    "address", c.getAddress(), "website", c.getWebsite()) );
                return null;
            });
        }
    }

    public void deleteCompany_bySymbol(String symbol)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "",
                        parameters( "symbol", symbol));
                return null;
            });
        }
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


