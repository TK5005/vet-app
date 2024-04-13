package control;

import model.DataModel;
import model.Exam;
import model.Invoice;
import model.Pet;
import view.invoice.InvoiceView;
import model.Client;

import java.time.LocalDate;
import java.util.ArrayList;

public class InvoiceController {
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

    private DataModel dataModel;
    private ArrayList<IInvoiceView> views;

    private InvoiceView invoiceView;

    private int currentInvoiceID;

    private InvoiceController() {
        dataModel = DataModel.getInstance();
        views = new ArrayList<>();
    }

    public void registerView(IInvoiceView view) {
        views.add(view);
    }

    public void refreshViews() {
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

    public Invoice getInvoice(int invoiceID) {
        return dataModel.getInvoice(invoiceID);
    }

    public Client getClient(int clientID) {
        return dataModel.getClient(clientID);
    }

    public Pet getPet(int petID) {
        return dataModel.getPet(petID);
    }

    public Exam getExam(int examID) {
        return dataModel.getExam(examID);
    }

    public void showInvoiceList() {
        invoiceView.showListView();
        refreshViews();
    }

    public void showInvoiceDetail(int invoiceID) {
        currentInvoiceID = invoiceID;
        invoiceView.showDetailView();
        refreshViews();
    }

    public void addInvoice() {
        Invoice invoice = new Invoice();
        dataModel.addInvoice(invoice);
        refreshViews();
    }

    public void removeInvoice(int invoiceID) {
        dataModel.deleteInvoice(invoiceID);
        refreshViews();
    }

    public void updateInvoice(int invoiceID, int examID, String status, LocalDate invoiceDate, String amtDue) {
        Invoice invoice = dataModel.getInvoice(invoiceID);
        invoice.setExamID(examID);
        invoice.setClientID(dataModel.getPet(dataModel.getExam(examID).getPetID()).getOwnerID());
        for (Invoice.Status s : Invoice.Status.values()) {
            if (s.toString().equals(status)) {
                invoice.setStatus(s);
            }
        }
        invoice.setInvoiceDate(invoiceDate);
        refreshViews();
    }

    public int[] getExamIDs() {
        Exam[] exams = dataModel.getExams();
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
        Invoice[] invoices = dataModel.getInvoices();
        Object[][] tableData = new Object[invoices.length][6];
        for (int i = 0; i < invoices.length; i++) {
            Exam exam = dataModel.getExam(invoices[i].getExamID());
            int invoiceID = invoices[i].getInvoiceID();
            String clientName = dataModel.getClient(invoices[i].getClientID()).getName();
            String petName = dataModel.getPet(exam.getPetID()).getName();
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