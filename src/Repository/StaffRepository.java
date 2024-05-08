package Repository;

import DAL.ConnectionManager;
import com.mysql.cj.x.protobuf.MysqlxPrepare;
import model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StaffRepository {

//    private DataModel model;
//    private Connection conn;
//    public StaffRepository(DataModel model) {
//        this.model = model;
//    }
//    public StaffRepository(){
//        this.conn = ConnectionManager.getConnection();
//    }
    public Staff[] Get(){
        String sql = "SELECT * FROM STAFF";
        List<Staff> ret = new ArrayList<>();
        try(Connection conn = ConnectionManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

           ResultSet rs = stmt.executeQuery();
            Staff staff = new Staff();
            while(rs.next()){
                staff.setEmpID(rs.getInt("empID"));
                staff.setFirstName(rs.getString("firstName"));
                staff.setLastName(rs.getString("lastName"));
                staff.setSex(rs.getString("sex")); 
                staff.setDob(rs.getObject("dob",LocalDate.class));
                staff.setSsn(rs.getString("ssn"));
                staff.setPhone(rs.getString("phone"));
                staff.setStreet(rs.getString("street"));
                staff.setCity(rs.getString("city"));
                staff.setState(rs.getString("state"));
                staff.setZip(Integer.parseInt(rs.getString("zip")));
                ret.add(staff);                        
            }
        }catch (SQLException ex) {
            System.out.println("Error running Staff Get statement");
            ex.printStackTrace();
        }
        return ret.toArray(new Staff[0]);
    }
    public Staff getStaff(int empID){
        String sql = "SELECT * FROM STAFF WHERE empID=?";
        Staff ret = new Staff();
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1,empID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                ret.setEmpID(rs.getInt("empID"));
                ret.setFirstName(rs.getString("firstName"));
                ret.setLastName(rs.getString("lastName"));
                ret.setSex(rs.getString("sex"));
                ret.setDob(rs.getObject("dob",LocalDate.class));
                ret.setSsn(rs.getString("ssn"));
                ret.setPhone(rs.getString("phone"));
                ret.setStreet(rs.getString("street"));
                ret.setCity(rs.getString("city"));
                ret.setState(rs.getString("state"));
                ret.setZip(Integer.parseInt(rs.getString("zip")));
            }
        }catch (SQLException ex) {
            System.out.println("Error running Staff Get statement");
            ex.printStackTrace();
        }
        return ret;

    }

    public Staff[] getGeneralStaff(){
        String sql= "SELECT * FROM STAFF WHERE empID NOT IN (SELECT empID FROM TECH) " +
                "AND empID NOT IN (SELECT empID FROM VET)";
        List<Staff> ret = new ArrayList<>();
        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement get = conn.prepareStatement(sql)){

            ResultSet rs = get.executeQuery();

            while(rs.next()){
                Staff add = new Staff();
                add.setEmpID(rs.getInt("empID"));
                add.setFirstName(rs.getString("firstName"));
                add.setLastName(rs.getString("lastName"));
                add.setSex(rs.getString("sex"));
                add.setDob(rs.getObject("dob",LocalDate.class));
                add.setSsn(rs.getString("ssn"));
                add.setPhone(rs.getString("phone"));
                add.setStreet(rs.getString("street"));
                add.setCity(rs.getString("city"));
                add.setState(rs.getString("state"));
                add.setZip(Integer.parseInt(rs.getString("zip")));
                ret.add(add);
            }

        }catch (SQLException ex) {
            System.out.println("Error running Staff Get statement");
            ex.printStackTrace();
        }
        return ret.toArray(new Staff[0]);
    }

    public Vet[] getVets(){
        String sql
                = "SELECT s.*, v.licenseNo FROM STAFF s JOIN VET v ON v.empID = s.empID";
        List<Vet> ret = new ArrayList<>();
        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement get = conn.prepareStatement(sql)){
            ResultSet rs = get.executeQuery();
            while(rs.next()){
                Vet add = new Vet();
                add.setEmpID(rs.getInt("empID"));
                add.setFirstName(rs.getString("firstName"));
                add.setLastName(rs.getString("lastName"));
                add.setSex(rs.getString("sex"));
                add.setDob(rs.getDate("dob").toLocalDate());
                add.setSsn(rs.getString("ssn"));
                add.setPhone(rs.getString("phone"));
                add.setStreet(rs.getString("street"));
                add.setCity(rs.getString("city"));
                add.setState(rs.getString("state"));
                add.setZip(Integer.parseInt(rs.getString("zip")));
                add.setLicenseNumber(rs.getString("licenseNo"));
                ret.add(add);
            }
        }catch (SQLException ex) {
            System.out.println("Error running Vet Get statement");
            ex.printStackTrace();
        }
        return ret.toArray(new Vet[0]);
    }
    public Vet getVet(int vetID)
    {
        String sql
                = "SELECT s.*, v.licenseNo FROM STAFF s JOIN VET v ON v.empID = s.empID " +
                "WHERE s.empID = ?";
        Vet ret = null;
        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement get = conn.prepareStatement(sql)){
            get.setInt(1,vetID);

            ResultSet rs = get.executeQuery();

            while(rs.next()){
                ret = new Vet();
                ret.setEmpID(rs.getInt("empID"));
                ret.setFirstName(rs.getString("firstName"));
                ret.setLastName(rs.getString("lastName"));
                ret.setSex(rs.getString("sex"));
                ret.setDob(rs.getDate("dob").toLocalDate());
                ret.setSsn(rs.getString("ssn"));
                ret.setPhone(rs.getString("phone"));
                ret.setStreet(rs.getString("street"));
                ret.setCity(rs.getString("city"));
                ret.setState(rs.getString("state"));
                ret.setZip(Integer.parseInt(rs.getString("zip")));
                ret.setLicenseNumber(rs.getString("licenseNo"));
            }

        }catch (SQLException ex) {
            System.out.println("Error running Vet Get statement");
            ex.printStackTrace();
        }

        return ret;
    }

    public Tech[] getTechs(){
        String sql
                = "SELECT s.*, t.certNumber FROM STAFF s JOIN TECH t ON t.empID = s.empID";
        List<Tech> ret = new ArrayList<>();
        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement get = conn.prepareStatement(sql)){
            ResultSet rs = get.executeQuery();
            while(rs.next()){
                Tech add = new Tech();
                add.setEmpID(rs.getInt("empID"));
                add.setFirstName(rs.getString("firstName"));
                add.setLastName(rs.getString("lastName"));
                add.setSex(rs.getString("sex"));
                add.setDob(rs.getDate("dob").toLocalDate());
                add.setSsn(rs.getString("ssn"));
                add.setPhone(rs.getString("phone"));
                add.setStreet(rs.getString("street"));
                add.setCity(rs.getString("city"));
                add.setState(rs.getString("state"));
                add.setZip(Integer.parseInt(rs.getString("zip")));
                add.setCertification(rs.getString("certNumber"));

                ret.add(add);
            }
        }catch (SQLException ex) {
            System.out.println("Error running Staff Get statement");
            ex.printStackTrace();
        }
        return ret.toArray(new Tech[0]);
    }

    public Tech getTech(int techID)
    {
        String sql
                = "SELECT s.*, t.certNumber FROM STAFF s JOIN TECH t ON t.empID = s.empID " +
                "WHERE s.empID = ?";
        Tech ret = null;
        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement get = conn.prepareStatement(sql)){
            get.setInt(1,techID);

            ResultSet rs = get.executeQuery();

            while(rs.next()){
                ret = new Tech();
                ret.setEmpID(rs.getInt("empID"));
                ret.setFirstName(rs.getString("firstName"));
                ret.setLastName(rs.getString("lastName"));
                ret.setSex(rs.getString("sex"));
                ret.setDob(rs.getDate("dob").toLocalDate());
                ret.setSsn(rs.getString("ssn"));
                ret.setPhone(rs.getString("phone"));
                ret.setStreet(rs.getString("street"));
                ret.setCity(rs.getString("city"));
                ret.setState(rs.getString("state"));
                ret.setZip(Integer.parseInt(rs.getString("zip")));
                ret.setCertification(rs.getString("certNumber"));
            }

        }catch (SQLException ex) {
            System.out.println("Error running Tech Get statement");
            ex.printStackTrace();
        }

        return ret;
    }

    public void deleteStaff(int empID){
        String sql = "DELETE FROM STAFF WHERE empID =?";
        try(Connection conn = ConnectionManager.getConnection();
        PreparedStatement del = conn.prepareStatement(sql)){
            del.setInt(1,empID);
            del.executeUpdate();
            conn.commit();
        }catch(SQLException ex){
            System.err.println("Error deleting Staff entry");
            ex.printStackTrace();
        }
    }

    public Staff addStaff(Staff mod){
        String sql
                = "INSERT INTO STAFF (firstName,lastName,sex,dob,ssn,phone,street,city,state,zip) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?)";

        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement create = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            create.setString(1,mod.getFirstName());
            create.setString(2,mod.getLastName());
            create.setString(3,mod.getSex());
            create.setDate(4, java.sql.Date.valueOf(mod.getDob()));
            create.setString(5,mod.getSsn());
            create.setString(6,mod.getPhone());
            create.setString(7,mod.getStreet());
            create.setString(8,mod.getCity());
            create.setString(9,mod.getState());
            create.setString(10,Integer.toString(mod.getZip()));

            create.executeUpdate();
            ResultSet rs = create.getGeneratedKeys();
            while(rs.next()){
                mod.setEmpID(rs.getInt(1));
            }
            conn.commit();
        }catch (SQLException ex) {
            System.err.println("Error updating Staff entry");
            ex.printStackTrace();
        }
        return mod;
    }

    public void updateStaff(Staff mod){
        String sql
                = "UPDATE STAFF SET firstName = ?, lastName=?,sex=?,dob=?,ssn=?,phone=?,street=?,city=?,state=?,zip=? " +
                "WHERE empID=?";

        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement update = conn.prepareStatement(sql)){
            update.setString(1, mod.getFirstName());
            update.setString(2, mod.getLastName());
            update.setString(3, mod.getSex());
            update.setDate(4, java.sql.Date.valueOf(mod.getDob()));
            update.setString(5, mod.getSsn());
            update.setString(6, mod.getPhone());
            update.setString(7, mod.getStreet());
            update.setString(8, mod.getCity());
            update.setString(9, mod.getState());
            update.setString(10, Integer.toString(mod.getZip()));
            update.setInt(11,mod.getEmpID());

            update.executeUpdate();
            conn.commit();
        }catch (SQLException ex) {
            System.err.println("Error updating Staff entry");
            ex.printStackTrace();
        }
    }

    public Vet addVet(Vet mod){
        Staff newStaff = addStaff(mod);
        mod.setEmpID(newStaff.getEmpID());
        String sql =
                "INSERT INTO VET VALUES(?,?)";
        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement create = conn.prepareStatement(sql)){
            create.setInt(1, mod.getEmpID());
            create.setString(2, mod.getLicenseNumber());

            create.executeUpdate();
            conn.commit();
        }catch (SQLException ex) {
            System.err.println("Error updating Vet entry");
            ex.printStackTrace();
        }
        return mod;
    }

    public void updateVet(Vet mod){
        updateStaff(mod);
        String sql
                ="UPDATE VET SET licenseNo=? WHERE empID=?";

        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement update = conn.prepareStatement(sql)){
            update.setString(1, mod.getLicenseNumber());
            update.setInt(2,mod.getEmpID());

            update.executeUpdate();
            conn.commit();
        }catch (SQLException ex) {
            System.err.println("Error updating Vet entry");
            ex.printStackTrace();
        }
    }

    public void deleteVet(int empID){
        deleteSpecialties(empID);
        blankAppointments(empID);
        String sql = "DELETE FROM VET WHERE empID =?";
        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement del = conn.prepareStatement(sql)){
            del.setInt(1,empID);
            del.executeUpdate();
            conn.commit();
        }catch(SQLException ex){
            System.err.println("Error deleting vet entry");
            ex.printStackTrace();
        }
        deleteStaff(empID);
    }

    public Tech addTech(Tech mod){
        Staff newStaff = addStaff(mod);
        mod.setEmpID(newStaff.getEmpID());
        String sql =
                "INSERT INTO TECH VALUES(?,?)";
        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement create = conn.prepareStatement(sql)){
            create.setInt(1, mod.getEmpID());
            create.setString(2, mod.getCertification());

            create.executeUpdate();
            conn.commit();
        }catch (SQLException ex) {
            System.err.println("Error updating Tech entry");
            ex.printStackTrace();
        }
        return mod;
    }

    public void updateTech(Tech mod) {
        updateStaff(mod);
        String sql
                = "UPDATE TECH SET certNumber=? WHERE empID=?";

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement update = conn.prepareStatement(sql)) {
            update.setString(1, mod.getCertification());
            update.setInt(2, mod.getEmpID());

            update.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            System.err.println("Error updating Tech entry");
            ex.printStackTrace();

        }
    }
    public void deleteTech(int empID){
        deleteCertifications(empID);
        String sql = "DELETE FROM TECH WHERE empID =?";
        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement del = conn.prepareStatement(sql)){
            del.setInt(1,empID);
            del.executeUpdate();
            conn.commit();
        }catch(SQLException ex){
            System.err.println("Error deleting tech entry");
            ex.printStackTrace();
        }
        deleteStaff(empID);
    }
    public void updateSpecialties(int vetID, String[] specialties){
        deleteSpecialties(vetID);
        String sql = "INSERT INTO SPECIALTIES VALUES(?,?)";

        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement create = conn.prepareStatement(sql)){

            for(String spec : specialties){
                create.setInt(1,vetID);
                create.setString(2,spec);
                create.addBatch();
            }

            create.executeBatch();
            conn.commit();

        }catch(SQLException ex){
            System.err.println("Error inserting specialties");
            ex.printStackTrace();
        }
    }

    public void deleteSpecialties(int vetID){
        String sql = "DELETE FROM SPECIALTIES WHERE empID = ?";

        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement del = conn.prepareStatement(sql)){

            del.setInt(1,vetID);
            del.executeUpdate();
            conn.commit();

        }catch(SQLException ex){
            System.err.println("Error deleting specialties");
            ex.printStackTrace();
        }
    }
    public void updateCertifications(int techID, String[] certs){
        deleteCertifications(techID);
        String sql = "INSERT INTO CERTIFICATIONS VALUES(?,?)";

        try(Connection conn = ConnectionManager.getConnection();
        PreparedStatement create = conn.prepareStatement(sql)){

            for(String cert : certs){
                create.setInt(1,techID);
                create.setString(2,cert);
                create.addBatch();
            }

            create.executeBatch();
            conn.commit();

        }catch(SQLException ex){
            System.err.println("Error inserting certifications");
            ex.printStackTrace();
        }
    }

    public void deleteCertifications(int techID){
        String sql = "DELETE FROM CERTIFICATIONS WHERE empID = ?";

        try(Connection conn = ConnectionManager.getConnection();
        PreparedStatement del = conn.prepareStatement(sql)){

            del.setInt(1,techID);
            del.executeUpdate();
            conn.commit();

        }catch(SQLException ex){
            System.err.println("Error deleting certifications");
            ex.printStackTrace();
        }
    }
    public Specialty[] getSpecialties(int vetID){
        String sql = "SELECT * FROM SPECIALTIES WHERE empID = ?";
        List<Specialty> ret = new ArrayList<>();
        try(Connection conn = ConnectionManager.getConnection();
        PreparedStatement get = conn.prepareStatement(sql)){

            get.setInt(1,vetID);
            ResultSet rs = get.executeQuery();
            while(rs.next()){
                Specialty add = new Specialty();
                add.setSpecialty(rs.getString("name"));
                add.setEmpID(rs.getInt("empID"));
                ret.add(add);
            }

        }catch (SQLException ex) {
            System.err.println("Error running get Specialties");
            ex.printStackTrace();

        }
        return ret.toArray(new Specialty[0]);
    }

    public Certification[] getCertifications(int techID){
        String sql = "SELECT * FROM CERTIFICATIONS WHERE empID = ?";
        List<Certification> ret = new ArrayList<>();
        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement get = conn.prepareStatement(sql)){

            get.setInt(1,techID);
            ResultSet rs = get.executeQuery();
            while(rs.next()){
                Certification add = new Certification();
                add.setCertification(rs.getString("name"));
                add.setEmpID(rs.getInt("empID"));
                ret.add(add);
            }

        }catch (SQLException ex) {
            System.err.println("Error running get Certifications");
            ex.printStackTrace();

        }
        return ret.toArray(new Certification[0]);
    }

    public void blankAppointments(int empID){
        String sql = "UPDATE APPOINTMENT SET staffID = ? WHERE staffID = ?";

        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement update = conn.prepareStatement(sql)){

            update.setNull(1, Types.INTEGER);
            update.setInt(2, empID);

            update.executeUpdate();

            conn.commit();

        }catch(SQLException ex){
            System.err.println("Error inserting certifications");
            ex.printStackTrace();
        }
    }
}

