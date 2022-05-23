package db_access;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;


public class DBConnection {

//	private static final String driver = "jdbc:sqlserver://localhost:1433";
//    private static final String  databaseName = ";databaseName=project";
//    
//    private static String  userName = ";user=ted";
//    private static String password = ";password=andreiteodor";
    private static final String  driver = "jdbc:sqlserver://hildur.ucn.dk:1433";
    private static final String  databaseName = ";databaseName=CSC-CSD-S212_10407008";

    private static final String userName = ";user=CSC-CSD-S212_10407008";
    private static final String password = ";password=Password1!";
    private static final String encryption = ";encrypt=false;";

    private DatabaseMetaData dma;
    private static Connection con;


    private static DBConnection  instance = null;


    private DBConnection()
    {
        // Connection string
        String url = driver + databaseName + userName + password + encryption;

        try{
            // Returns an instance of the Class named as the parameter
            // If this fails it means that the driver could not be found
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("Driver class loaded ok");

        }
        catch(Exception e){
            System.out.println("Cannot find the driver");
            System.out.println(e.getMessage());
        }
        try{
            // we get the connection and store it in a Connection instance using the driver manager
            con = DriverManager.getConnection(url);
            // setting the connection to auto-commit to the database (this is true by default)
            con.setAutoCommit(true);
            // Following JDBC program establishes a connection with the database and retrieves information about the underlying database such as name of the database, driver name, URL etc…
            dma = con.getMetaData();
            // these print URL, driver name and db product name
            System.out.println("Connection to " + dma.getURL());
            System.out.println("Driver " + dma.getDriverName());
            System.out.println("Database product name " + dma.getDatabaseProductName());
        }
        catch(Exception e){
            System.out.println("Problems with the connection to the database:");
            System.out.println(e.getMessage());
            System.out.println(url);
        }
    }

    // When you are done using your Connection, you need to explicitly close it by calling its close() method in order to release any other database resources (cursors, handles, etc.) the connection may be holding on to.
    public static void closeConnection()
    {
        try{
            con.close();
            instance= null;
            System.out.println("The connection is closed");
        }
        catch (Exception e){
            System.out.println("Error trying to close the database " +  e.getMessage());
        }
    }

    // Singleton pattern (we use it to not mess up with the UCN server if we make a mistake)
    public Connection getDBCon()
    {
        return con;
    }

    public static boolean instanceIsNull()
    {
        return (instance == null);
    }

    public static DBConnection getInstance()
    {
        if (instance == null)
        {
            instance = new DBConnection();
        }
        return instance;
    }

}//end DbConnection