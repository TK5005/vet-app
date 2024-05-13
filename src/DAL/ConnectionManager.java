package DAL;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;


public class ConnectionManager {

    private static Connection con;
    private static Properties prop;
    public static Connection getConnection(){
        try{
            Class.forName(prop.getProperty("driver"));
            try{
                con = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
                con.setAutoCommit(false);
            }catch(SQLException ex){
                System.out.println("Failed to create the database connection");
            }
        }catch (ClassNotFoundException ex){
            System.out.println("Driver not found");
        }
        return con;
    }

    public static void populateProps(){
        prop = new Properties();
        try {
            prop.load(ConnectionManager.class.getResourceAsStream("/config.properties"));
        }catch(IOException ex){
            System.out.println("Error Reading Config File");
            ex.printStackTrace();
        }
    }

}
