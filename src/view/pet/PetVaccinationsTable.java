package view.pet;

import javax.swing.JLabel;
import javax.swing.JPanel;
import control.IClientView;
import control.ClientController;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;

public class PetVaccinationsTable extends JPanel implements IClientView
{
    private ClientController clientController;
    private DefaultTableModel tableModel;

    public PetVaccinationsTable()
    {
        clientController = ClientController.getInstance();
        clientController.registerView(this);
        createUI();
    }

    public void createUI()
    {
        JPanel vaccinationTable = createVaccinationTable();
        this.add(vaccinationTable, BorderLayout.CENTER);
    }

    private JPanel createVaccinationTable()
    {
        JPanel vaccinationPanel = new JPanel();
        vaccinationPanel.setLayout(new BorderLayout());

        JLabel vaccinationLabel = new JLabel("Vaccinations");

        // Create the table with two columns
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make the action cell editable
                return column == 1;
            }
        };

        tableModel.addColumn("Date");
        tableModel.addColumn("Description");

        JTable vaccinationTable = new JTable(tableModel);
        vaccinationTable.setRowHeight(50);
        vaccinationTable.getTableHeader().setOpaque(false);
        vaccinationTable.getTableHeader().setBackground(new Color(173, 216, 230));

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(vaccinationTable);

        vaccinationPanel.add(vaccinationLabel, BorderLayout.NORTH);
        vaccinationPanel.add(scrollPane, BorderLayout.CENTER);

        return vaccinationPanel;
    }

    public void refresh()
    {
        // Clear the table
        tableModel.setRowCount(0);

        /*
        // Get the pets data from ClientController
        Vaccination[] vaccinations = clientController.getVaccinations();

        // Populate the table with pet data
        for (Vaccination vaccination : vaccinations) {
            Object[] rowData = {"",""};
            tableModel.addRow(rowData);
        }
        */
    }
}