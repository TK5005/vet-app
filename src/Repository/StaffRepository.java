package Repository;

import DAL.ConnectionManager;
import model.DataModel;
import model.Staff;
import model.Tech;
import model.Vet;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StaffRepository {

    private DataModel model;
    private Connection conn;
    public StaffRepository(DataModel model) {
        this.model = model;
    }
    public StaffRepository(){
        this.conn = ConnectionManager.getConnection();
    }
    public Staff[] Get(){
        Statement stmt = null;
        ResultSet rs = null;
        List<Staff> ret = new ArrayList<>();
        try {
            String sql = "SELECT * FROM STAFF";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            Staff staff = new Staff();
            while(rs.next()){
                staff.setEmpID(rs.getInt("empID")); 
                staff.setFirstName(rs.getString("firstName"));
                staff.setLastName(rs.getString("lastName"));
                staff.setSex(rs.getString("sex")); 
                staff.setDob(rs.getObject("date",LocalDate.class)); 
                staff.setSsn(rs.getString("ssn"));
                staff.setPhone(rs.getString("phone"));
                staff.setStreet(rs.getString("street"));
                staff.setCity(rs.getString("city"));
                staff.setState(rs.getString("state"));
                staff.setZip(rs.getInt("zip"));
                ret.add(staff);                        
            }
        }catch (SQLException ex) {
            System.out.println("Error running Staff Get statement");
            ex.printStackTrace();
        }
        Staff[] cli = new Staff[ret.size()];
        cli = ret.toArray(new Staff[ret.size()]);
        return cli;
    }
    public Staff[] GetStaff(int empID){
        Statement stmt = null;
        ResultSet rs = null;
        List<Staff> ret = new ArrayList<>();
        try {
            String sql = "SELECT * FROM STAFF WHERE empID="+empID;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            Staff staff = new Staff();
            while(rs.next()){
                staff.setEmpID(rs.getInt("empID")); 
                staff.setFirstName(rs.getString("firstName"));
                staff.setLastName(rs.getString("lastName"));
                staff.setSex(rs.getString("sex")); 
                staff.setDob(rs.getObject("date",LocalDate.class)); 
                staff.setSsn(rs.getString("ssn"));
                staff.setPhone(rs.getString("phone"));
                staff.setStreet(rs.getString("street"));
                staff.setCity(rs.getString("city"));
                staff.setState(rs.getString("state"));
                staff.setZip(rs.getInt("zip"));
                ret.add(staff);                        
            }
        }catch (SQLException ex) {
            System.out.println("Error running Staff Get statement");
            ex.printStackTrace();
        }
        Staff[] cli = new Staff[ret.size()];
        cli = ret.toArray(new Staff[ret.size()]);
        return cli;
    }
    public Vet[] getVets(){
        String sql
                = "SELECT * FROM STAFF WHERE empID in (SELECT empID FROM VET)";
        List<Vet> ret = new ArrayList<>();
        try(PreparedStatement get = conn.prepareStatement(sql)){
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
                add.setState(rs.getString("street"));
                add.setCity(rs.getString("city"));
                add.setState(rs.getString("state"));
                add.setZip(rs.getInt("zip"));

                ret.add(add);
            }
        }catch (SQLException ex) {
            System.out.println("Error running Staff Get statement");
            ex.printStackTrace();
        }
        return ret.toArray(new Vet[0]);
    }

    public Tech[] getTechs(){
        String sql
                = "SELECT * FROM STAFF WHERE empID in (SELECT empID FROM TECH)";
        List<Tech> ret = new ArrayList<>();
        try(PreparedStatement get = conn.prepareStatement(sql)){
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
                add.setState(rs.getString("street"));
                add.setCity(rs.getString("city"));
                add.setState(rs.getString("state"));
                add.setZip(rs.getInt("zip"));

                ret.add(add);
            }
        }catch (SQLException ex) {
            System.out.println("Error running Staff Get statement");
            ex.printStackTrace();
        }
        return ret.toArray(new Tech[0]);
    }
    public void deleteStaff(int empID){
    }
    public Staff addStaff(Staff mod){
        String sql
                = "INSERT INTO STAFF (firstName,lastName,sex,dob,ssn,phone,street,city,state,zip) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?)";

        try(PreparedStatement create = conn.prepareStatement(sql)){
            create.setString(1,mod.getFirstName());
            create.setString(2,mod.getLastName());
            create.setString(3,mod.getSex());
            create.setDate(4, java.sql.Date.valueOf(mod.getDob()));
            create.setString(5,mod.getSsn());
            create.setString(6,mod.getPhone());
            create.setString(7,mod.getStreet());
            create.setString(8,mod.getCity());
            create.setString(9,mod.getState());
            create.setInt(10,mod.getZip());

            create.executeUpdate();
            ResultSet rs = create.getGeneratedKeys();
            while(rs.next()){
                mod.setEmpID(rs.getInt(1));
            }
            conn.commit();
        }catch (SQLException ex) {
            System.err.println("Error updating Staff entry");
            ex.printStackTrace();
            try {
                System.err.println("Rolling back changes");
                conn.rollback();
            } catch (SQLException e) {
                System.err.println("Error rolling back Staff changes");
                e.printStackTrace();
            }
        }
        return mod;
    }

    public Vet addVet(Vet mod){
        Staff newStaff = addStaff(mod);
        mod.setEmpID(newStaff.getEmpID());
        String sql =
                "INSERT INTO VET VALUES(?,?)";
        try(PreparedStatement create = conn.prepareStatement(sql)){
            create.setInt(1, mod.getEmpID());
            create.setString(2,mod.getLicenseNumber());

            create.executeUpdate();
            conn.commit();
        }catch (SQLException ex) {
            System.err.println("Error updating Vet entry");
            ex.printStackTrace();
            try {
                System.err.println("Rolling back changes");
                conn.rollback();
            } catch (SQLException e) {
                System.err.println("Error rolling back Vet changes");
                e.printStackTrace();
            }
        }
        return mod;
    }

    public Tech addTech(Tech mod){
        Staff newStaff = addStaff(mod);
        mod.setEmpID(newStaff.getEmpID());
        String sql =
                "INSERT INTO TECH VALUES(?,?)";
        try(PreparedStatement create = conn.prepareStatement(sql)){
            create.setInt(1, mod.getEmpID());
            create.setString(2,mod.getCertNumber());

            create.executeUpdate();
            conn.commit();
        }catch (SQLException ex) {
            System.err.println("Error updating Tech entry");
            ex.printStackTrace();
            try {
                System.err.println("Rolling back changes");
                conn.rollback();
            } catch (SQLException e) {
                System.err.println("Error rolling back Tech changes");
                e.printStackTrace();
            }
        }
        return mod;
    }
}

