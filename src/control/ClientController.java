package control;

import java.time.LocalDate;
import java.time.LocalDateTime;

import Repository.AppointmentRepository;
import Repository.ClientRepository;
import Repository.ExamRepository;
import Repository.InventoryRepository;
import Repository.InvoiceRepository;
import Repository.PetRepository;
import Repository.StaffRepository;
import Repository.TreatmentRepository;
import model.Appointment;
import model.Client;
import model.Exam;
import model.Invoice;
import model.Medication;
import model.Pet;
import model.Tech;
import model.Treatment;
import model.Vet;
import view.clientPatient.ClientsView;

public class ClientController extends ViewController {

    private static ClientController instance;

    public static ClientController getInstance() {
        if (instance == null) {
            synchronized (ClientController.class) {
                if (instance == null) {
                    instance = new ClientController();
                }
            }
        }
        return instance;
    }

    private ClientRepository clientRepository;
    private PetRepository petRepository;
    private ExamRepository examRepository;
    private AppointmentRepository appointmentRepository;
    private TreatmentRepository treatmentRepository;
    private InventoryRepository inventoryRepository;
    private InvoiceRepository invoiceRepository;
    private StaffRepository staffRepository;
    private ClientsView clientPage;
    private int currentPetID = -1;
    private int currentClientID = -1;
    private int currentExamID = -1;

    private ClientController() {
        super();
        clientRepository = new ClientRepository();
        petRepository = new PetRepository();
        examRepository = new ExamRepository();
        treatmentRepository = new TreatmentRepository();
        inventoryRepository = new InventoryRepository();
        invoiceRepository = new InvoiceRepository();
        staffRepository = new StaffRepository();
        appointmentRepository = new AppointmentRepository();
    }

    public void setCurrentPetID(int currentPetID) {
        this.currentPetID = currentPetID;
    }

    public int getCurrentPetID() {
        return currentPetID;
    }

    public void setCurrentClientID(int currentClientID) {
        this.currentClientID = currentClientID;
    }

    public int getCurrentClientID() {
        return currentClientID;
    }

    public void setCurrentExamID(int currentExamID) {
        this.currentExamID = currentExamID;
    }

    public int getCurrentExamID() {
        return currentExamID;
    }


    /*
     * Client Methods
     */

    public Client[] getClients() {
        return clientRepository.getAll();
    }

    public void addClient() {
        Client client = new Client();
        client.setFirstName("New");
        client.setLastName("Client");
        client.setPhone("000-000-0000");
        client.setEmail("");
        client.setStreet("");
        client.setCity("");
        client.setState("");
        client.setZip(0);
        clientRepository.createClient(client);
        refreshViews();
    }

    public void updateClient(int clientID, String fName, String lName,
                                String phone, String email, String street,
                                String city, String state, int zip) {
        Client client = new Client();
        client.setClientID(clientID);
        client.setFirstName(fName);
        client.setLastName(lName);
        client.setPhone(phone);
        client.setEmail(email);
        client.setStreet(street);
        client.setCity(city);
        client.setState(state);
        client.setZip(zip);
        clientRepository.updateClient(client);
        refreshViews();
    }

    public void deleteClient(int clientID) {
        clientRepository.removeClient(clientID);
        refreshViews();
    }

    public Client getClient(int clientID) {
        return clientRepository.getSpecificClient(clientID);
    }


    /*
     * Pet Methods
     */

    public void addPet() {
        Pet pet = new Pet();
        pet.setOwnerID(this.getCurrentClientID());
        pet.setName("New Pet");
        pet.setSex("-");
        pet.setBirthdate(LocalDate.now());
        pet.setSpecies("-");
        pet.setBreed("-");
        pet.setColor("-");
        pet.setWeight(0);
        pet.setMicrochipNumber(0);
        pet.setRabiesTag(0);
        petRepository.addPet(pet);
        refreshViews();
    }

    public void updatePet(int petID, String name, String sex, String color, String species,
            String breed, LocalDate birthdate, int weight, long microchipNumber, long rabiesTag) {
        Pet pet = petRepository.getSpecificPet(petID);
        pet.setName(name);
        pet.setSex(sex);
        pet.setColor(color);
        pet.setSpecies(species);
        pet.setBreed(breed);
        pet.setBirthdate(birthdate);
        pet.setWeight(weight);
        pet.setMicrochipNumber(microchipNumber);
        pet.setRabiesTag(rabiesTag);
        petRepository.updatePet(pet);
        refreshViews();
    }

    public Pet[] getPets(int clientID) {
        return petRepository.getPetsByClientID(clientID);
    }

    public Pet getPet(int petID) {
        return petRepository.getSpecificPet(petID);
    }

    public void deletePet(int petID) {
        petRepository.removePet(petID);
        refreshViews();
    }


    /*
     * Medication Methods
     */
    public Exam[] getExams(int petID) {
        return examRepository.getExamsByPetID(petID);
    }


    /*
     * Treatment Methods
     */

    public void addTreatment(int examID) {
        Treatment treatment = new Treatment();
        treatment.setExamID(examID);
        treatment.setMedicationID(0);
        treatment.setType(Treatment.TreatType.LIFESTYLE);
        treatment.setStartDate(LocalDate.now());
        treatment.setEndDate(LocalDate.now());
        treatment.setDirections("New Treatment");
        treatmentRepository.addTreatment(treatment);
        refreshViews();
    }

    public void updateTreatment(int treatmentID, int examID, int medicationID, String type,
                                LocalDate startDate, LocalDate endDate, String directions) {
        Treatment treatment = treatmentRepository.getSpecificTreatment(treatmentID);
        treatment.setExamID(examID);
        treatment.setMedicationID(medicationID);
        treatment.setType(Treatment.TreatType.valueOf(type));
        treatment.setStartDate(startDate);
        treatment.setEndDate(endDate);
        treatment.setDirections(directions);
        treatmentRepository.updateTreatment(treatment);
        refreshViews();
    }

    public Treatment getTreatment(int treatmentID) {
        return treatmentRepository.getSpecificTreatment(treatmentID);
    }

    public Treatment[] getTreatments() {
        int examID = this.getCurrentExamID();
        return treatmentRepository.getTreatmentsByExamID(examID);
    }

    public void removeTreatment(int treatmentID)
    {
        treatmentRepository.removeTreatment(treatmentID);
        refreshViews();
    }


    /*
     * Exam Methods
     */

    public void addExam() {
        Exam exam = new Exam();
        exam.setPetID(this.getCurrentPetID());
        exam.setDate(LocalDateTime.now());
        exam.setDescription("New Exam");
        exam.setVitals("Enter Vitals Here...");
        exam.setWeight(0);
        exam.setLocation("Location");
        examRepository.addExam(exam);
        refreshViews();
    }

    public void updateExam(int examID, LocalDateTime date, int vetID, int techID, String description,
                           String vitals,
                           int weight, String location) {
        Exam exam = examRepository.getSpecificExam(examID);
        exam.setVetID(vetID);
        exam.setTechID(techID);
        exam.setDescription(description);
        exam.setVitals(vitals);
        exam.setWeight(weight);
        exam.setLocation(location);
        exam.setDate(date);
        examRepository.updateExam(exam);
    }

    public void updateTreatment(Treatment treatment) {
        treatmentRepository.updateTreatment(treatment);
        refreshViews();
    }

    public Medication getMedication(int medicationID) {
        return inventoryRepository.getSpecificMedication(medicationID);
    }

    public Treatment[] getVaccinationsFromPetID(int petID) {
        return treatmentRepository.getVaccinationsByPetID(petID);
    }

    public Exam getExam(int examID) {
        return examRepository.getSpecificExam(examID);
    }

    public void deleteExam(int examID) {
        examRepository.deleteExam(examID);
        refreshViews();
    }


    /*
     * Vet and Tech Methods
     */

    public Vet getVet(int vetID) {
        return staffRepository.getVet(vetID);
    }

    public Tech getTech(int techID) {
        return staffRepository.getTech(techID);
    }

    public Treatment[] getTreatmentFromExamID(int examID) {
        return treatmentRepository.getTreatmentsByExamID(examID);
    }

    public Vet[] getVets() {
        return staffRepository.getVets();
    }

    public Tech[] getTechs() {
        return staffRepository.getTechs();
    }


    /*
     * Appointment Methods
     *
     */

    public Appointment[] getAppointments(int petID) {
        return appointmentRepository.getAppointmentsByPetID(petID);
    }

    public Invoice[] getInvoices(int petID) {
        return invoiceRepository.getInvoicesByPetID(petID);
    }

    public Object[][] getPetAppointmentData() {
        Appointment[] appointments = appointmentRepository.getAppointmentsByPetID(currentPetID);
        Object[][] tableData = new Object[appointments.length][2];
        for (int i = 0; i < appointments.length; i++) {
            tableData[i][0] = appointments[i].getAppointmentDate();
            tableData[i][1] = appointments[i].getDescription();
        }
        return tableData;
    }

    public String getInventoryNameByID(int itemID){
        return inventoryRepository.getSpecificItem(itemID).getName();
    }

    public Medication[] getInStockMedications(){
        return inventoryRepository.getInStockMedications();
    }


    /*
     * Page Navigation Methods
     */

    public void setClientPage(ClientsView clientPage) {
        this.clientPage = clientPage;
    }

    public void showClientInfo(int clientID) {
        this.setCurrentClientID(clientID);
        refreshViews();
        clientPage.showClientInfo();
    }

    public void closeClientInfoView() {
        this.setCurrentClientID(-1);
        clientPage.closeClientInfoView();
        refreshViews();
    }

    public void showExamInfo(int examID) {
        this.setCurrentExamID(examID);
        refreshViews();
        clientPage.showExamInfo();
    }

    public void showPetInfo(int petID) {
        this.setCurrentPetID(petID);
        refreshViews();
        clientPage.showPetInfo();
    }

    public void closePetInfoView() {
        this.setCurrentPetID(-1);
        clientPage.closePetInfoView();
        refreshViews();
    }

    public void closeExamInfoView() {
        this.setCurrentExamID(-1);
        clientPage.closeExamInfoView();
        refreshViews();
    }
}