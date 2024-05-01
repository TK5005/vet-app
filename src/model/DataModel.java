package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class DataModel {
    private static DataModel instance;
    private ArrayList<Client> clients;
    private ArrayList<Pet> pets;
    private ArrayList<Exam> exams;
    private ArrayList<Vet> vets;
    private ArrayList<Tech> techs;
    private ArrayList<Treatment> treatments;
    private ArrayList<Invoice> invoices;
    private ArrayList<Vaccination> vaccinations;
    private ArrayList<Appointment> appointments;
    private ArrayList<Inventory> inventory;
    private ArrayList<Staff> staffs;
    private ArrayList<Medication> medications;

    /**
     * Constructor for the data model
     */
    private DataModel() {
        clients = new ArrayList<Client>();
        pets = new ArrayList<Pet>();
        exams = new ArrayList<Exam>();
        vets = new ArrayList<Vet>();
        techs = new ArrayList<Tech>();
        treatments = new ArrayList<Treatment>();
        invoices = new ArrayList<Invoice>();
        vaccinations = new ArrayList<Vaccination>();
        appointments = new ArrayList<Appointment>();
        inventory = new ArrayList<Inventory>();
        staffs = new ArrayList<Staff>();
        medications = new ArrayList<Medication>();
        loadClients();
        loadPets();
        loadVets();
        loadTechs();
        loadExams();
        loadTreatments();
        loadAppointments();
        loadVaccinations();
        loadInvoices();
        loadInventory();
        loadStaff();
        loadMedications();
    }

    /**
     * Get the instance of the data model
     * 
     * @return The instance of the data model
     */
    public static DataModel getInstance() {
        if (instance == null) {
            synchronized (DataModel.class) {
                if (instance == null) {
                    instance = new DataModel();
                }
            }
        }
        return instance;
    }

    // Client methods

    /**
     * Get all clients
     * 
     * @return An array of clients
     */
    public Client[] getClients() {
        return clients.toArray(new Client[clients.size()]);
    }

    

    /**
     * Update a client
     * 
     * @param clientID The client ID
     * @param client   The client to update
     */
    public void updateClient(int clientID, Client client) {
        // Update client based on clientID
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getClientID() == clientID) {
                clients.set(i, client);
                break;
            }
        }
    }

    /**
     * Delete a client
     * 
     * @param clientID The client ID
     */
    public void deleteClient(long clientID) {
        // Delete client based on clientID
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getClientID() == clientID) {
                clients.remove(i);
                break;
            }
        }
    }

    /**
     * Get a client
     * 
     * @param clientID The client ID
     * @return The client object
     */
    public Client getClient(long clientID) {
        // Get client based on clientID
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getClientID() == clientID) {
                return clients.get(i);
            }
        }
        return null;
    }

    // Pet methods

    /**
     * Add a new pet
     * 
     * @param pet      The pet to add
     * @param clientID The client ID
     */
    public void addPet(Pet pet, int clientID) {
        // Upate patientID so that it is unique
        if (pets.size() > 0) {
            pet.setPetID(pets.get(pets.size() - 1).getPetID() + 1);
        } else {
            pet.setPetID(0);
        }

        // Assign the client as the owner of the pet
        pet.setOwnerID(clientID);

        // Add pet to the list
        pets.add(pet);
    }

    /**
     * Get all pets for a client
     * 
     * @param clientID The client ID
     * @return An array of pets
     */
    public Pet[] getPets(long clientID) {
        ArrayList<Pet> clientPets = new ArrayList<Pet>();
        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i).getOwnerID() == clientID) {
                clientPets.add(pets.get(i));
            }
        }
        return clientPets.toArray(new Pet[clientPets.size()]);
    }

    /**
     * Update a pet
     * 
     * @param patientID The patient ID
     * @param pet       The new pet object
     */
    public void updatePet(long patientID, Pet pet) {
        // Update pet based on patientID
        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i).getPetID() == patientID) {
                pets.set(i, pet);
                break;
            }
        }
    }

    /**
     * Delete a pet
     * 
     * @param patientID The patient ID
     */
    public void deletePet(int patientID) {
        // Delete pet based on patientID
        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i).getPetID() == patientID) {
                pets.remove(i);
                break;
            }
        }
    }

    /**
     * Get a pet
     * 
     * @param patientID The patient ID
     * @return The pet object
     */
    public Pet getPet(long patientID) {
        // Get pet based on patientID
        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i).getPetID() == patientID) {
                return pets.get(i);
            }
        }
        return null;
    }

    // Exam methods

    /**
     * Get all exams for a pet
     * 
     * @param patientID The patient ID
     * @return An array of exams
     */
    public Exam[] getExams(long patientID) {
        // Get exams based on patientID
        ArrayList<Exam> patientExams = new ArrayList<Exam>();
        for (int i = 0; i < exams.size(); i++) {
            if (exams.get(i).getPetID() == patientID) {
                patientExams.add(exams.get(i));
            }
        }
        return patientExams.toArray(new Exam[patientExams.size()]);
    }

    /**
     * Add a new exam
     * 
     * @param exam      The exam to add
     * @param patientID The patient ID
     */
    public int addExam(Exam exam, int petID) {
        // Upate examID so that it is unique
        if (exams.size() > 0) {
            exam.setExamID(exams.get(exams.size() - 1).getExamID() + 1);
        } else {
            exam.setExamID(0);
        }

        // Assign the patient as the owner of the exam
        exam.setPetID(petID);

        // Add exam to the list
        exams.add(exam);
        return exam.getExamID();
    }

    public int addTreatment(Treatment treatment) {
        // Upate treatmentID so that it is unique
        if (treatments.size() > 0) {
            treatment.setTreatmentID(treatments.get(treatments.size() - 1).getTreatmentID() + 1);
        } else {
            treatment.setTreatmentID(0);
        }

        // Add treatment to the list
        treatments.add(treatment);
        return treatment.getTreatmentID();
    }

    public void removeTreatment(int treatmentID)
    {
        // Delete treatment based on treatmentID
        for (int i = 0; i < treatments.size(); i++) {
            if (treatments.get(i).getTreatmentID() == treatmentID) {
                treatments.remove(i);
                break;
            }
        }
    }

    /**
     * Update an exam
     * 
     * @param examID The exam ID
     * @param exam   The new exam object
     */
    public void updateExam(long examID, Exam exam) {
        // Update exam based on examID
        for (int i = 0; i < exams.size(); i++) {
            if (exams.get(i).getExamID() == examID) {
                exams.set(i, exam);
                break;
            }
        }
    }

    /**
     * Delete an exam
     * 
     * @param examID The exam ID
     */
    public void deleteExam(long examID) {
        // Delete exam based on examID
        for (int i = 0; i < exams.size(); i++) {
            if (exams.get(i).getExamID() == examID) {
                exams.remove(i);
                break;
            }
        }
    }

    public Treatment getTreatment(int treatmentID) {
        // Get treatment based on treatmentID
        for (int i = 0; i < treatments.size(); i++) {
            if (treatments.get(i).getTreatmentID() == treatmentID) {
                return treatments.get(i);
            }
        }
        return null;
    }

    /**
     * Get an Exam
     * 
     * @param examID The exam ID
     * @return The exam object
     */
    public Exam getExam(long examID) {
        // Get exam based on examID
        for (int i = 0; i < exams.size(); i++) {
            if (exams.get(i).getExamID() == examID) {
                return exams.get(i);
            }
        }
        return null;
    }

    // Vet and Tech methods

    /**
     * Get Vet
     * 
     * @param vetID The vet ID
     * @return The vet object
     */
    public Vet getVet(long vetID) {
        // Get vet based on vetID
        for (int i = 0; i < vets.size(); i++) {
            if (vets.get(i).getEmpID() == vetID) {
                return vets.get(i);
            }
        }
        return null;
    }

    /**
     * Get Tech
     * 
     * @param techID The tech ID
     * @return The tech object
     */
    public Tech getTech(long techID) {
        // Get tech based on techID
        for (int i = 0; i < techs.size(); i++) {
            if (techs.get(i).getEmpID() == techID) {
                return techs.get(i);
            }
        }
        return null;
    }

    public Treatment getTreatmentFromExamID(long examID) {
        // Get treatment based on examID
        for (int i = 0; i < treatments.size(); i++) {
            if (treatments.get(i).getExamID() == examID) {
                return treatments.get(i);
            }
        }
        return null;
    }

    public void updateTreatment(long treatmentID, Treatment treatment) {
        // Update treatment based on treatmentID
        for (int i = 0; i < treatments.size(); i++) {
            if (treatments.get(i).getTreatmentID() == treatmentID) {
                treatments.set(i, treatment);
                break;
            }
        }
    }

    public Exam[] getExams() {
        return exams.toArray(new Exam[exams.size()]);
    }

    public Vet[] getVets() {
        return vets.toArray(new Vet[vets.size()]);
    }

    public Tech[] getTechs() {
        return techs.toArray(new Tech[techs.size()]);
    }

    public Appointment[] getAppointments(long petID) {
        ArrayList<Appointment> petAppointments = new ArrayList<Appointment>();
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getPetID() == petID) {
                petAppointments.add(appointments.get(i));
            }
        }
        return petAppointments.toArray(new Appointment[petAppointments.size()]);
    }

    public Invoice[] getInvoices(long petID) {
        ArrayList<Invoice> petInvoices = new ArrayList<Invoice>();
        for (int i = 0; i < invoices.size(); i++) {
            if (invoices.get(i).getExamID() == petID) {
                petInvoices.add(invoices.get(i));
            }
        }
        return petInvoices.toArray(new Invoice[petInvoices.size()]);
    }

    public Invoice[] getInvoices() {
        return invoices.toArray(new Invoice[invoices.size()]);
    }

    public void addInvoice(Invoice invoice) {
        // Upate invoiceNo so that it is unique
        if (invoices.size() > 0) {
            invoice.setInvoiceID(invoices.get(invoices.size() - 1).getInvoiceID() + 1);
        } else {
            invoice.setInvoiceID(0);
        }

        // Add invoice to the list
        invoices.add(invoice);
    }

    public Invoice getInvoice(long invoiceID) {
        // Get invoice based on invoiceID
        for (int i = 0; i < invoices.size(); i++) {
            if (invoices.get(i).getInvoiceID() == invoiceID) {
                return invoices.get(i);
            }
        }
        return null;
    }

    public void updateInvoice(long invoiceID, Invoice invoice) {
        // Update invoice based on invoiceID
        for (int i = 0; i < invoices.size(); i++) {
            if (invoices.get(i).getInvoiceID() == invoiceID) {
                invoices.set(i, invoice);
                break;
            }
        }
    }

    public void deleteInvoice(long invoiceID) {
        // Delete invoice based on invoiceID
        for (int i = 0; i < invoices.size(); i++) {
            if (invoices.get(i).getInvoiceID() == invoiceID) {
                invoices.remove(i);
                break;
            }
        }
    }

    // Inventory menthods
    public void addInventoryItem(Inventory item) {
        // Upate itemID so that it is unique
        if (inventory.size() > 0) {
            item.setItemID(inventory.get(inventory.size() - 1).getItemID() + 1);
        } else {
            item.setItemID(0);
        }

        // Add item to the list
        inventory.add(item);
    }

    public Inventory[] getInventory() {
        return inventory.toArray(new Inventory[inventory.size()]);
    }

    public Inventory getInventoryItem(int itemID) {
        // Get item based on itemID
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getItemID() == itemID) {
                return inventory.get(i);
            }
        }
        return null;
    }

    public void updateInventoryItem(int itemID, Inventory item) {
        // Update item based on itemID
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getItemID() == itemID) {
                inventory.set(i, item);
                break;
            }
        }
    }

    public Vaccination[] getVaccinationsFromPetID(int petID) {
        ArrayList<Vaccination> petVaccinations = new ArrayList<Vaccination>();
        for (int i = 0; i < vaccinations.size(); i++) {
            if (vaccinations.get(i).getPetId() == petID) {
                petVaccinations.add(vaccinations.get(i));
            }
        }
        return petVaccinations.toArray(new Vaccination[petVaccinations.size()]);
    }

    public void updateVaccination(int vaccinationID, Vaccination vaccination) {
        // Update vaccination based on vaccinationID
        for (int i = 0; i < vaccinations.size(); i++) {
            if (vaccinations.get(i).getVaccinationId() == vaccinationID) {
                vaccinations.set(i, vaccination);
                break;
            }
        }
    }

    public void addVaccination(Vaccination vaccination) {
        // Upate vaccinationID so that it is unique
        if (vaccinations.size() > 0) {
            vaccination.setVaccinationId(vaccinations.get(vaccinations.size() - 1).getVaccinationId() + 1);
        } else {
            vaccination.setVaccinationId(0);
        }

        // Add vaccination to the list
        vaccinations.add(vaccination);
    }

    public void removeVaccination(int vaccinationID) {
        // Delete vaccination based on vaccinationID
        for (int i = 0; i < vaccinations.size(); i++) {
            if (vaccinations.get(i).getVaccinationId() == vaccinationID) {
                vaccinations.remove(i);
                break;
            }
        }
    }

    public Vaccination getVaccination(int vaccinationID) {
        // Get vaccination based on vaccinationID
        for (int i = 0; i < vaccinations.size(); i++) {
            if (vaccinations.get(i).getVaccinationId() == vaccinationID) {
                return vaccinations.get(i);
            }
        }
        return null;
    }

    public Treatment[] getTreatments(int examID) {
        ArrayList<Treatment> examTreatments = new ArrayList<Treatment>();
        for (int i = 0; i < treatments.size(); i++) {
            if (treatments.get(i).getExamID() == examID) {
                examTreatments.add(treatments.get(i));
            }
        }
        return examTreatments.toArray(new Treatment[examTreatments.size()]);
    }

    // Temp Data methods

    /**
     * Load clients into the data model
     */
    private void loadClients() {
        // Generate 5 random clients
        for (int i = 0; i < 5; i++) {
            Client client = new Client();
            client.setClientID(i);
            client.setFirstName("Client" + i);
            client.setLastName("Last" + i);
            client.setEmail("client" + i + "@gmail.com");
            client.setPhone("410-392-394" + i);
            client.setStreet("1234 Main St");
            client.setCity("Baltimore");
            client.setState("MD");
            client.setZip(Integer.parseInt("2123" + i));
            clients.add(client);
        }
    }

    /**
     * Load pets into the data model
     */
    private void loadPets() {
        // Generate 10 random pets and assign one of the clients to each pet as the
        // owner
        for (int i = 0; i < 10; i++) {
            Pet pet = new Pet();
            pet.setPetID(i);
            pet.setOwnerID(i % 5);
            pet.setName("Pet" + i);
            pet.setSex("M");
            pet.setColor("Color");
            pet.setSpecies("Species");
            pet.setBreed("Breed");
            pet.setBirthdate(LocalDate.of(2021, 1, 1));
            pet.setWeight(50);
            pet.setMicrochipNumber(123456789);
            pet.setRabiesTag(987654321);
            pets.add(pet);
        }
    }

    /**
     * Load exams into the data model
     */
    private void loadExams() {
        // Generate 10 exams and assign one to each pet via patientID also assign a vet
        // and tech to each exam
        for (int i = 0; i < 10; i++) {
            Exam exam = new Exam();
            exam.setExamID(i);
            exam.setPetID(i % 10);
            exam.setDate(LocalDateTime.of(2021, 1, 1, 8, 0));
            exam.setVetID(i % 5);
            exam.setTechID(i % 5);
            exam.setDescription("Exam Description");
            exam.setWeight(50);
            exam.setLocation("Exam Room");
            exam.setVitals("Vitals");
            exams.add(exam);
        }
    }

    /**
     * Load vets into the data model
     */
    private void loadVets() {
        // Generate 5 random vets
        for (int i = 0; i < 5; i++) {
            Vet vet = new Vet();
            vet.setEmpID(i);
            vet.setFirstName("Vet" + i);
            vet.setLastName("Last" + i);
            vet.setPhone("410-392-394" + i);
            vet.setStreet("1234 Main St");
            vet.setCity("Baltimore");
            vet.setState("MD");
            vet.setZip(Integer.parseInt("2123" + i));
            vets.add(vet);
        }
    }

    /**
     * Load techs into the data model
     */
    private void loadTechs() {
        // Generate 5 random techs
        for (int i = 0; i < 5; i++) {
            Tech tech = new Tech();
            tech.setEmpID(i);
            tech.setFirstName("Tech" + i);
            tech.setLastName("Last" + i);
            tech.setPhone("410-392-394" + i);
            tech.setStreet("1234 Main St");
            tech.setCity("Baltimore");
            tech.setState("MD");
            tech.setZip(Integer.parseInt("2123" + i));
            techs.add(tech);
        }
    }

    private void loadTreatments() {
        // Generate 10 treatments and assign one to each exam via examID
        for (int i = 0; i < 10; i++) {
            Treatment treatment = new Treatment();
            treatment.setTreatmentID(i);
            treatment.setExamID(i % 10);
            treatment.setType(Treatment.TreatType.VACCINE);
            treatment.setStartDate(LocalDate.of(2021, 1, 1));
            treatment.setEndDate(LocalDate.of(2021, 1, 1));
            treatment.setDirections("Treatment Directions");
            treatments.add(treatment);
        }
    }

    private void loadInvoices() {
        // Generate 10 invoices and assign one to each exam via examID
        for (int i = 0; i < 10; i++) {
            Invoice invoice = new Invoice();
            invoice.setInvoiceID(i);
            invoice.setExamID(i % 10);
            invoice.setClientID(i % 5);
            invoice.setAmtDue(100.00F);
            invoice.setStatus(Invoice.Status.UNPAID);
            invoice.setInvoiceDate(LocalDate.of(2021, 1, 1));
            invoices.add(invoice);
        }
    }

    private void loadVaccinations() {
        // Generate 10 vaccinations and assign one to each pet via patientID
        for (int i = 0; i < 10; i++) {
            Vaccination vaccination = new Vaccination();
            vaccination.setVaccinationId(i);
            vaccination.setPetId(i % 10);
            vaccination.setName("Vaccination" + i);
            vaccination.setDate(LocalDate.of(2021, 1, 1));
            vaccinations.add(vaccination);
        }
    }

    private void loadAppointments() {
        // Generate 10 appointments and assign one to each client via clientID
        for (int i = 0; i < 10; i++) {
            Appointment appointment = new Appointment();
            appointment.setAppointmentID(i);
            appointment.setClientID(i % 5);
            appointment.setPetID(i % 10);
            appointment.setStaffID(i % 5);
            appointment.setAppointmentDate(LocalDate.of(2021, 1, 1));
            appointment.setDescription("Appointment Description");
            appointments.add(appointment);
        }
    }

    private void loadInventory() {
        // Generate 10 inventory items
        for (int i = 0; i < 10; i++) {
            Inventory item = new Inventory();
            item.setItemID(i);
            item.setName("Item" + i);
            item.setReorderLevel(10);
            item.setQuantity(100);
            item.setReorderQuantity(50);
            item.setRetailCost(10.00F);
            item.setWholesaleCost(5.00F);
            item.setType("Item Type");
            item.setManufacturer("Manufacturer");
            inventory.add(item);
        }
    }

    private void loadMedications() {
        // Generate 10 medications
        for (int i = 0; i < 10; i++) {
            Medication medication = new Medication();
            medication.setMedicationID(i);
            medication.setName("Medication" + i);
            medication.setDosage("Dosage");
            medication.setQuantity(100);
            medication.setCost(10.00F);
            medications.add(medication);
        }
    }

    public Medication getMedication(int medicationID) {
        // Get medication based on medicationID
        for (int i = 0; i < medications.size(); i++) {
            if (medications.get(i).getMedicationID() == medicationID) {
                return medications.get(i);
            }
        }
        return null;
    }

    /* Vien's Methods, to integrate */
    /**
     * staff
     * 
     * @param empID The staff ID
     */
    public void deleteStaff(int empID) {
        // Delete staff based on empID
        for (int i = 0; i < staffs.size(); i++) {
            if (staffs.get(i).getEmpID() == empID) {
                staffs.remove(i);
                break;
            }
        }
    }
    public void updateStaff(int empID, Staff info) {
        // Update staff info based on empID
        for (int i = 0; i < staffs.size(); i++) {
            if (staffs.get(i).getEmpID() == empID) {
                staffs.set(i,info);
                break;
            }
        }
    }
    public void addStaff(Staff newStaff) {
        // Upate clientID so that it is unique
        if (staffs.size() > 0) {
            newStaff.setEmpID(staffs.get(staffs.size() - 1).getEmpID() + 1);
        } else {
            newStaff.setEmpID(0);
        }

        // Add client to the list
        staffs.add(newStaff);
    }
    public Staff[] getStaffs() {
        return staffs.toArray(new Staff[staffs.size()]);
    }

    // appointment methods
    public ArrayList<Appointment> updateAppointments(long petID, Appointment app) {
        ArrayList<Appointment> petAppointments = new ArrayList<Appointment>();
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getPetID() == petID) {
                petAppointments.set(i,app);
            }
        }
        return petAppointments;
    }
    public Appointment[] deleteAppointments(long petID) {
        ArrayList<Appointment> petAppointments = new ArrayList<Appointment>();
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getPetID() == petID) {
                petAppointments.remove(appointments.get(i));
            }
        }
        return petAppointments.toArray(new Appointment[petAppointments.size()]);
    }
    public Appointment[] getAppointmentsClient(long id) {
        ArrayList<Appointment> clientAppointments = new ArrayList<Appointment>();
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getClientID()== id) {
                clientAppointments.add(appointments.get(i));
            }
        }
        return clientAppointments.toArray(new Appointment[clientAppointments.size()]);
    }
    public Appointment[] getAppointments() {
        return appointments.toArray(new Appointment[appointments.size()]);
    }

    public String[][] loadActivePatient() {
        String[][] data = { { "Tom", "4/5/2024", "Exam1", "test/test", "Vaccination" },
                { "Mary", "5/5/2024", "Test", "test/test", "Trimming" } };
        return data;
    }

    public String[][] loadTempAppointments() {
        String[][] data = { {"1", "Smith Henry", "Brandy", "443-123-4567", "4/28/2024 8:00AM", "" },
                { "2","Mary", "Sassy", "443-890-1234", "5/23/2024 2:30PM", "" } };
        return data;
    }

    public String[][] loadReview() {
        String[][] data = { { "Smith Henry", "Brandy", "443-123-4567", "4/28/2024 8:00AM", "test" },
                { "Mary", "Sassy", "443-890-1234", "5/23/2024 2:30PM", "test" } };
        return data;
    }

    public String[][] loadMedication() {
        String[][] data = { { "Smith Henry", "Brandy", "443-123-4567", "12", "Yes" },
                { "Mary", "Sassy", "443-890-1234", "5", "No" } };
        return data;
    }

    public String[][] loadStaff() {
         String[][] data = { {"1", "sb12","Smith Brandy","vet","",""},
                { "2", "tes34","test","tech","",""  } };
        return data;
    }
}

/*
 * 
    public void addClient(Client client) {
        // Upate clientID so that it is unique
        if (clients.size() > 0) {
            client.setClientID(clients.get(clients.size() - 1).getClientID() + 1);
        } else {
            client.setClientID(0);
        }

        // Add client to the list
        clients.add(client);
    }
 */