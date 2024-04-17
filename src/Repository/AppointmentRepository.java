package Repository;

import DAL.ConnectionManager;
import model.DataModel;

import java.sql.Connection;

public class AppointmentRepository {

    private DataModel model;
    private Connection conn;
    public AppointmentRepository(DataModel model) {
        this.model = model;
    }
    public AppointmentRepository(){
        this.conn = ConnectionManager.getConnection();
    }
}
