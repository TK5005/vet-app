package view.invoice;

import javax.swing.JPanel;
import control.InvoiceController;
import control.IInvoiceView;

import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.FlowLayout;

import model.Client;
import model.Invoice;
import model.Exam;
import model.Pet;

public class InvoiceDetailView extends JPanel implements IInvoiceView
{
    private InvoiceController controller;

    private JTextField invoiceIDField;
    private JTextField ownerNameField;
    private JTextField petNameField;
    private JTextField invoiceDateField;
    private JTextField invoiceAmtField;
    private JComboBox<String> invoiceStatusField;
    private JComboBox<Integer> examSelection;
    private String[] statusOptions;

    private JButton saveButton;
    private JButton closeButton;

    public InvoiceDetailView()
    {
        this.controller = InvoiceController.getInstance();
        controller.registerView(this);
        statusOptions = controller.getStatusOptions();

        createUI();
        createActionListeners();
    }
    
    public void refresh()
    {
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

        if(invoice != null)
        {
            examSelection.setSelectedItem(invoice.getExamID());
            invoiceIDField.setText(Integer.toString(invoice.getInvoiceID()));
            invoiceDateField.setText(invoice.getInvoiceDate().toString());

            Client client = controller.getClient(invoice.getClientID());
            ownerNameField.setText(client.getName());
            Exam exam = controller.getExam(invoice.getExamID());
            Pet pet = controller.getPet(exam.getPetID());
            petNameField.setText(pet.getName());

            invoiceAmtField.setText(Double.toString(invoice.getAmtDue()));
            invoiceStatusField.setSelectedItem(invoice.getStatusString());
        }
    }

    private void createActionListeners()
    {
        saveButton.addActionListener(e -> {
            updateInvoice();
        });

        closeButton.addActionListener(e -> {
            controller.showInvoiceList();
        });

        examSelection.addActionListener(e -> {
            try
            {
                int examID = (int)examSelection.getSelectedItem();
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

    private void updateInvoice()
    {
        int examID = (int)examSelection.getSelectedItem();
        String amtDue = invoiceAmtField.getText();
        String status = (String) invoiceStatusField.getSelectedItem();
        String invoiceDate = invoiceDateField.getText();

        controller.updateInvoice(controller.getCurrentInvoiceID(), examID, status, invoiceDate, amtDue);
    }

    private void createUI()
    {
        setLayout(new BorderLayout());
    
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        JPanel invoiceFieldPanel = createInvoiceFieldPanel();
        add(invoiceFieldPanel, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel()
    {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

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

    private JPanel createInvoiceFieldPanel()
    {
        JPanel invoiceDataFields = new JPanel();
        invoiceDataFields.setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel invoiceIDPanel = new JPanel();
        invoiceIDPanel.setLayout(new BoxLayout(invoiceIDPanel, BoxLayout.Y_AXIS));
        JLabel invoiceIDLabel = new JLabel("Invoice #");
        invoiceIDField = new JTextField(10);
        invoiceIDField.setEditable(false);
        invoiceIDPanel.add(invoiceIDLabel);
        invoiceIDPanel.add(invoiceIDField);

        JPanel ownerNamePanel = new JPanel();
        ownerNamePanel.setLayout(new BoxLayout(ownerNamePanel, BoxLayout.Y_AXIS));
        JLabel ownerNameLabel = new JLabel("Owner Name");
        ownerNameField = new JTextField(10);
        ownerNameField.setEditable(false);
        ownerNamePanel.add(ownerNameLabel);
        ownerNamePanel.add(ownerNameField);
    
        JPanel petNamePanel = new JPanel();
        petNamePanel.setLayout(new BoxLayout(petNamePanel, BoxLayout.Y_AXIS));
        JLabel petNameLabel = new JLabel("Pet Name");
        petNameField = new JTextField(10);
        petNameField.setEditable(false);
        petNamePanel.add(petNameLabel);
        petNamePanel.add(petNameField);

        JPanel invoiceDatePanel = new JPanel();
        invoiceDatePanel.setLayout(new BoxLayout(invoiceDatePanel, BoxLayout.Y_AXIS));
        JLabel invoiceDateLabel = new JLabel("Invoice Date");
        invoiceDateField = new JTextField(10);
        invoiceDateField.setEditable(true);
        invoiceDatePanel.add(invoiceDateLabel);
        invoiceDatePanel.add(invoiceDateField);

        JPanel invoiceAmtPanel = new JPanel();
        invoiceAmtPanel.setLayout(new BoxLayout(invoiceAmtPanel, BoxLayout.Y_AXIS));
        JLabel invoiceAmtLabel = new JLabel("Amount Due");
        invoiceAmtField = new JTextField(10);
        invoiceAmtField.setEditable(true);
        invoiceAmtPanel.add(invoiceAmtLabel);
        invoiceAmtPanel.add(invoiceAmtField);

        JPanel invoiceStatusPanel = new JPanel();
        invoiceStatusPanel.setLayout(new BoxLayout(invoiceStatusPanel, BoxLayout.Y_AXIS));

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