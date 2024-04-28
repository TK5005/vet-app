package Repository;

import DAL.ConnectionManager;
import model.Prescription;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionRepository {
    private final Connection conn;

    public PrescriptionRepository(){conn = ConnectionManager.getConnection();}

    public Prescription[] getAll(){
        String sql = "select * from Prescription";
        List<Prescription> ret = new ArrayList<>();

        try(PreparedStatement get = conn.prepareStatement(sql)){
            ResultSet rs = get.executeQuery();

            while(rs.next()){
                Prescription add = new Prescription();
                add.setEmpID(rs.getInt("empID"));
                add.setMedID(rs.getInt("medID"));
                add.setTreatmentID(rs.getInt("trmntID"));

                ret.add(add);
            }

        }catch (SQLException ex) {
            System.err.println("Error running Prescription Get statement");
            ex.printStackTrace();
        }
        return ret.toArray(new Prescription[0]);
    }

    public Prescription[] getPrescriptionsByVet(int vetID){
        String sql = "select * from Prescription where empID = ?";
        List<Prescription> ret = new ArrayList<>();

        try(PreparedStatement get = conn.prepareStatement(sql)){
            get.setInt(1,vetID);
            ResultSet rs = get.executeQuery();

            while(rs.next()){
                Prescription add = new Prescription();
                add.setEmpID(rs.getInt("empID"));
                add.setMedID(rs.getInt("medID"));
                add.setTreatmentID(rs.getInt("trmntID"));

                ret.add(add);
            }

        }catch (SQLException ex) {
            System.err.println("Error running Prescription Get statement");
            ex.printStackTrace();
        }
        return ret.toArray(new Prescription[0]);
    }
    private void addPrescription(int vetID, int medID, int treatmentID){
        String sql
                = "INSERT INTO PRESCRIPTION (empID, medID, trmntID) " +
                "VALUES(?, ?, ?)";
        try(PreparedStatement create = conn.prepareStatement(sql)){
            create.setInt(1,vetID);
            create.setInt(2,medID);
            create.setInt(3,treatmentID);

            create.executeQuery();
            conn.commit();
        }catch (SQLException ex) {
            System.err.println("Error inserting prescription entry");
            ex.printStackTrace();
            try {
                System.err.println("Rolling back changes");
                conn.rollback();
            } catch (SQLException e) {
                System.err.println("Error rolling back prescription changes");
                e.printStackTrace();
            }
        }
    }

    //Checks to see if the current medication is prescribed for the given treatment
    //if the medication has been changed, we add a new entry to the prescription table for the same treatment
    //since it's vital to track all prescribed medications
    public void addOrUpdatePrescription(int vetID, int medID, int treatmentID){
        String sql
                = "SELECT * FROM PRESCRIPTION WHERE trmntID = ?";

        try(PreparedStatement get = conn.prepareStatement(sql)){
            get.setInt(1,treatmentID);
            ResultSet rs = get.executeQuery();

            boolean medChange = true;
            while(rs.next()){
                if(rs.getInt("medID") == medID)
                    medChange = false;
            }

            if(medChange){
                addPrescription(vetID, medID, treatmentID);
            }

        }catch (SQLException ex) {
            System.err.println("Error running Treatment Prescription Get statement");
            ex.printStackTrace();
        }
    }
}
