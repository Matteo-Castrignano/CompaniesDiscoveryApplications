package Neo4j;

import org.neo4j.driver.*;

public class Neo4jDatabaseAccess implements AutoCloseable{

    protected static Driver driver;

    public Neo4jDatabaseAccess() {
        driver = GraphDatabase.driver( "neo4j://localhost:7687", AuthTokens.basic( "neo4j", "root" ) );
    }

    public void close() throws Exception {
        driver.close();
    }
}
