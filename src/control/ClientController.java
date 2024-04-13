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
    private long currentPetID = -1;
    private long currentClientID = -1;
    private long currentExamID = -1;

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

    public void setCurrentPetID(long currentPetID)
    {
        this.currentPetID = currentPetID;
    }

    public void setCurrentClientID(long currentClientID)
    {
        this.currentClientID = currentClientID;
    }

    public void setCurrentExamID(long currentExamID)
    {
        this.currentExamID = currentExamID;
    }

    public long getCurrentPetID()
    {
        return currentPetID;
    }

    public long getCurrentClientID()
    {
        return currentClientID;
    }

    public long getCurrentExamID()
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
     * @param clientID The client ID
     * @param client The new client object
     */
    public void updateClient(long clientID, Client client)
    {
        dataModel.updateClient(clientID, client);
        refreshViews();
    }

    /**
     * Delete a client
     * @param clientID The client ID
     */
    public void deleteClient(long clientID)
    {
        dataModel.deleteClient(clientID);
        refreshViews();
    }

    /**
     * Get a client
     * @param clientID The client ID
     * @return The client object
     */
    public Client getClient(Long clientID)
    {
        return dataModel.getClient(clientID);
    }


    // Pet methods

    /**
     * Get all pets for a client
     * @param clientID The client ID
     * @return An array of pets
     */
    public Pet[] getPets(long clientID)
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
    public void updatePet(String name, String sex, String color, String species,
                            String breed, String birthdate, long weight, long microchipNumber, long rabiesTag)
    {
        Pet pet = dataModel.getPet(currentPetID);
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
    public void deletePet(Long petID)
    {
        dataModel.deletePet(petID);
        refreshViews();
    }

    /**
     * Get a pet
     * @param petID The pet ID
     * @return The pet object
     */
    public Pet getPet(long petID)
    {
        return dataModel.getPet(petID);
    }


    // Exam methods

    /**
     * Get all exams for a pet
     * @param petID The pet ID
     * @return An array of exams
     */
    public Exam[] getExams(long petID)
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
        dataModel.addExam(exam, this.getCurrentPetID());
        refreshViews();
    }

    /**
     * Update an exam
     * @param examID The exam ID
     * @param exam The new exam object
     */
    public void updateExam(long examID, Exam exam)
    {
        dataModel.updateExam(examID, exam);
    }

    public void updateTreatment(long treatmentID, Treatment treatment)
    {
        dataModel.updateTreatment(treatmentID, treatment);
    }

    /**
     * Get an Exam
     * @param examID The exam ID
     * @return The exam object
     */
    public Exam getExam(long examID)
    {
        return dataModel.getExam(examID);
    }

    /**
     * Delete an exam
     * @param examID The exam ID
     */
    public void deleteExam(long examID)
    {
        dataModel.deleteExam(examID);
        refreshViews();
    }

    public Tech[] getTechs()
    {
        return dataModel.getTechs();
    }

    // Vet and Tech Methods
    public Vet getVet(long vetID)
    {
        return dataModel.getVet(vetID);
    }

    public Tech getTech(long techID)
    {
        return dataModel.getTech(techID);
    }

    public Treatment getTreatmentFromExamID(long examID)
    {
        return dataModel.getTreatmentFromExamID(examID);
    }

    public Vet[] getVets()
    {
        return dataModel.getVets();
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
    public void showClientInfo(long clientID)
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
    public void showExamInfo(Long examID)
    {
        this.setCurrentExamID(examID);
        refreshViews();
        clientPage.showExamInfo();
    }

    /**
     * Show the pet info page
     * @param patientID The patient ID
     */
    public void showPetInfo(long petID)
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

    public Appointment[] getAppointments(long petID)
    {
        return dataModel.getAppointments(petID);
    }

    public Invoice[] getInvoices(long petID)
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
}