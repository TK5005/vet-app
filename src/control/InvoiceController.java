package control;

import model.DataModel;
import model.Exam;
import model.Invoice;
import model.Pet;
import view.InvoiceView;
import model.Client;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class InvoiceController 
{
    private DataModel dataModel;
    private ArrayList<IInvoiceView> views;
    private InvoiceView invoiceView;
    private int currentInvoiceID;

    private static InvoiceController instance;

    private InvoiceController() {
        dataModel = DataModel.getInstance();
        views = new ArrayList<>();
    }

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

    public void registerView(IInvoiceView view)
    {
        views.add(view);
    }

    public void refreshViews()
    {
        for (IInvoiceView view : views) {
            view.refresh();
        }
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

    public void showInvoiceList()
    {
        invoiceView.showListView();
        refreshViews();
    }

    public void showInvoiceDetail(int invoiceID)
    {
        currentInvoiceID = invoiceID;
        invoiceView.showDetailView();
        refreshViews();
    }

    public Invoice[] getInvoices() {
        return dataModel.getInvoices();
    }

    public void addInvoice() {
        Invoice invoice = new Invoice();
        dataModel.addInvoice(invoice);
        refreshViews();
    }

    public void updateInvoice(int examID, String status, String invoiceDate, String amtDue)
    {
        Invoice invoice = dataModel.getInvoice(currentInvoiceID);
        invoice.setExamID(examID);
        invoice.setClientID(dataModel.getPet(dataModel.getExam(examID).getPetID()).getOwnerID());
        for(Invoice.Status s : Invoice.Status.values())
        {
            if(s.toString().equals(status))
            {
                invoice.setStatus(s);
            }
        }
        try {
            invoice.setInvoiceDate(LocalDateTime.parse(invoiceDate));
        } catch (Exception e) {}
        try {
            invoice.setAmtDue(Double.parseDouble(amtDue));
        } catch (Exception e) {}
        refreshViews();
    }

    public void deleteInvoice(Invoice invoice) {
        dataModel.deleteInvoice(invoice.getInvoiceNo());
        refreshViews();
    }

    public Invoice getInvoice(int invoiceID) {
        return dataModel.getInvoice(invoiceID);
    }

    public Client getClientByID(int clientID) {
        return dataModel.getClient(clientID);
    }

    public Pet getPatientByID(int petID) {
        return dataModel.getPet(petID);
    }

    public Pet getPatientByExamID(int examID) {
        Exam exam = dataModel.getExam(examID);
        return dataModel.getPet(exam.getPetID());
    }

    public Client[] getClients() {
        return dataModel.getClients();
    }

    public Pet[] getPatientsByClientID(int clientID) {
        return dataModel.getPets(clientID);
    }

    public Exam[] getExams() {
        return dataModel.getExams();
    }

    public Exam getExam(int examID) {
        return dataModel.getExam(examID);
    }

    public int[] getExamIDs() {
        Exam[] exams = dataModel.getExams();
        int[] examIDs = new int[exams.length];
        for (int i = 0; i < exams.length; i++) {
            examIDs[i] = exams[i].getExamID();
        }
        return examIDs;
    }

    public int getCurrentInvoiceExamID() {
        Invoice invoice = dataModel.getInvoice(currentInvoiceID);
        return invoice.getExamID();
    }

    public String getCurrentInvoiceDateString() {
        Invoice invoice = dataModel.getInvoice(currentInvoiceID);
        return invoice.getInvoiceDate().toString();
    }

    public String getCurrentInvoiceOwnerName() {
        Invoice invoice = dataModel.getInvoice(currentInvoiceID);
        Pet pet = dataModel.getPet(dataModel.getExam(invoice.getExamID()).getPetID());
        Client client = dataModel.getClient(pet.getOwnerID());
        return client.getName();
    }

    public String getCurrentInvoicePetName() {
        Invoice invoice = dataModel.getInvoice(currentInvoiceID);
        Pet pet = dataModel.getPet(dataModel.getExam(invoice.getExamID()).getPetID());
        return pet.getName();
    }

    public String getCurrentInvoiceStatus() {
        Invoice invoice = dataModel.getInvoice(currentInvoiceID);
        return invoice.getStatusString();
    }

    public String[] getStatusOptions() {
        return Invoice.getStatusOptions();
    }

    public String getOwnerFromExamID(int examID)
    {
        Pet pet = dataModel.getPet(dataModel.getExam(examID).getPetID());
        Client client = dataModel.getClient(pet.getOwnerID());
        return client.getName();
    }

    public String getPetFromExamID(int examID)
    {
        Pet pet = dataModel.getPet(dataModel.getExam(examID).getPetID());
        return pet.getName();
    }

    public String getCurrentInvoiceAmtDue() {
        Invoice invoice = dataModel.getInvoice(currentInvoiceID);
        return Double.toString(invoice.getAmtDue());
    }

    public Object[][] getInvoiceTableData()
    {
        Invoice[] invoices = dataModel.getInvoices();
        Object[][] tableData = new Object[invoices.length][6];
        for (int i = 0; i < invoices.length; i++) {
            Exam exam = dataModel.getExam(invoices[i].getExamID());
            int invoiceNo = invoices[i].getInvoiceNo();
            String clientName = dataModel.getClient(invoices[i].getClientID()).getName();
            String petName = dataModel.getPet(exam.getPetID()).getName();
            String invoiceDate = "";
            LocalDateTime date = invoices[i].getInvoiceDate();
            String amtDue = invoices[i].getFormattedAmtDue();
            if(date != null)
            {
                invoiceDate = date.toString();
            }
            String status = invoices[i].getStatusString();
            tableData[i] = new Object[]{invoiceNo, clientName, petName, invoiceDate, amtDue, status, ""};
        }
        return tableData;
    }
}