package view.pet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import control.ClientController;
import control.IClientView;

public class PetAppointmentsTable extends JPanel implements IClientView {
    private ClientController clientController;
    private DefaultTableModel tableModel;

    public PetAppointmentsTable() {
        clientController = ClientController.getInstance();
        clientController.registerView(this);
        createUI();
    }

    public void createUI() {
        setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());

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

        JTable appointmentTable = new JTable(tableModel);
        appointmentTable.setBackground(Color.WHITE);
        appointmentTable.setRowHeight(50);
        appointmentTable.getTableHeader().setOpaque(false);
        appointmentTable.getTableHeader().setBackground(new Color(173, 216, 230));

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(appointmentTable);
        scrollPane.setBackground(Color.WHITE);
        JLabel appointmentLabel = new JLabel("Appointments");
        this.add(appointmentLabel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void refresh() {
        // Clear the table
        tableModel.setRowCount(0);

        Object[][] tableData = clientController.getPetAppointmentData();

        for (Object[] rowData : tableData) {
            tableModel.addRow(rowData);
        }
    }
}