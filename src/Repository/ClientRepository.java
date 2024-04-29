package Repository;

import DAL.ConnectionManager;
import model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepository {
    private final Connection conn;

    public ClientRepository() {
        this.conn = ConnectionManager.getConnection();
    }

    //Get statement to retrieve all clients
    public Client[] getAll() {
        String sql = "Select * from Client";
        List<Client> ret = new ArrayList<>();
        try (PreparedStatement get = conn.prepareStatement(sql)) {

            ResultSet rs = get.executeQuery();
            while (rs.next()) {
                ret.add(new Client(rs.getInt("clientID"), rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("email"), rs.getString("phone"), rs.getString("street"), rs.getString("city"),
                        rs.getString("state"), rs.getInt("zip")));
            }
        } catch (SQLException ex) {
            System.err.println("Error running Client Get statement");
            ex.printStackTrace();
        }
        return ret.toArray(new Client[0]);
    }

    public Client getSpecificClient(int clientID){
        String sql = "Select * from Client where clientID = ?";
        Client ret = new Client();

        try(PreparedStatement get = conn.prepareStatement(sql)){
            get.setInt(1,clientID);
            ResultSet rs = get.executeQuery();
            while(rs.next()){
                ret.setClientID(rs.getInt("clientID"));
                ret.setFirstName(rs.getString(("firstName")));
                ret.setLastName(rs.getString("lastName"));
                ret.setEmail(rs.getString("email"));
                ret.setPhone(rs.getString("phone"));
                ret.setStreet(rs.getString("street"));
                ret.setCity(rs.getString("city"));
                ret.setState(rs.getString("state"));
                ret.setZip(rs.getInt("zip"));
            }
        }catch (SQLException ex) {
            System.err.println("Error running Client Get statement");
            ex.printStackTrace();
        }
        return ret;
    }

    //Inserts a new client into client table and returns the given client model with the new ClientID
    public Client createClient(Client mod) {
        String createSQL
                = "INSERT INTO CLIENT (firstName, lastName, email, phone, street, city, state, zip)" +
                "VALUES(? , ? , ? , ? , ? , ? , ? , ? )";
        try (PreparedStatement create = conn.prepareStatement(createSQL)) {
            create.setString(1,mod.getFirstName());
            create.setString(2,mod.getLastName());
            create.setString(3,mod.getEmail());
            create.setString(4,mod.getPhone());
            create.setString(5,mod.getStreet());
            create.setString(6,mod.getCity());
            create.setString(7,mod.getState());
            create.setInt(8,mod.getZip());

            create.executeUpdate();
            ResultSet rs = create.getGeneratedKeys();
            while(rs.next()){
                mod.setClientID(rs.getInt(1));
            }
            conn.commit();
        } catch (SQLException ex) {
            System.err.println("Error inserting Client entry");
            ex.printStackTrace();
            try {
                System.err.println("Rolling back changes");
                conn.rollback();
            } catch (SQLException e) {
                System.err.println("Error rolling back client changes");
                e.printStackTrace();
            }
        }
        return mod;
    }

    public void updateClient(Client mod) {
        String updateSQL
                = "UPDATE CLIENT SET firstName = ? , lastName = ? , email = ? , phone = ? , street = ? , city = ? , " +
                "state = ? , zip = ?  WHERE clientID = ?";
        try (PreparedStatement update = conn.prepareStatement(updateSQL)) {
            update.setString(1, mod.getFirstName());
            update.setString(2, mod.getLastName());
            update.setString(3, mod.getEmail());
            update.setString(4, mod.getPhone());
            update.setString(5, mod.getStreet());
            update.setString(6, mod.getCity());
            update.setString(7, mod.getState());
            update.setInt(8, mod.getZip());
            update.setInt(9, mod.getClientID());

            update.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            System.err.println("Error updating Client entry");
            ex.printStackTrace();
            try {
                System.err.println("Rolling back changes");
                conn.rollback();
            } catch (SQLException e) {
                System.err.println("Error rolling back client changes");
                e.printStackTrace();
            }
        }
    }
}
