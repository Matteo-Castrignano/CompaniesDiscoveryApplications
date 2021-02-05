package Neo4j;

import org.neo4j.driver.*;

public class Neo4jDatabaseAccess{

    protected static Driver driver;

    public static void initDriver() {
        driver = GraphDatabase.driver( "neo4j://localhost:7687", AuthTokens.basic( "neo4j", "root" ) );
    }

    public static void close() throws Exception {
        driver.close();
    }
}
