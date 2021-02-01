package Neo4j;

import Entities.Companies;
import Entities.ProfessionalUser;
import Entities.User;
import org.neo4j.driver.*;


import java.util.Date;

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
                tx.run( "CREATE(u:User{ username: $username, password: $password, name:$name, surname: $surname," +
                                "birth_Date: $date, gender: $gender, email: $email, country: $country })",
                        parameters( "username", u.getUsername(), "password", u.getPassword(), "name", u.getName(),
                                "surname", u.getSurname(), "date", u.getDateOfBirth(), "gender", u.getGender(),
                                "email", u.getEmail(), "country", u.getCountry()) );
                return null;
            });
        }

    }

    public void readUser_byUsername(String username)
    {
        try ( Session session = driver.session() )
        {
           User u = session.readTransaction((TransactionWork<User>)tx -> {
                Result result = tx.run( "MATCH(u:User) WHERE u.username = $username RETURN u",
                        parameters("username", username));
               Value v = result.single().get(0);

                User u2 = new User(v.get("username").asString(), v.get("password").asString(), v.get("name").asString(),
                        v.get("surname").asString(), v.get("birth_Date").asString(), v.get("gender").asString().charAt(0),
                        v.get("email").asString(), v.get("country").asString());
                return u2;
            });
           System.out.println(u.toString());
        }
    }

    public void addUser_toFollow(String username1, String username2)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MATCH(u1:User) WHERE u1.username = $username MATCH(u2:User) WHERE u2.username = $username2 " +
                        "CREATE(u1)-[:FOLLOW]->(u2)", parameters("username1", username1, "username2", username2 ));
                return null;
            });
        }
    }

    public void unfollow_User(String username1, String username2)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MATCH(u1:User {username:$username})-[f:FOLLOW]->(u2:User {username:$usernameToUnfollow}) DELETE f",
                        parameters("username1", username1, "username2", username2 ));
                return null;
            });
        }
    }

    public void followCompany_byUser(String username, String symbol)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MATCH(u1:User) WHERE u1.username = $username MATCH(c:Company) WHERE c.symbol = $symbol CREATE(u1)-[:WATCHLIST]->(c)",
                        parameters("username", username, "symbol", symbol));
                return null;
            });
        }
    }

    public void unfollowCompany_byUser(String username, String symbol)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MATCH(u1:User {username:$username})-[w:WATCHLIST]->(c:Company {symbol:$symbol}) DELETE w",
                        parameters("username", username, "symbol", symbol));
                return null;
            });
        }
    }

    public void followProfessionalUser_byUser(String username, String username_pf)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MATCH(u1:User) WHERE u1.username = $username MATCH(p:Professional_User) WHERE p.username = $username_pf " +
                                "CREATE(u1)-[:FOLLOW]->(p)",
                        parameters("username", username, "username_pf", username_pf ));
                return null;
            });
        }
    }

    public void unfollowProfessionalUser_byUser(String username, String username_pf)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MATCH(u1:User {username:$username})-[f:FOLLOW]->(p:Professional_User {username:$username_pf}) DELETE f",
                        parameters("username", username, "username_pf", username_pf ));
                return null;
            });
        }
    }

    public void rate_ProfessionalUser(String username, String username_pf, int voto)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MATCH (u:User {username:$username}) MATCH (p:Professional_User{username:$username_pf})" +
                                "MATCH (u)-[:FOLLOW]->(p) MERGE (u)-[r:RATE]->(p) ON CREATE SET r.vote = $vote ON MATCH SET r.vote = $vote",
                        parameters("username", username, "username_pf", username_pf, "voto", voto ));
                return null;
            });
        }
    }

    public void deleteUser_byUsername(String username)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MATCH(u:User {username:$username}) DETACH DELETE u",
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
                tx.run( "CREATE(p:Professiona_User{ username: $username, password: $password, name:$name, surname: $surname, " +
                                "birth_Date: $date, gender: $gender, email: $email, country: $country, profession: $profession, " +
                                "specializationSector: $sector, avg_Rating: $rate })",
                        parameters( "username", pu.getUsername(), "password", pu.getPassword(), "name", pu.getName(),
                                "surname", pu.getSurname(), "date", pu.getDateOfBirth(), "gender", pu.getGender(),
                                "email", pu.getEmail(), "country", pu.getCountry(), "profession", pu.getProfession(),
                                "sector", pu.getSpecializationSector(), "rate", 0) );
                return null;
            });
        }
    }

    public void readProfessionlUser_byUsername(String username)
    {
        try ( Session session = driver.session() )
        {
            ProfessionalUser pu = session.readTransaction(tx -> {
                Result result = tx.run( "MATCH(p:Professional_User) WHERE p.username = $username RETURN p",
                        parameters("username", username ));
                return (ProfessionalUser) result.single().get(0).asEntity();
            });
            System.out.println(pu.toString());
        }
    }

   /* public void follow_User_byProfessionlUser(String username1, String username2) //da fare
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "", parameters("username1", username1, "username2", username2 ));
                return null;
            });
        }
    }

    public void unfollow_User_byProfessionlUser(String username1, String username2) //da fare
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
    }*/

    public void deleteProfessionlUser(String username)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MATCH(p:Professional_User {username:$username}) DETACH DELETE p",
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
                tx.run( "CREATE(c:Company{ symbol: $symbol name: $name, exchange: $exchange, fullTimeEmployees: $fte, " +
                                "description: $description, city: $city, phone: $phone, state: $state, country: $country, " +
                                "address: $address, website: $website, sector: $sector })",
                        parameters( "symbol", c.getSymbol(), "name", c.getName(), "exchange", c.getExchange(),
                                    "sector", c.getSector(), "fte", c.getFullTimesemployees(), "description", c.getDescription(),
                                    "city", c.getCity(), "phone", c.getPhone(), "state", c.getState(), "country", c.getCountry(),
                                    "address", c.getAddress(), "website", c.getWebsite()) );
                return null;
            });
        }
    }

    public void readCompany_bySymbol(String symbol)
    {
        try ( Session session = driver.session() )
        {
            Companies c = session.readTransaction(tx -> {
                Result result = tx.run( "MATCH(c:Company) WHERE c.symbol = $symbol return c",
                        parameters("symbol", symbol ));
                return (Companies) result.single().get(0).asEntity();
            });
            System.out.println(c.toString());
        }
    }

    public void deleteCompany_bySymbol(String symbol)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MATCH(c:Company {symbol:$symbol}) DETACH DELETE c",
                        parameters( "symbol", symbol));
                return null;
            });
        }
    }



    public static void main(String[] args) throws Exception
    {
        try ( CrudOperation neo4j = new CrudOperation( "neo4j://localhost:7687", "neo4j", "root" ) )
        {
            System.out.println("---------------------------");
            //User u = new User( "prova", "prova", "prova", "prova","prova", 'M', "prova", "prova");
            //neo4j.addUser(u);
            neo4j.readUser_byUsername("prova");
            //neo4j.addUser_toFollow("prova", "cum3");
            //neo4j.followCompany_byUser("prova","AAPL");
            //neo4j.followProfessionalUser_byUser("prova","ad3");
            //neo4j.rate_ProfessionalUser("prova", "ad3", 3);
        }
    }
}


