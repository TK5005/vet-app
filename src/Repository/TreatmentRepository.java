package Repository;

import DAL.ConnectionManager;
import model.Pet;
import model.Treatment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TreatmentRepository {
//    private final Connection conn;
//
//    public TreatmentRepository(){this.conn = ConnectionManager.getConnection();}

    public Treatment[] getTreatmentsByExamID(int examID){
        String sql = "SELECT * FROM TREATMENT WHERE examID = ?";
        List<Treatment> ret = new ArrayList<>();
        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement get = conn.prepareStatement(sql)){
            get.setInt(1,examID);
            ResultSet rs = get.executeQuery();

            while(rs.next()){
                Treatment add = new Treatment();
                add.setExamID(rs.getInt("examID"));
                add.setTreatmentID(rs.getInt("trmntID"));
                add.setType(Treatment.TreatType.valueOf(rs.getString("type")));
                add.setMedicationID(rs.getInt("medID"));
                add.setStartDate(rs.getDate("startDate").toLocalDate());
                add.setEndDate(rs.getDate("endDate").toLocalDate());
                add.setDirections(rs.getString("directions"));

                ret.add(add);
            }
        }catch (SQLException ex) {
            System.err.println("Error running Treatment Get statement");
            ex.printStackTrace();
        }
        return ret.toArray(new Treatment[0]);
    }

    // Changed treatmentID to trmntID in the query - KS
    // Added ret = add to return the treatment object - KS
    public Treatment getSpecificTreatment(int treatmentID){
        String sql = "SELECT * FROM TREATMENT WHERE trmntID = ?";
        Treatment ret = null;

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement get = conn.prepareStatement(sql)) {
            get.setInt(1, treatmentID);
            ResultSet rs = get.executeQuery();
            while (rs.next()) {
                Treatment add = new Treatment();
                add.setExamID(rs.getInt("examID"));
                add.setTreatmentID(rs.getInt("trmntID"));
                add.setType(Treatment.TreatType.valueOf(rs.getString("type")));
                add.setMedicationID(rs.getInt("medID"));
                add.setStartDate(rs.getDate("startDate").toLocalDate());
                add.setEndDate(rs.getDate("endDate").toLocalDate());
                add.setDirections(rs.getString("directions"));
                ret = add;
            }
            
        } catch (SQLException ex) {
            System.err.println("Error running Treatment Get statement");
            ex.printStackTrace();
        }
        return ret;
    }

    public Treatment addTreatment(Treatment mod){
        String sql
                = "INSERT INTO TREATMENT (examID, medID, type, startDate, endDate, directions) " +
                "VALUES(?, ?, ?, ?, ?, ?)";
        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement create = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            create.setInt(1,mod.getExamID());
            if (mod.getMedicationID() == null) create.setNull(2, Types.INTEGER); else  create.setInt(2,mod.getMedicationID());
            create.setString(3,mod.getTreatmentTypeString());
            create.setDate(4,java.sql.Date.valueOf(mod.getStartDate()));
            create.setDate(5,java.sql.Date.valueOf(mod.getEndDate()));
            create.setString(6,mod.getDirections());

            create.executeUpdate();

            ResultSet rs = create.getGeneratedKeys();
            while(rs.next()){
                mod.setTreatmentID(rs.getInt(1));
            }

            conn.commit();
        }catch (SQLException ex) {
            System.err.println("Error inserting Pet entry");
            ex.printStackTrace();
        }
        return mod;
    }

    // Added treatmentID to the query - KS
    public void updateTreatment(Treatment mod){
        String sql
                = "UPDATE TREATMENT SET medID = ?, type = ?, startDate = ?, endDate = ?, directions = ? " +
                "WHERE trmntID = ?";

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement update = conn.prepareStatement(sql)){
            if (mod.getMedicationID() == null) update.setNull(1, Types.INTEGER); else  update.setInt(1,mod.getMedicationID());
            update.setString(2, mod.getTreatmentTypeString());
            update.setDate(3, java.sql.Date.valueOf(mod.getStartDate()));
            update.setDate(4, java.sql.Date.valueOf(mod.getEndDate()));
            update.setString(5, mod.getDirections());
            update.setInt(6, mod.getTreatmentID());
            update.executeUpdate();
            conn.commit();
        }catch (SQLException ex) {
            System.err.println("Error updating Treatment entry");
            ex.printStackTrace();
        }
    }

    public Treatment[] getVaccinationsByPetID(int petID){
        String sql
                = "SELECT * FROM TREATMENT t WHERE t.type = 'VACCINE' AND " +
                " t.examID in (SELECT examID FROM EXAMINATION WHERE petID = ?)";

        List<Treatment> ret = new ArrayList<>();
        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement get = conn.prepareStatement(sql)){

            get.setInt(1,petID);
            ResultSet rs = get.executeQuery();

            while(rs.next()){
                Treatment add = new Treatment();
                add.setTreatmentID(rs.getInt("trmntID"));
                add.setExamID(rs.getInt("examID"));
                add.setMedicationID(rs.getInt("medID"));
                add.setType(Treatment.TreatType.valueOf(rs.getString("type")));
                add.setStartDate(rs.getDate("startDate").toLocalDate());
                add.setEndDate(rs.getDate("endDate").toLocalDate());
                add.setDirections(rs.getString("directions"));

                ret.add(add);
            }
        }catch (SQLException ex) {
            System.err.println("Error running Treatment Get statement");
            ex.printStackTrace();
        }
        return ret.toArray(new Treatment[0]);
    }

    public void removeTreatment(int treatmentID){
        String sql = "DELETE FROM TREATMENT WHERE trmntID = ?";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement delete = conn.prepareStatement(sql)) {
            delete.setInt(1, treatmentID);
            delete.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            System.err.println("Error deleting Treatment entry");
            ex.printStackTrace();
        }
    }
}