package control;

import Repository.AppointmentRepository;
import Repository.ClientRepository;
import Repository.PetRepository;
import Repository.StaffRepository;
import model.Appointment;
import model.Client;
import model.Pet;
import model.Staff;
import ui.DashboardPanel;

public class DashboardController extends ViewController{
    private static DashboardController instance;
    private AppointmentRepository appointmentRepository;
    private ClientRepository clientRepository;
    private PetRepository petRepository;
    private StaffRepository staffRepository;
    private DashboardPanel panel;

    private DashboardController() {
        appointmentRepository = new AppointmentRepository();
        clientRepository = new ClientRepository();
        petRepository = new PetRepository();
        staffRepository = new StaffRepository();
    }

    public static DashboardController getInstance() {
        if (instance == null) {
            synchronized (DashboardController.class) {
                if (instance == null) {
                    instance = new DashboardController();
                }
            }
        }
        return instance;
    }

    public void setPanel(DashboardPanel panel) {
        this.panel = panel;
    }

    public void showApptView()
    {
        panel.setApptView();
        refreshViews();
    }

    public void showPatientView()
    {
        panel.setPatientView();
        refreshViews();
    }

    public Client getClientByID(int ID) {
        return clientRepository.getSpecificClient(ID);
    }

    public Pet getPetByID(int ID) {
        return petRepository.getSpecificPet(ID);
    }

    public Appointment[] getAppointments() {
        Appointment[] appointments = appointmentRepository.getAll();
        return appointments;
    }

    public Staff getStaffByID(int ID) {
//        Staff[] staff = staffRepository.getStaff(ID);
//        if(staff.length == 0)
//        {
//            return null;
//        }
        return staffRepository.getStaff(ID);
    }
}
