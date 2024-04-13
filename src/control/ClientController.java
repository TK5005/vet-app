package control;

import java.util.ArrayList;
import java.time.LocalDate;

import model.Client;
import model.DataModel;
import model.Exam;
import model.Pet;
import model.Vet;
import view.clientPatient.ClientPageView;
import model.Tech;
import model.Treatment;
import model.Appointment;
import model.Invoice;

public class ClientController
{
    private static ClientController instance;
    private DataModel dataModel;
    private ArrayList<IClientView> views;
    private ClientPageView clientPage;
    private int currentPetID = -1;
    private int currentClientID = -1;
    private int currentExamID = -1;

    /**
     * Constructor for the client controller
     */
    private ClientController()
    {
        dataModel = DataModel.getInstance();
        views = new ArrayList<>();
    }

    /**
     * Get the instance of the client controller
     * @return The instance of the client controller
     */
    public static ClientController getInstance()
    {
        if (instance == null) {
            synchronized (ClientController.class)
            {
                if (instance == null) {
                    instance = new ClientController();
                }
            }
        }
        return instance;
    }

    public void registerView(IClientView view)
    {
        views.add(view);
    }

    /**
     * Refresh the views
     */
    private void refreshViews()
    {
        for (IClientView view : views) {
            view.refresh();
        }
    }

    public void setCurrentPetID(int currentPetID)
    {
        this.currentPetID = currentPetID;
    }

    public int getCurrentPetID()
    {
        return currentPetID;
    }

    public void setCurrentClientID(int currentClientID)
    {
        this.currentClientID = currentClientID;
    }

    public int getCurrentClientID()
    {
        return currentClientID;
    }

    public void setCurrentExamID(int currentExamID)
    {
        this.currentExamID = currentExamID;
    }

    public int getCurrentExamID()
    {
        return currentExamID;
    }

    // Client methods

    /**
     * Get all clients
     * @return An array of clients
     */
    public Client[] getClients()
    {
        return dataModel.getClients();
    }

    /**
     * Add a new client
     */
    public void addClient()
    {
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
            String phone, String email, String street, String city, String state, int zip)
    {
        Client client = dataModel.getClient(clientID);
        client.setFirstName(fName);
        client.setLastName(lName);
        client.setPhone(phone);
        client.setEmail(email);
        client.setStreet(street);
        client.setCity(city);
        client.setState(state);
        client.setZip(zip);

        dataModel.updateClient(clientID, client);
        refreshViews();
    }

    /**
     * Delete a client
     * @param clientID The client ID
     */
    public void deleteClient(int clientID)
    {
        dataModel.deleteClient(clientID);
        refreshViews();
    }

    /**
     * Get a client
     * @param clientID The client ID
     * @return The client object
     */
    public Client getClient(int clientID)
    {
        return dataModel.getClient(clientID);
    }

    // Pet methods

    /**
     * Get all pets for a client
     * @param clientID The client ID
     * @return An array of pets
     */
    public Pet[] getPets(int clientID)
    {
        return dataModel.getPets(clientID);
    }

    /**
     * Add a new pet
     * @param clientID The client ID
     */
    public void addPet()
    {
        Pet pet = new Pet();
        pet.setName("New Pet");
        dataModel.addPet(pet, this.getCurrentClientID());
        refreshViews();
    }

    /**
     * Update a pet
     */
    public void updatePet(int petID, String name, String sex, String color, String species,
                            String breed, String birthdate, int weight, long microchipNumber, long rabiesTag)
    {
        Pet pet = dataModel.getPet(petID);
        pet.setName(name);
        pet.setSex(sex);
        pet.setColor(color);
        pet.setSpecies(species);
        pet.setBreed(breed);
        if(birthdate != null && !birthdate.isEmpty())
        {
            String[] date = birthdate.split("/");
            pet.setBirthdate(LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[0]), Integer.parseInt(date[1])));
        }
        pet.setWeight(weight);
        pet.setMicrochipNumber(microchipNumber);
        pet.setRabiesTag(rabiesTag);
        dataModel.updatePet(currentPetID, pet);
        refreshViews();
    }

    /**
     * Delete a pet
     * @param petID The pet ID
     */
    public void deletePet(int petID)
    {
        dataModel.deletePet(petID);
        refreshViews();
    }

    /**
     * Get a pet
     * @param petID The pet ID
     * @return The pet object
     */
    public Pet getPet(int petID)
    {
        return dataModel.getPet(petID);
    }


    // Exam methods

    /**
     * Get all exams for a pet
     * @param petID The pet ID
     * @return An array of exams
     */
    public Exam[] getExams(int petID)
    {
        return dataModel.getExams(petID);
    }

    /**
     * Add a new exam
     * @param patientID The patient ID
     */
    public void addExam()
    {
        Exam exam = new Exam();
        exam.setDescription("New Exam");
        int examID = dataModel.addExam(exam, this.getCurrentPetID());
        addTreatment(examID);
        refreshViews();
    }

    public void addTreatment(int examID)
    {
        Treatment treatment = new Treatment();
        treatment.setExamID(examID);
        treatment.setStartDate(LocalDate.now());
        treatment.setEndDate(LocalDate.now());
        treatment.setDirections("New Treatment");
        dataModel.addTreatment(treatment);
    }

    /**
     * Update an exam
     * @param examID The exam ID
     * @param exam The new exam object
     */
    public void updateExam(int examID, String dateTime, int vetID, int techID, String description, String vitals,
                            int weight, String location)
    {
        Exam exam = dataModel.getExam(examID);
        exam.setVetID(vetID);
        exam.setTechID(techID);
        exam.setDescription(description);
        exam.setVitals(vitals);
        exam.setWeight(weight);
        exam.setLocation(location);
        if(dateTime != null && !dateTime.isEmpty())
        {
            //TODO: Set exam Date and Time
        }
        dataModel.updateExam(examID, exam);
    }

    public void updateTreatment(int treatmentID, Treatment treatment)
    {
        dataModel.updateTreatment(treatmentID, treatment);
    }

    /**
     * Get an Exam
     * @param examID The exam ID
     * @return The exam object
     */
    public Exam getExam(int examID)
    {
        return dataModel.getExam(examID);
    }

    /**
     * Delete an exam
     * @param examID The exam ID
     */
    public void deleteExam(int examID)
    {
        dataModel.deleteExam(examID);
        refreshViews();
    }

    public Tech[] getTechs()
    {
        return dataModel.getTechs();
    }

    // Vet and Tech Methods
    public Vet getVet(int vetID)
    {
        return dataModel.getVet(vetID);
    }

    public Tech getTech(int techID)
    {
        return dataModel.getTech(techID);
    }

    public Treatment getTreatmentFromExamID(int examID)
    {
        return dataModel.getTreatmentFromExamID(examID);
    }

    public Vet[] getVets()
    {
        return dataModel.getVets();
    }

    public Appointment[] getAppointments(int petID)
    {
        return dataModel.getAppointments(petID);
    }

    public Invoice[] getInvoices(int petID)
    {
        return dataModel.getInvoices(petID);
    }

    public Object[][] getPetAppointmentData()
    {
        Appointment[] appointments = dataModel.getAppointments(currentPetID);
        Object[][] tableData = new Object[appointments.length][2];
        for (int i = 0; i < appointments.length; i++) {
            tableData[i][0] = appointments[i].getAppointmentDate();
            tableData[i][1] = appointments[i].getDescription();
        }
        return tableData;
    }

    // Page navigation methods

    /**
     * Set the client page
     * @param clientPage The client page
     */
    public void setClientPage(ClientPageView clientPage)
    {
        this.clientPage = clientPage;
    }

    /**
     * Show the client info page
     * @param clientID The client ID
     */
    public void showClientInfo(int clientID)
    {
        this.setCurrentClientID(clientID);
        refreshViews();
        clientPage.showClientInfo();
    }

    /**
     * Close the client info page
     */
    public void closeClientInfoView()
    {
        this.setCurrentClientID(-1);
        clientPage.closeClientInfoView();
        refreshViews();
    }

    /**
     * Show the exam info page
     * @param examID The exam ID
     */
    public void showExamInfo(int examID)
    {
        this.setCurrentExamID(examID);
        refreshViews();
        clientPage.showExamInfo();
    }

    /**
     * Show the pet info page
     * @param patientID The patient ID
     */
    public void showPetInfo(int petID)
    {
        this.setCurrentPetID(petID);
        refreshViews();
        clientPage.showPetInfo();
    }

    /**
     * Close the pet info page
     */
    public void closePetInfoView()
    {
        this.setCurrentPetID(-1);
        clientPage.closePetInfoView();
        refreshViews();
    }

    /**
     * Close the exam info page
     */
    public void closeExamInfoView()
    {
        this.setCurrentExamID(-1);
        clientPage.closeExamInfoView();
        refreshViews();
    }
}