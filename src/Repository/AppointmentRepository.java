package Repository;

import DAL.ConnectionManager;
import model.Appointment;
import model.DataModel;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppointmentRepository {

    private final Connection conn;
    public AppointmentRepository(){
        this.conn = ConnectionManager.getConnection();
    }

    public Appointment[] getAppointmentsByPetID(int petID)
    {
        String sql = "SELECT * FROM APPOINTMENT WHERE petID = ?";
        List<Appointment> ret = new ArrayList<>();
        try (PreparedStatement get = conn.prepareStatement(sql)) {
            get.setInt(1, petID);
            ResultSet rs = get.executeQuery();
            while (rs.next()) {
                LocalDate appDate = rs.getDate("appointmentDate").toLocalDate();
                String appTime = rs.getTimestamp("appointmentDate").toLocalDateTime().toLocalTime().toString();
                ret.add(new Appointment(rs.getInt("appointmentID"), rs.getInt("clientID"),
                        rs.getInt("petID"), rs.getInt("staffID"),
                        appDate, appTime, rs.getString("description")));
            }
        } catch (SQLException ex) {
            System.err.println("Error running Client Get statement");
            ex.printStackTrace();
        }
        return ret.toArray(new Appointment[0]);
    }

    public Appointment[] getAll(){
        String sql = "SELECT * FROM APPOINTMENT";
        List<Appointment> ret = new ArrayList<>();
        try (PreparedStatement get = conn.prepareStatement(sql)){
            ResultSet rs = get.executeQuery();
            while(rs.next()){
                LocalDate appDate = rs.getDate("appointmentDate").toLocalDate();
                String appTime = rs.getTimestamp("appointmentDate").toLocalDateTime().toLocalTime().toString();
                ret.add (new Appointment(rs.getInt("appointmentID"), rs.getInt("clientID"),
                        rs.getInt("petID"), rs.getInt("staffID"),
                        appDate, appTime, rs.getString("description")));
            }
        }catch (SQLException ex) {
            System.err.println("Error running Client Get statement");
            ex.printStackTrace();
        }

        return ret.toArray(new Appointment[0]);
    }

    public Appointment[] GetApp(int appID){
        Statement stmt = null;
        ResultSet rs = null;
        List<Appointment> ret = new ArrayList<>();
        try {
            String sql = "SELECT * FROM APPOINTMENT WHERE appID="+appID;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            Appointment app = new Appointment();
            while(rs.next()){
                app.setAppointmentID(rs.getInt("apptNo"));
                app.setClientID(rs.getInt("clientID"));
                app.setPetID(rs.getInt("petID"));
                app.setStaffID(rs.getInt("staffID"));
                app.setAppointmentDate(rs.getObject("appointmentDate",LocalDate.class)); 
                app.setDescription(rs.getString("description"));
                app.setAppointmentTime(rs.getString("Time"));
                ret.add(app);                    
            }
        }catch (SQLException ex) {
            System.out.println("Error running Appointment Get statement");
            ex.printStackTrace();
        }
        Appointment[] cli = new Appointment[ret.size()];
        cli = ret.toArray(new Appointment[ret.size()]);
        return cli;
    }

    public void deleteAppoitment(int id){
        
    }
    public void addAppoitment(Appointment appointment){
        
    }
}
