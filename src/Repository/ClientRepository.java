package Repository;

import DAL.ConnectionManager;
import model.Client;
import model.DataModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepository {
    private DataModel model;
    private Connection conn;
    public ClientRepository(DataModel model) {
        this.model = model;
    }
    public ClientRepository(){
        this.conn = ConnectionManager.getConnection();
    }

    public Client[] Get(){
        Statement stmt = null;
        ResultSet rs = null;
        List<Client> ret = new ArrayList<>();
        try {
            String sql = "Select * from Client";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while(rs.next()){
                ret.add(new Client(rs.getInt("clientID"), rs.getString("fname"), rs.getString("lname"),
                        rs.getString("email"),rs.getString("phone"), rs.getString("street"), rs.getString("city"),
                        rs.getString("state"), rs.getInt("zip")));
            }
        }catch (SQLException ex) {
            System.out.println("Error running Client Get statement");
            ex.printStackTrace();
        }
        Client[] cli = new Client[ret.size()];
        cli = ret.toArray(new Client[ret.size()]);
        return cli;
    }
}
