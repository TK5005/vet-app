package Repository;

import DAL.ConnectionManager;
import model.Appointment;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppointmentRepository {

//    private final Connection conn;
//    public AppointmentRepository(){
//        this.conn = ConnectionManager.getConnection();
//    }

    public Appointment[] getAppointmentsByPetID(int petID)
    {
        String sql = "SELECT * FROM APPOINTMENT WHERE petID = ?";
        List<Appointment> ret = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement get = conn.prepareStatement(sql)) {
            get.setInt(1, petID);
            ResultSet rs = get.executeQuery();
            while (rs.next()) {
                Appointment add = new Appointment();
                add.setAppointmentID(rs.getInt("apptNo"));
                add.setClientID(rs.getInt("clientID"));
                add.setPetID(rs.getInt("petID"));
                add.setStaffID(rs.getInt("staffID"));
                add.setAppointmentDate(rs.getTimestamp("start_time").toLocalDateTime().toLocalDate());
                add.setCheckInTime(rs.getTimestamp("checkin_time").toLocalDateTime());
                add.setDescription(rs.getString("description"));

                ret.add(add);

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
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement get = conn.prepareStatement(sql)){
            ResultSet rs = get.executeQuery();
            while(rs.next()){
                Appointment add = new Appointment();
                add.setAppointmentID(rs.getInt("apptNo"));
                add.setClientID(rs.getInt("clientID"));
                add.setPetID(rs.getInt("petID"));
                add.setStaffID(rs.getInt("staffID"));
                add.setAppointmentDate(rs.getTimestamp("start_time").toLocalDateTime().toLocalDate());
                add.setCheckInTime(rs.getTimestamp("checkin_time").toLocalDateTime());
                add.setDescription(rs.getString("description"));
            }
        }catch (SQLException ex) {
            System.err.println("Error running Client Get statement");
            ex.printStackTrace();
        }

        return ret.toArray(new Appointment[0]);
    }

    public Appointment[] GetApp(int appID){
        String sql = "SELECT * FROM APPOINTMENT WHERE appID=?";
        List<Appointment> ret = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, appID);
            ResultSet rs = stmt.executeQuery(sql);
            Appointment app = new Appointment();
            while(rs.next()){
                Appointment add = new Appointment();
                add.setAppointmentID(rs.getInt("apptNo"));
                add.setClientID(rs.getInt("clientID"));
                add.setPetID(rs.getInt("petID"));
                add.setStaffID(rs.getInt("staffID"));
                add.setAppointmentDate(rs.getTimestamp("start_time").toLocalDateTime().toLocalDate());
                add.setCheckInTime(rs.getTimestamp("checkin_time").toLocalDateTime());
                add.setDescription(rs.getString("description"));
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

    public void deleteAppointment(int id){
        //TODO: Imlement deleteAppointment
        System.out.println("deleteAppointment not implemented");
    }
    public Appointment addAppointment(Appointment mod){
        String sql
                = "INSERT INTO APPOINTMENT (clientID, petID, staffID, start_time, description) " +
                "VALUES(?,?,?,?,?)";

        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement create = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            create.setInt(1, mod.getClientID());
            create.setInt(2, mod.getPetID());
            create.setInt(3, mod.getStaffID());
            create.setTimestamp(4, java.sql.Timestamp.valueOf(mod.getAppointmentTime()));
            create.setString(5, mod.getDescription());

            create.executeUpdate();

            ResultSet rs = create.getGeneratedKeys();
            while(rs.next()){
                mod.setAppointmentID(rs.getInt(1));
            }
            conn.commit();
        }catch (SQLException ex) {
            System.err.println("Error inserting Appointment entry");
            ex.printStackTrace();
        }
        return mod;
    }

    public void updateAppointment(Appointment mod){
        String sql =
                "UPDATE APPOINTMENT SET clientID=?,petID=?,staffID=?,start_time=?,checkin_time=?,description=? " +
                        "WHERE apptNo=?";

        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement update = conn.prepareStatement(sql)){

            update.setInt(1,mod.getClientID());
            update.setInt(2,mod.getPetID());
            update.setInt(3,mod.getStaffID());
            update.setTimestamp(4,java.sql.Timestamp.valueOf(mod.getAppointmentTime()));
            update.setTimestamp(5,java.sql.Timestamp.valueOf(mod.getCheckInTime()));
            update.setString(6,mod.getDescription());

            update.executeUpdate();
            conn.commit();

        }catch (SQLException ex) {
            System.err.println("Error updating Appointment entry");
            ex.printStackTrace();
        }
    }
}
