package Repository;

import DAL.ConnectionManager;
import model.DataModel;
import model.Staff;

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
            String sql = "Select * from Staff";
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
            String sql = "Select * from Staff Where empID="+empID;
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
    public void deleteStaff(int empID){
    }
    public void addStaff(Staff user){
    }
}

