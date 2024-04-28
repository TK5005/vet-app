package DAL;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;


public class ConnectionManager {

    private static Connection con;
    private static String urlstring;
    public static Connection getConnection(){
        try{
            Properties prop = new Properties();
            prop.load(new FileInputStream("Resources/config.properties"));
            Class.forName(prop.getProperty("driver"));
            try{
                con = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
                con.setAutoCommit(false);
            }catch(SQLException ex){
                System.out.println("Failed to create the database connection");
            }
        }catch (ClassNotFoundException ex){
            System.out.println("Driver not found");
        }catch(IOException ex){
            System.out.println("Error Reading Config File");
            ex.printStackTrace();
        }
        return con;
    }

}
