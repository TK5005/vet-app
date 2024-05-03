package Repository;

import DAL.ConnectionManager;
import model.Specialty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpecialtyRepository {
//    private final Connection conn;
//
//    public SpecialtyRepository(){conn = ConnectionManager.getConnection();}

    public Specialty[] getSpecialtiesByVetID(int vetID){
        String sql
                = "SELECT * FROM SPECIALTIES WHERE empID = ?";
        List<Specialty> ret = new ArrayList<>();
        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement get = conn.prepareStatement(sql)){
            get.setInt(1,vetID);

            ResultSet rs = get.executeQuery();

            while (rs.next()){
                Specialty add = new Specialty();
                add.setEmpID(rs.getInt("empID"));
                add.setSpecialty(rs.getString("name"));

                ret.add(add);
            }
        }catch (SQLException ex) {
            System.err.println("Error running Specialty Get statement");
            ex.printStackTrace();
        }
        return ret.toArray(new Specialty[0]);
    }

    public void addSpecialty(Specialty mod){
        String sql =
                "INSERT INTO SPECIALTIES VALUES(?,?)";

        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement create = conn.prepareStatement(sql)){
            create.setInt(1, mod.getEmpID());
            create.setString(2,mod.getSpecialty());

            create.executeUpdate();
            conn.commit();
        }catch (SQLException ex) {
            System.err.println("Error inserting Specialty entry");
            ex.printStackTrace();
        }
    }
    public void deleteSpecialty(Specialty mod){
        String sql
                = "DELETE FROM SPECIALTIES WHERE empID = ? AND name = ?";

        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement delete = conn.prepareStatement(sql)){
            delete.setInt(1,mod.getEmpID());
            delete.setString(2,mod.getSpecialty());

            delete.executeUpdate();
            conn.commit();
        }catch (SQLException ex) {
            System.err.println("Error deleting Specialty entry");
            ex.printStackTrace();
        }
    }
}
