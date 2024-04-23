package DAL;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;


public class ConnectionManager {

    //temporary values for local db
    private static String url = "jdbc:mysql://localhost:3306/vetapp";
    private static String driverName = "com.mysql.cj.jdbc.Driver";
    private static String username = "drodri";
    private static String password = "P4$$w0rd";
    private static Connection con;
    private static String urlstring;
    public static Connection getConnection(){
        try{
            Class.forName(driverName);
            try{
                con = DriverManager.getConnection(url, username, password);
                con.setAutoCommit(false);
            }catch(SQLException ex){
                System.out.println("Failed to create the database connection");
            }
        }catch (ClassNotFoundException ex){
            System.out.println("Driver not found");
        }
        return con;
    }

}
