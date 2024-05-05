package control;

import java.time.LocalDate;

import Repository.ClientRepository;
import Repository.ExamRepository;
import Repository.InvoiceRepository;
import Repository.PetRepository;
import model.Client;
import model.Exam;
import model.Invoice;
import model.Invoice.Status;
import model.Pet;
import view.invoice.InvoiceView;

public class InvoiceController extends ViewController{
    private static InvoiceController instance;

    public static InvoiceController getInstance() {
        if (instance == null) {
            synchronized (InvoiceController.class) {
                if (instance == null) {
                    instance = new InvoiceController();
                }
            }
        }
        return instance;
    }

    private InvoiceView invoiceView;
    private InvoiceRepository invoiceRepository;
    private ExamRepository examRepository;
    private ClientRepository clientRepository;
    private PetRepository petRepository;

    private int currentInvoiceID;

    private InvoiceController() {
        invoiceRepository = new InvoiceRepository();
        examRepository = new ExamRepository();
        clientRepository = new ClientRepository();
        petRepository = new PetRepository();
    }

    public void setInvoicePanel(InvoiceView invoiceView) {
        this.invoiceView = invoiceView;
    }

    public void setCurrentInvoiceID(int invoiceID) {
        this.currentInvoiceID = invoiceID;
    }

    public int getCurrentInvoiceID() {
        return currentInvoiceID;
    }

    public Invoice getInvoice(int invoiceID) {
        return invoiceRepository.getSpecificInvoice(invoiceID);
    }

    public Client getClient(int clientID) {
        return clientRepository.getSpecificClient(clientID);
    }

    public Client[] getClients() {
        return clientRepository.getAll();
    }

    public Pet getPet(int petID) {
        return petRepository.getSpecificPet(petID);
    }

    public Exam getExam(int examID) {
        return examRepository.getSpecificExam(examID);
    }

    public void showInvoiceList() {
        invoiceView.showListView();
        refreshViews();
    }

    public void addInvoice() {
        Invoice invoice = new Invoice();
        invoice.setExamID(1);
        invoice.setClientID(1);
        invoice.setAmtDue(0.0f);
        invoice.setStatus(Status.UNPAID);
        invoice.setInvoiceDate(LocalDate.now());
        invoiceRepository.addInvoice(invoice);
        refreshViews();
    }

    public void removeInvoice(int invoiceID) {
        invoiceRepository.deleteInvoice(invoiceID);
        refreshViews();
    }

    public void updateInvoice(int invoiceID, int examID, String status, LocalDate invoiceDate, float amtDue) {
        Invoice invoice = invoiceRepository.getSpecificInvoice(invoiceID);
        invoice.setExamID(examID);
        Exam exam = examRepository.getSpecificExam(examID);
        Pet pet = petRepository.getSpecificPet(exam.getPetID());
        Client client = clientRepository.getSpecificClient(pet.getOwnerID());
        invoice.setClientID(client.getClientID());
        for (Invoice.Status s : Invoice.Status.values()) {
            if (s.toString().equals(status)) {
                invoice.setStatus(s);
            }
        }
        invoice.setAmtDue(amtDue);
        invoice.setInvoiceDate(invoiceDate);
        invoiceRepository.updateInvoice(invoice);
        refreshViews();
    }

    public String getClientName(int examID) {
        //Don't need to grab the invoice here, just need the exam info for the invoice - Dan
        //Invoice invoice = invoiceRepository.getSpecificInvoice(invoiceID);
        Exam exam = examRepository.getSpecificExam(examID);
        Pet pet = petRepository.getSpecificPet(exam.getPetID());
        Client client = clientRepository.getSpecificClient(pet.getOwnerID());
        return client.getName();
    }

    public int[] getExamIDs() {
        Exam[] exams = examRepository.getAllBasicExamData();
        int[] examIDs = new int[exams.length];
        for (int i = 0; i < exams.length; i++) {
            examIDs[i] = exams[i].getExamID();
        }
        return examIDs;
    }


    public String[] getStatusOptions() {
        return Invoice.getStatusOptions();
    }

    public Object[][] getInvoiceTableData() {
        Invoice[] invoices = invoiceRepository.getAll();
        Object[][] tableData = new Object[invoices.length][6];
        for (int i = 0; i < invoices.length; i++) {
            Exam exam = examRepository.getSpecificExam(invoices[i].getExamID());
            int invoiceID = invoices[i].getInvoiceID();
            String clientName = clientRepository.getSpecificClient(invoices[i].getClientID()).getName();
            String petName = petRepository.getSpecificPet(exam.getPetID()).getName();
            String invoiceDate = "";
            LocalDate date = invoices[i].getInvoiceDate();
            String amtDue = invoices[i].getFormattedAmtDue();
            if (date != null) {
                invoiceDate = date.toString();
            }
            String status = invoices[i].getStatusString();
            tableData[i] = new Object[] { invoiceID, clientName, petName, invoiceDate, amtDue, status, "" };
        }
        return tableData;
    }
}