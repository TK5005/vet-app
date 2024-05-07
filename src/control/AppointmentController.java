package control;

import Repository.AppointmentRepository;
import Repository.ClientRepository;
import Repository.PetRepository;
import Repository.StaffRepository;
import model.*;

import java.time.LocalDateTime;

public class AppointmentController extends ViewController{

    private static AppointmentController instance;

    public static AppointmentController getInstance(){
        if(instance == null){
            synchronized (AppointmentController.class){
                if(instance == null){
                    instance = new AppointmentController();
                }
            }
        }
        return instance;
    }

    private AppointmentRepository appointmentRepository;
    private ClientRepository clientRepository;
    private PetRepository petRepository;

    private StaffRepository staffRepository;

    private int currentAppointmentID;

    private AppointmentController(){
        appointmentRepository = new AppointmentRepository();
        clientRepository = new ClientRepository();
        petRepository = new PetRepository();
        staffRepository = new StaffRepository();
    }

    public Appointment getAppointment(int appointmentID){return appointmentRepository.GetApp(appointmentID);}

    public Appointment[] getAppointments(){return appointmentRepository.getAll();}

    public void addNewAppointment(Appointment add){

        appointmentRepository.addAppointment(add);
        refreshViews();
    }

    public void updateAppointment(Appointment update){

        appointmentRepository.updateAppointment(update);
        refreshViews();

    }

    public Pet[] getPetsByClientID(int clientID){
        return petRepository.getPetsByClientID(clientID);
    }

    public Client[] getClients(){
        return clientRepository.getAll();
    }

    public Client getClient(int clientID){return clientRepository.getSpecificClient(clientID);}
    public Pet getPet(int petID){return petRepository.getSpecificPet(petID);}

    public Vet getVet(int vetID){return staffRepository.getVet(vetID);}

    public Vet[] getVets(){return staffRepository.getVets();}
}
