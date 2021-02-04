package Neo4j;

import Entities.Companies;
import Entities.ProfessionalUser;
import Entities.User;
import org.neo4j.driver.*;


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

    public void addUser(User u)//OK
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "CREATE(u:User{ username: $username, password: $password, name:$name, surname: $surname," +
                                "birth_Date: $date, gender: $gender, email: $email, country: $country, type_user:2 })",
                        parameters( "username", u.getUsername(), "password", u.getPassword(), "name", u.getName(),
                                "surname", u.getSurname(), "date", u.getDateOfBirth(), "gender", u.getGender(),
                                "email", u.getEmail(), "country", u.getCountry()) );
                return null;
            });
        }

    }

    public void readUser_byUsername(String username)//OK
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

    public void addUser_toFollow(String username1, String username2)//OK
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MATCH(u1:User) WHERE u1.username = $username1 MATCH(u2:User) WHERE u2.username = $username2 " +
                        "CREATE(u1)-[:FOLLOW]->(u2)", parameters("username1", username1, "username2", username2 ));
                return null;
            });
        }
    }

    public void unfollow_User(String username1, String username2)//OK
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MATCH(u1:User {username:$username1})-[f:FOLLOW]->(u2:User {username:$username2}) DELETE f",
                        parameters("username1", username1, "username2", username2 ));
                return null;
            });
        }
    }

    public void followCompany_byUser(String username, String symbol)//OK
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

    public void unfollowCompany_byUser(String username, String symbol)//OK
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

    public void followProfessionalUser_byUser(String username, String username_pf)//OK
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

    public void unfollowProfessionalUser_byUser(String username, String username_pf)//OK
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MATCH(u1:User {username:$username})-[f:FOLLOW]->(p:Professional_User {username:$username_pf})" +
                                "MATCH(u1:User {username:$username})-[r:RATE]->(p:Professional_User {username:$username_pf}) DELETE f,r",
                        parameters("username", username, "username_pf", username_pf ));
                return null;
            });
        }
    }

    public void rate_ProfessionalUser(String username, String username_pf, int vote)//OK
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MATCH (u:User {username:$username}) MATCH (p:Professional_User{username:$username_pf})" +
                                "MERGE (u)-[:FOLLOW]->(p) MERGE (u)-[r:RATE]->(p) ON CREATE SET r.vote = $vote ON MATCH SET r.vote = $vote",
                        parameters("username", username, "username_pf", username_pf, "vote", vote ));
                return null;
            });
        }
        UpdateRate_ProfessionalUser(username_pf);
    }

    private void UpdateRate_ProfessionalUser(String username_pf)//OK
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MATCH()-[r:RATE]->(p:Professional_User) WHERE p.username = $username_pf WITH p, round(avg(r.vote), 1) as value SET p.avg_Rating = value",
                        parameters( "username_pf", username_pf ));
                return null;
            });
        }
    }


    public void deleteUser_byUsername(String username)//OK
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

    public void addProfessionlUser(ProfessionalUser pu)//OK
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "CREATE(p:Professional_User{ username: $username, password: $password, name:$name, surname: $surname, " +
                                "birth_Date: $date, gender: $gender, email: $email, country: $country, profession: $profession, " +
                                "specializationSector: $sector, avg_Rating: $rate, type_user:1 })",
                        parameters( "username", pu.getUsername(), "password", pu.getPassword(), "name", pu.getName(),
                                "surname", pu.getSurname(), "date", pu.getDateOfBirth(), "gender", pu.getGender(),
                                "email", pu.getEmail(), "country", pu.getCountry(), "profession", pu.getProfession(),
                                "sector", pu.getSpecializationSector(), "rate", 0) );
                return null;
            });
        }
    }

    public void readProfessionalUser_byUsername(String username)//OK
    {
        try ( Session session = driver.session() )
        {
            ProfessionalUser pu = session.readTransaction((TransactionWork<ProfessionalUser>)tx -> {
                Result result = tx.run( "MATCH(p:Professional_User) WHERE p.username = $username RETURN p",
                        parameters("username", username));
                Value v = result.single().get(0);

                ProfessionalUser pr = new ProfessionalUser(v.get("username").asString(), v.get("password").asString(), v.get("name").asString(),
                        v.get("surname").asString(), v.get("birth_Date").asString(), v.get("gender").asString().charAt(0),
                        v.get("email").asString(), v.get("country").asString(), v.get("specialized_sector").asString(),
                         v.get("profession").asString(), v.get("avg_Rating").asDouble());
                return pr;
            });
            System.out.println(pu.toString());
        }
    }

    public void followCompany_byProfessionalUser(String username, String symbol)//OK
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MATCH(u1:Professional_User) WHERE u1.username = $username MATCH(c:Company) WHERE c.symbol = $symbol CREATE(u1)-[:WATCHLIST]->(c)",
                        parameters("username", username, "symbol", symbol));
                return null;
            });
        }
    }

    public void unfollowCompany_byProfessionalUser(String username, String symbol)//OK
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MATCH(u1:Professional_User {username:$username})-[w:WATCHLIST]->(c:Company {symbol:$symbol}) DELETE w",
                        parameters("username", username, "symbol", symbol));
                return null;
            });
        }
    }

    public void deleteProfessionalUser(String username)//OK
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

    public void addCompany(Companies c)//OK
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "CREATE(c:Company{ symbol: $symbol, name: $name, exchange: $exchange, fullTimeEmployees: $fte, " +
                                "description: $description, city: $city, phone: $phone, state: $state, country: $country, " +
                                "address: $address, website: $website, sector: $sector })",
                        parameters( "symbol", c.getSymbol(), "name", c.getName(), "exchange", c.getExchange(),
                                    "sector", c.getSector(), "fte", c.getFullTimesemployees(), "description", c.getDescription(),
                                    "city", c.getCity(), "phone", c.getPhone(), "state", c.getState(), "country", c.getCountry(),
                                    "address", c.getAddress(), "website", c.getWebsite() ));
                return null;
            });
        }
    }

    public void readCompany_bySymbol(String symbol)//OK
    {
        try ( Session session = driver.session() )
        {
            Companies c = session.readTransaction((TransactionWork<Companies>)tx -> {
                Result result = tx.run( "MATCH(c:Company) WHERE c.symbol = $symbol RETURN c",
                        parameters("symbol", symbol));
                Value v = result.single().get(0);

                Companies c1 = new Companies( v.get("symbol").asString(), v.get("name").asString(), v.get("exchange").asString(),
                        v.get("sector").asString(), v.get("fullTimeEmployees").asInt(), v.get("description").asString(), v.get("city").asString(),
                        v.get("phone").asString(), v.get("state").asString(), v.get("country").asString(), v.get("address").asString(),
                        v.get("website").asString(), null );
                return c1;
            });
            System.out.println(c.toString2());
        }
    }

    public void deleteCompany_bySymbol(String symbol)//
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

    public void addAdmin(String username)//OK
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MATCH(u:User) WHERE u.username = $username SET u.type_user=0 ",
                        parameters("username", username));
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
            //neo4j.readUser_byUsername("prova");
            //neo4j.addUser_toFollow("prova", "cum3");
            //neo4j.followCompany_byUser("prova","AAPL");
            //neo4j.followProfessionalUser_byUser("prova","ad3");
            //neo4j.rate_ProfessionalUser("cristina23", "Giancarlo", 4);
            //neo4j.rate_ProfessionalUser("prova", "Giancarlo", 2);
            //neo4j.unfollow_User("prova","cum3");
            //neo4j.unfollowCompany_byUser("prova","AAPL");
            //neo4j.unfollowProfessionalUser_byUser("prova","ad3");
            //neo4j.deleteUser_byUsername("prova");

            //ProfessionalUser pu = new ProfessionalUser( "Giancarlo", "prova", "prova", "prova","prova", 'M', "prova", "prova", "p1", "p2", 2.5);
            //neo4j.addProfessionlUser(pu);
            //neo4j.readProfessionalUser_byUsername("Giancarlo");
            //neo4j.followCompany_byProfessionalUser("Giancarlo","AAPL");
            //neo4j.unfollowCompany_byProfessionalUser("Giancarlo","AAPL");
            //neo4j.deleteProfessionalUser("Giancarlo");

            //Companies c = new Companies("PROVA","Pippo","NYS","info", 2, "livorno", "123", "ita", "toscana", "ar", "afsasf", "www", null);
            //neo4j.addCompany(c);
            //neo4j.readCompany_bySymbol("PROVA");
            //neo4j.deleteCompany_bySymbol("PROVA");

            //neo4j.addAdmin("prova");

        }
    }
}


