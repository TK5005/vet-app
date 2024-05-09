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

    ClientController clientController;
    InvoiceController invoiceController;
    InventoryController inventoryController;
    DashboardController dashboardController;


    public AppController() {
        model = DataModel.getInstance();
        clientController = ClientController.getInstance();
        invoiceController = InvoiceController.getInstance();
        inventoryController = InventoryController.getInstance();
        dashboardController = DashboardController.getInstance();
    }

    public void refreshViews()
    {
        clientController.refreshViews();
        invoiceController.refreshViews();
        inventoryController.refreshViews();
        dashboardController.refreshViews();
    }

    public String[][] loadStaff() {
        String[][] data = model.loadStaff();
        return data;
    }
    public String[][] loadStaffDetails() {
        String[][] data = model.loadStaffDetails();
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
}