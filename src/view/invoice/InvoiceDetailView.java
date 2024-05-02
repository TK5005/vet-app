package view.invoice;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.time.LocalDate;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.lgooddatepicker.components.DatePicker;

import control.IVetAppView;
import control.InvoiceController;
import model.Client;
import model.Exam;
import model.Invoice;
import model.Pet;

public class InvoiceDetailView extends JPanel implements IVetAppView {
    private InvoiceController controller;

    private JTextField invoiceIDField;
    private JTextField ownerNameField;
    private JTextField petNameField;
    private DatePicker invoiceDateField;
    private JTextField invoiceAmtField;
    private JComboBox<String> invoiceStatusField;
    private JComboBox<Integer> examSelection;
    private String[] statusOptions;

    private JButton saveButton;
    private JButton closeButton;

    public InvoiceDetailView() {
        this.controller = InvoiceController.getInstance();
        controller.registerView(this);
        statusOptions = controller.getStatusOptions();

        createUI();
        createActionListeners();
    }

    public void refresh() {
        examSelection.removeAllItems();

        int[] examIDs = controller.getExamIDs();
        for (int id : examIDs) {
            examSelection.addItem(id);
        }

        invoiceStatusField.removeAllItems();

        statusOptions = controller.getStatusOptions();

        for (String status : statusOptions) {
            invoiceStatusField.addItem(status);
        }

        Invoice invoice = controller.getInvoice(controller.getCurrentInvoiceID());

        if (invoice != null) {
            examSelection.setSelectedItem(invoice.getExamID());
            invoiceIDField.setText(Integer.toString(invoice.getInvoiceID()));
            invoiceDateField.setDate(invoice.getInvoiceDate());

            Client client = controller.getClient(invoice.getClientID());
            ownerNameField.setText(client.getName());
            Exam exam = controller.getExam(invoice.getExamID());
            Pet pet = controller.getPet(exam.getPetID());
            petNameField.setText(pet.getName());

            invoiceAmtField.setText(Float.toString(invoice.getAmtDue()));
            invoiceStatusField.setSelectedItem(invoice.getStatusString());
        }
    }

    private void createActionListeners() {
        saveButton.addActionListener(e -> {
            updateInvoice();
        });

        closeButton.addActionListener(e -> {
            controller.showInvoiceList();
        });

        examSelection.addActionListener(e -> {
            try {
                int examID = (int) examSelection.getSelectedItem();
                Exam exam = controller.getExam(examID);
                Client client = controller.getClient(controller.getPet(exam.getPetID()).getOwnerID());
                Pet pet = controller.getPet(exam.getPetID());
                ownerNameField.setText(client.getName());
                petNameField.setText(pet.getName());
            } catch (NullPointerException ex) {
                // Do nothing
            }

        });
    }

    private void updateInvoice() {
        int examID = (int) examSelection.getSelectedItem();
        String amtDue = invoiceAmtField.getText();
        String status = (String) invoiceStatusField.getSelectedItem();
        LocalDate invoiceDate = invoiceDateField.getDate();

        controller.updateInvoice(controller.getCurrentInvoiceID(), examID, status, invoiceDate, amtDue);
    }

    private void createUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        JPanel invoiceFieldPanel = createInvoiceFieldPanel();
        invoiceFieldPanel.setBackground(Color.WHITE);
        add(invoiceFieldPanel, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.WHITE);

        JLabel examLabel = new JLabel("Exam:");
        examSelection = new JComboBox<Integer>();
        saveButton = new JButton("Save");
        closeButton = new JButton("Close");

        headerPanel.add(examLabel);
        headerPanel.add(examSelection);
        headerPanel.add(saveButton);
        headerPanel.add(closeButton);

        return headerPanel;
    }

    private JPanel createInvoiceFieldPanel() {
        JPanel invoiceDataFields = new JPanel();
        invoiceDataFields.setLayout(new FlowLayout(FlowLayout.LEFT));
        invoiceDataFields.setBackground(Color.WHITE);

        JPanel invoiceIDPanel = new JPanel();
        invoiceIDPanel.setLayout(new BoxLayout(invoiceIDPanel, BoxLayout.Y_AXIS));
        invoiceIDPanel.setBackground(Color.WHITE);
        JLabel invoiceIDLabel = new JLabel("Invoice #");
        invoiceIDField = new JTextField(10);
        invoiceIDField.setEditable(false);
        invoiceIDPanel.add(invoiceIDLabel);
        invoiceIDPanel.add(invoiceIDField);

        JPanel ownerNamePanel = new JPanel();
        ownerNamePanel.setLayout(new BoxLayout(ownerNamePanel, BoxLayout.Y_AXIS));
        ownerNamePanel.setBackground(Color.WHITE);
        JLabel ownerNameLabel = new JLabel("Owner Name");
        ownerNameField = new JTextField(10);
        ownerNameField.setEditable(false);
        ownerNamePanel.add(ownerNameLabel);
        ownerNamePanel.add(ownerNameField);

        JPanel petNamePanel = new JPanel();
        petNamePanel.setLayout(new BoxLayout(petNamePanel, BoxLayout.Y_AXIS));
        petNamePanel.setBackground(Color.WHITE);
        JLabel petNameLabel = new JLabel("Pet Name");
        petNameField = new JTextField(10);
        petNameField.setEditable(false);
        petNamePanel.add(petNameLabel);
        petNamePanel.add(petNameField);

        JPanel invoiceDatePanel = new JPanel();
        invoiceDatePanel.setLayout(new BoxLayout(invoiceDatePanel, BoxLayout.Y_AXIS));
        invoiceDatePanel.setBackground(Color.WHITE);
        JLabel invoiceDateLabel = new JLabel("Invoice Date");
        invoiceDateField = new DatePicker();
        invoiceDatePanel.add(invoiceDateLabel);
        invoiceDatePanel.add(invoiceDateField);

        JPanel invoiceAmtPanel = new JPanel();
        invoiceAmtPanel.setLayout(new BoxLayout(invoiceAmtPanel, BoxLayout.Y_AXIS));
        invoiceAmtPanel.setBackground(Color.WHITE);
        JLabel invoiceAmtLabel = new JLabel("Amount Due");
        invoiceAmtField = new JTextField(10);
        invoiceAmtField.setEditable(true);
        invoiceAmtPanel.add(invoiceAmtLabel);
        invoiceAmtPanel.add(invoiceAmtField);

        JPanel invoiceStatusPanel = new JPanel();
        invoiceStatusPanel.setLayout(new BoxLayout(invoiceStatusPanel, BoxLayout.Y_AXIS));
        invoiceStatusPanel.setBackground(Color.WHITE);

        JLabel invoiceStatusLabel = new JLabel("Status");
        invoiceStatusField = new JComboBox<String>();
        invoiceStatusField.setEditable(false);
        invoiceStatusPanel.add(invoiceStatusLabel);
        invoiceStatusPanel.add(invoiceStatusField);

        invoiceDataFields.add(invoiceIDPanel);
        invoiceDataFields.add(ownerNamePanel);
        invoiceDataFields.add(petNamePanel);
        invoiceDataFields.add(invoiceDatePanel);
        invoiceDataFields.add(invoiceAmtPanel);
        invoiceDataFields.add(invoiceStatusPanel);

        return invoiceDataFields;
    }
}