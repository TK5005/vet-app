package Repository;

import DAL.ConnectionManager;
import model.Certification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CertificationRepository {
//    private final Connection conn;
//
//    public CertificationRepository(){conn = ConnectionManager.getConnection();}

    public Certification[] getCertsByTechID(int techID){
        String sql
                = "SELECT * FROM CERTIFICATIONS WHERE empID = ?";
        List<Certification> ret = new ArrayList<>();
        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement get = conn.prepareStatement(sql)){
            get.setInt(1,techID);

            ResultSet rs = get.executeQuery();

            while (rs.next()){
                Certification add = new Certification();
                add.setEmpID(rs.getInt("empID"));
                add.setCertification(rs.getString("name"));

                ret.add(add);
            }
        }catch (SQLException ex) {
            System.err.println("Error running Certification Get statement");
            ex.printStackTrace();
        }
        return ret.toArray(new Certification[0]);
    }

    public void addCertification(Certification mod){
        String sql =
                "INSERT INTO CERTIFICATIONS VALUES(?,?)";

        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement create = conn.prepareStatement(sql)){
            create.setInt(1, mod.getEmpID());
            create.setString(2,mod.getCertification());

            create.executeUpdate();
            conn.commit();
        }catch (SQLException ex) {
            System.err.println("Error inserting Certification entry");
            ex.printStackTrace();
        }
    }
    public void deleteCertification(Certification mod){
        String sql
                = "DELETE FROM CERTIFICATIONS WHERE empID = ? AND name = ?";

        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement delete = conn.prepareStatement(sql)){
            delete.setInt(1,mod.getEmpID());
            delete.setString(2,mod.getCertification());

            delete.executeUpdate();
            conn.commit();
        }catch (SQLException ex) {
            System.err.println("Error deleting Certification entry");
            ex.printStackTrace();
        }
    }
}
