package control;

import Repository.AppointmentRepository;
import Repository.ClientRepository;
import Repository.PetRepository;
import model.Appointment;
import model.Client;
import model.Pet;

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

    private int currentAppointmentID;

    private AppointmentController(){
        appointmentRepository = new AppointmentRepository();
        clientRepository = new ClientRepository();
        petRepository = new PetRepository();
    }

    public Appointment getAppointment(int appointmentID){return appointmentRepository.GetApp(appointmentID);}

    public Appointment[] getAppointments(){return appointmentRepository.getAll();}

    public void addNewAppointment(LocalDateTime appDate, LocalDateTime checkinTime, String description, int clientID, int petID, int staffID){
        Appointment app = new Appointment();
        app.setDescription(description);
        app.setAppointmentDate(appDate);
        app.setCheckInTime(checkinTime);
        app.setClientID(clientID);
        app.setPetID(petID);
        app.setStaffID(staffID);

        appointmentRepository.addAppointment(app);
        refreshViews();
    }

    public void updateAppointment(int appID, LocalDateTime appDate, LocalDateTime checkinTime, String description, int clientID, int petID, int staffID){
        Appointment app = new Appointment();
        app.setAppointmentID(appID);
        app.setDescription(description);
        app.setAppointmentDate(appDate);
        app.setCheckInTime(checkinTime);
        app.setClientID(clientID);
        app.setPetID(petID);
        app.setStaffID(staffID);

        appointmentRepository.addAppointment(app);
        refreshViews();

    }

    public Pet[] getPetsByClientID(int clientID){
        return petRepository.getPetsByClientID(clientID);
    }

    public Client[] getClients(){
        return clientRepository.getAll();
    }
}
