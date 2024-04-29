package Repository;

import DAL.ConnectionManager;
import model.Pet;
import model.Treatment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TreatmentRepository {
    private final Connection conn;

    public TreatmentRepository(){this.conn = ConnectionManager.getConnection();}

    public Treatment[] getTreatmentsByExamID(int examID){
        String sql = "select * from treatment where examID = ?";
        List<Treatment> ret = new ArrayList<>();
        try(PreparedStatement get = conn.prepareStatement(sql)){
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

    public Treatment getSpecificTreatment(int treatmentID){
        String sql = "select * from treatment where treatmentID = ?";
        Treatment ret = null;

        try (PreparedStatement get = conn.prepareStatement(sql)) {
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

            }
        } catch (SQLException ex) {
            System.err.println("Error running Treatment Get statement");
            ex.printStackTrace();
        }
        return ret;
    }

    public Treatment addTreatment(Treatment mod, int vetID){
        String sql
                = "INSERT INTO TREATMENT (examID, medID, type, startDate, endDate, directions) " +
                "VALUES(?, ?, ?, ?, ?, ?)";
        try(PreparedStatement create = conn.prepareStatement(sql)){
            create.setInt(1,mod.getExamID());
            create.setInt(2,mod.getMedicationID());
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
            try {
                System.err.println("Rolling back changes");
                conn.rollback();
            } catch (SQLException e) {
                System.err.println("Error rolling back pet changes");
                e.printStackTrace();
            }
        }
        return mod;
    }

    public void updateTreatment(Treatment mod, int vetID){
        String sql
                = "UPDATE TREATMENT SET medID = ?, type = ?, startDate = ?, endDate = ?, directions = ? " +
                "WHERE trmntID = ?";

        try (PreparedStatement update = conn.prepareStatement(sql)){
            update.setInt(1,mod.getMedicationID());
            update.setString(2, mod.getTreatmentTypeString());
            update.setDate(3, java.sql.Date.valueOf(mod.getStartDate()));
            update.setDate(4, java.sql.Date.valueOf(mod.getEndDate()));
            update.setString(5, mod.getDirections());

            update.executeUpdate();
            conn.commit();
        }catch (SQLException ex) {
            System.err.println("Error updating Treatment entry");
            ex.printStackTrace();
            try {
                System.err.println("Rolling back changes");
                conn.rollback();
            } catch (SQLException e) {
                System.err.println("Error rolling back Treatment changes");
                e.printStackTrace();
            }
        }
    }

    public Treatment[] getVaccinationsByPetID(int petID){
        String sql
                = "SELECT * FROM TREATMENT t WHERE t.type = 'VACCINE' AND " +
                " t.examID in (SELECT examID FROM EXAMINATION WHERE petID = ?)";

        List<Treatment> ret = new ArrayList<>();
        try(PreparedStatement get = conn.prepareStatement(sql)){

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

}