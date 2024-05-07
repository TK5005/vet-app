package control;

import Repository.AppointmentRepository;
import Repository.ClientRepository;
import Repository.PetRepository;
import Repository.StaffRepository;
import model.Appointment;
import model.Client;
import model.Pet;
import model.Staff;

public class DashboardController extends ViewController{
    private static DashboardController instance;
    private AppointmentRepository appointmentRepository;
    private ClientRepository clientRepository;
    private PetRepository petRepository;
    private StaffRepository staffRepository;

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
        Staff[] staff = staffRepository.GetStaff(ID);
        if(staff.length == 0)
        {
            return null;
        }
        return staff[0];
    }
}
