package Repository;

import DAL.ConnectionManager;
import model.Appointment;
import model.DataModel;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentRepository {

    private final Connection conn;
    public AppointmentRepository(){
        this.conn = ConnectionManager.getConnection();
    }

    public Appointment[] getAll(){
        String sql = "Select * from Appointment";
        List<Appointment> ret = new ArrayList<>();
        try (PreparedStatement get = conn.prepareStatement(sql)){
            ResultSet rs = get.executeQuery();
            while(rs.next()){
                ret.add (new Appointment(rs.getInt("appointmentID"), rs.getInt("clientID"),
                        rs.getInt("petID"), rs.getInt("staffID"),
                        rs.getTimestamp("appointmentDate").toLocalDateTime(), rs.getString("description")));
            }
        }catch (SQLException ex) {
            System.err.println("Error running Client Get statement");
            ex.printStackTrace();
        }

        return ret.toArray(new Appointment[0]);
    }
}
