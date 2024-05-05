package view.invoice;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import com.github.lgooddatepicker.components.DatePicker;

import control.InvoiceController;
import model.Invoice;

public class InvoiceInfo extends JPanel{
    InvoiceController controller;
    private int invoiceID;

    JButton saveButton;

    JTextField invoiceIDField;
    JComboBox<Integer> examIDField;
    JTextField clientNameField;
    JFormattedTextField amtDueField;
    JComboBox<String> statusField;
    DatePicker invoiceDateField;

    Invoice invoice;

    NumberFormat integerFormat = NumberFormat.getIntegerInstance();
    NumberFormatter numberFormatter = new NumberFormatter(integerFormat) {
        @Override
        public Object stringToValue(String string) throws ParseException {
            Number number = (Number) super.stringToValue(string);
            return number.intValue(); // Always return an Integer
        }
    };

    public InvoiceInfo(int invoiceID)
    {
        this.invoiceID = invoiceID;
        controller = InvoiceController.getInstance();
        invoice = controller.getInvoice(invoiceID);
        createUI();
        creatEventListeners();
    }

    private void loadData()
    {
        invoiceIDField.setText(Integer.toString(invoiceID));
        examIDField.removeAllItems();
        statusField.removeAllItems();

        int[] examIDs = controller.getExamIDs();
        for (int id : examIDs)
        {
            examIDField.addItem(id);
        }

        clientNameField.setText(controller.getClientName(invoice.getExamID()));

        String[] statusOptions = controller.getStatusOptions();
        for (String status : statusOptions)
        {
            statusField.addItem(status);
        }

        examIDField.setSelectedItem(invoice.getExamID());
        amtDueField.setValue(invoice.getAmtDue());
        statusField.setSelectedItem(invoice.getStatus().toString());
        invoiceDateField.setDate(invoice.getInvoiceDate());
    }

    private void saveItem()
    {
        int id = Integer.parseInt(invoiceIDField.getText());
        int examID = (Integer) examIDField.getSelectedItem();
        float amtDue = 0.0f;
        try{
            amtDue = NumberFormat.getCurrencyInstance().parse(amtDueField.getText()).floatValue();
        } catch(ParseException e) {
            e.printStackTrace();
        }
        String status = (String) statusField.getSelectedItem();
        LocalDate invoiceDate = invoiceDateField.getDate();
        controller.updateInvoice(id, examID, status, invoiceDate, amtDue);
    }

    private void createUI()
    {
        this.setLayout(new BorderLayout());
        saveButton = new JButton("Save");
        add(createInvoiceDetails(), BorderLayout.CENTER);
        add(saveButton, BorderLayout.SOUTH);
        loadData();
    }

    private void creatEventListeners()
    {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveItem();
            }
        });

        examIDField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientNameField.setText(controller.getClientName((Integer) examIDField.getSelectedItem()));
            }
        });
    }

    private JPanel createInvoiceDetails()
    {
        JPanel invoiceDetails = new JPanel();
        invoiceDetails.setLayout(new BoxLayout(invoiceDetails, BoxLayout.Y_AXIS));
        invoiceDetails.setBackground(Color.WHITE);

        JPanel invoiceIDPanel = new JPanel();
        invoiceIDPanel.setLayout(new BoxLayout(invoiceIDPanel, BoxLayout.Y_AXIS));
        invoiceIDPanel.setBackground(Color.WHITE);
        JLabel idLabel = new JLabel("Invoice ID");
        invoiceIDField = new JTextField(10);
        invoiceIDField.setEditable(false);
        invoiceIDPanel.add(idLabel);
        invoiceIDPanel.add(invoiceIDField);

        JPanel examIDPanel = new JPanel();
        examIDPanel.setLayout(new BoxLayout(examIDPanel, BoxLayout.Y_AXIS));
        examIDPanel.setBackground(Color.WHITE);
        JLabel examIDLabel = new JLabel("Exam ID");
        examIDField = new JComboBox<Integer>();
        examIDPanel.add(examIDLabel);
        examIDPanel.add(examIDField);

        JPanel clientNamePanel = new JPanel();
        clientNamePanel.setLayout(new BoxLayout(clientNamePanel, BoxLayout.Y_AXIS));
        clientNamePanel.setBackground(Color.WHITE);
        JLabel clientNameLabel = new JLabel("Client Name");
        clientNameField = new JTextField(10);
        clientNameField.setEditable(false);
        clientNamePanel.add(clientNameLabel);
        clientNamePanel.add(clientNameField);

        JPanel amtDuePanel = new JPanel();
        amtDuePanel.setLayout(new BoxLayout(amtDuePanel, BoxLayout.Y_AXIS));
        amtDuePanel.setBackground(Color.WHITE);
        JLabel amtDueLabel = new JLabel("Amount Due");
        amtDueField = new JFormattedTextField(new NumberFormatter(NumberFormat.getCurrencyInstance()));
        amtDuePanel.add(amtDueLabel);
        amtDuePanel.add(amtDueField);

        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
        statusPanel.setBackground(Color.WHITE);
        JLabel statusLabel = new JLabel("Status");
        statusField = new JComboBox<String>();
        statusPanel.add(statusLabel);
        statusPanel.add(statusField);

        JPanel invoiceDatePanel = new JPanel();
        invoiceDatePanel.setLayout(new BoxLayout(invoiceDatePanel, BoxLayout.Y_AXIS));
        invoiceDatePanel.setBackground(Color.WHITE);
        JLabel invoiceDateLabel = new JLabel("Invoice Date");
        invoiceDateField = new DatePicker();
        invoiceDatePanel.add(invoiceDateLabel);
        invoiceDatePanel.add(invoiceDateField);

        invoiceDetails.add(invoiceIDPanel);
        invoiceDetails.add(examIDPanel);
        invoiceDetails.add(clientNamePanel);
        invoiceDetails.add(amtDuePanel);
        invoiceDetails.add(statusPanel);
        invoiceDetails.add(invoiceDatePanel);

        return invoiceDetails;
    }
}
