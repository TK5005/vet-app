package control;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import Repository.*;
import model.*;
import view.clientPatient.ClientPageView;

public class ClientController {

    private static ClientController instance;

    /**
     * Get the instance of the client controller
     * 
     * @return The instance of the client controller
     */
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

    private DataModel dataModel;
    private ClientRepository clientRepository;
    private PetRepository petRepository;
    private ExamRepository examRepository;
    private TreatmentRepository treatmentRepository;
    private InventoryRepository inventoryRepository;
    private MedicationRepository medicationRepository;
    private InvoiceRepository invoiceRepository;
    private StaffRepository staffRepository;
    private ArrayList<IClientView> views;
    private ClientPageView clientPage;
    private int currentPetID = -1;
    private int currentClientID = -1;
    private int currentVaccintaionID = -1;
    private int currentExamID = -1;

    /**
     * Constructor for the client controller
     */
    private ClientController() {
        dataModel = DataModel.getInstance();
        clientRepository = new ClientRepository();
        petRepository = new PetRepository();
        examRepository = new ExamRepository();
        treatmentRepository = new TreatmentRepository();
        inventoryRepository = new InventoryRepository();
        medicationRepository = new MedicationRepository();
        invoiceRepository = new InvoiceRepository();
        staffRepository = new StaffRepository();
        views = new ArrayList<>();
    }

    public void registerView(IClientView view) {
        views.add(view);
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

    public void setCurrentVaccinationID(int currentVaccinationID) {
        this.currentVaccintaionID = currentVaccinationID;
    }

    public int getCurrentVaccinationID() {
        return currentVaccintaionID;
    }

    /**
     * Get all clients
     * 
     * @return An array of clients
     */
    public Client[] getClients() {
        return clientRepository.getAll();
        //return dataModel.getClients();
    }

    // Client methods

    /**
     * Add a new client
     */
    public void addClient() {
        Client client = new Client();
        client.setFirstName("New");
        client.setLastName("Client");
        dataModel.addClient(client);
        refreshViews();
    }

    /**
     * Update a client
     */
    public void updateClient(int clientID, String fName, String lName,
            String phone, String email, String street, String city, String state, int zip) {
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

        //dataModel.updateClient(clientID, client);
        clientRepository.updateClient(client);
        refreshViews();
    }

    /**
     * Delete a client
     * 
     * @param clientID The client ID
     */
    public void deleteClient(int clientID) {
        dataModel.deleteClient(clientID);
        refreshViews();
    }

    /**
     * Get a client
     * 
     * @param clientID The client ID
     * @return The client object
     */
    public Client getClient(int clientID) {
        //return dataModel.getClient(clientID);
        return clientRepository.getSpecificClient(clientID);
    }

    /**
     * Get all pets for a client
     * 
     * @param clientID The client ID
     * @return An array of pets
     */
    public Pet[] getPets(int clientID) {
        //return dataModel.getPets(clientID);
        return petRepository.getPetsByClientID(clientID);
    }

    // Pet methods

    /**
     * Add a new pet
     * 
     * @param clientID The client ID
     */
    public void addPet() {
        Pet pet = new Pet();
        pet.setName("New Pet");
        dataModel.addPet(pet, this.getCurrentClientID());
        refreshViews();
    }

    /**
     * Update a pet
     */
    public void updatePet(int petID, String name, String sex, String color, String species,
            String breed, LocalDate birthdate, int weight, long microchipNumber, long rabiesTag) {
        Pet pet = dataModel.getPet(petID);
        pet.setName(name);
        pet.setSex(sex);
        pet.setColor(color);
        pet.setSpecies(species);
        pet.setBreed(breed);
        pet.setBirthdate(birthdate);
        pet.setWeight(weight);
        pet.setMicrochipNumber(microchipNumber);
        pet.setRabiesTag(rabiesTag);
        //dataModel.updatePet(currentPetID, pet);
        petRepository.updatePet(pet);
        refreshViews();
    }

    /**
     * Delete a pet
     * 
     * @param petID The pet ID
     */
    public void deletePet(int petID) {
        dataModel.deletePet(petID);
        refreshViews();
    }

    /**
     * Get a pet
     * 
     * @param petID The pet ID
     * @return The pet object
     */
    public Pet getPet(int petID) {
        //return dataModel.getPet(petID);
        return petRepository.getSpecificPet(petID);
    }

    /**
     * Get all exams for a pet
     * 
     * @param petID The pet ID
     * @return An array of exams
     */
    public Exam[] getExams(int petID) {
        return dataModel.getExams(petID);
    }

    // Exam methods

    /**
     * Add a new exam
     * 
     * @param patientID The patient ID
     */
    public void addExam() {
        Exam exam = new Exam();
        exam.setDescription("New Exam");
        int examID = dataModel.addExam(exam, this.getCurrentPetID());
        addTreatment(examID);
        refreshViews();
    }

    public void addTreatment(int examID) {
        Treatment treatment = new Treatment();
        treatment.setExamID(examID);
        treatment.setStartDate(LocalDate.now());
        treatment.setEndDate(LocalDate.now());
        treatment.setDirections("New Treatment");
        dataModel.addTreatment(treatment);
    }

    /**
     * Update an exam
     * 
     * @param examID The exam ID
     * @param exam   The new exam object
     */
    public void updateExam(int examID, LocalDateTime date, int vetID, int techID, String description,
                           String vitals,
                           int weight, String location) {
        Exam exam = dataModel.getExam(examID);
        exam.setVetID(vetID);
        exam.setTechID(techID);
        exam.setDescription(description);
        exam.setVitals(vitals);
        exam.setWeight(weight);
        exam.setLocation(location);
        exam.setDate(date);
        dataModel.updateExam(examID, exam);
    }

    public void updateTreatment(int treatmentID, int vetID, Treatment treatment) {
        dataModel.updateTreatment(treatmentID, treatment);
    }

    public Treatment[] getVaccinationsFromPetID(int petID) {
        //return dataModel.getVaccinationsFromPetID(petID);
        return treatmentRepository.getVaccinationsByPetID(petID);
    }

    public void addVaccination(int petID) {
        Vaccination vaccination = new Vaccination();
        vaccination.setPetId(petID);
        vaccination.setName("New Vaccination");
        vaccination.setDate(LocalDate.now());
        dataModel.addVaccination(vaccination);
        refreshViews();
    }

    public void removeVaccination(int vaccinationID) {
        dataModel.removeVaccination(vaccinationID);
        refreshViews();
    }

    public Vaccination getVaccination(int vaccinationID) {
        return dataModel.getVaccination(vaccinationID);
    }

    public void updateVaccination(int vaccinationID, String name, LocalDate date) {
        Vaccination vaccination = dataModel.getVaccination(vaccinationID);
        vaccination.setName(name);
        vaccination.setDate(date);
        dataModel.updateVaccination(vaccinationID, vaccination);
        closeVaccinationInfoView();
        refreshViews();
    }

    /**
     * Get an Exam
     * 
     * @param examID The exam ID
     * @return The exam object
     */
    public Exam getExam(int examID) {
        return dataModel.getExam(examID);
    }

    /**
     * Delete an exam
     * 
     * @param examID The exam ID
     */
    public void deleteExam(int examID) {
        dataModel.deleteExam(examID);
        refreshViews();
    }

    public Tech[] getTechs() {
        //return dataModel.getTechs();
        return staffRepository.getTechs();
    }

    // Vet and Tech Methods
    public Vet getVet(int vetID) {
        return dataModel.getVet(vetID);
    }

    public Tech getTech(int techID) {
        return dataModel.getTech(techID);
    }

    public Treatment getTreatmentFromExamID(int examID) {
        return dataModel.getTreatmentFromExamID(examID);
    }

    public Vet[] getVets() {
        //return dataModel.getVets();
        return staffRepository.getVets();
    }

    public Appointment[] getAppointments(int petID) {
        return dataModel.getAppointments(petID);
    }

    public Invoice[] getInvoices(int petID) {
        //return dataModel.getInvoices(petID);
        return invoiceRepository.getInvoicesByPetID(petID);
    }

    public Object[][] getPetAppointmentData() {
        Appointment[] appointments = dataModel.getAppointments(currentPetID);
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
        return medicationRepository.getAllInStock();
    }

    /**
     * Set the client page
     * 
     * @param clientPage The client page
     */
    public void setClientPage(ClientPageView clientPage) {
        this.clientPage = clientPage;
    }

    // Page navigation methods

    /**
     * Show the client info page
     * 
     * @param clientID The client ID
     */
    public void showClientInfo(int clientID) {
        this.setCurrentClientID(clientID);
        refreshViews();
        clientPage.showClientInfo();
    }

    /**
     * Close the client info page
     */
    public void closeClientInfoView() {
        this.setCurrentClientID(-1);
        clientPage.closeClientInfoView();
        refreshViews();
    }

    /**
     * Show the exam info page
     * 
     * @param examID The exam ID
     */
    public void showExamInfo(int examID) {
        this.setCurrentExamID(examID);
        refreshViews();
        clientPage.showExamInfo();
    }

    /**
     * Show the pet info page
     * 
     * @param patientID The patient ID
     */
    public void showPetInfo(int petID) {
        this.setCurrentPetID(petID);
        refreshViews();
        clientPage.showPetInfo();
    }

    /**
     * Close the pet info page
     */
    public void closePetInfoView() {
        this.setCurrentPetID(-1);
        clientPage.closePetInfoView();
        refreshViews();
    }

    /**
     * Close the exam info page
     */
    public void closeExamInfoView() {
        this.setCurrentExamID(-1);
        clientPage.closeExamInfoView();
        refreshViews();
    }

    public void showVaccinationInfo(int vaccinationID) {
        this.setCurrentVaccinationID(vaccinationID);
        clientPage.showPetVaccinationInfo();
        refreshViews();
    }

    public void closeVaccinationInfoView() {
        this.setCurrentVaccinationID(-1);
        clientPage.closePetVaccinationInfoView();
        refreshViews();
    }

    /**
     * Refresh the views
     */
    private void refreshViews() {
        for (IClientView view : views) {
            view.refresh();
        }
    }
}