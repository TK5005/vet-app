package view.pet;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import control.IClientView;
import control.ClientController;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Invoice;
import java.awt.Color;

public class PetInvoiceTable extends JPanel implements IClientView
{
    private ClientController clientController;
    private DefaultTableModel tableModel;

    public PetInvoiceTable()
    {
        clientController = ClientController.getInstance();
        clientController.registerView(this);
        createUI();
    }

    public void createUI()
    {
        JPanel invoiceTable = createInvoiceTable();
        this.add(invoiceTable, BorderLayout.CENTER);
    }

    private JPanel createInvoiceTable()
    {
        JPanel invoicePanel = new JPanel();
        invoicePanel.setLayout(new BorderLayout());

        JLabel invoiceLabel = new JLabel("Invoices");

        // Create the table with two columns
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make the action cell editable
                return column == 1;
            }
        };

        tableModel.addColumn("Exam");
        tableModel.addColumn("Amount Due");

        JTable invoiceTable = new JTable(tableModel);
        invoiceTable.setRowHeight(50);
        invoiceTable.getTableHeader().setOpaque(false);
        invoiceTable.getTableHeader().setBackground(new Color(173, 216, 230));

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(invoiceTable);

        invoicePanel.add(invoiceLabel, BorderLayout.NORTH);
        invoicePanel.add(scrollPane, BorderLayout.CENTER);

        return invoicePanel;
    }

    public void refresh()
    {
        // Clear the table
        tableModel.setRowCount(0);

        // Get the pets data from ClientController
        Invoice[] invoices = clientController.getInvoices(clientController.getCurrentPetID());

        // Populate the table with pet data
        for (Invoice invoice : invoices) {
            Object[] rowData = {invoice.getExamID(), invoice.getAmtDue()};
            tableModel.addRow(rowData);
        }
    }
}