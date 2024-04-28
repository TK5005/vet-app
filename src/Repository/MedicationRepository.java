package Repository;

import DAL.ConnectionManager;
import model.Medication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicationRepository {

    private final Connection conn;

    public MedicationRepository(){this.conn = ConnectionManager.getConnection();}

    public Medication[] getAll(){
        String sql = "select * from medication join inventory on medID = itemID";
        List<Medication> ret = new ArrayList<>();

        try(PreparedStatement get = conn.prepareStatement(sql)){
            ResultSet rs = get.executeQuery();

            while(rs.next()){
                Medication add = new Medication();
                add.setName(rs.getString("name"));
                add.setItemID(rs.getInt("medID"));
                add.setInteractions(rs.getString("interactions"));

                ret.add(add);
            }
        }catch (SQLException ex) {
            System.err.println("Error running Pet Get statement");
            ex.printStackTrace();
        }
        return ret.toArray(new Medication[0]);
    }

    public Medication[] getAllInStock(){
        String sql = "select * from medication join inventory on medID = itemID where quantity > 0";
        List<Medication> ret = new ArrayList<>();

        try(PreparedStatement get = conn.prepareStatement(sql)){
            ResultSet rs = get.executeQuery();

            while(rs.next()){
                Medication add = new Medication();
                add.setName(rs.getString("name"));
                add.setItemID(rs.getInt("medID"));
                add.setInteractions(rs.getString("interactions"));

                ret.add(add);
            }
        }catch (SQLException ex) {
            System.err.println("Error running Pet Get statement");
            ex.printStackTrace();
        }
        return ret.toArray(new Medication[0]);
    }

    public Medication getSpecificMedication(int medID){
        String sql = "select * from medication join inventory on medID = itemID where medID = ?";
        Medication ret = null;

        try(PreparedStatement get = conn.prepareStatement(sql)){
            get.setInt(1, medID);
            ResultSet rs = get.executeQuery();

            while(rs.next()){
                ret = new Medication();
                ret.setName(rs.getString("name"));
                ret.setItemID(rs.getInt("medID"));
                ret.setInteractions(rs.getString("interactions"));

            }
        }catch (SQLException ex) {
            System.err.println("Error running Pet Get statement");
            ex.printStackTrace();
        }
        return ret;
    }

    public Medication addMedication(Medication mod){
        String sql =
                "INSERT INTO MEDICATION VALUES(?,?,?)";

        try(PreparedStatement create = conn.prepareStatement(sql)){
            create.setInt(1, mod.getItemID());
            create.setString(2, mod.getName());
            create.setString(3, mod.getInteractions());

            conn.commit();
        }catch (SQLException ex) {
            System.err.println("Error inserting Medication entry");
            ex.printStackTrace();
            try {
                System.err.println("Rolling back changes");
                conn.rollback();
            } catch (SQLException e) {
                System.err.println("Error rolling back medication changes");
                e.printStackTrace();
            }
        }
        return mod;
    }

    public void updateMedication(Medication mod){
        String sql =
                "UPDATE MEDICATION SET name = ?, interaction = ? where medID = ?";

        try(PreparedStatement update = conn.prepareStatement(sql)){
            update.setString(1, mod.getName());
            update.setString(2, mod.getInteractions());
            update.setInt(3, mod.getItemID());

            update.executeUpdate();
            conn.commit();
        }catch (SQLException ex) {
            System.err.println("Error inserting Medication entry");
            ex.printStackTrace();
            try {
                System.err.println("Rolling back changes");
                conn.rollback();
            } catch (SQLException e) {
                System.err.println("Error rolling back medication changes");
                e.printStackTrace();
            }
        }
    }
}
