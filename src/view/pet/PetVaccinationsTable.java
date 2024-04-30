package view.pet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import control.ClientController;
import control.IClientView;
import model.Treatment;
import model.Vaccination;

public class PetVaccinationsTable extends JPanel implements IClientView {
    private ClientController clientController;
    private DefaultTableModel tableModel;

    public PetVaccinationsTable() {
        clientController = ClientController.getInstance();
        clientController.registerView(this);
        createUI();
    }

    public void createUI() {
        setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());
        JPanel vaccinationTable = createVaccinationTable();
        this.add(vaccinationTable, BorderLayout.CENTER);
    }

    private JPanel createVaccinationTable() {
        JPanel vaccinationPanel = new JPanel();
        vaccinationPanel.setBackground(Color.WHITE);
        vaccinationPanel.setLayout(new BorderLayout());

        // Create the table with two columns
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make the action cell editable
                return column == 2;
            }
        };

        tableModel.addColumn("Name");
        tableModel.addColumn("Date");

        JTable vaccinationTable = new JTable(tableModel);
        vaccinationTable.setBackground(Color.WHITE);
        vaccinationTable.setRowHeight(50);
        vaccinationTable.getTableHeader().setOpaque(false);
        vaccinationTable.getTableHeader().setBackground(new Color(173, 216, 230));

        // Custom renderer for the pet column
        vaccinationTable.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(clientController.getInventoryNameByID(Treatment.class.cast(value).getMedicationID()));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            }
        });

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(vaccinationTable);
        scrollPane.setBackground(Color.WHITE);
        vaccinationPanel.add(scrollPane, BorderLayout.CENTER);

        return vaccinationPanel;
    }

    public void refresh() {
        // Clear the table
        tableModel.setRowCount(0);

        // Get the pets data from ClientController
        Treatment[] vaccinations = clientController.getVaccinationsFromPetID(clientController.getCurrentPetID());

        // Populate the table with pet data
        for (Treatment vaccination : vaccinations) {
            tableModel.addRow(new Object[] { vaccination, vaccination.getStartDate()});
        }
    }
}