package control;

import Repository.AppointmentRepository;
import model.Appointment;

public class AppointmentController extends ViewController{
    private static AppointmentController instance;
    private AppointmentRepository appointmentRepository;

    // Private constructor to prevent instantiation from outside the class
    private AppointmentController() {
        appointmentRepository = new AppointmentRepository();
    }

    // Public method to get the instance of the Singleton
    public static AppointmentController getInstance() {
        if (instance == null) {
            synchronized (AppointmentController.class) {
                if (instance == null) {
                    instance = new AppointmentController();
                }
            }
        }
        return instance;
    }

    public Appointment[] getAppointments() {
        Appointment[] appointments = appointmentRepository.getAll();
        return appointments;
    }
}