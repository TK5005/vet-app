package control;

import Repository.AppointmentRepository;
import Repository.StaffRepository;
import model.Appointment;
import model.DataModel;
import model.Staff;
import view.appointmentView.viewDetails;

/**
 * AppController is the controller class for the application. It is responsible
 * for
 * handling user input and updating the view. It communicates with the model to
 * retrieve data and update the view.
 */
public class AppController {
    DataModel model;
    AppointmentRepository appRepo;
    StaffRepository staffRepo;
    viewDetails details;

    public AppController() {
        model = DataModel.getInstance();
       // details = new viewDetails(null);
    }

    public String[][] loadStaff() {
        String[][] data = model.loadStaff();
        return data;
    }
    public String[][] loadActivePatient() {
        String[][] data = model.loadActivePatient();
        return data;
    }

    public String[][] loadAppointments() {
        String[][] data = model.loadTempAppointments();
        return data;
    }

    public String[][] loadReview() {
        String[][] data = model.loadReview();
        return data;
    }

    public String[][] loadMedication() {
        String[][] data = model.loadMedication();
        return data;
    }

    public Staff[] showStaffDetail(int empID) {
        return staffRepo.GetStaff(empID);
    }
    
    public void removeStaff(int empID) {
        staffRepo.deleteStaff(empID);
    }
    public void addStaff(Staff user) {
        staffRepo.addStaff(user);
    }
    public Appointment[] showAppointmentDetail(int appID) {
        return appRepo.GetApp(appID);
    }

    public void deleteAppointment(int appID) {
        appRepo.deleteAppointment(appID);
    }
    public void addAppointment(Appointment appointment) {
        appRepo.addAppointment(appointment);
    }
}